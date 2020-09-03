package com.company.ksena.web.utils;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import static com.company.ksena.web.utils.Constants.WAREHOUSE_ID;

public final class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

//    public static String createPointPopupInfo(WayPoint wayPoint) {
//        LOG.info("");
//
//        Map<String, String> dataMap = new TreeMap<>();
//        String brand = "???";
//        try {
//            brand = wayPoint.getBrand().getName();
//        } catch (Exception ex) {
////            LOG.error("", ex);
//        }
//        dataMap.put("1. Бренд", brand);
//
//        String customer = "???";
//        try {
//            customer = wayPoint.getCustomer().getName();
//        } catch (Exception ex) {
////            LOG.error("", ex);
//        }
//        dataMap.put("2. Контрагент", customer);
//
//        dataMap.put("3. Наименование", wayPoint.getName());
//        dataMap.put("4. Баланс", (wayPoint.getVolumeBalance() == null ? "???" : "" + wayPoint.getVolumeBalance()));
//        dataMap.put("5. Макс. объем", (wayPoint.getMaxVolume() == null ? "???" : "" + wayPoint.getMaxVolume()));
//        dataMap.put("6. Дата обновления", (wayPoint.getLastUpdateOfBalance() == null ? "???" : "" + wayPoint.getLastUpdateOfBalance()));
//
//        StringBuilder infoMsg = new StringBuilder();
//        for (String parameter : dataMap.keySet()) {
//            String value = dataMap.get(parameter);
//            infoMsg.append(createMsgItem(parameter, value));
//        }
//
//        return infoMsg.toString();
//    }


    private static String createMsgItem(String parameter, String value) {
        return String.format("<b>%s:<b/> %s<br/>", parameter, value);
    }

    public static TimeZone getUserTimeZone(UserSessionSource userSessionSource) {
        User user = userSessionSource.getUserSession().getUser();
        String timeZone = user.getTimeZone();

        if (timeZone == null || timeZone.isEmpty()) {
            UserSession session = userSessionSource.getUserSession();

            if (session.getTimeZone() == null)
                return TimeZone.getDefault();

            return session.getTimeZone();
        }

        return TimeZone.getTimeZone(timeZone);
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date, String timeZone) {
        TimeZone zone = TimeZone.getTimeZone(timeZone);
        return getStartOfDay(date, zone);
    }

    public static Date getStartOfDay(Date date, TimeZone zone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(zone);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static Date getEndOfDay(Date date, String timeZone) {
        TimeZone zone = TimeZone.getTimeZone(timeZone);
        return getEndOfDay(date, zone);
    }

    public static Date getEndOfDay(Date date, TimeZone zone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(zone);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static long parseTime(String timeString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date time = dateFormat.parse(timeString);
        Date startOfDay = getStartOfDay(time);
        long timeLong = time.getTime() - startOfDay.getTime();
        return timeLong / 1000;
    }

    public static long parseDateToUnix(String timeString, String format) throws ParseException {
        return parseDate(timeString, format).getTime() / 1000;
    }

    public static Date parseDate(String timeString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date time = dateFormat.parse(timeString);
        return time;
    }

    // Эта функция парсит время с учётом часвого пояса переводит в юникс 0.
    public static long parseDateWithCurrentZoneWithMillis(String timeString, String pattern, TimeZone timeZone) throws ParseException {

        ZoneId zoneId = timeZone.toZoneId();
//        ZoneId zoneId = ZoneId.of("UTC");

        DateTimeFormatter dateFormat = DateTimeFormatter
                .ofPattern(pattern)
                .withZone(zoneId);

        ZonedDateTime time = ZonedDateTime.parse(timeString, dateFormat);
        return time.toInstant().toEpochMilli();
    }

    public static String unixToString(long time, String pattern, TimeZone timeZone) {
        ZoneId zoneId = timeZone.toZoneId();
        return unixToString(time, pattern, zoneId);
    }

    public static String unixToString(long time, String pattern, ZoneId zoneId) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);

        ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), zoneId);
        return dateFormat.format(zdt);
    }

    //Перевод текста времени без учета верменной зоны - если в тексте написа 6 часов утро, то epochTime будет как
