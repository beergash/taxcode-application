package it.aresta.taxcodeApplication.utils;

import it.aresta.taxcodeApplication.exception.ValidationException;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * Utility class for date data
 *
 * @author A.Aresta
 */
public class DateUtils {

    /**
     * extracts year from date
     * @param date
     * @return
     */
    public static int extractYear(Date date) {
        return extractPartFromDate(date, Calendar.YEAR);
    }

    /**
     * extracts month from date
     * @param date
     * @return
     */
    public static int extraxtMonth(Date date) {
        return extractPartFromDate(date, Calendar.MONTH) + 1;
    }

    /**
     * extracts day from date
     * @param date
     * @return
     */
    public static int extractDay(Date date) {
        return extractPartFromDate(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * converts date to calendar and exctracts the request date part
     * @param date
     * @param datePart
     * @return
     */
    private static int extractPartFromDate(Date date, int datePart) {
        Optional.ofNullable(date)
                .orElseThrow(() -> new ValidationException("Date must not be null!"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(datePart);
    }
}
