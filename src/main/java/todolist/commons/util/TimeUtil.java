package todolist.commons.util;

import java.time.LocalDateTime;

public class TimeUtil extends StringUtil{
    
    private static final int[] DAYS_NON_LEAP = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] DAYS_LEAP = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] MONTH_ABBRE = {"jan", "feb", "mar", "apr", 
            "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    private static final String[] DAYS_OF_WEEK = {"mon", "tue", "wed", "thu", 
            "fri", "sat", "sun"};
    private static final int YEAR_ARG = 2;
    private static final int MONTH_ARG = 1;
    
    //Matching the format DD/MM/YYYY HH:MM
    private static final String TIME_FORMAT_REGEX_1 = "^\\d{1,2}/\\d{1,2}/\\d{4}"
            + "(\\s(\\d{1,2}:\\d{2}|\\d{1,2}(?i)([ap]m))){0,1}$";
    
    //Matching the format DD MM YYYY HH:MM
    private static final String TIME_FORMAT_REGEX_2 = "^\\d{1,2}\\s(?i)(\\w{3,9})\\s\\d{4}"
            + "(\\s(\\d{1,2}:\\d{2}|\\d{1,2}(?i)([ap]m))){0,1}$";
    
    //Matching the format DD MM HH:MM
    private static final String TIME_FORMAT_REGEX_3 = "^\\d{1,2}\\s(?i)(\\w{3,9})"
            + "(\\s(\\d{1,2}:\\d{2}|\\d{1,2}(?i)([ap]m))){0,1}$";
    
    //Matching the format "Day HH:MM"
    private static final String TIME_FORMAT_REGEX_4 = "^(?i)(\\w{3,9}\\s){1,2}"
            + "(\\d{1,2}:\\d{2}|\\d{1,2}(?i)([ap]m))$";
    
    //Matching the format like "next tuesday"
    private static final String TIME_FORMAT_REGEX_5 = "(\\w{3,9}\\s){1,7}?(\\w{3,9})";
    
    public static boolean isValidFormat(String timeArg) {
        return timeArg.matches(TIME_FORMAT_REGEX_1) ||
                timeArg.matches(TIME_FORMAT_REGEX_2) ||
                timeArg.matches(TIME_FORMAT_REGEX_3) ||
                timeArg.matches(TIME_FORMAT_REGEX_4) ||
                timeArg.matches(TIME_FORMAT_REGEX_5);
    }
    
    public static boolean hasNumberDay(String timeArg) {
        return timeArg.matches(TIME_FORMAT_REGEX_1) ||
                timeArg.matches(TIME_FORMAT_REGEX_2) ||
                timeArg.matches(TIME_FORMAT_REGEX_3);
    }
    
    public static boolean isValidMonthDay(String timeArg) {
        if (hasNumberDay(timeArg)) {
            int year = parseYear(timeArg);
            int month = parseMonth(timeArg);
            int day = parseDay(timeArg);
            if (isLeapYear(year)) {
                return day <= DAYS_LEAP[month-1];
            }
            else {
                return day <= DAYS_NON_LEAP[month-1];
            }
        }
        return true;
        
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private static int parseDay(String timeArg) {
        if (timeArg.matches(TIME_FORMAT_REGEX_1)) {
            String[] dateTime = timeArg.split(" ");
            String[] dates = dateTime[0].split("/");
            return Integer.parseInt(dates[0]);
        } else {
            String[] dateTime = timeArg.split(" ");
            return Integer.parseInt(dateTime[0]);
        }
    }

    private static int parseYear(String timeArg) {
        if (timeArg.matches(TIME_FORMAT_REGEX_1)) {
            String[] dateTime = timeArg.split(" ");
            String[] dates = dateTime[0].split("/");
            return Integer.parseInt(dates[0]);
        } else if (timeArg.matches(TIME_FORMAT_REGEX_2)){
            String[] dateTime = timeArg.split(" ");
            return Integer.parseInt(dateTime[2]);
        } else {
            return LocalDateTime.now().getYear();
        }
    }
    
    private static int parseMonth(String timeArg) {
        if (timeArg.matches(TIME_FORMAT_REGEX_1)) {
            String[] dateTime = timeArg.split(" ");
            String[] dates = dateTime[0].split("/");
            return Integer.parseInt(dates[1]);
        } else if (timeArg.matches(TIME_FORMAT_REGEX_2)) {
            String[] dateTime = timeArg.split(" ");
            return getMonthIndex(dateTime[1]);
        } else {
            String[] dateTime = timeArg.split(" ");
            return getMonthIndex(dateTime[1]);
        }
    }
    
    private static int getMonthIndex(String monthArg) {
        for (int i = 0; i < 12; i++) {
            if (monthArg.toLowerCase().contains(MONTH_ABBRE[i])) {
                return i+1;
            }
        }
        return 0;
    }
    
    private static int getWeekDayIndex(String dayArg) {
        for (int i = 0; i < 12; i++) {
            if (dayArg.toLowerCase().contains(DAYS_OF_WEEK[i])) {
                return i+1;
            }
        }
        return 0;
    }
    
}
