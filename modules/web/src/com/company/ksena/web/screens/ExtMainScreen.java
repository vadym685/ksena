package com.company.ksena.web.screens;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskStatus;
import com.company.ksena.service.google_calendar_api_service.GoogleCalendarService;
import com.company.ksena.web.screens.task.TaskEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.calendar.CalendarEventProvider;
import com.haulmont.cuba.gui.components.calendar.SimpleCalendarEvent;
import com.haulmont.cuba.gui.components.mainwindow.AppWorkArea;
import com.haulmont.cuba.gui.components.mainwindow.FoldersPane;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.WebConfig;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.haulmont.cuba.web.widgets.CubaHorizontalSplitPanel;
import com.vaadin.server.Sizeable;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@UiController("extMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen implements Window.HasFoldersPane {
    @Inject
    private SplitPanel foldersSplit;
    @Inject
    private FoldersPane foldersPane;
    @Inject
    private AppWorkArea workArea;
    @Inject
    private WebConfig webConfig;
    @Inject
    private Calendar<Date> calendar;
    @Inject
    private GoogleCalendarService googleCalendarService;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DateField<Date> startDate;
    @Inject
    private DateField<Date> finishDate;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Notifications notifications;

    public ExtMainScreen() {
        addInitListener(this::initLayout);
    }

//    @Subscribe("google")
//    public void onGoogleClick(Button.ClickEvent event) {
//        try {
//            googleCalendarService.getEvents();
//        } catch (IOException | GeneralSecurityException e) {
//            e.printStackTrace();
//        }
//    }
    
    protected void initLayout(@SuppressWarnings("unused") InitEvent event) {
        YearMonth month = YearMonth.now();
        LocalDate firstDay = month.atDay(1);
        LocalDate endDay = month.atEndOfMonth();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date firstDayDate = Date.from(firstDay.atStartOfDay(defaultZoneId).toInstant());
        Date endDayDate = Date.from(endDay.atStartOfDay(defaultZoneId).toInstant());

        calendar.setStartDate(firstDayDate);
        calendar.setEndDate(endDayDate);

        generateEvents(firstDayDate, endDayDate);
        if (webConfig.getFoldersPaneEnabled()) {
            if (webConfig.getFoldersPaneVisibleByDefault()) {
                foldersSplit.setSplitPosition(webConfig.getFoldersPaneDefaultWidth(), SizeUnit.PIXELS);
            } else {
                foldersSplit.setSplitPosition(0);
            }
            CubaHorizontalSplitPanel vSplitPanel = (CubaHorizontalSplitPanel) WebComponentsHelper.unwrap(foldersSplit);
            vSplitPanel.setDefaultPosition(webConfig.getFoldersPaneDefaultWidth() + "px");
            vSplitPanel.setMaxSplitPosition(50, Sizeable.Unit.PERCENTAGE);
            vSplitPanel.setDockable(true);
        } else {
            foldersPane.setEnabled(false);
            foldersPane.setVisible(false);
            foldersSplit.remove(workArea);
            int foldersSplitIndex = getWindow().indexOf(foldersSplit);
            getWindow().remove(foldersSplit);
            getWindow().add(workArea, foldersSplitIndex);
            getWindow().expand(workArea);
        }
    }

    private void generateEvents(Date firstDayDate, Date endDayDate) {
        List<Task> newList = dataManager.load(Task.class)
                .query("select e from ksena_Task e where (e.dateOfCompletion >= :firstDayDate) and (e.dateOfCompletion <= :endDayDate)")
                .parameter("firstDayDate", firstDayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .parameter("endDayDate", endDayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .view("task-view")
                .list();


        for (Task task : newList) {
//            try {
            String styleName;
            String description;
            if (!task.getTaskNumber().equals("")) {
                description = task.getTaskNumber();
            } else {
                continue;
            }

            if (task.getTaskStatus() == TaskStatus.EXECUTED) {
                styleName = "event-green";
            } else if (task.getTaskStatus() == TaskStatus.REJECTED || task.getTaskStatus() == TaskStatus.UNCOMPLETED) {
                styleName = "event-red";
            } else {
                styleName = "event-blue";
            }

            String eventName = "";
            if (task.getPoint() != null) {
                eventName = task.getCompany().getName() + ", " + task.getPoint().getName();
            } else {
                eventName = task.getCompany().getName();
            }

            generateEvent(eventName,
                    description,
                    task.getDateOfCompletion().atStartOfDay().toString().replace("T", " "),
                    task.getDateOfCompletion().atStartOfDay().plusHours(1).toString().replace("T", " "), true, styleName);
//            } catch (Exception ignored) {
//            }
        }
    }

    private void generateEvent(String caption, String description, String start, String end,
                               boolean isAllDay, String stylename) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        generateEvent(
                caption,
                description,
                df.parse(start, new ParsePosition(0)),
                df.parse(end, new ParsePosition(0)),
                isAllDay,
                stylename
        );
    }

    private void generateEvent(String caption, String description, Date start, Date end,
                               boolean isAllDay, String stylename) {
        SimpleCalendarEvent calendarEvent = new SimpleCalendarEvent();
        calendarEvent.setCaption(caption);
        calendarEvent.setDescription(description);
        calendarEvent.setStart(start);
        calendarEvent.setEnd(end);
        calendarEvent.setAllDay(isAllDay);
        calendarEvent.setStyleName(stylename);


        calendar.getEventProvider().addEvent(calendarEvent);
    }


    @Subscribe("calendar")
    public void onCalendarCalendarDateClick(Calendar.CalendarDateClickEvent<Date> event) {

    }
    @Subscribe("calendar")
    public void onCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<Date> event) {
        String taskNumber = event.getCalendarEvent().getDescription();
        List<Task> newList = dataManager.load(Task.class)
                .query("select e from ksena_Task e where e.taskNumber = :taskNumber")
                .parameter("taskNumber", taskNumber)
                .view("task-view")
                .list();
        Task task = null;
        if (newList.size() > 0) {
            task = newList.get(0);
        } else {
            return;
        }

        if (task != null) {
            TaskEdit screen = (TaskEdit) screenBuilders.editor(Task.class, this)
                    .withOpenMode(OpenMode.DIALOG)
                    .withScreenClass(TaskEdit.class)
                    .editEntity(task)
                    .build()
                    .show();
        }
    }
    @Nullable
    @Override
    public FoldersPane getFoldersPane() {
        return foldersPane;
    }

    @Subscribe("refresh")
    public void onRefreshClick(Button.ClickEvent event) {

        ZoneId defaultZoneId = ZoneId.systemDefault();

//        Date firstDayDate = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
//        Date endDayDate = Date.from(finishDate.atStartOfDay(defaultZoneId).toInstant());
        if (startDate.getValue() == null & finishDate.getValue() == null) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("refreshError"))
                    .show();
        } else {

            calendar.setStartDate(Objects.requireNonNull(startDate.getValue()));
            calendar.setEndDate(Objects.requireNonNull(finishDate.getValue()));
        }
    }

    @Subscribe("mainPeriod")
    public void onMainPeriodClick(Button.ClickEvent event) {
        if (startDate.getValue() != null & finishDate.getValue() != null) {
            calendar.setStartDate(Objects.requireNonNull(startDate.getValue()));
            calendar.setEndDate(Objects.requireNonNull(finishDate.getValue()));

            CalendarEventProvider eventProvider = calendar.getEventProvider();
            eventProvider.removeAllEvents();

            generateEvents(startDate.getValue(), finishDate.getValue());
        } else {
            startDate.setValue(null);
            finishDate.setValue(null);

            YearMonth month = YearMonth.now();
            LocalDate firstDay = month.atDay(1);
            LocalDate endDay = month.atEndOfMonth();

            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date firstDayDate = Date.from(firstDay.atStartOfDay(defaultZoneId).toInstant());
            Date endDayDate = Date.from(endDay.atStartOfDay(defaultZoneId).toInstant());

            CalendarEventProvider eventProvider = calendar.getEventProvider();
            eventProvider.removeAllEvents();

            generateEvents(firstDayDate, endDayDate);
            calendar.setStartDate(firstDayDate);
            calendar.setEndDate(endDayDate);
        }
    }
}