// 6 часов утра по Гринвичу
    public static long dateTimeStringToEpoch(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZoneOffset zone = ZoneOffset.UTC;

        return LocalDateTime.parse(dateString, formatter)
                .toEpochSecond(zone);
    }

    //Перевод текста времени без учета верменной зоны - если в тексте написа 6 часов утро, то epochTime будет как
// 6 часов утра  по Гринвичу + или - n часов в зависиомсти от временной зоны
    public static long dateTimeStringToEpochWithZone(String dateString, String pattern, TimeZone timeZone) {
        return dateTimeStringToEpochWithZone(dateString, pattern, timeZone.toZoneId());
    }

    public static long dateTimeStringToEpochWithZone(String dateString, String pattern, ZoneId zoneId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        LocalDateTime ldt = LocalDateTime.parse(dateString, formatter);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
        return zdt.toEpochSecond();
    }

    static long R = 6372795; // — средний радиус земного шара.

    public static double calculateDistanceBetweenGeoPoints(org.vaadin.addon.leaflet.shared.Point firstP,
                                                           org.vaadin.addon.leaflet.shared.Point secondP) {
        Point.Double centerPoint = new Point2D.Double(firstP.getLat(), firstP.getLon());
        Point.Double selectedPoint = new Point2D.Double(secondP.getLat(), secondP.getLon());
        return Utils.calculateDistanceBetweenGeoPoints(centerPoint, selectedPoint);
    }

    public static double calculateDistanceBetweenGeoPoints(Point.Double firstP, Point.Double secondP) {
        double rad = 3.14159265358979 / 180;
        double src_lat = firstP.getX() * rad;
        double src_lon = firstP.getY() * rad;
        double dst_lat = secondP.getX() * rad;
        double dst_lon = secondP.getY() * rad;

//        val radius = findRadius(src_lon,dst_lon)
        double distance = R * Math.acos(
                Math.sin((src_lat)) *
                        Math.sin((dst_lat)) +
                        Math.cos((src_lat)) *
                                Math.cos((dst_lat)) *
                                Math.cos((src_lon - dst_lon))
        );
//       LOG.info("firstP.x: ${firstP.x} firstP.y :${firstP.y}\nsecondP.x:${secondP.x} secondP.y:${secondP.y} distance :$distance ")
        return Math.round(distance * 100.0) / 100.0;

//        return calculateDistanceBetween2Points(firstP,secondP)
    }

    public static String dateToFormat(Date date, String format) {
//        LOG.info("");
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String formattedDate = formatter.format(date);

//        LOG.info("Selected date" + formattedDate);
        return formattedDate;
    }

    public static LocalDate convertToLocalDate(Date chosenDate) {
        return convertToLocalDate(chosenDate, ZoneId.of("UTC"));
    }

    public static LocalDate convertToLocalDate(Date chosenDate, TimeZone timeZone) {
        return convertToLocalDate(chosenDate, timeZone.toZoneId());
    }

    public static LocalDate convertToLocalDate(Date chosenDate, ZoneId timeZone) {
        return chosenDate.toInstant()
                .atZone(timeZone)
                .toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date chosenDate, TimeZone timeZone) {
        return convertToLocalDateTime(chosenDate, timeZone.toZoneId());
    }

    public static LocalDateTime convertToLocalDateTime(long chosenDate) {
        return convertToLocalDateTime(new Date(chosenDate), ZoneId.of("UTC"));
    }

    public static LocalDateTime convertToLocalDateTime(Date chosenDate) {
        return convertToLocalDateTime(chosenDate, ZoneId.of("UTC"));
    }


    public static LocalDateTime convertToLocalDateTime(Date chosenDate, ZoneId timeZone) {
        return chosenDate.toInstant()
                .atZone(timeZone)
                .toLocalDateTime();
    }

    public static Date convertLocalDateToDate(LocalDate value) {
        return Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime value) {
        return Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime parseLocalDataTime(String value, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
        return dateTime;
    }


    public static boolean inDateRange(LocalDateTime localValue, Date startRange, Date endRange) {
        LocalDateTime startLocalValue = Utils.convertToLocalDateTime(startRange, ZoneId.of("UTC"));
        LocalDateTime endLocalValue = Utils.convertToLocalDateTime(endRange, ZoneId.of("UTC"));
        return startLocalValue.compareTo(localValue) >= 0 && endLocalValue.compareTo(localValue) <= 0;
    }

    public static boolean inDateRange(Date value, Date startRange, Date endRange) {
        return startRange.getTime() <= value.getTime() && endRange.getTime() >= value.getTime();
    }

    public static boolean inDateRange(Date value, LocalDateTime startRange, LocalDateTime endRange) {
        return inDateRange(
                value,
                Utils.convertLocalDateTimeToDate(startRange),
                Utils.convertLocalDateTimeToDate(endRange)
        );
    }


    public static boolean inDateRange(LocalDateTime value, LocalDateTime startRange, LocalDateTime endRange) {
        return inDateRange(
                Utils.convertLocalDateTimeToDate(value),
                Utils.convertLocalDateTimeToDate(startRange),
                Utils.convertLocalDateTimeToDate(endRange)
        );
    }

    public static boolean inDateRange(LocalDate value, LocalDateTime startRange, LocalDateTime endRange) {
        return inDateRange(
                Utils.convertLocalDateToDate(value),
                Utils.convertLocalDateTimeToDate(startRange),
                Utils.convertLocalDateTimeToDate(endRange)
        );
    }

    public static boolean isWarehouseTask(String taskID) {
        return taskID.equals(WAREHOUSE_ID);
    }

    public static Color getRandomColor() {
        Random rand = new Random();
        // Will produce only bright / light colours:
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        Color randomColor = new Color(r, g, b);
        return randomColor;
    }

    public static Color getRandomLightColor() {
        Random rand = new Random();
        // Will produce only bright / light colours:
        float h = rand.nextInt(100)/100f;

        int min = 35;
        float s = (rand.nextInt(100 - min) + min)/100f;
        float b = (rand.nextInt(100 - min) + min)/100f;

        System.out.println(String.format("h %f s %f b %f",h,s,b));
        int color = Color.HSBtoRGB(h, s, b);
        return new Color(color);
    }


    public static String toHexColor(Color color) {
//            val hex = java.lang.String.format("#%02x%02x%02x%02x", color.alpha, color.red, color.green, color.blue)
        String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        return hex;
    }


    private int searchMatchesOld(String sensorName, String transportName) {
        int matches = 0;
        String[] transportSplit = transportName.split(" ");
        String[] sensorSplit = sensorName.split(" ");
        for (String s1 : transportSplit) {
            for (String s2 : sensorSplit) {
                if (s1.length() <= 1 || s2.length() <= 1)
                    continue;

                if (s1.contains(s2))
                    matches++;
            }
        }
        for (String s2 : sensorSplit) {
            for (String s1 : transportSplit) {
                if (s1.length() <= 1 || s2.length() <= 1)
                    continue;

                if (s2.contains(s1))
                    matches++;
            }
        }
        return matches;
    }

    public static double searchMatches(String sensorName, String transportName) {
        int matches = 0;

        String[] transportSplit = transportName.toLowerCase().split(" ");
        String[] sensorSplit = sensorName.toLowerCase().split(" ");
        for (String s1 : transportSplit) {
            for (String s2 : sensorSplit) {
                if (s1.length() <= 1 || s2.length() <= 1)
                    continue;

                if (s1.contains(s2))
                    matches++;
            }
        }

        double percent = matches * 100.0 / transportSplit.length;

        return percent;
    }


    public static PercentStyle[] styles = {
            new PercentStyle(0.0, 15.0, "one_matches"),
            new PercentStyle(15.0, 30.0, "two_matches"),
            new PercentStyle(30.0, 45.0, "three_matches"),
            new PercentStyle(45.0, 60.0, "four_matches"),
            new PercentStyle(60.0, 75.0, "fife_matches"),
            new PercentStyle(75.0, 100.0, "six_matches")
    };

    public static class PercentStyle {

        public PercentStyle(double min,
                            double max,
                            String style) {
            this.max = max;
            this.min = min;
            this.style = style;
        }

        double min;
        double max;
        String style;

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }
    }
}
