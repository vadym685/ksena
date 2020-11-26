package com.company.ksena.web.screens.taskCalendar;

import com.company.ksena.entity.task.Task;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.TimeSource;
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
    private TimeSource timeSource;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private DataManager dataManager;
//

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

        generateEvents(firstDayDate,endDayDate);
    }


    private void generateEvents(Date firstDayDate, Date endDayDate) {
        String[] captions = {"Training", "Development", "Design", "Weekend", "Party"};
        String[] descriptions = {
                "Student training",
                "Platform development",
                "UI development",
                "Weekend",
                "Party with friends"
        };

        List<Task> newList = dataManager.load(Task.class)
                .query("select e from ksena_Task e where (e.dateOfCompletion >= :firstDayDate) and (e.dateOfCompletion <= :endDayDate)")
                .parameter("firstDayDate", firstDayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .parameter("endDayDate", endDayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .view("task-view")
                .list();


        for (Task task : newList) {
            try {
                String planedTime = "00:01";
                if (!task.getTaskTimePlane().toString().equals("")){
                    planedTime = task.getTaskTimePlane().toString();
                }
                generateEvent(task.getCompany().getName(),
                        planedTime,
                        task.getDateOfCompletion().atStartOfDay().toString().replace("T"," "),
                        task.getDateOfCompletion().atStartOfDay().toString().replace("T"," "),
                        true,
                        "event-blue");
            } catch (Exception ignored) {
            }
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