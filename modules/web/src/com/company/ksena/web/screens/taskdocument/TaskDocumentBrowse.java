package com.company.ksena.web.screens.taskdocument;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.TaskDocument;

@UiController("ksena_TaskDocument.browse")
@UiDescriptor("task-document-browse.xml")
@LookupComponent("taskDocumentsTable")
@LoadDataBeforeShow
public class TaskDocumentBrowse extends StandardLookup<TaskDocument> {
}