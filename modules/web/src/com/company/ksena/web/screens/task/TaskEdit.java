package com.company.ksena.web.screens.task;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.cleaning_map.Room;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.inventory.InventoryWrapper;
import com.company.ksena.entity.people.Employee;
import com.company.ksena.entity.people.EmployeeType;
import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.task.*;
import com.company.ksena.entity.template.Template;
import com.company.ksena.service.google_calendar_api_service.GoogleCalendarService;
import com.company.ksena.web.screens.employee.EmployeeBrowse;
import com.company.ksena.web.screens.employee.TemporaryEmployeeBrowse;
import com.company.ksena.web.screens.inventory.AvaibleInventoryBrowse;
import com.company.ksena.web.screens.room.RoomBrowse;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EmailInfo;
import com.haulmont.cuba.core.global.EmailInfoBuilder;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.Page;
import com.vaadin.v7.ui.AbstractSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@UiController("ksena_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
@LoadDataBeforeShow
public class TaskEdit extends StandardEditor<Task> {
    @Inject
    private LookupField<TaskStatus> taskStatusField;
    @Inject
    private Notifications notifications;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<InventoryWrapper> inventoryDc;
    @Inject
    private CollectionPropertyContainer<PositionWrapper> cleaningMapDc;
    @Inject
    private Table<PositionWrapper> cleaningMapTable;
    @Inject
    private DataManager dataManager;

    private final Logger LOG = LoggerFactory.getLogger(TaskEdit.class);

    @Inject
    private PickerField<Company> companyField;
    @Inject
    private CollectionLoader<Point> pointsForCompanyLc;
    @Inject
    private LookupPickerField<Point> pointField;
    @Inject
    private Dialogs dialogs;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Button cleaningMapPositionUp;
    @Inject
    private Button cleaningMapPositionDown;
    @Inject
    private Button addPosition;
    @Inject
    private LookupPickerField<TaskDocument> taskDocumentField;
    @Inject
    private Button ok;
    @Inject
    private SplitPanel mainSplit;
    @Inject
    private Table<CleaningPosition> possitionTable;
    @Inject
    private InstanceContainer<Task> taskDc;
    @Inject
    private Button excludePosition;
    @Inject
    private EmailService emailService;
    @Inject
    private GoogleCalendarService googleCalendarService;
    @Inject
    private CollectionPropertyContainer<Employee> employeesDc;
    @Inject
    private DateField<LocalDate> dateOfCompletionField;
    @Inject
    private CollectionPropertyContainer<GoogleCalendarEventId> googleCalendarEventIdDc;
    @Inject
    private TextField<Double> costPerHourField;
    @Inject
    private TextField<Double> fullCostField;
    @Inject
    private TextField<Double> fixedCostForCleaningField;
    @Inject
    private CollectionLoader<TaskDocument> taskDocumentsLc;
    @Inject
    private TextField<String> taskNumberField;
    @Inject
    private CheckBox addPriseExpendableMaterialField;
    @Inject
    private TextField<Double> priseExpendableMaterialField;
    @Inject
    private CheckBox inventoryDeliveryRequiredField;
    @Inject
    private TextField<Double> costOfDeliveryField;
    @Inject
    private LookupPickerField responsibleForTheDeliveryOfInventoryField;
    @Inject
    private CollectionLoader<Employee> responsibleForTheDeliveryOfInventoryLc;
    @Inject
    private Screens screens;

    @Subscribe
    public void onInit(InitEvent event) {

        responsibleForTheDeliveryOfInventoryLc.setParameter("employeeType", EmployeeType.DRIVER);
        responsibleForTheDeliveryOfInventoryLc.load();

        possitionTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table ->
                table.setDragMode(com.vaadin.v7.ui.Table.TableDragMode.MULTIROW)
        );

        cleaningMapTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table -> {
            table.setDragMode(com.vaadin.v7.ui.Table.TableDragMode.MULTIROW);
            table.setDropHandler(new DropHandler() {
                @Override
                public void drop(DragAndDropEvent event) {

                    UUID itemId = (UUID) event.getTransferable().getData("itemId");
                    GroupInfo<Room> targetId = (GroupInfo) ((AbstractSelect.AbstractSelectTargetDetails) event.getTargetDetails()).getItemIdOver();
                    if (targetId != null) {
                        Room room = (Room) targetId.getValue();

                        removeAllNullPositions(room);

                        CleaningPosition cleaningPosition = null;
                        try {
                            cleaningPosition = dataManager.load(CleaningPosition.class).id(itemId).one();
                        } catch (Exception e) {
                            LOG.error("");
                        }

                        if (cleaningPosition != null) {
                            PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                            positionWrapper.setPosition(cleaningPosition);
                            positionWrapper.setRoomName(room);
                            positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                    .stream()
                                    .filter(wrapper -> wrapper.getRoomName().equals(room))
                                    .count() + 1
                            );

                            positionWrapper.setTask(taskDc.getItem());

                            cleaningMapDc.getMutableItems().add(positionWrapper);
                        } else {
                            PositionWrapper positionWrapper = cleaningMapDc.getItemOrNull(itemId);

                            if (positionWrapper != null && !positionWrapper.getRoomName().equals(room)) {
                                renumberAllPositions(positionWrapper);

                                positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                        .stream()
                                        .filter(wrapper -> wrapper.getRoomName().equals(room))
                                        .count() + 1
                                );
                                positionWrapper.setRoomName(room);

                                cleaningMapDc.getMutableItems().remove(positionWrapper);
                                cleaningMapDc.getMutableItems().add(positionWrapper);
                            }
                        }
                    }
                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }
            });
        });

        if (addPriseExpendableMaterialField.getValue()) {
            priseExpendableMaterialField.setVisible(true);
        }

        if (inventoryDeliveryRequiredField.getValue()) {
            responsibleForTheDeliveryOfInventoryField.setVisible(true);
            costOfDeliveryField.setVisible(true);
        } else {
            responsibleForTheDeliveryOfInventoryField.setVisible(false);
            costOfDeliveryField.setVisible(false);
            costOfDeliveryField.clear();
            responsibleForTheDeliveryOfInventoryField.clear();
        }

    }

    @Subscribe("typeOfCostFormationField")
    public void onTypeOfCostFormationFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {

        TypeOfCostFormation type = this.getEditedEntity().getTypeOfCostFormation();

        if (type == null) {
            costPerHourField.setVisible(false);
            fullCostField.setVisible(false);
            costPerHourField.clear();
        } else {

            if (type.equals(TypeOfCostFormation.FOR_TIME)) {
                costPerHourField.setVisible(true);
                fixedCostForCleaningField.setVisible(false);
                fullCostField.setVisible(false);
            } else if (type.equals(TypeOfCostFormation.FIXED_PRICE_FOR_CLEANING)) {
                costPerHourField.setVisible(false);
                fullCostField.setVisible(false);
                fixedCostForCleaningField.setVisible(true);
                costPerHourField.clear();
            } else if (type.equals(TypeOfCostFormation.FIXED_PRICE)) {
                costPerHourField.setVisible(false);
                fullCostField.setVisible(true);
                fixedCostForCleaningField.setVisible(false);
                costPerHourField.clear();
            } else {
                costPerHourField.setVisible(false);
                fullCostField.setVisible(false);
                fixedCostForCleaningField.setVisible(false);
                costPerHourField.clear();
            }
        }

    }

    private void sendByEmail() {
        Task taskItem = getEditedEntity();

        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses("vadym685@gmail.com")
                .setCaption(taskItem.getCompany().getName())
                .setFrom("hd@sngtrans.com.ua")
                .setTemplatePath("com/company/ksena/templates/task_item.txt")
                .setTemplateParameters(Collections.singletonMap("taskId", taskItem.getTaskNumber()))
                .setTemplateParameters(Collections.singletonMap("completedDate", taskItem.getDateOfCompletion()))
                .setTemplateParameters(Collections.singletonMap("company", taskItem.getCompany().getName()))
                .setTemplateParameters(Collections.singletonMap("point", taskItem.getPoint().getName()))
                .setTemplateParameters(Collections.singletonMap("address", taskItem.getPoint().getCity() + " " + taskItem.getPoint().getStreet() + " " + taskItem.getPoint().getHouseNumber()))
                .setTemplateParameters(Collections.singletonMap("getToObject", taskItem.getPoint().getGetToObject()))
                .setAttachments()
                .build();
        emailService.sendEmailAsync(emailInfo);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
    }

    @Subscribe("addTemplate")
    public void onAddTemplateClick(Button.ClickEvent event) {
        screenBuilders.lookup(Template.class, this)
                .withSelectHandler(e -> {
                    if (cleaningMapDc.getItems().size() > 0) {
                        dialogs.createOptionDialog()
                                .withCaption(messageBundle.getMessage("replace"))
                                .withMessage(messageBundle.getMessage("doYouWantToReplace"))
                                .withActions(
                                        new DialogAction(DialogAction.Type.YES).withHandler(yesEvent -> {
                                            cleaningMapDc.getMutableItems().forEach(wrapper -> dataManager.remove(wrapper));
                                            cleaningMapDc.getMutableItems().clear();
                                            e.forEach(template -> {
                                                template.getCleaningMap().forEach(wrapper ->
                                                        wrapper.setTask(super.getEditedEntity()));
                                                cleaningMapDc.getMutableItems().addAll(template.getCleaningMap());

                                                cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
                                            });
                                        }),
                                        new DialogAction(DialogAction.Type.NO).withHandler(noEvent -> {
                                                }
//                                                e.forEach(template -> {
//                                                    template.getCleaningMap().forEach(wrapper ->
//                                                            wrapper.setTask(super.getEditedEntity()));
//                                                    cleaningMapDc.getMutableItems().addAll(template.getCleaningMap());
//                                                })
                                        )
                                ).show();

                    } else {
                        e.forEach(template -> {
                            template.getCleaningMap().forEach(wrapper -> {
                                PositionWrapper clone = metadata.getTools().copy(wrapper);
                                clone.setId(UUID.randomUUID());
                                clone.setTemplate(null);
                                clone.setTask(this.getEditedEntity());
                                cleaningMapDc.getMutableItems().add(clone);

                                cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
                            });
                        });
                    }
                })
                .withOpenMode(OpenMode.DIALOG)
                .show();
    }

    private void renumberAllPositions(PositionWrapper positionWrapper) {
        List<PositionWrapper> wrappers = cleaningMapDc.getItems()
                .stream()
                .filter(wrapper -> wrapper.getRoomName().equals(positionWrapper.getRoomName()))
                .collect(Collectors.toList());

        for (int i = wrappers.indexOf(positionWrapper); i < wrappers.size(); i++) {
            wrappers.get(i).setPriorityCleaningPosition(i);
        }
    }

    private void removeAllNullPositions(Room room) {
        List<PositionWrapper> wrappers = cleaningMapDc.getItems()
                .stream()
                .filter(wrapper -> wrapper.getPosition() == null && wrapper.getRoomName().equals(room))
                .collect(Collectors.toList());

        cleaningMapDc.getMutableItems().removeAll(wrappers);

    }

    @Subscribe("companyField")
    public void onCompanyFieldValueChange(HasValue.ValueChangeEvent<Company> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                pointField.clear();
                taskDocumentField.clear();
                taskNumberField.clear();
                pointsForCompanyLc.setParameter("company", event.getValue());
                taskDocumentsLc.setParameter("company", event.getValue());
            } else {

                pointField.clear();
                taskDocumentField.clear();
                taskNumberField.clear();
                pointsForCompanyLc.removeParameter("company");
                taskDocumentsLc.removeParameter("company");
            }
            pointsForCompanyLc.load();
            taskDocumentsLc.load();
        }
    }

    @Subscribe("taskDocumentField")
    public void onTaskDocumentFieldValueChange(HasValue.ValueChangeEvent<TaskDocument> event) {
        TaskDocument document = event.getValue();
        if (document != null && event.isUserOriginated() == true) {

            if (getEditedEntity().getInventoryMap() != null || getEditedEntity().getCleaningMap() != null) {
                dialogs.createOptionDialog()
                        .withCaption("")
                        .withMessage(messageBundle.getMessage("youSetNewTaskDoc"))
                        .withActions(
                                new DialogAction(DialogAction.Type.YES).withHandler(e -> {
                                    getEditedEntity().setPoint(null);
                                    getEditedEntity().setFixedCostForCleaning(null);
                                    getEditedEntity().setFullCost(null);
                                    getEditedEntity().setCostPerHour(null);
                                    getEditedEntity().setTaskNumber(null);
                                    getEditedEntity().setCompany(null);
                                    getEditedEntity().setDelay(null);
                                    getEditedEntity().setSalaryElementary(null);
                                    getEditedEntity().setSalaryHigh(null);
                                    getEditedEntity().setSalaryMedium(null);
                                    getEditedEntity().setAddPriseExpendableMaterial(null);
                                    getEditedEntity().setEmployees(null);
                                    getEditedEntity().setTaskTimePlane(null);

                                    if (getEditedEntity().getCleaningMap() != null) {
                                        List<PositionWrapper> clearCleaningMapList = getEditedEntity().getCleaningMap();

                                        for (PositionWrapper clearPositionWrapper : clearCleaningMapList) {
                                            dataManager.remove(clearPositionWrapper);

                                        }
                                    }
                                    if (getEditedEntity().getInventoryMap() != null) {
                                        List<InventoryWrapper> clearInventoryWrapperList = getEditedEntity().getInventoryMap();

                                        for (InventoryWrapper clearinventoryWrapper : clearInventoryWrapperList) {
                                            dataManager.remove(clearinventoryWrapper);
                                        }
                                    }

                                    getEditedEntity().setInventoryMap(null);
                                    getEditedEntity().setCleaningMap(null);


                                    String resultString = document.getCreateDate().toString().replaceAll("-", "");


                                    List newList = (List) dataManager.load(Task.class)
                                            .query("select e from ksena_Task e where e.taskDocument.createDate = :createDate")
                                            .parameter("createDate", document.getCreateDate())
                                            .list();
                                    int listSize = 0;
                                    if (newList.size() == 0) {
                                        listSize = 1;
                                    } else {
                                        listSize = newList.size() + 1;
                                    }
                                    getEditedEntity().setTaskNumber(document.getDocNumber() + " - " + listSize);

                                    getEditedEntity().setPoint(document.getPoint());
                                    getEditedEntity().setCompany(document.getCompany());
                                    getEditedEntity().setDelay(document.getDelay());
                                    getEditedEntity().setSalaryElementary(document.getSalaryElementary());
                                    getEditedEntity().setSalaryHigh(document.getSalaryHigh());
                                    getEditedEntity().setSalaryMedium(document.getSalaryMedium());
                                    getEditedEntity().setAddPriseExpendableMaterial(document.getAddPriseExpendableMaterial());
                                    getEditedEntity().setEmployees(document.getEmployeesMap());
                                    getEditedEntity().setTypeOfCostFormation(document.getTypeOfCostFormation());
                                    getEditedEntity().setFixedCostForCleaning(document.getFixedCostForCleaning());
                                    getEditedEntity().setFullCost(document.getFullCost());
                                    getEditedEntity().setCostPerHour(document.getCostPerHour());
                                    getEditedEntity().setTaskTimePlane(document.getTaskTimePlane());

                                    List<PositionWrapper> cleaningMapList = document.getCleaningMap();
                                    List<InventoryWrapper> inventoryWrapperList = document.getInventoryMap();

                                    cleaningMapDc.getMutableItems().clear();
                                    document.getCleaningMap().forEach(wrapper -> {
                                        PositionWrapper positionWrapper = metadata.getTools().copy(wrapper);

                                        positionWrapper.setId(UUID.randomUUID());
                                        positionWrapper.setTaskDocuments(null);
                                        positionWrapper.setTask(getEditedEntity());

                                        cleaningMapDc.getMutableItems().add(positionWrapper);
                                        cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
                                    });

                                    for (InventoryWrapper inventoryWrapper : inventoryWrapperList) {
                                        InventoryWrapper newInventoryWrapper = metadata.create(InventoryWrapper.class);
                                        newInventoryWrapper.setInventory(inventoryWrapper.getInventory());
                                        newInventoryWrapper.setQuantityInventory(1);
                                        newInventoryWrapper.setTask(this.getEditedEntity());
                                        newInventoryWrapper.setTaskDocuments(null);

                                        inventoryDc.getMutableItems().add(newInventoryWrapper);
                                    }
                                }),
                                new DialogAction(DialogAction.Type.NO).withHandler(e -> {

                                })
                        ).show();

            } else {

                String resultString = document.getCreateDate().toString().replaceAll("-", "");


                List newList = (List) dataManager.load(Task.class)
                        .query("select e from ksena_Task e where e.taskDocument.createDate = :createDate")
                        .parameter("createDate", document.getCreateDate())
                        .list();
                int listSize = 0;
                if (newList.size() == 0) {
                    listSize = 1;
                } else {
                    listSize = newList.size() + 1;
                }
                getEditedEntity().setTaskNumber(document.getDocNumber() + " - " + listSize);
                getEditedEntity().setPoint(document.getPoint());
                getEditedEntity().setCompany(document.getCompany());
                getEditedEntity().setDelay(document.getDelay());
                getEditedEntity().setSalaryElementary(document.getSalaryElementary());
                getEditedEntity().setSalaryHigh(document.getSalaryHigh());
                getEditedEntity().setSalaryMedium(document.getSalaryMedium());
                getEditedEntity().setAddPriseExpendableMaterial(document.getAddPriseExpendableMaterial());
                getEditedEntity().setEmployees(document.getEmployeesMap());
                getEditedEntity().setTypeOfCostFormation(document.getTypeOfCostFormation());
                getEditedEntity().setFixedCostForCleaning(document.getFixedCostForCleaning());
                getEditedEntity().setFullCost(document.getFullCost());
                getEditedEntity().setCostPerHour(document.getCostPerHour());
                getEditedEntity().setTaskTimePlane(document.getTaskTimePlane());

                List<PositionWrapper> cleaningMapList = document.getCleaningMap();
                List<InventoryWrapper> inventoryWrapperList = document.getInventoryMap();

                cleaningMapDc.getMutableItems().clear();
                document.getCleaningMap().forEach(wrapper -> {
                    PositionWrapper positionWrapper = metadata.getTools().copy(wrapper);

                    positionWrapper.setId(UUID.randomUUID());
                    positionWrapper.setTaskDocuments(null);
                    positionWrapper.setTask(getEditedEntity());

                    cleaningMapDc.getMutableItems().add(positionWrapper);
                    cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
                });

                for (InventoryWrapper inventoryWrapper : inventoryWrapperList) {
                    InventoryWrapper newInventoryWrapper = metadata.create(InventoryWrapper.class);
                    newInventoryWrapper.setInventory(inventoryWrapper.getInventory());
                    newInventoryWrapper.setQuantityInventory(1);
                    newInventoryWrapper.setTask(this.getEditedEntity());
                    newInventoryWrapper.setTaskDocuments(null);

                    inventoryDc.getMutableItems().add(newInventoryWrapper);
                }
            }
        }
    }

    @Subscribe("excludePosition")
    public void onExcludePositionClick(Button.ClickEvent event) {
        renumberAllPositions(cleaningMapTable.getSingleSelected());
        cleaningMapDc.getMutableItems().get(cleaningMapDc.getItems().indexOf(cleaningMapTable.getSingleSelected())).setTemplate(null);
        cleaningMapDc.getMutableItems().remove(cleaningMapTable.getSingleSelected());
    }

    @Subscribe(id = "inventoryDc", target = Target.DATA_CONTAINER)
    public void onInventoryDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<InventoryWrapper> event) {
        if (event.getProperty().equals("quantityInventory")) {

            if (event.getItem().getInventory().getSerialNumber() != null) {
                notifications.create().withDescription("You cannot set the quantity to unique inventory").show();
                event.getItem().setQuantityInventory(1);
            }
        }
    }

    @Subscribe("addInventory")
    public void onAddInventoryClick(Button.ClickEvent event) {
        AvaibleInventoryBrowse selectInventory = screenBuilders.lookup(Inventory.class, this)
                .withScreenClass(AvaibleInventoryBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {

                    Inventory inventory = e.getScreen().getSelectedInventory();

                    StandardCloseAction closeAction = (StandardCloseAction) e.getCloseAction();


                    if (inventory != null && closeAction.getActionId().equals("select")) {
                        InventoryWrapper inventoryWrapper = metadata.create(InventoryWrapper.class);
                        inventoryWrapper.setInventory(inventory);
                        inventoryWrapper.setQuantityInventory(1);
                        inventoryWrapper.setTask(this.getEditedEntity());


                        inventoryDc.getMutableItems().add(inventoryWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();

        selectInventory.show();
    }

    @Subscribe("addPosition")
    public void onAddPositionClick(Button.ClickEvent event) {
        mainSplit.setSplitPosition(65);
        addPosition.setVisible(false);
        ok.setVisible(true);
    }

    @Subscribe("ok")
    public void onOkClick(Button.ClickEvent event) {
        mainSplit.setSplitPosition(100);
        addPosition.setVisible(true);
        ok.setVisible(false);
    }

    @Subscribe("cleaningMapPositionAddRoom")
    public void onCleaningMapPositionAddRoomClick(Button.ClickEvent event) {
        RoomBrowse selectRoom = screenBuilders.lookup(Room.class, this)
                .withScreenClass(RoomBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    if (((StandardCloseAction) e.getCloseAction()).getActionId().equals("select")) {
                        Room room = e.getScreen().getSelectedRoom();

                        for (PositionWrapper wrapper : cleaningMapDc.getItems()) {
                            if (wrapper.getRoomName().equals(room)) {
                                dialogs.createExceptionDialog()
                                        .withCaption(messageBundle.getMessage("warning"))
                                        .withThrowable(new Throwable())
                                        .withMessage(messageBundle.getMessage("roomAlreadyAdded"))
                                        .show();
                                return;
                            }
                        }

                        PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                        positionWrapper.setPosition(null);
                        positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                .stream()
                                .filter(wrapper -> wrapper.getRoomName().equals(room))
                                .count() + 1);
                        positionWrapper.setRoomName(room);
                        positionWrapper.setTask(this.getEditedEntity());

                        cleaningMapDc.getMutableItems().add(positionWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();
        selectRoom.show();
    }

    @Subscribe("cleaningMapTable")
    public void onCleaningMapTableSelection(Table.SelectionEvent<PositionWrapper> event) {
        setEnabledForButtons();
    }

    private void setEnabledForButtons() {
        PositionWrapper wrapper = cleaningMapTable.getSingleSelected();
        cleaningMapPositionUp.setEnabled(wrapper != null && wrapper.getPriorityCleaningPosition() != 1);

        int lastIndex = (int) cleaningMapDc.getItems()
                .stream()
                .filter(positionWrapper -> {
                    if (wrapper != null) {
                        return positionWrapper.getRoomName().equals(wrapper.getRoomName());
                    }
                    return false;
                })
                .count();
        cleaningMapPositionDown.setEnabled(
                wrapper != null && wrapper.getPriorityCleaningPosition() != lastIndex
        );

        if (wrapper == null) {
            excludePosition.setEnabled(false);
            cleaningMapPositionUp.setEnabled(false);
            cleaningMapPositionDown.setEnabled(false);
        } else {
            excludePosition.setEnabled(true);
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        try {


            if (googleCalendarEventIdDc.getMutableItems().size() > 0) {
                for (GoogleCalendarEventId id : googleCalendarEventIdDc.getMutableItems()) {
                    try {
                        googleCalendarService.removeEvent(id.getEventId());
                    } catch (IOException | GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (GoogleCalendarEventId id : googleCalendarEventIdDc.getMutableItems()) {
                id.setTask(null);
            }
            getEditedEntity().setGoogleCalendarEventId(null);

            String summary = "Task for cleaning:" + Objects.requireNonNull(companyField.getValue()).getName();
            String location = Objects.requireNonNull(pointField.getValue()).getCity() + " " + pointField.getValue().getStreet();
            String description = "Get to object: " + pointField.getValue().getGetToObject() + ". Object access" + pointField.getValue().getObjectAccess();
            String startDateTime = dateOfCompletionField.getValue().atStartOfDay().toString();
            String endDateTime = dateOfCompletionField.getValue().atStartOfDay().plusHours(1).toString();
            String timeZone = TimeZone.getDefault().getID();

            for (Employee employee : employeesDc.getMutableItems()) {
                String email = employee.getEmail();
                try {
                    String eventId = googleCalendarService.getEvents(summary, location, description, startDateTime, endDateTime, email, timeZone);


                    GoogleCalendarEventId googleCalendarEventId = metadata.create(GoogleCalendarEventId.class);

                    googleCalendarEventId.setEventId(eventId);
                    googleCalendarEventId.setTask(getEditedEntity());

                    googleCalendarEventIdDc.getMutableItems().add(googleCalendarEventId);
                } catch (IOException | GeneralSecurityException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
        googleCalendarEventIdDc.getItems().forEach(id -> {
            event.getDataContext().merge(id);
        });

        cleaningMapDc.getItems().forEach(wrapper -> {
            event.getDataContext().merge(wrapper);
        });

        inventoryDc.getItems().forEach(wrapper -> {
            event.getDataContext().merge(wrapper);
        });
    }

    @Subscribe("cleaningMapPositionUp")
    public void onCleaningMapPositionUpClick(Button.ClickEvent event) {
        if (cleaningMapTable.getSingleSelected() != null) {
            throwUpper(cleaningMapTable.getSingleSelected());
        }
    }

    private void throwUpper(PositionWrapper wrapperToUp) {
        PositionWrapper wrapperToDown = cleaningMapDc.getMutableItems()
                .stream()
                .filter(positionWrapper -> positionWrapper.getRoomName().equals(wrapperToUp.getRoomName()))
                .collect(Collectors.toList())
                .get(wrapperToUp.getPriorityCleaningPosition() - 2);

        wrapperToDown.setPriorityCleaningPosition(wrapperToDown.getPriorityCleaningPosition() + 1);
        wrapperToUp.setPriorityCleaningPosition(wrapperToUp.getPriorityCleaningPosition() - 1);

        cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
        setEnabledForButtons();
    }

    @Subscribe("cleaningMapPositionDown")
    public void onCleaningMapPositionDownClick(Button.ClickEvent event) {
        PositionWrapper selected = cleaningMapTable.getSingleSelected();
        if (selected != null) {
            PositionWrapper wrapperToDown = cleaningMapDc.getItems()
                    .stream()
                    .filter(wrapper -> wrapper.getRoomName().equals(selected.getRoomName()))
                    .collect(Collectors.toList())
                    .get(selected.getPriorityCleaningPosition());
            throwUpper(wrapperToDown);
        }
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

    public LookupPickerField<TaskDocument> getTaskDocumentField() {
        return taskDocumentField;
    }

    @Subscribe("addPriseExpendableMaterialField")
    public void onAddPriseExpendableMaterialFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (addPriseExpendableMaterialField.getValue()) {
            priseExpendableMaterialField.setVisible(true);
        } else {
            priseExpendableMaterialField.setVisible(false);
            priseExpendableMaterialField.clear();
        }

    }

    @Subscribe("inventoryDeliveryRequiredField")
    public void onInventoryDeliveryRequiredFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (inventoryDeliveryRequiredField.getValue()) {
            responsibleForTheDeliveryOfInventoryField.setVisible(true);
            costOfDeliveryField.setVisible(true);
        } else {
            responsibleForTheDeliveryOfInventoryField.setVisible(false);
            costOfDeliveryField.setVisible(false);
            costOfDeliveryField.clear();
            responsibleForTheDeliveryOfInventoryField.clear();
        }
    }

    @Subscribe("addTemporaryEmployees")
    public void onAddTemporaryEmployeesClick(Button.ClickEvent event) {
        TemporaryEmployeeBrowse selectedEmployee;
        selectedEmployee = screenBuilders.lookup(Employee.class, this)
                .withScreenClass(TemporaryEmployeeBrowse.class)
                .withOpenMode(OpenMode.THIS_TAB)
                .withAfterCloseListener(e -> {

                    if (((StandardCloseAction) e.getCloseAction()).getActionId().equals("select")) {
                        Employee employee1 = e.getScreen().getSelectedEmployee();
                        for (Employee employee : employeesDc.getItems()) {
                            if (employee.getId().equals(employee1.getId())) {
                                dialogs.createExceptionDialog()
                                        .withCaption(messageBundle.getMessage("warning"))
                                        .withThrowable(new Throwable())
                                        .withMessage(messageBundle.getMessage("employeeAlreadyAdded"))
                                        .show();
                                return;
                            }

                        }
                        employeesDc.getMutableItems().add(employee1);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();
        selectedEmployee.show();
    }

    @Subscribe("createCompany")
    public void onCreateCompanyClick(Button.ClickEvent event) {
        CreateCompanyScreen screen = screens.create(CreateCompanyScreen.class);
        screen.addAfterCloseListener(e -> {
                    if (((StandardCloseAction) e.getCloseAction()).getActionId().equals("close")) {

                        e.getSource().getWindow().getComponent("companyNameField");
                    }
                }
        );

        screens.show(screen);
    }

}