package com.company.ksena.web.screens.company;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.company.CompanyType;
import com.company.ksena.entity.people.ClientEmployee;
import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.company.ksena.web.screens.clientemployee.ClientEmployeeBrowse;
import com.company.ksena.web.screens.clientemployee.ClientEmployeeWithCompanyBrowse;
import com.company.ksena.web.screens.task.TaskEdit;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataComponents;
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
    @Inject
    private CollectionPropertyContainer<ClientEmployee> responsibleEmployeeDc;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<Task> taskDl;
    @Inject
    private Notifications notifications;
    @Inject
    private DataComponents dataComponents;
    @Inject
    private CollectionLoader<TaskDocument> taskDocDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (this.getEditedEntity().getName() != null) {
//            CollectionLoader<Task> loader = dataComponents.createCollectionLoader();
            taskDl.setParameter("name", this.getEditedEntity().getName());
            taskDl.load();

//            CollectionLoader<TaskDocument> loader = dataComponents.createCollectionLoader();
            taskDocDl.setParameter("name", this.getEditedEntity().getName());
            taskDocDl.load();
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        try {
            if (Objects.nonNull(nameField.getValue()) && Objects.nonNull(companyTypeField.getValue().getName())) {
                fullNameField.setValue(nameField.getValue() + " " + companyTypeField.getValue().getName());
            }
        } catch (Exception e) {

        }
    }

    public void createTaskDoc() {
        LOG.info("");

        TaskDocument newTaskDocument = metadata.create(TaskDocument.class);
        newTaskDocument.setCompany(this.getEditedEntity());
        newTaskDocument.setCreateDate(LocalDate.now());
        newTaskDocument.setDateOfCompletion(LocalDate.now());
        newTaskDocument.setIsActive(true);

        screenBuilders.editor(TaskDocument.class, this)
                .editEntity(newTaskDocument)
                .show();
    }

    public void createTask() {
        LOG.info("");

        Task newTask = metadata.create(Task.class);
        newTask.setCompany(this.getEditedEntity());

        screenBuilders.editor(Task.class, this)
                .editEntity(newTask)
                .show();
    }

    @Subscribe("companyTypeField")
    public void onCompanyTypeFieldFieldValueChange(PickerField.ValueChangeEvent<CompanyType> event) {
        if (companyTypeField.getValue() != null) {
            if (nameField.getValue() != null) {
                if (Objects.nonNull(nameField.getValue()) && Objects.nonNull(companyTypeField.getValue().getName())) {
                    fullNameField.setValue(nameField.getValue() + " " + companyTypeField.getValue().getName());
                } else {
                    fullNameField.setValue(null);
                }
            } else {
                fullNameField.setValue(null);
            }
        } else {
            fullNameField.setValue(null);
        }
    }

    @Subscribe("nameField")
    public void onNameFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        if (companyTypeField.getValue() != null) {
            if (nameField.getValue() != null) {
                if (Objects.nonNull(nameField.getValue()) && Objects.nonNull(companyTypeField.getValue().getName())) {
                    fullNameField.setValue(nameField.getValue() + " " + companyTypeField.getValue().getName());
                } else {
                    fullNameField.setValue(null);
                }
            } else {
                fullNameField.setValue(null);
            }
        } else {
            fullNameField.setValue(null);
        }
    }

    //
    @Subscribe("addResponsibleEmployee")
    public void addResponsibleEmployee(Button.ClickEvent event) {

        if (this.nameField.getRawValue() == "") {
            notifications.create().withDescription("Before adding employees, save the company").show();
        } else {
            Company company = dataManager.load(Company.class)
                    .query("select e from ksena_Company e where e.name = :name")
                    .parameter("name", this.nameField.getRawValue())
                    .view("company-view")
                    .one();

            ClientEmployeeWithCompanyBrowse selectClientEmployee = screenBuilders.lookup(ClientEmployee.class, this)
                    .withScreenClass(ClientEmployeeWithCompanyBrowse.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .withAfterCloseListener(e -> {

                        ClientEmployee clientEmployee = e.getScreen().getSelectedClientEmployee();

                        StandardCloseAction closeAction = (StandardCloseAction) e.getCloseAction();

                        if (clientEmployee != null && closeAction.getActionId().equals("select")) {

                            responsibleEmployeeDc.getMutableItems().add(clientEmployee);
                        }
                    })
                    .withSelectHandler(e -> {

                    })
                    .build();

            selectClientEmployee.setQueryParametr(company);
            selectClientEmployee.show();
        }
    }
}