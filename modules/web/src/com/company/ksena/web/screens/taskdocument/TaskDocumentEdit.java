package com.company.ksena.web.screens.taskdocument;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.TaskDocument;

@UiController("ksena_TaskDocument.edit")
@UiDescriptor("task-document-edit.xml")
@EditedEntityContainer("taskDocumentDc")
@LoadDataBeforeShow
public class TaskDocumentEdit extends StandardEditor<TaskDocument> {
}