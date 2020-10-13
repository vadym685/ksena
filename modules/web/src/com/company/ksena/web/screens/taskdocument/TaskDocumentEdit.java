package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.cleaning_map.Room;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.inventory.InventoryWrapper;
import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.task.*;

import com.company.ksena.web.screens.cleaningposition.CleaningPositionBrowse;
import com.company.ksena.web.screens.inventory.AvaibleInventoryBrowse;
import com.company.ksena.web.screens.inventory.InventoryBrowse;
import com.company.ksena.web.screens.room.RoomBrowse;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.widgets.CubaTable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.Page;
import com.vaadin.v7.ui.AbstractSelect;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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
        if (event.getProperty() == "quantityInventory"){

            if (event.getItem().getInventory().getSerialNumber() != null){
                notifications.create().withDescription("You cannot set the quantity to unique inventory").show();
                event.getItem().setQuantityInventory(1);
            }
        }
    }

    @Subscribe
    public void onInit(InitEvent event) {
        possitionTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table ->

        {
            table.setDragMode(com.vaadin.v7.ui.Table.TableDragMode.MULTIROW);

        });

        cleaningMapTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table ->

        {
            table.setDropHandler(new DropHandler() {
                @Override
                public void drop(DragAndDropEvent event) {

                UUID itemId =(UUID) event.getTransferable().getData("itemId");

               CleaningPosition cleaningPosition = dataManager.load(CleaningPosition.class).id(itemId).one();

                    PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                    positionWrapper.setPosition(cleaningPosition);
                    positionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);

                    GroupInfo targetId = (GroupInfo) ((AbstractSelect.AbstractSelectTargetDetails)event.getTargetDetails()).getItemIdOver();

                    Room room = (Room) targetId.getValue();

                    positionWrapper.setRoomName(room);

                    positionWrapper.setTaskDocuments(taskDocumentDc.getItem());

                    cleaningMapDc.getMutableItems().add(positionWrapper);

                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }
            });
        });

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
                .withSelectHandler(e  -> {
                })
                .build();
        selectInventory.show();
    }
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
//                        positionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
////                        positionWrapper.setRoomName(cleaningPosition.getRoom());
//                        positionWrapper.setTaskDocuments(this.getEditedEntity());
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

    @Subscribe("cleaningMapPositionAddRoom")
    public void onCleaningMapPositionAddRoomClick(Button.ClickEvent event) {
        RoomBrowse selectRoom = screenBuilders.lookup(Room.class, this)
                .withScreenClass(RoomBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    Room room = e.getScreen().getSelectedRoom();


                        PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                        positionWrapper.setPosition(null);
                        positionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
                        positionWrapper.setRoomName(room);
                        positionWrapper.setTaskDocuments(this.getEditedEntity());

                        cleaningMapDc.getMutableItems().add(positionWrapper);

                })
                .withSelectHandler(e -> {
                })
                .build();
        selectRoom.show();

    }

//    @Subscribe
//    public void onAfterShow(AfterShowEvent event) {
//        cleaningMapDc.getItems().forEach(positionWrapper ->  {
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

//    @Subscribe(id = "cleaningMapDc", target = Target.DATA_CONTAINER)
//    public void onCleaningMapDcCollectionChange(CollectionContainer.CollectionChangeEvent<PositionWrapper> event) {
//       if (event.getChangeType().toString().equals("REMOVE_ITEMS")){
//
//           int index = 0;
//
//           for (PositionWrapper wrappers:cleaningMapDc.getItems()) {
//               wrappers.setPriorityCleaningPosition(index + 1);
//               cleaningMapDc.replaceItem(wrappers);
//               index = index+1;
//           }
//
//       }
//
//        cleaningMapDc.getItems().forEach(positionWrapper ->  {
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
//
//
//    }

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
