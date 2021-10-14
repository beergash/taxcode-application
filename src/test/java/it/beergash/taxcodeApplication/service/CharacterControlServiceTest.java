package it.beergash.taxcodeApplication.service;

import it.beergash.taxcodeApplication.exception.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class of @{@link CharacterControlService}
 *
 * @author A.Aresta
 */
@RunWith(MockitoJUnitRunner.class)
public class CharacterControlServiceTest {

    @InjectMocks
    private CharacterControlService characterControlService;

    @Before
    public void initTest() {
        characterControlService.init();
    }

    @Test
    public void testCalculateCharacterControl() {
        String taxCode = "ABC12X";
        String result = characterControlService.calculateCharacterControl(taxCode);
        Assert.assertEquals("K", result);
    }

    @Test(expected = ValidationException.class)
    public void testCalculateCharacterControlCaseNull() {
        String result = characterControlService.calculateCharacterControl(null);
    }

    @Test
    public void testReplaceNumbersForOmocodiaCases() {
        String taxCode = "ABCDEF12L28M419";
        String result = characterControlService.replaceNumbersForOmocodiaCases(taxCode);
        Assert.assertEquals("ABCDEFMNLNUMQMV", result);
    }

    @Test(expected = ValidationException.class)
    public void testReplaceNumbersForOmocodiaCasesNullTaxCode() {
        characterControlService.replaceNumbersForOmocodiaCases(null);
    }
}
