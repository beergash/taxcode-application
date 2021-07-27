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
    public void testGetBirthDateSubset() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19800223" );
        String subset = registryDataService.getBirthYearSubset(date);
        Assert.assertEquals("80", subset);
    }

    @Test(expected = ValidationException.class)
    public void testGetBirthDateSubsetCaseDateNull() {
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
    public void testGetBirthDaySubsetCaseFeale() throws ParseException {
        Date date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19800223");
        String subset = registryDataService.getBirthDaySubset(date, GenderEnum.F);
        Assert.assertEquals("63", subset);
    }

    @Test(expected = ValidationException.class)
    public void testGetBirthDaySubsetDateNull() {
        registryDataService.getBirthDaySubset(null, null);
    }
}
