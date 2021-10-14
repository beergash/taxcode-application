package it.beergash.taxcodeApplication.utils;

import it.beergash.taxcodeApplication.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Test class of @{@link DateUtils}
 *
 * @author A.Aresta
 */
public class DateUtilsTest {

    @Test
    public void testExtractYear() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19910406");
        int result = DateUtils.extractYear(date);
        Assert.assertEquals(1991, result);
    }

    @Test(expected = ValidationException.class)
    public void testExtractYearCaseNull() {
        DateUtils.extractYear(null);
    }

    @Test
    public void testExtractMonth() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19910406");
        int result = DateUtils.extraxtMonth(date);
        Assert.assertEquals(4, result);
    }

    @Test(expected = ValidationException.class)
    public void testExtractMonthCaseNull() {
        DateUtils.extraxtMonth(null);
    }

    @Test
    public void testExtractDay() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19910406");
        int result = DateUtils.extractDay(date);
        Assert.assertEquals(6, result);
    }

    @Test(expected = ValidationException.class)
    public void testExtractDayCaseNull() {
        DateUtils.extractDay(null);
    }
}
