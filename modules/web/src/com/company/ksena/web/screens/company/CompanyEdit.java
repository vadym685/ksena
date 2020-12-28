package com.company.ksena.web.screens.company;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.company.CompanyType;
import com.company.ksena.entity.people.ClientEmployee;
import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.company.ksena.web.screens.clientemployee.ClientEmployeeWithCompanyBrowse;
import com.company.ksena.web.screens.task.TaskEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.*;
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
    private DataManager dataManager;
    @Inject
    private CollectionLoader<Task> taskDl;
    @Inject
    private Notifications notifications;
    @Inject
    private DataComponents dataComponents;
    @Inject
    private CollectionLoader<TaskDocument> taskDocDl;
    @Inject
    private CollectionContainer<Task> taskDc;
    @Inject
    private Table<Task> taskTable;
    @Inject
    private Table<TaskDocument> taskDocTable;
    @Inject
    private CollectionLoader<ClientEmployee> responsibleEmployeesDl;
    @Inject
    private CollectionLoader<Point> pointsDl;
    @Inject
    private Table<ClientEmployee> responsibleEmployeeTable;
    @Inject
    private Table<Point> pointsTable;
    @Inject
    private CollectionContainer<ClientEmployee> responsibleEmployeeDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (this.getEditedEntity().getName() != null) {

            taskDl.setParameter("name", this.getEditedEntity().getName());
            taskDl.load();

            responsibleEmployeesDl.setParameter("name", this.getEditedEntity().getName());
            responsibleEmployeesDl.load();

            pointsDl.setParameter("name", this.getEditedEntity().getName());
            pointsDl.load();

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
//    @Subscribe("addResponsibleEmployee")
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

    @Subscribe("tabSheet")
    public void onTabSheetSelectedTabChange(TabSheet.SelectedTabChangeEvent event) {

        String tabName = event.getSelectedTab().getName();

        taskDl.load();
        taskDocDl.load();
        responsibleEmployeesDl.load();
        pointsDl.load();

        taskDocTable.refresh();
        taskTable.refresh();
        responsibleEmployeeTable.refresh();
        pointsTable.refresh();
    }

    @Subscribe("createTask")
    public void onCreateClick(Button.ClickEvent event) {
        Task newTask = metadata.create(Task.class);
        newTask.setCompany(this.getEditedEntity());

        screenBuilders.editor(Task.class, this)
                .editEntity(newTask)
                .show();
    }

    @Subscribe("createTaskDoc")
    public void onCreateTaskDocClick(Button.ClickEvent event) {
        TaskDocument newTaskDocument = metadata.create(TaskDocument.class);
        newTaskDocument.setCompany(this.getEditedEntity());
        newTaskDocument.setCreateDate(LocalDate.now());
        newTaskDocument.setDateOfCompletion(LocalDate.now());
        newTaskDocument.setIsActive(true);

        screenBuilders.editor(TaskDocument.class, this)
                .editEntity(newTaskDocument)
                .show();
    }
}