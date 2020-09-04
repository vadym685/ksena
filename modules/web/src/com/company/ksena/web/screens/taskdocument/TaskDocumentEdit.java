package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.task.*;

import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.UUID;

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

        TypeOfCostFormation type =   this.getEditedEntity().getTypeOfCostFormation();

        if ( type.equals(TypeOfCostFormation.FOR_TIME)) {
            costPerHourField.setVisible(true);
            fullCostField.setVisible(false);
        } else if (type.equals(TypeOfCostFormation.FOR_CLEANING_MAP)) {
            costPerHourField.setVisible(false);
            fullCostField.setVisible(false);
            costPerHourField.clear();
        }else if (type.equals(TypeOfCostFormation.FIXED_PRICE)) {
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
        if (event.getChangeType().name() == "ADD_ITEMS"){

                for (Inventory changeInventory : event.getChanges()) {

                    Inventory newInventory = metadata.getTools().deepCopy(changeInventory);

                    CommitContext cc = new CommitContext();

                    newInventory.setId(UUID.randomUUID());
                    newInventory.setVisible(true);

                    changeInventory.setVisible(false);

                    cc.addInstanceToCommit(newInventory);

                    dataManager.commit(cc);

                }
        }
    }

    @Subscribe(id = "cleaningMapDc", target = Target.DATA_CONTAINER)
    public void onCleaningMapDcCollectionChange(CollectionContainer.CollectionChangeEvent<CleaningPosition> event) {
        if (event.getChangeType().name() == "ADD_ITEMS"){

            for (CleaningPosition changeCleaningPosition : event.getChanges()) {

                CleaningPosition newCleaningPosition = metadata.getTools().deepCopy(changeCleaningPosition);

                CommitContext cc = new CommitContext();

                newCleaningPosition.setId(UUID.randomUUID());
                newCleaningPosition.setVisible(true);
                int size = cleaningMapDc.getMutableItems().size();

                changeCleaningPosition.setVisible(false);
                changeCleaningPosition.setPriorityCleaningPosition(size);

                cc.addInstanceToCommit(newCleaningPosition);

                dataManager.commit(cc);


            }
        }
    }

    public void cleaningMapPositionUp() {
        if(cleaningMapTable.getSelected().isEmpty()) {
            notifications.create().withDescription("SetPosition").show();
        }
        else
            {

                for (CleaningPosition CleaningPosition :  cleaningMapTable.getSelected()) {

                    int nowPriority =  CleaningPosition.getPriorityCleaningPosition();
                    if (nowPriority != 1)
                    {
                        int newPriority = cleaningMapDc.getMutableItems().get(nowPriority - 2).getPriorityCleaningPosition();

                        CleaningPosition.setPriorityCleaningPosition(newPriority);
                        cleaningMapDc.getMutableItems().get(nowPriority - 2).setPriorityCleaningPosition(nowPriority);
                    }
                }
                cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);
            }
        }


    public void cleaningMapPositionDown() {
        if(cleaningMapTable.getSelected().isEmpty()) {
            notifications.create().withDescription("SetPosition").show();
        }
        else
        {

            for (CleaningPosition CleaningPosition :  cleaningMapTable.getSelected()) {

                int nowPriority =  CleaningPosition.getPriorityCleaningPosition();
              int size = cleaningMapTable.getItems().size();
                if (nowPriority != size)
                {
                    int newPriority = cleaningMapDc.getMutableItems().get(nowPriority).getPriorityCleaningPosition();

                    CleaningPosition.setPriorityCleaningPosition(newPriority);
                    cleaningMapDc.getMutableItems().get(nowPriority).setPriorityCleaningPosition(nowPriority);
                }
            }
            cleaningMapTable.sort("priorityCleaningPosition", Table.SortDirection.ASCENDING);
        }
    }
}