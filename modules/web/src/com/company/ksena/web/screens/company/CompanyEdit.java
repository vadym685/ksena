package com.company.ksena.web.screens.company;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.company.CompanyType;
import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.company.ksena.web.screens.task.TaskEdit;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Objects;

@UiController("ksena_Company.edit")
@UiDescriptor("company-edit.xml")
@EditedEntityContainer("companyDc")
@LoadDataBeforeShow
public class CompanyEdit extends StandardEditor<Company> {
    @Inject
    private TextField<String> nameField;
    @Inject
    private PickerField<CompanyType> companyTypeField;
    @Inject
    private TextField<String> fullNameField;
    @Inject
    private Metadata metadata;


    private final Logger LOG = LoggerFactory.getLogger(TaskEdit.class);
    @Inject
    private DataContext dataContext;
    @Inject
    private ScreenBuilders screenBuilders;


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
       if (Objects.nonNull(nameField.getValue()) && Objects.nonNull(companyTypeField.getValue().getName())){
           fullNameField.setValue(nameField.getValue() + " " + companyTypeField.getValue().getName());
        }
    }

    public void createTaskDoc() {
        LOG.info("");

        TaskDocument newTaskDocument =metadata.create(TaskDocument.class);
        newTaskDocument.setCompany(this.getEditedEntity());
        newTaskDocument.setCreateDate(LocalDate.now());
        newTaskDocument.setDateOfCompletion(LocalDate.now());
        newTaskDocument.setIsActive(true);

        dataContext.merge(newTaskDocument);
        dataContext.commit();

            }


    public void createTask() {
        LOG.info("");

        Task newTask =metadata.create(Task.class);
        newTask.setCompany(this.getEditedEntity());

        dataContext.merge(newTask);
        dataContext.commit();

    }
}
