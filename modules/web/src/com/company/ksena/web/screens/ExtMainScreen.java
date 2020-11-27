package com.company.ksena.web.screens;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskStatus;
import com.company.ksena.web.screens.task.TaskEdit;
import com.company.ksena.web.screens.taskCalendar.Taskcalendar;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.calendar.SimpleCalendarEvent;
import com.haulmont.cuba.gui.components.mainwindow.AppWorkArea;
import com.haulmont.cuba.gui.components.mainwindow.FoldersPane;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
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
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    public ExtMainScreen() {
        addInitListener(this::initLayout);
    }

    protected void initLayout(@SuppressWarnings("unused") InitEvent event) {
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

        YearMonth month = YearMonth.now();
        LocalDate firstDay = month.atDay(1);
        LocalDate endDay = month.atEndOfMonth();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date firstDayDate = Date.from(firstDay.atStartOfDay(defaultZoneId).toInstant());
        Date endDayDate = Date.from(endDay.atStartOfDay(defaultZoneId).toInstant());

        calendar.setStartDate(firstDayDate);
        calendar.setEndDate(endDayDate);

        generateEvents(firstDayDate, endDayDate);
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

            generateEvent(task.getCompany().getName(),
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

    @Subscribe("mainScreen")
    public void onMainScreenClick(Button.ClickEvent event) {
        YearMonth month = YearMonth.now();
        LocalDate firstDay = month.atDay(1);
        LocalDate endDay = month.atEndOfMonth();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date firstDayDate = Date.from(firstDay.atStartOfDay(defaultZoneId).toInstant());
        Date endDayDate = Date.from(endDay.atStartOfDay(defaultZoneId).toInstant());

        calendar.setStartDate(firstDayDate);
        calendar.setEndDate(endDayDate);

        generateEvents(firstDayDate, endDayDate);
    }
}