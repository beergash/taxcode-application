package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.ValidationException;
import it.aresta.taxcodeApplication.model.GenderEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Test class of @{@link RegistryDataService}
 *
 * @author A.Aresta
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistryDataServiceTest {

    @InjectMocks
    private RegistryDataService registryDataService;

    @Mock
    private MonthsCodingService monthsCodingService;

    @Test
    public void testGetSubsetName() {
        String name = "Kevin";
        String subset = registryDataService.getSubsetCharactersFromName(name, false);
        Assert.assertEquals(subset, "KVN");
    }

    @Test
    public void testGetSubsetInsufficientConsonants() {
        String name = "Mario";
        String subset = registryDataService.getSubsetCharactersFromName(name, false);
        Assert.assertEquals(subset, "MRA");
    }

    @Test
    public void testGetSubsetNameCaseNameShorter3() {
        String name = "AS";
        String subset = registryDataService.getSubsetCharactersFromName(name, false);
        Assert.assertEquals("SAX", subset);
    }

    @Test
    public void testGetSubsetNameCaseName() {
        String name = "Kevin";
        String subset = registryDataService.getSubsetCharactersFromName(name, true);
        Assert.assertEquals(subset, "KVN");
    }

    @Test
    public void testGetSubsetNameCaseNameMore4Consonant() {
        String name = "Vittorio";
        String subset = registryDataService.getSubsetCharactersFromName(name, true);
        Assert.assertEquals(subset, "VTR");
    }

    @Test(expected = ValidationException.class)
    public void testGetSubsetNameCaseNameNull() {
        registryDataService.getSubsetCharactersFromName(null, false);
    }

    @Test
    public void testGetBirthYearSubset() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19800223" );
        String subset = registryDataService.getBirthYearSubset(date);
        Assert.assertEquals("80", subset);
    }

    @Test(expected = ValidationException.class)
    public void testGetBirthYearSubsetCaseDateNull() {
        registryDataService.getBirthYearSubset(null);
    }

    @Test
    public void testGetBirthMonth() throws ParseException {
        HashMap<Integer, String> monthsCode = new HashMap<>();
        monthsCode.put(2, "MESE_TEST");
        Mockito.when(monthsCodingService.getMonthsCodes()).thenReturn(monthsCode);
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19800223" );
        String subset = registryDataService.getMonthSubset(date);
        Assert.assertEquals("MESE_TEST", subset);
    }

    @Test(expected = ValidationException.class)
    public void testGetBirthMonthSubsetCaseDateNull() {
        registryDataService.getMonthSubset(null);
    }

    @Test
    public void testGetBirthDaySubsetCaseMale() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19800223");
        String subset = registryDataService.getBirthDaySubset(date, GenderEnum.M);
        Assert.assertEquals("23", subset);
    }

    @Test
    public void testGetBirthDaySubsetCaseFemale() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19800223");
        String subset = registryDataService.getBirthDaySubset(date, GenderEnum.F);
        Assert.assertEquals("63", subset);
    }

    @Test(expected = ValidationException.class)
    public void testGetBirthDaySubsetDateNull() {
        registryDataService.getBirthDaySubset(null, null);
    }

    @Test
    public void testGetBirthDateFromTaxCode() {
        HashMap<Integer, String> monthsCode = new HashMap<>();
        monthsCode.put(12, "T");
        Mockito.when(monthsCodingService.getMonthsCodes()).thenReturn(monthsCode);
        final String taxCode = "ABCDEF84T18";
        Date date = registryDataService.getBirthDateFromTaxCode(taxCode);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Assert.assertEquals(1984, cal.get(Calendar.YEAR));
        Assert.assertEquals(11, cal.get(Calendar.MONTH));
        Assert.assertEquals(18, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetBirthDateFromTaxCodeCase2000Born() {
        HashMap<Integer, String> monthsCode = new HashMap<>();
        monthsCode.put(12, "T");
        Mockito.when(monthsCodingService.getMonthsCodes()).thenReturn(monthsCode);
        final String taxCode = "ABCDEF13T18";
        Date date = registryDataService.getBirthDateFromTaxCode(taxCode);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Assert.assertEquals(2013, cal.get(Calendar.YEAR));
        Assert.assertEquals(11, cal.get(Calendar.MONTH));
        Assert.assertEquals(18, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetBirthDateFromTaxCodeCaseFemale() {
        HashMap<Integer, String> monthsCode = new HashMap<>();
        monthsCode.put(12, "T");
        Mockito.when(monthsCodingService.getMonthsCodes()).thenReturn(monthsCode);
        final String taxCode = "ABCDEF84T55";
        Date date = registryDataService.getBirthDateFromTaxCode(taxCode);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Assert.assertEquals(1984, cal.get(Calendar.YEAR));
        Assert.assertEquals(11, cal.get(Calendar.MONTH));
        Assert.assertEquals(15, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test(expected = ValidationException.class)
    public void testGetBirthDateFromTaxCodeCaseNotValidMonth() {
        final String taxCode = "ABCDEF84T55";
        registryDataService.getBirthDateFromTaxCode(taxCode);
    }

    @Test
    public void testGetGenderFromTaxCodeCaseMale() {
        final String taxCode = "ABCDEF84T15";
        String result = registryDataService.getGenderFromTaxCode(taxCode);
        Assert.assertEquals(GenderEnum.M.name(), result);
    }

    @Test
    public void testGetGenderFromTaxCodeCaseFemale() {
        final String taxCode = "ABCDEF84T45";
        String result = registryDataService.getGenderFromTaxCode(taxCode);
        Assert.assertEquals(GenderEnum.F.name(), result);
    }

    @Test(expected = ValidationException.class)
    public void testGetGenderFromTaxCodeNotValidBirthDay() {
        registryDataService.getGenderFromTaxCode("ABCDEF84T85");
    }
}
