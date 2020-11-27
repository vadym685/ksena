package com.company.ksena.web.screens.taskCalendar;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskStatus;
import com.company.ksena.web.screens.task.TaskEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.calendar.ListCalendarEventProvider;
import com.haulmont.cuba.gui.components.calendar.SimpleCalendarEvent;
import com.haulmont.cuba.gui.screen.*;


import javax.inject.Inject;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@UiController("ksena_Taskcalendar")
@UiDescriptor("taskCalendar.xml")
public class Taskcalendar extends Screen {

    @Inject
    private Calendar calendar;

    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onInit(InitEvent event) {
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


//    @Subscribe
//    public void onBeforeShow(BeforeShowEvent event) {
//
//        YearMonth month = YearMonth.now();
//        LocalDate firstDay = month.atDay(1);
//        LocalDate endDay = month.atEndOfMonth();
//
//        ZoneId defaultZoneId = ZoneId.systemDefault();
//        Date firstDayDate = Date.from(firstDay.atStartOfDay(defaultZoneId).toInstant());
//        Date endDayDate = Date.from(endDay.atStartOfDay(defaultZoneId).toInstant());
//
//        calendar.setStartDate(firstDayDate);
//        calendar.setEndDate(endDayDate);
//    }
}