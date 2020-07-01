package com.company.ksena.web.screens.taskdocument;

import com.company.ksena.entity.task.TaskType;
import com.company.ksena.entity.task.TypeOfCostFormation;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.TaskDocument;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Date;

@UiController("ksena_TaskDocument.edit")
@UiDescriptor("task-document-edit.xml")
@EditedEntityContainer("taskDocumentDc")
@LoadDataBeforeShow
public class TaskDocumentEdit extends StandardEditor<TaskDocument> {

    @Inject
    private Calendar<Date> calendar;
    @Inject
    private DateField<LocalDate> dateOfCompletionField;
    @Inject
    private TextField<Double> costPerHourField;

    @Subscribe("taskTypeField")
    public void onTaskTypeFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getTaskType() == TaskType.fromId("ONE TIME"))
            {calendar.setVisible(false);
                dateOfCompletionField.setVisible(true);}
        else if (this.getEditedEntity().getTaskType() == TaskType.fromId("REPEAT"))
            {calendar.setVisible(true);
                dateOfCompletionField.setVisible(false);}
        else {calendar.setVisible(false);
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
}