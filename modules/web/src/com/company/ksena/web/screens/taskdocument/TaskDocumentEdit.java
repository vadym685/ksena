package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.cleaning_map.Room;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.inventory.InventoryWrapper;
import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.task.*;

import com.company.ksena.entity.template.Template;
import com.company.ksena.web.screens.inventory.AvaibleInventoryBrowse;
import com.company.ksena.web.screens.room.RoomBrowse;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.Page;
import com.vaadin.v7.ui.AbstractSelect;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@UiController("ksena_TaskDocument.edit")
@UiDescriptor("task-document-edit.xml")
@EditedEntityContainer("taskDocumentDc")
@LoadDataBeforeShow
public class TaskDocumentEdit extends StandardEditor<TaskDocument> {

    @Inject
    private DateField<LocalDate> dateOfCompletionField;
    @Inject
    private TextField<Double> costPerHourField;
    @Inject
    private LookupField<TypeOfPeriodicity> typeOfPeriodicityField;
    @Inject
    private TextField<Integer> periodicityField;
    @Inject
    private GroupBoxLayout cleaningDayBox;
    @Inject
    private CollectionPropertyContainer<DayInterval> cleaningDayDc;
    @Inject
    private Table<DayInterval> cleaningDayTable;
    @Inject
    private TextField<Double> fullCostField;

    @Inject
    private Metadata metadata;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionPropertyContainer<InventoryWrapper> inventoryDc;
    @Inject
    private CollectionPropertyContainer<PositionWrapper> cleaningMapDc;
    @Inject
    private Table<PositionWrapper> cleaningMapTable;
    @Inject
    private Notifications notifications;
    @Inject
    private InstanceContainer<TaskDocument> taskDocumentDc;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Table<InventoryWrapper> inventoryTable;
    @Inject
    private CollectionLoader<Point> pointsForCompanyLc;
    @Inject
    private LookupPickerField<Point> pointField;
    @Inject
    private Table<CleaningPosition> possitionTable;
    @Inject
    private SplitPanel mainSplit;
    @Inject
    private Button addPosition;
    @Inject
    private Button ok;
    @Inject
    private Dialogs dialogs;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Button cleaningMapPositionUp;
    @Inject
    private Button cleaningMapPositionDown;


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

    @Subscribe("taskTypeField")
    public void onTaskTypeFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getTaskType() == TaskType.fromId("ONE TIME")) {
            typeOfPeriodicityField.setVisible(false);
//            dateOfCompletionField.setVisible(true);
            typeOfPeriodicityField.clear();
        } else if (this.getEditedEntity().getTaskType() == TaskType.fromId("REPEAT")) {
            typeOfPeriodicityField.setVisible(true);
//            dateOfCompletionField.setVisible(false);
//            dateOfCompletionField.clear();
        } else {
            typeOfPeriodicityField.setVisible(false);
//            dateOfCompletionField.setVisible(false);
//            dateOfCompletionField.clear();
            typeOfPeriodicityField.clear();
        }
    }

    @Subscribe("typeOfCostFormationField")
    public void onTypeOfCostFormationFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {

        TypeOfCostFormation type = this.getEditedEntity().getTypeOfCostFormation();

        if (type.equals(TypeOfCostFormation.FOR_TIME)) {
            costPerHourField.setVisible(true);
            fullCostField.setVisible(false);
        } else if (type.equals(TypeOfCostFormation.FOR_CLEANING_MAP)) {
            costPerHourField.setVisible(false);
            fullCostField.setVisible(false);
            costPerHourField.clear();
        } else if (type.equals(TypeOfCostFormation.FIXED_PRICE)) {
            costPerHourField.setVisible(false);
            fullCostField.setVisible(true);
            costPerHourField.clear();
        } else {
            costPerHourField.setVisible(false);
            fullCostField.setVisible(false);
            costPerHourField.clear();
        }

    }

    @Subscribe("typeOfPeriodicityField")
    public void onTypeOfPeriodicityFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getTypeOfPeriodicity() == TypeOfPeriodicity.fromId("PERIOD")) {
            periodicityField.setVisible(false);
            periodicityField.clear();
            cleaningDayBox.setVisible(true);

        } else if (this.getEditedEntity().getTypeOfPeriodicity() == TypeOfPeriodicity.fromId("PERIODICITY")) {
            periodicityField.setVisible(true);
            cleaningDayBox.setVisible(false);

        } else {
            periodicityField.setVisible(false);
            periodicityField.clear();
            cleaningDayBox.setVisible(false);
            this.cleaningDayDc.getMutableItems().clear();

        }

    }

    @Subscribe(id = "inventoryDc", target = Target.DATA_CONTAINER)
    public void onInventoryDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<InventoryWrapper> event) {
        if (event.getProperty().equals("quantityInventory")) {

            if (event.getItem().getInventory().getSerialNumber() != null) {
                notifications.create().withDescription("You cannot set the quantity to unique inventory").show();
                event.getItem().setQuantityInventory(1);
            }
        }
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
                    if (targetId != null) {
                        Room room = (Room) targetId.getValue();

                        removeAllNullPositions(room);

                        CleaningPosition cleaningPosition = null;
                        try {
                            cleaningPosition = dataManager.load(CleaningPosition.class).id(itemId).one();
                        } catch (Exception ignored) {
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

                            positionWrapper.setTaskDocuments(taskDocumentDc.getItem());

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
                                                        wrapper.setTaskDocuments(super.getEditedEntity()));
                                                cleaningMapDc.getMutableItems().addAll(template.getCleaningMap());
                                            });
                                        }),
                                        new DialogAction(DialogAction.Type.NO).withHandler(noEvent -> {}
//                                                e.forEach(template -> {
//                                                    template.getCleaningMap().forEach(wrapper ->
//                                                            wrapper.setTaskDocuments(super.getEditedEntity()));
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
                                clone.setTaskDocuments(this.getEditedEntity());
                                cleaningMapDc.getMutableItems().add(clone);
                            });
                        });
                    }
                })
                .withOpenMode(OpenMode.DIALOG)
                .show();
    }

    @Subscribe("excludePosition")
    public void onExcludePositionClick(Button.ClickEvent event) {
        renumberAllPositions(cleaningMapTable.getSingleSelected());
        cleaningMapDc.getMutableItems().get(cleaningMapDc.getItems().indexOf(cleaningMapTable.getSingleSelected())).setTemplate(null);
        cleaningMapDc.getMutableItems().remove(cleaningMapTable.getSingleSelected());
    }

    private void renumberAllPositions(PositionWrapper positionWrapper) {
        List<PositionWrapper> wrappers = cleaningMapDc.getItems()
                .stream()
                .filter(wrapper -> wrapper.getRoomName().equals(positionWrapper.getRoomName()))
                .collect(Collectors.toList());

        for (int i = wrappers.indexOf(positionWrapper); i < wrappers.size(); i++) {
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
                        inventoryWrapper.setTaskDocument(this.getEditedEntity());


                        inventoryDc.getMutableItems().add(inventoryWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();
        selectInventory.show();
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
        if (selected != null) {
            PositionWrapper wrapperToDown = cleaningMapDc.getItems()
                    .stream()
                    .filter(wrapper -> wrapper.getRoomName().equals(selected.getRoomName()))
                    .collect(Collectors.toList())
                    .get(selected.getPriorityCleaningPosition());
            throwUpper(wrapperToDown);
        }
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
                        positionWrapper.setTaskDocuments(this.getEditedEntity());

                        cleaningMapDc.getMutableItems().add(positionWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();
        selectRoom.show();
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
