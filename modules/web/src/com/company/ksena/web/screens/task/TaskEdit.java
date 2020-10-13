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
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.Task;
import com.vaadin.server.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.*;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("ksena_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
@LoadDataBeforeShow
public class TaskEdit extends StandardEditor<Task> {
//    @Inject
//    private LookupField<TaskStatus> taskStatusField;
//    @Inject
//    private Notifications notifications;
//    @Inject
//    private ScreenBuilders screenBuilders;
//    @Inject
//    private Metadata metadata;
//    @Inject
//    private CollectionPropertyContainer<InventoryWrapper> inventoryDc;
//    @Inject
//    private CollectionPropertyContainer<PositionWrapper> cleaningMapDc;
//    @Inject
//    private Table<PositionWrapper> cleaningMapTable;
//    @Inject
//    private DataManager dataManager;
//
//    private final Logger LOG = LoggerFactory.getLogger(TaskEdit.class);
//
//    @Inject
//    private PickerField<Company> companyField;
//    @Inject
//    private CollectionLoader<Point> pointsForCompanyLc;
//    @Inject
//    private LookupPickerField<Point> pointField;
//    @Inject
//    private Dialogs dialogs;
//    @Inject
//    private MessageBundle messageBundle;
//
//    @Subscribe("companyField")
//    public void onCompanyFieldValueChange(HasValue.ValueChangeEvent<Company> event) {
//        if (event.getValue() != null) {
//            pointField.clear();
//            pointsForCompanyLc.setParameter("company", event.getValue());
//        } else {
//            pointField.clear();
//            pointsForCompanyLc.removeParameter("company");
//        }
//        pointsForCompanyLc.load();
//    }
//
//    @Subscribe("taskDocumentField")
//    public void onTaskDocumentFieldValueChange(HasValue.ValueChangeEvent<TaskDocument> event) {
//        TaskDocument document = event.getValue();
//        if (document != null && event.isUserOriginated() == true) {
//
//            if (getEditedEntity().getInventoryMap() != null || getEditedEntity().getCleaningMap() != null) {
//                dialogs.createOptionDialog()
//                        .withCaption("")
//                        .withMessage(messageBundle.getMessage("youSetNewTaskDoc"))
//                        .withActions(
//                                new DialogAction(DialogAction.Type.YES).withHandler(e -> {
//                                    getEditedEntity().setPoint(null);
//                                    getEditedEntity().setCompany(null);
//                                    getEditedEntity().setDelay(null);
//                                    getEditedEntity().setSalaryElementary(null);
//                                    getEditedEntity().setSalaryHigh(null);
//                                    getEditedEntity().setSalaryMedium(null);
//                                    getEditedEntity().setAddPriseExpendableMaterial(null);
//                                    getEditedEntity().setEmployees(null);
//
//                                    if (getEditedEntity().getCleaningMap() != null) {
//                                        List<PositionWrapper> clearCleaningMapList = getEditedEntity().getCleaningMap();
//
//                                        for (PositionWrapper clearPositionWrapper : clearCleaningMapList) {
//                                            dataManager.remove(clearPositionWrapper);
//
//                                        }
//                                    }
//                                    if (getEditedEntity().getInventoryMap() != null) {
//                                        List<InventoryWrapper> clearInventoryWrapperList = getEditedEntity().getInventoryMap();
//
//                                        for (InventoryWrapper clearinventoryWrapper : clearInventoryWrapperList) {
//                                            dataManager.remove(clearinventoryWrapper);
//                                        }
//                                    }
//
//                                    getEditedEntity().setInventoryMap(null);
//                                    getEditedEntity().setCleaningMap(null);
//
//                                    getEditedEntity().setPoint(document.getPoint());
//                                    getEditedEntity().setCompany(document.getCompany());
//                                    getEditedEntity().setDelay(document.getDelay());
//                                    getEditedEntity().setSalaryElementary(document.getSalaryElementary());
//                                    getEditedEntity().setSalaryHigh(document.getSalaryHigh());
//                                    getEditedEntity().setSalaryMedium(document.getSalaryMedium());
//                                    getEditedEntity().setAddPriseExpendableMaterial(document.getAddPriseExpendableMaterial());
//                                    getEditedEntity().setEmployees(document.getEmployeesMap());
//
//                                    List<PositionWrapper> cleaningMapList = document.getCleaningMap();
//                                    List<InventoryWrapper> inventoryWrapperList = document.getInventoryMap();
//
//                                    for (PositionWrapper positionWrapper : cleaningMapList) {
//                                        PositionWrapper newPositionWrapper = metadata.create(PositionWrapper.class);
//                                        newPositionWrapper.setPosition(positionWrapper.getPosition());
//                                        newPositionWrapper.setRoomName(positionWrapper.getRoomName());
//                                        newPositionWrapper.setNoteCleaningPosition(positionWrapper.getNoteCleaningPosition());
//                                        newPositionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
//                                        newPositionWrapper.setTask(this.getEditedEntity());
//                                        newPositionWrapper.setTaskDocuments(null);
//
//                                        cleaningMapDc.getMutableItems().add(newPositionWrapper);
//                                    }
//
//                                    for (InventoryWrapper inventoryWrapper : inventoryWrapperList) {
//                                        InventoryWrapper newInventoryWrapper = metadata.create(InventoryWrapper.class);
//                                        newInventoryWrapper.setInventory(inventoryWrapper.getInventory());
//                                        newInventoryWrapper.setQuantityInventory(1);
//                                        newInventoryWrapper.setTask(this.getEditedEntity());
//                                        newInventoryWrapper.setTaskDocuments(null);
//
//                                        inventoryDc.getMutableItems().add(newInventoryWrapper);
//                                    }
//                                }),
//                                new DialogAction(DialogAction.Type.NO).withHandler(e -> {
//
//                                })
//                        ).show();
//
//            } else {
//                getEditedEntity().setPoint(document.getPoint());
//                getEditedEntity().setCompany(document.getCompany());
//                getEditedEntity().setDelay(document.getDelay());
//                getEditedEntity().setSalaryElementary(document.getSalaryElementary());
//                getEditedEntity().setSalaryHigh(document.getSalaryHigh());
//                getEditedEntity().setSalaryMedium(document.getSalaryMedium());
//                getEditedEntity().setAddPriseExpendableMaterial(document.getAddPriseExpendableMaterial());
//                getEditedEntity().setEmployees(document.getEmployeesMap());
//
//                List<PositionWrapper> cleaningMapList = document.getCleaningMap();
//                List<InventoryWrapper> inventoryWrapperList = document.getInventoryMap();
//
//                for (PositionWrapper positionWrapper : cleaningMapList) {
//                    PositionWrapper newPositionWrapper = metadata.create(PositionWrapper.class);
//                    newPositionWrapper.setPosition(positionWrapper.getPosition());
//                    newPositionWrapper.setRoomName(positionWrapper.getRoomName());
//                    newPositionWrapper.setNoteCleaningPosition(positionWrapper.getNoteCleaningPosition());
//                    newPositionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
//                    newPositionWrapper.setTask(this.getEditedEntity());
//                    newPositionWrapper.setTaskDocuments(null);
//
//                    cleaningMapDc.getMutableItems().add(newPositionWrapper);
//                }
//
//                for (InventoryWrapper inventoryWrapper : inventoryWrapperList) {
//                    InventoryWrapper newInventoryWrapper = metadata.create(InventoryWrapper.class);
//                    newInventoryWrapper.setInventory(inventoryWrapper.getInventory());
//                    newInventoryWrapper.setQuantityInventory(1);
//                    newInventoryWrapper.setTask(this.getEditedEntity());
//                    newInventoryWrapper.setTaskDocuments(null);
//
//                    inventoryDc.getMutableItems().add(newInventoryWrapper);
//                }
//            }
//        }
//    }
//
//    @Subscribe(id = "inventoryDc", target = Target.DATA_CONTAINER)
//    public void onInventoryDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<InventoryWrapper> event) {
//        if (event.getProperty() == "quantityInventory") {
//
//            if (event.getItem().getInventory().getSerialNumber() != null) {
//                notifications.create().withDescription("You cannot set the quantity to unique inventory").show();
//                event.getItem().setQuantityInventory(1);
//            }
//        }
//    }
//
//    @Subscribe("addInventory")
//    public void onAddInventoryClick(Button.ClickEvent event) {
//        AvaibleInventoryBrowse selectInventory = screenBuilders.lookup(Inventory.class, this)
//                .withScreenClass(AvaibleInventoryBrowse.class)
//                .withOpenMode(OpenMode.DIALOG)
//                .withAfterCloseListener(e -> {
//
//                    Inventory inventory = e.getScreen().getSelectedInventory();
//
//                    StandardCloseAction closeAction = (StandardCloseAction) e.getCloseAction();
//
//
//                    if (inventory != null && closeAction.getActionId().equals("select")) {
//                        InventoryWrapper inventoryWrapper = metadata.create(InventoryWrapper.class);
//                        inventoryWrapper.setInventory(inventory);
//                        inventoryWrapper.setQuantityInventory(1);
//                        inventoryWrapper.setTask(this.getEditedEntity());
//
//
//                        inventoryDc.getMutableItems().add(inventoryWrapper);
//                    }
//                })
//                .withSelectHandler(e -> {
//                })
//                .build();
//
//        selectInventory.show();
//    }
//
//
//    @Subscribe("addPosition")
//    public void onAddPositionClick(Button.ClickEvent event) {
//
//        CleaningPositionBrowse selectCleaningPosition = screenBuilders.lookup(CleaningPosition.class, this)
//                .withScreenClass(CleaningPositionBrowse.class)
//                .withOpenMode(OpenMode.DIALOG)
//                .withAfterCloseListener(e -> {
//
//                    CleaningPosition cleaningPosition = e.getScreen().getSelectedCleaningPosition();
//
//                    StandardCloseAction closeAction = (StandardCloseAction) e.getCloseAction();
//
//                    if (cleaningPosition != null && closeAction.getActionId().equals("select")) {
//
//                        PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
//                        positionWrapper.setPosition(cleaningPosition);
////                        positionWrapper.setRoomName(cleaningPosition.getRoom());
//                        positionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
//                        positionWrapper.setTask(this.getEditedEntity());
//
//
//                        cleaningMapDc.getMutableItems().add(positionWrapper);
//                    }
//                })
//                .withSelectHandler(e -> {
//                })
//                .build();
//        selectCleaningPosition.show();
//    }
//
//    @Subscribe("cleaningMapPositionUp")
//    public void onCleaningMapPositionUpClick(Button.ClickEvent event) {
//        if (cleaningMapTable.getSelected().isEmpty()) {
//            notifications.create().withDescription("SetPosition").show();
//        } else {
//
//            for (PositionWrapper positionWrapper : cleaningMapTable.getSelected()) {
//
//                int nowPriority = positionWrapper.getPriorityCleaningPosition();
//                if (nowPriority != 1) {
//                    int newPriority = cleaningMapDc.getMutableItems().get(nowPriority - 2).getPriorityCleaningPosition();
//
//                    PositionWrapper nowCleaningPosition = Objects.requireNonNull(cleaningMapTable.getItems()).getItem(cleaningMapDc.getMutableItems().get(nowPriority - 2));
//                    positionWrapper.setPriorityCleaningPosition(newPriority);
//                    assert nowCleaningPosition != null;
//                    nowCleaningPosition.setPriorityCleaningPosition(nowPriority);
//                }
//            }
//            cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);
//
//        }
//    }
//
//    @Subscribe("cleaningMapPositionDown")
//    public void onCleaningMapPositionDownClick(Button.ClickEvent event) {
//        if (cleaningMapTable.getSelected().isEmpty()) {
//            notifications.create().withDescription("SetPosition").show();
//        } else {
//
//            for (PositionWrapper positionWrapper : cleaningMapTable.getSelected()) {
//
//                int nowPriority = positionWrapper.getPriorityCleaningPosition();
//                int size = Objects.requireNonNull(cleaningMapTable.getItems()).size();
//                if (nowPriority != size) {
//                    int newPriority = cleaningMapDc.getMutableItems().get(nowPriority).getPriorityCleaningPosition();
//
//                    PositionWrapper nowPositionWrapper = cleaningMapDc.getMutableItems().get(nowPriority);
//
//                    positionWrapper.setPriorityCleaningPosition(newPriority);
//                    nowPositionWrapper.setPriorityCleaningPosition(nowPriority);
//                }
//            }
//            cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);
//        }
//    }
//
//    @Subscribe("cleaningMapPositionAddRoom")
//    public void onCleaningMapPositionAddRoomClick(Button.ClickEvent event) {
//        RoomBrowse selectRoom = screenBuilders.lookup(Room.class, this)
//                .withScreenClass(RoomBrowse.class)
//                .withOpenMode(OpenMode.DIALOG)
//                .withAfterCloseListener(e -> {
//                    Room room = e.getScreen().getSelectedRoom();
//                    if (room != null) {
//                        List newList = room.getCleaningPosition()
//                                .stream()
//                                .filter(cleaningPosition -> cleaningPosition.getStandartPosition() != null && cleaningPosition.getStandartPosition())
//                                .collect(Collectors.toList());
//                        for (Object Element : newList) {
//
//                            PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
//                            positionWrapper.setPosition((CleaningPosition) Element);
////                            positionWrapper.setRoomName(((CleaningPosition) Element).getRoom().getName());
//                            positionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
//                            positionWrapper.setTask(this.getEditedEntity());
//
//                            cleaningMapDc.getMutableItems().add(positionWrapper);
//                        }
//                    }
//                })
//                .withSelectHandler(e -> {
//                })
//                .build();
//        selectRoom.show();
//
//    }
//
//    @Subscribe
//    public void onAfterShow(AfterShowEvent event) {
//
//        taskStatusField.setValue(TaskStatus.CREATE);
//
//        cleaningMapDc.getItems().forEach(positionWrapper -> {
//            Room room = positionWrapper.getPosition().getRoom();
//
//            if (room != null) {
//                Room reloadRoom = dataManager.load(Id.of(room)).one();
//                if (reloadRoom.getColor() != null) {
//                    injectColorCss(reloadRoom.getColor(), positionWrapper.getId());
//                }
//            }
//        });
//
//        cleaningMapTable.setStyleProvider(new GroupTable.StyleProvider<PositionWrapper>() {
//            @Nullable
//            @Override
//            public String getStyleName(PositionWrapper entity, @Nullable String property) {
//                if (property != null && property.equals("position.room") && entity.getPosition().getRoom() != null) {
//
//                    return "colored-cell-" + entity.getId() + "-" + entity.getPosition().getRoom().getColor();
//                }
//                return null;
//            }
//        });
//    }
//
//    @Subscribe(id = "cleaningMapDc", target = Target.DATA_CONTAINER)
//    public void onCleaningMapDcCollectionChange(CollectionContainer.CollectionChangeEvent<PositionWrapper> event) {
//
//        if (event.getChangeType().toString().equals("REMOVE_ITEMS")) {
//
//            int index = 0;
//
//            for (PositionWrapper wrappers : cleaningMapDc.getItems()) {
//                wrappers.setPriorityCleaningPosition(index + 1);
//                cleaningMapDc.replaceItem(wrappers);
//                index = index + 1;
//            }
//
//        }
//
//        cleaningMapDc.getItems().forEach(positionWrapper -> {
//            Room room = positionWrapper.getPosition().getRoom();
//
//            if (room != null) {
//                Room reloadRoom = dataManager.load(Id.of(room)).one();
//                if (reloadRoom.getColor() != null) {
//                    injectColorCss(reloadRoom.getColor(), positionWrapper.getId());
//                }
//            }
//        });
//
//        cleaningMapTable.setStyleProvider(new GroupTable.StyleProvider<PositionWrapper>() {
//            @Nullable
//            @Override
//            public String getStyleName(PositionWrapper entity, @Nullable String property) {
//                if (property != null && property.equals("position.room") && entity.getPosition().getRoom() != null) {
//
//                    return "colored-cell-" + entity.getId() + "-" + entity.getPosition().getRoom().getColor();
//                }
//                return null;
//            }
//        });
//    }
//
//    @Subscribe
//    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
//        cleaningMapDc.getItems().forEach(wrapper -> {
//            event.getDataContext().merge(wrapper);
//        });
//
//        inventoryDc.getItems().forEach(wrapper -> {
//            event.getDataContext().merge(wrapper);
//        });
//    }
//
//
//    private void injectColorCss(String color, UUID id) {
//        Page.Styles styles = Page.getCurrent().getStyles();
//        if (color.contains("#")) {
//            color = color.replace("#", "");
//        }
//        styles.add(String.format(
//                ".colored-cell-%s-%s{background-color:#%s;}",
//                id.toString(), color, color));
//    }
//

}