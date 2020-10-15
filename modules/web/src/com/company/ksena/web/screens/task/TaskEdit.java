package com.company.ksena.web.screens.task;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.cleaning_map.Room;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.inventory.InventoryWrapper;
import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.task.TaskDocument;
import com.company.ksena.entity.task.TaskStatus;
import com.company.ksena.entity.template.Template;
import com.company.ksena.web.screens.cleaningposition.CleaningPositionBrowse;
import com.company.ksena.web.screens.inventory.AvaibleInventoryBrowse;
import com.company.ksena.web.screens.inventory.InventoryBrowse;
import com.company.ksena.web.screens.room.RoomBrowse;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.builders.AfterScreenCloseEvent;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.Task;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.Page;
import com.vaadin.v7.ui.AbstractSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.*;
import javax.validation.constraints.Null;
import java.util.*;
import java.util.stream.Collectors;

@UiController("ksena_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
@LoadDataBeforeShow
public class TaskEdit extends StandardEditor<Task> {
    @Inject
    private LookupField<TaskStatus> taskStatusField;
    @Inject
    private Notifications notifications;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<InventoryWrapper> inventoryDc;
    @Inject
    private CollectionPropertyContainer<PositionWrapper> cleaningMapDc;
    @Inject
    private Table<PositionWrapper> cleaningMapTable;
    @Inject
    private DataManager dataManager;

    private final Logger LOG = LoggerFactory.getLogger(TaskEdit.class);

    @Inject
    private PickerField<Company> companyField;
    @Inject
    private CollectionLoader<Point> pointsForCompanyLc;
    @Inject
    private LookupPickerField<Point> pointField;
    @Inject
    private Dialogs dialogs;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Button cleaningMapPositionUp;
    @Inject
    private Button cleaningMapPositionDown;
    @Inject
    private Button addPosition;
    @Inject
    private Button ok;
    @Inject
    private SplitPanel mainSplit;
    @Inject
    private Table<CleaningPosition> possitionTable;
    @Inject
    private InstanceContainer<Task> taskDc;

    @Subscribe
    public void onInit(InitEvent event) {
        possitionTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table ->
                table.setDragMode(com.vaadin.v7.ui.Table.TableDragMode.MULTIROW)
        );

        cleaningMapTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table -> {
            table.setDragMode(com.vaadin.v7.ui.Table.TableDragMode.MULTIROW);
            table.setDropHandler(new DropHandler() {
                @Override
                public void drop(DragAndDropEvent event) {

                    UUID itemId = (UUID) event.getTransferable().getData("itemId");
                    GroupInfo<Room> targetId = (GroupInfo) ((AbstractSelect.AbstractSelectTargetDetails) event.getTargetDetails()).getItemIdOver();
                    if(targetId != null) {
                        Room room = (Room) targetId.getValue();

                        removeAllNullPositions(room);

                        CleaningPosition cleaningPosition = null;
                        try {
                            cleaningPosition = dataManager.load(CleaningPosition.class).id(itemId).one();
                        } catch (Exception e) {
                            LOG.error("");
                        }

                        if (cleaningPosition != null) {
                            PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                            positionWrapper.setPosition(cleaningPosition);
                            positionWrapper.setRoomName(room);
                            positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                    .stream()
                                    .filter(wrapper -> wrapper.getRoomName().equals(room))
                                    .count() + 1
                            );

                            positionWrapper.setTask(taskDc.getItem());

                            cleaningMapDc.getMutableItems().add(positionWrapper);
                        } else {
                            PositionWrapper positionWrapper = cleaningMapDc.getItemOrNull(itemId);

                            if (positionWrapper != null && !positionWrapper.getRoomName().equals(room)) {
                                renumberAllPositions(positionWrapper);

                                positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                        .stream()
                                        .filter(wrapper -> wrapper.getRoomName().equals(room))
                                        .count() + 1
                                );
                                positionWrapper.setRoomName(room);

                                cleaningMapDc.getMutableItems().remove(positionWrapper);
                                cleaningMapDc.getMutableItems().add(positionWrapper);
                            }
                        }
                    }
                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }
            });
        });

    }

    @Subscribe("addTemplate")
    public void onAddTemplateClick(Button.ClickEvent event) {
        screenBuilders.lookup(Template.class, this)
                .withSelectHandler(e -> {
                    if (cleaningMapDc.getItems().size() > 0) {
                        dialogs.createOptionDialog()
                                .withCaption(messageBundle.getMessage("replace"))
                                .withMessage(messageBundle.getMessage("doYouWantToReplace"))
                                .withActions(
                                        new DialogAction(DialogAction.Type.YES).withHandler(yesEvent -> {
                                            cleaningMapDc.getMutableItems().forEach(wrapper -> dataManager.remove(wrapper));
                                            cleaningMapDc.getMutableItems().clear();
                                            e.forEach(template -> {
                                                template.getCleaningMap().forEach(wrapper ->
                                                        wrapper.setTask(super.getEditedEntity()));
                                                cleaningMapDc.getMutableItems().addAll(template.getCleaningMap());
                                            });
                                        }),
                                        new DialogAction(DialogAction.Type.NO).withHandler(noEvent -> {}
//                                                e.forEach(template -> {
//                                                    template.getCleaningMap().forEach(wrapper ->
//                                                            wrapper.setTask(super.getEditedEntity()));
//                                                    cleaningMapDc.getMutableItems().addAll(template.getCleaningMap());
//                                                })
                                        )
                                ).show();

                    } else {
                        e.forEach(template -> {
                            template.getCleaningMap().forEach(wrapper -> {
                                PositionWrapper clone = metadata.getTools().copy(wrapper);
                                clone.setId(UUID.randomUUID());
                                clone.setTemplate(null);
                                clone.setTask(this.getEditedEntity());
                                cleaningMapDc.getMutableItems().add(clone);
                            });
                        });
                    }
                })
                .withOpenMode(OpenMode.DIALOG)
                .show();
    }

    private void renumberAllPositions(PositionWrapper positionWrapper) {
        List<PositionWrapper> wrappers = cleaningMapDc.getItems()
                .stream()
                .filter(wrapper -> wrapper.getRoomName().equals(positionWrapper.getRoomName()))
                .collect(Collectors.toList());

        for (int i = wrappers.indexOf(positionWrapper); i < wrappers.size(); i++){
            wrappers.get(i).setPriorityCleaningPosition(i);
        }
    }

    private void removeAllNullPositions(Room room) {
        List<PositionWrapper> wrappers = cleaningMapDc.getItems()
                .stream()
                .filter(wrapper -> wrapper.getPosition() == null && wrapper.getRoomName().equals(room))
                .collect(Collectors.toList());

        cleaningMapDc.getMutableItems().removeAll(wrappers);

    }

    @Subscribe("companyField")
    public void onCompanyFieldValueChange(HasValue.ValueChangeEvent<Company> event) {
        if (event.getValue() != null) {
            pointField.clear();
            pointsForCompanyLc.setParameter("company", event.getValue());
        } else {
            pointField.clear();
            pointsForCompanyLc.removeParameter("company");
        }
        pointsForCompanyLc.load();
    }

    @Subscribe("taskDocumentField")
    public void onTaskDocumentFieldValueChange(HasValue.ValueChangeEvent<TaskDocument> event) {
        TaskDocument document = event.getValue();
        if (document != null && event.isUserOriginated() == true) {

            if (getEditedEntity().getInventoryMap() != null || getEditedEntity().getCleaningMap() != null) {
                dialogs.createOptionDialog()
                        .withCaption("")
                        .withMessage(messageBundle.getMessage("youSetNewTaskDoc"))
                        .withActions(
                                new DialogAction(DialogAction.Type.YES).withHandler(e -> {
                                    getEditedEntity().setPoint(null);
                                    getEditedEntity().setCompany(null);
                                    getEditedEntity().setDelay(null);
                                    getEditedEntity().setSalaryElementary(null);
                                    getEditedEntity().setSalaryHigh(null);
                                    getEditedEntity().setSalaryMedium(null);
                                    getEditedEntity().setAddPriseExpendableMaterial(null);
                                    getEditedEntity().setEmployees(null);

                                    if (getEditedEntity().getCleaningMap() != null) {
                                        List<PositionWrapper> clearCleaningMapList = getEditedEntity().getCleaningMap();

                                        for (PositionWrapper clearPositionWrapper : clearCleaningMapList) {
                                            dataManager.remove(clearPositionWrapper);

                                        }
                                    }
                                    if (getEditedEntity().getInventoryMap() != null) {
                                        List<InventoryWrapper> clearInventoryWrapperList = getEditedEntity().getInventoryMap();

                                        for (InventoryWrapper clearinventoryWrapper : clearInventoryWrapperList) {
                                            dataManager.remove(clearinventoryWrapper);
                                        }
                                    }

                                    getEditedEntity().setInventoryMap(null);
                                    getEditedEntity().setCleaningMap(null);

                                    getEditedEntity().setPoint(document.getPoint());
                                    getEditedEntity().setCompany(document.getCompany());
                                    getEditedEntity().setDelay(document.getDelay());
                                    getEditedEntity().setSalaryElementary(document.getSalaryElementary());
                                    getEditedEntity().setSalaryHigh(document.getSalaryHigh());
                                    getEditedEntity().setSalaryMedium(document.getSalaryMedium());
                                    getEditedEntity().setAddPriseExpendableMaterial(document.getAddPriseExpendableMaterial());
                                    getEditedEntity().setEmployees(document.getEmployeesMap());

                                    List<PositionWrapper> cleaningMapList = document.getCleaningMap();
                                    List<InventoryWrapper> inventoryWrapperList = document.getInventoryMap();

                                    for (PositionWrapper positionWrapper : cleaningMapList) {
                                        PositionWrapper newPositionWrapper = metadata.create(PositionWrapper.class);
                                        newPositionWrapper.setPosition(positionWrapper.getPosition());
                                        newPositionWrapper.setRoomName(positionWrapper.getRoomName());
                                        newPositionWrapper.setNoteCleaningPosition(positionWrapper.getNoteCleaningPosition());
                                        newPositionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
                                        newPositionWrapper.setTask(this.getEditedEntity());
                                        newPositionWrapper.setTaskDocuments(null);

                                        cleaningMapDc.getMutableItems().add(newPositionWrapper);
                                    }

                                    for (InventoryWrapper inventoryWrapper : inventoryWrapperList) {
                                        InventoryWrapper newInventoryWrapper = metadata.create(InventoryWrapper.class);
                                        newInventoryWrapper.setInventory(inventoryWrapper.getInventory());
                                        newInventoryWrapper.setQuantityInventory(1);
                                        newInventoryWrapper.setTask(this.getEditedEntity());
                                        newInventoryWrapper.setTaskDocuments(null);

                                        inventoryDc.getMutableItems().add(newInventoryWrapper);
                                    }
                                }),
                                new DialogAction(DialogAction.Type.NO).withHandler(e -> {

                                })
                        ).show();

            } else {
                getEditedEntity().setPoint(document.getPoint());
                getEditedEntity().setCompany(document.getCompany());
                getEditedEntity().setDelay(document.getDelay());
                getEditedEntity().setSalaryElementary(document.getSalaryElementary());
                getEditedEntity().setSalaryHigh(document.getSalaryHigh());
                getEditedEntity().setSalaryMedium(document.getSalaryMedium());
                getEditedEntity().setAddPriseExpendableMaterial(document.getAddPriseExpendableMaterial());
                getEditedEntity().setEmployees(document.getEmployeesMap());

                List<PositionWrapper> cleaningMapList = document.getCleaningMap();
                List<InventoryWrapper> inventoryWrapperList = document.getInventoryMap();

                for (PositionWrapper positionWrapper : cleaningMapList) {
                    PositionWrapper newPositionWrapper = metadata.create(PositionWrapper.class);
                    newPositionWrapper.setPosition(positionWrapper.getPosition());
                    newPositionWrapper.setRoomName(positionWrapper.getRoomName());
                    newPositionWrapper.setNoteCleaningPosition(positionWrapper.getNoteCleaningPosition());
                    newPositionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
                    newPositionWrapper.setTask(this.getEditedEntity());
                    newPositionWrapper.setTaskDocuments(null);

                    cleaningMapDc.getMutableItems().add(newPositionWrapper);
                }

                for (InventoryWrapper inventoryWrapper : inventoryWrapperList) {
                    InventoryWrapper newInventoryWrapper = metadata.create(InventoryWrapper.class);
                    newInventoryWrapper.setInventory(inventoryWrapper.getInventory());
                    newInventoryWrapper.setQuantityInventory(1);
                    newInventoryWrapper.setTask(this.getEditedEntity());
                    newInventoryWrapper.setTaskDocuments(null);

                    inventoryDc.getMutableItems().add(newInventoryWrapper);
                }
            }
        }
    }

    @Subscribe("excludePosition")
    public void onExcludePositionClick(Button.ClickEvent event) {
        try {
            if(cleaningMapTable.getSingleSelected() != null) {
                dataManager.remove(cleaningMapTable.getSingleSelected());
            }
        } catch (Exception e){
            LOG.error("");
        }
        renumberAllPositions(cleaningMapTable.getSingleSelected());
        cleaningMapDc.getMutableItems().remove(cleaningMapTable.getSingleSelected());
    }

    @Subscribe(id = "inventoryDc", target = Target.DATA_CONTAINER)
    public void onInventoryDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<InventoryWrapper> event) {
        if (event.getProperty() == "quantityInventory") {

            if (event.getItem().getInventory().getSerialNumber() != null) {
                notifications.create().withDescription("You cannot set the quantity to unique inventory").show();
                event.getItem().setQuantityInventory(1);
            }
        }
    }

    @Subscribe("addInventory")
    public void onAddInventoryClick(Button.ClickEvent event) {
        AvaibleInventoryBrowse selectInventory = screenBuilders.lookup(Inventory.class, this)
                .withScreenClass(AvaibleInventoryBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {

                    Inventory inventory = e.getScreen().getSelectedInventory();

                    StandardCloseAction closeAction = (StandardCloseAction) e.getCloseAction();


                    if (inventory != null && closeAction.getActionId().equals("select")) {
                        InventoryWrapper inventoryWrapper = metadata.create(InventoryWrapper.class);
                        inventoryWrapper.setInventory(inventory);
                        inventoryWrapper.setQuantityInventory(1);
                        inventoryWrapper.setTask(this.getEditedEntity());


                        inventoryDc.getMutableItems().add(inventoryWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();

        selectInventory.show();
    }

    @Subscribe("addPosition")
    public void onAddPositionClick(Button.ClickEvent event) {
        mainSplit.setSplitPosition(65);
        addPosition.setVisible(false);
        ok.setVisible(true);
    }

    @Subscribe("ok")
    public void onOkClick(Button.ClickEvent event) {
        mainSplit.setSplitPosition(100);
        addPosition.setVisible(true);
        ok.setVisible(false);
    }

    @Subscribe("cleaningMapPositionAddRoom")
    public void onCleaningMapPositionAddRoomClick(Button.ClickEvent event) {
        RoomBrowse selectRoom = screenBuilders.lookup(Room.class, this)
                .withScreenClass(RoomBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    if (((StandardCloseAction) e.getCloseAction()).getActionId().equals("select")) {
                        Room room = e.getScreen().getSelectedRoom();

                        for (PositionWrapper wrapper : cleaningMapDc.getItems()) {
                            if (wrapper.getRoomName().equals(room)) {
                                dialogs.createExceptionDialog()
                                        .withCaption(messageBundle.getMessage("warning"))
                                        .withThrowable(new Throwable())
                                        .withMessage(messageBundle.getMessage("roomAlreadyAdded"))
                                        .show();
                                return;
                            }
                        }

                        PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                        positionWrapper.setPosition(null);
                        positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                .stream()
                                .filter(wrapper -> wrapper.getRoomName().equals(room))
                                .count() + 1);
                        positionWrapper.setRoomName(room);
                        positionWrapper.setTask(this.getEditedEntity());

                        cleaningMapDc.getMutableItems().add(positionWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();
        selectRoom.show();
    }

    @Subscribe("cleaningMapTable")
    public void onCleaningMapTableSelection(Table.SelectionEvent<PositionWrapper> event) {
        setEnabledForButtons();
    }

    private void setEnabledForButtons() {
        PositionWrapper wrapper = cleaningMapTable.getSingleSelected();
        cleaningMapPositionUp.setEnabled(wrapper != null && wrapper.getPriorityCleaningPosition() != 1);

        int lastIndex = (int) cleaningMapDc.getItems()
                .stream()
                .filter(positionWrapper -> {
                    if (wrapper != null) {
                        return positionWrapper.getRoomName().equals(wrapper.getRoomName());
                    }
                    return false;
                })
                .count();
        cleaningMapPositionDown.setEnabled(
                wrapper != null && wrapper.getPriorityCleaningPosition() != lastIndex
        );

        if (wrapper == null) {
            cleaningMapPositionUp.setEnabled(false);
            cleaningMapPositionDown.setEnabled(false);
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        cleaningMapDc.getItems().forEach(wrapper -> {
            event.getDataContext().merge(wrapper);
        });

        inventoryDc.getItems().forEach(wrapper -> {
            event.getDataContext().merge(wrapper);
        });
    }

    @Subscribe("cleaningMapPositionUp")
    public void onCleaningMapPositionUpClick(Button.ClickEvent event) {
        if (cleaningMapTable.getSingleSelected() != null) {
            throwUpper(cleaningMapTable.getSingleSelected());
        }
    }

    private void throwUpper(PositionWrapper wrapperToUp) {
        PositionWrapper wrapperToDown = cleaningMapDc.getMutableItems()
                .stream()
                .filter(positionWrapper -> positionWrapper.getRoomName().equals(wrapperToUp.getRoomName()))
                .collect(Collectors.toList())
                .get(wrapperToUp.getPriorityCleaningPosition() - 2);

        wrapperToDown.setPriorityCleaningPosition(wrapperToDown.getPriorityCleaningPosition() + 1);
        wrapperToUp.setPriorityCleaningPosition(wrapperToUp.getPriorityCleaningPosition() - 1);

        cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
        setEnabledForButtons();
    }

    @Subscribe("cleaningMapPositionDown")
    public void onCleaningMapPositionDownClick(Button.ClickEvent event) {
        PositionWrapper selected = cleaningMapTable.getSingleSelected();
        if(selected != null) {
            PositionWrapper wrapperToDown = cleaningMapDc.getItems()
                    .stream()
                    .filter(wrapper -> wrapper.getRoomName().equals(selected.getRoomName()))
                    .collect(Collectors.toList())
                    .get(selected.getPriorityCleaningPosition());
            throwUpper(wrapperToDown);
        }
    }

    private void injectColorCss(String color, UUID id) {
        Page.Styles styles = Page.getCurrent().getStyles();
        if (color.contains("#")) {
            color = color.replace("#", "");
        }
        styles.add(String.format(
                ".colored-cell-%s-%s{background-color:#%s;}",
                id.toString(), color, color));
    }
}