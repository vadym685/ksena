package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.Room;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.task.*;

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
    private CollectionPropertyContainer<Inventory> inventoryDc;
    @Inject
    private CollectionPropertyContainer<CleaningPosition> cleaningMapDc;
    @Inject
    private Table<CleaningPosition> cleaningMapTable;
    @Inject
    private Notifications notifications;
    @Inject
    private InstanceContainer<TaskDocument> taskDocumentDc;
    @Inject
    private ScreenBuilders screenBuilders;

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
    public void onInventoryDcCollectionChange(CollectionContainer.CollectionChangeEvent<Inventory> event) {
        if (event.getChangeType().name() == "ADD_ITEMS") {

            for (Inventory changeInventory : event.getChanges()) {

                Inventory newInventory = metadata.getTools().deepCopy(changeInventory);

                CommitContext cc = new CommitContext();

                newInventory.setId(UUID.randomUUID());
                newInventory.setVisible(true);
                newInventory.setQuantityInventory(1);

                changeInventory.setVisible(false);

                cc.addInstanceToCommit(newInventory);

                dataManager.commit(cc);
            }
        }
    }

    @Subscribe(id = "cleaningMapDc", target = Target.DATA_CONTAINER)
    public void onCleaningMapDcCollectionChange(CollectionContainer.CollectionChangeEvent<CleaningPosition> event) {
        if (event.getChangeType().name() == "ADD_ITEMS") {

            for (CleaningPosition changeCleaningPosition : event.getChanges()) {

                CleaningPosition newCleaningPosition = metadata.getTools().deepCopy(changeCleaningPosition);

                CommitContext cc = new CommitContext();

                newCleaningPosition.setId(UUID.randomUUID());
                newCleaningPosition.setVisible(true);
                int size = cleaningMapDc.getMutableItems().size();

                changeCleaningPosition.setVisible(false);
                changeCleaningPosition.setPriorityCleaningPosition(size);

                cc.addInstanceToCommit(newCleaningPosition);
                cc.addInstanceToCommit(changeCleaningPosition);

                dataManager.commit(cc);
            }
        }
    }

    @Subscribe(id = "cleaningMapDc", target = Target.DATA_CONTAINER)
    public void onCleaningMapDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<CleaningPosition> event) {
        if (event.getProperty().equals("noteCleaningPosition")) {
            CommitContext cc = new CommitContext();
            CleaningPosition changeCleaningPosition = event.getItem();

            cc.addInstanceToCommit(changeCleaningPosition);
            dataManager.commit(cc);
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {

        for (CleaningPosition CleaningPosition : cleaningMapDc.getItems()) {
            com.company.ksena.entity.cleaning_map.CleaningPosition reloadCleaningPosition = dataManager.load(Id.of(CleaningPosition)).one();
            cleaningMapDc.setItem(dataContext.merge(reloadCleaningPosition));
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        cleaningMapDc.getItems().forEach(cleaningPosition -> {
            Room room = cleaningPosition.getRoom();

            if (room != null) {
                Room reloadRoom = dataManager.load(Id.of(room)).one();
                if (reloadRoom.getColor() != null) {
                    injectColorCss(reloadRoom.getColor(), cleaningPosition.getId());
                }
            }
        });

        cleaningMapTable.setStyleProvider(new GroupTable.StyleProvider<CleaningPosition>() {
            @Nullable
            @Override
            public String getStyleName(CleaningPosition entity, @Nullable String property) {
                if (property != null && property.equals("room") && entity.getRoom() != null) {

                    return "colored-cell-" + entity.getId() + "-" + entity.getRoom().getColor();
                }
                return null;
            }
        });
    }

    public void cleaningMapPositionUp() {
        if (cleaningMapTable.getSelected().isEmpty()) {
            notifications.create().withDescription("SetPosition").show();
        } else {

            for (CleaningPosition CleaningPosition : cleaningMapDc.getItems()) {
                com.company.ksena.entity.cleaning_map.CleaningPosition reloadCleaningPosition = dataManager.load(Id.of(CleaningPosition)).one();
                cleaningMapDc.setItem(dataContext.merge(reloadCleaningPosition));
            }

            for (CleaningPosition CleaningPosition : cleaningMapTable.getSelected()) {



                int nowPriority = CleaningPosition.getPriorityCleaningPosition();
                if (nowPriority != 1) {
                    int newPriority = cleaningMapDc.getMutableItems().get(nowPriority - 2).getPriorityCleaningPosition();

                    com.company.ksena.entity.cleaning_map.CleaningPosition nowCleaningPosition = cleaningMapDc.getMutableItems().get(nowPriority - 2);

                    CleaningPosition.setPriorityCleaningPosition(newPriority);
                    nowCleaningPosition.setPriorityCleaningPosition(nowPriority);

                    CommitContext cc = new CommitContext();
                    cc.addInstanceToCommit(CleaningPosition);
                    cc.addInstanceToCommit(nowCleaningPosition);
                    dataManager.commit(cc);
                }
            }
            cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);
        }
    }

    public void cleaningMapPositionDown() {
        if (cleaningMapTable.getSelected().isEmpty()) {
            notifications.create().withDescription("SetPosition").show();
        } else {

            for (CleaningPosition CleaningPosition : cleaningMapDc.getItems()) {
                com.company.ksena.entity.cleaning_map.CleaningPosition reloadCleaningPosition = dataManager.load(Id.of(CleaningPosition)).one();
                cleaningMapDc.setItem(dataContext.merge(reloadCleaningPosition));
            }

            for (CleaningPosition CleaningPosition : cleaningMapTable.getSelected()) {

                int nowPriority = CleaningPosition.getPriorityCleaningPosition();
                int size = cleaningMapTable.getItems().size();
                if (nowPriority != size) {
                    int newPriority = cleaningMapDc.getMutableItems().get(nowPriority).getPriorityCleaningPosition();

                    com.company.ksena.entity.cleaning_map.CleaningPosition nowCleaningPosition = cleaningMapDc.getMutableItems().get(nowPriority);

                    CleaningPosition.setPriorityCleaningPosition(newPriority);
                    nowCleaningPosition.setPriorityCleaningPosition(nowPriority);

                    CommitContext cc = new CommitContext();
                    cc.addInstanceToCommit(CleaningPosition);
                    cc.addInstanceToCommit(nowCleaningPosition);
                    dataManager.commit(cc);
                }
            }
            cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);
        }
    }

    public void cleaningMapPositionAddRoom() {

        RoomBrowse selectRoom = (RoomBrowse) screenBuilders.lookup(Room.class, this)
                .withScreenClass(RoomBrowse.class)
                .withOpenMode(OpenMode.NEW_TAB)
                .withAfterCloseListener(e -> {
                    Room room = e.getScreen().getSelectedRoom();

                    List newList = dataManager.load(CleaningPosition.class)
                            .query("select e from ksena_CleaningPosition e where e.room = :room and e.visible = true and e.standartPosition = true")
                            .parameter("room", room)
                            .view("cleaningPosition-view")
                            .list();
                    for (Object Element : newList) {
                        cleaningMapDc.getMutableItems().add(cleaningMapDc.getMutableItems().size(), (CleaningPosition) Element);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();


        selectRoom.show();
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
