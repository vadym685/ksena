package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.cleaning_map.Room;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.inventory.InventoryWrapper;
import com.company.ksena.entity.task.*;

import com.company.ksena.web.screens.cleaningposition.CleaningPositionBrowse;
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
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.vaadin.server.Page;

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

    @Subscribe("addInventory")
    public void onAddInventoryClick(Button.ClickEvent event) {
        InventoryBrowse selectInventory = screenBuilders.lookup(Inventory.class, this)
                .withScreenClass(InventoryBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {

                    Inventory inventory = e.getScreen().getSelectedInventory();

                    if (inventory != null) {

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

    @Subscribe("addPosition")
    public void onAddPositionClick(Button.ClickEvent event) {

        CleaningPositionBrowse selectCleaningPosition = screenBuilders.lookup(CleaningPosition.class, this)
                .withScreenClass(CleaningPositionBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {

                    CleaningPosition cleaningPosition = e.getScreen().getSelectedCleaningPosition();

                    if (cleaningPosition != null) {

                        PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                        positionWrapper.setPosition(cleaningPosition);
                        positionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
                        positionWrapper.setTaskDocuments(this.getEditedEntity());


                        cleaningMapDc.getMutableItems().add(positionWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();
        selectCleaningPosition.show();
    }

    @Subscribe("cleaningMapPositionUp")
    public void onCleaningMapPositionUpClick(Button.ClickEvent event) {
        if (cleaningMapTable.getSelected().isEmpty()) {
            notifications.create().withDescription("SetPosition").show();
        } else {

            for (PositionWrapper positionWrapper : cleaningMapTable.getSelected()) {

                int nowPriority = positionWrapper.getPriorityCleaningPosition();
                if (nowPriority != 1) {
                    int newPriority = cleaningMapDc.getMutableItems().get(nowPriority - 2).getPriorityCleaningPosition();

                    PositionWrapper nowCleaningPosition = Objects.requireNonNull(cleaningMapTable.getItems()).getItem(cleaningMapDc.getMutableItems().get(nowPriority - 2));
                    positionWrapper.setPriorityCleaningPosition(newPriority);
                    assert nowCleaningPosition != null;
                    nowCleaningPosition.setPriorityCleaningPosition(nowPriority);
                }
            }
            cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);

        }
    }

    @Subscribe("cleaningMapPositionDown")
    public void onCleaningMapPositionDownClick(Button.ClickEvent event) {
        if (cleaningMapTable.getSelected().isEmpty()) {
            notifications.create().withDescription("SetPosition").show();
        } else {

            for (PositionWrapper positionWrapper : cleaningMapTable.getSelected()) {

                int nowPriority = positionWrapper.getPriorityCleaningPosition();
                int size = Objects.requireNonNull(cleaningMapTable.getItems()).size();
                if (nowPriority != size) {
                    int newPriority = cleaningMapDc.getMutableItems().get(nowPriority).getPriorityCleaningPosition();

                    PositionWrapper nowPositionWrapper = cleaningMapDc.getMutableItems().get(nowPriority);

                    positionWrapper.setPriorityCleaningPosition(newPriority);
                    nowPositionWrapper.setPriorityCleaningPosition(nowPriority);
                }
            }
            cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);
        }
    }

    @Subscribe("cleaningMapPositionAddRoom")
    public void onCleaningMapPositionAddRoomClick(Button.ClickEvent event) {
        RoomBrowse selectRoom = screenBuilders.lookup(Room.class, this)
                .withScreenClass(RoomBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    Room room = e.getScreen().getSelectedRoom();

                    List newList = room.getCleaningPosition()
                            .stream()
                            .filter(cleaningPosition -> cleaningPosition.getStandartPosition())
                            .collect(Collectors.toList());
                    for (Object Element : newList) {

                        PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                        positionWrapper.setPosition((CleaningPosition) Element);
                        positionWrapper.setPriorityCleaningPosition(cleaningMapDc.getItems().size() + 1);
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
    public void onAfterShow(AfterShowEvent event) {
        cleaningMapDc.getItems().forEach(positionWrapper ->  {
            Room room = positionWrapper.getPosition().getRoom();

            if (room != null) {
                Room reloadRoom = dataManager.load(Id.of(room)).one();
                if (reloadRoom.getColor() != null) {
                    injectColorCss(reloadRoom.getColor(), positionWrapper.getId());
                }
            }
        });

        cleaningMapTable.setStyleProvider(new GroupTable.StyleProvider<PositionWrapper>() {
            @Nullable
            @Override
            public String getStyleName(PositionWrapper entity, @Nullable String property) {
                if (property != null && property.equals("position.room") && entity.getPosition().getRoom() != null) {

                    return "colored-cell-" + entity.getId() + "-" + entity.getPosition().getRoom().getColor();
                }
                return null;
            }
        });
    }

    @Subscribe(id = "cleaningMapDc", target = Target.DATA_CONTAINER)
    public void onCleaningMapDcCollectionChange(CollectionContainer.CollectionChangeEvent<PositionWrapper> event) {
        cleaningMapDc.getItems().forEach(positionWrapper ->  {
            Room room = positionWrapper.getPosition().getRoom();

            if (room != null) {
                Room reloadRoom = dataManager.load(Id.of(room)).one();
                if (reloadRoom.getColor() != null) {
                    injectColorCss(reloadRoom.getColor(), positionWrapper.getId());
                }
            }
        });

        cleaningMapTable.setStyleProvider(new GroupTable.StyleProvider<PositionWrapper>() {
            @Nullable
            @Override
            public String getStyleName(PositionWrapper entity, @Nullable String property) {
                if (property != null && property.equals("position.room") && entity.getPosition().getRoom() != null) {

                    return "colored-cell-" + entity.getId() + "-" + entity.getPosition().getRoom().getColor();
                }
                return null;
            }
        });
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
