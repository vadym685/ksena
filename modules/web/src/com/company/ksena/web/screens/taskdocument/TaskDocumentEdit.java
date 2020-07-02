package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.task.TaskType;
import com.company.ksena.entity.task.TypeOfCostFormation;
import com.company.ksena.entity.task.TypeOfPeriodicity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.calendar.CalendarEvent;
import com.haulmont.cuba.gui.components.calendar.ListCalendarEventProvider;
import com.haulmont.cuba.gui.components.calendar.SimpleCalendarEvent;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.TaskDocument;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@UiController("ksena_TaskDocument.edit")
@UiDescriptor("task-document-edit.xml")
@EditedEntityContainer("taskDocumentDc")
@LoadDataBeforeShow
public class TaskDocumentEdit extends StandardEditor<TaskDocument> {

//    @Inject
//    private Calendar<Date> calendar;
    @Inject
    private DateField<LocalDate> dateOfCompletionField;
    @Inject
    private TextField<Double> costPerHourField;
    @Inject
    private LookupField<TypeOfPeriodicity> typeOfPeriodicityField;
    @Inject
    private TextField<Integer> intervalField;

    @Subscribe("taskTypeField")
    public void onTaskTypeFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getTaskType() == TaskType.fromId("ONE TIME"))
            {typeOfPeriodicityField.setVisible(false);
                dateOfCompletionField.setVisible(true);}
        else if (this.getEditedEntity().getTaskType() == TaskType.fromId("REPEAT"))
            {typeOfPeriodicityField.setVisible(true);
                dateOfCompletionField.setVisible(false);}
        else {typeOfPeriodicityField.setVisible(false);
            dateOfCompletionField.setVisible(false);}
    }
    @Subscribe("typeOfCostFormationField")
    public void onTypeOfCostFormationFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getTypeOfCostFormation() == TypeOfCostFormation.fromId("FOR TIME"))
           {costPerHourField.setVisible(true);}
        else if (this.getEditedEntity().getTypeOfCostFormation() == TypeOfCostFormation.fromId("FOR CLEANING MAP"))
           {costPerHourField.setVisible(false);}
        else
           {costPerHourField.setVisible(false);}
    }
    @Subscribe("typeOfPeriodicityField")
    public void onTypeOfPeriodicityFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getTypeOfPeriodicity() == TypeOfPeriodicity.fromId("PERIOD"))
        {intervalField.setVisible(false);}
        else if (this.getEditedEntity().getTypeOfPeriodicity() == TypeOfPeriodicity.fromId("PERIODICITY"))
        {intervalField.setVisible(true);}
        else
        {intervalField.setVisible(false);}
    }

}