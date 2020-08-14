package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.task.*;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.calendar.CalendarEvent;
import com.haulmont.cuba.gui.components.calendar.ListCalendarEventProvider;
import com.haulmont.cuba.gui.components.calendar.SimpleCalendarEvent;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

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
        if (this.getEditedEntity().getTypeOfCostFormation() == TypeOfCostFormation.fromId("FOR TIME")) {
            costPerHourField.setVisible(true);
        } else if (this.getEditedEntity().getTypeOfCostFormation() == TypeOfCostFormation.fromId("FOR CLEANING MAP")) {
            costPerHourField.setVisible(false);
            costPerHourField.clear();
        } else {
            costPerHourField.setVisible(false);
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


}