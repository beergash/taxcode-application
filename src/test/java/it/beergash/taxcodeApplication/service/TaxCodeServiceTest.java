package it.beergash.taxcodeApplication.service;

import it.beergash.taxcodeApplication.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Test class of @{@link TaxCodeService}
 *
 * @author A.Aresta
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxCodeServiceTest {

    @Mock
    private RegistryDataService registryDataService;

    @Spy
    private CharacterControlService characterControlService;

    @Mock
    private CityCodeService cityCodeService;

    @Mock
    private AgenziaEntrateService agenziaEntrateService;

    @InjectMocks
    private TaxCodeService taxCodeService;

    private Person person;

    @Before
    public void init() throws ParseException {
        person = new Person();
        person.setName("Mario");
        person.setSurname("Rossi");
        person.setGender("M");
        person.setBirthDate(new SimpleDateFormat( "yyyyMMdd" ).parse( "19910406"));
        person.setBirthCity("Milano");
    }

    /**
     * Checks string returned in case not already existing tax code
     * and verifies there is only one iteraction of calculation of character control
     */
    @Test
    public void testGenerateTaxCodeFromPersonalData() {
        mockServices();
        Mockito.when(agenziaEntrateService.isExistingTaxCode(Mockito.anyString()))
                .thenReturn(false);
        String taxCode = taxCodeService.generateTaxCodeFromPersonalData(person);
        Mockito.verify(characterControlService, Mockito.times(1))
                .calculateCharacterControl(Mockito.anyString());
        Assert.assertEquals("XXXYYY91P06ABCDZ", taxCode);
    }

    /**
     * Checks string returned in case already existing tax code
     * and verifies there are iteractions of calculation of character control
     * because in case of omocodia character control must be recalculated
     */
    @Test
    public void testGenerateTaxCodeFromPersonalDataOmocodiaCase() {
        mockServices();
        Mockito.when(agenziaEntrateService.isExistingTaxCode(Mockito.anyString()))
                .thenReturn(true);
        String taxCode = taxCodeService.generateTaxCodeFromPersonalData(person);
        Mockito.verify(characterControlService, Mockito.times(2))
                .calculateCharacterControl(Mockito.anyString());
        Assert.assertEquals("XXXYYY91P06ABCDZ", taxCode);
    }

    @Test
    public void testGetPersonalDataFromTaxCode() throws ParseException {
        Mockito.when(registryDataService.getBirthDateFromTaxCode(Mockito.anyString()))
                .thenReturn(new SimpleDateFormat( "yyyyMMdd" ).parse( "19910406"));
        Mockito.when(registryDataService.getGenderFromTaxCode(Mockito.anyString()))
                .thenReturn("M");
        Mockito.when(cityCodeService.getNameFromCatastoCode(Mockito.anyString()))
                .thenReturn("Venezia");
        Person pers = taxCodeService.getPersonalDataFromTaxCode("ABCDEF12A24LLLLA");
        Calendar cal = Calendar.getInstance();
        cal.setTime(pers.getBirthDate());
        Assert.assertEquals("M", pers.getGender());
        Assert.assertEquals("Venezia", pers.getBirthCity());
        Assert.assertEquals(1991, cal.get(Calendar.YEAR));


    }

    private void mockServices() {
        Mockito.when(registryDataService.getSubsetCharactersFromName(person.getSurname(), false))
                .thenReturn("XXX");
        Mockito.when(registryDataService.getSubsetCharactersFromName(person.getName(), true))
                .thenReturn("YYY");
        Mockito.when(registryDataService.getBirthYearSubset(Mockito.any()))
                .thenReturn("91");
        Mockito.when(registryDataService.getMonthSubset(Mockito.any()))
                .thenReturn("P");
        Mockito.when(registryDataService.getBirthDaySubset(Mockito.any(), Mockito.any()))
                .thenReturn("06");
        Mockito.when(cityCodeService.getCatastoCodeFromName(Mockito.anyString()))
                .thenReturn("ABCD");
        Mockito.when(characterControlService.calculateCharacterControl(Mockito.anyString()))
                .thenReturn("Z");
    }

}
