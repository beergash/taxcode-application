package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.CityServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class of @{@link CsvCityCodeService}
 *
 * @author A.Aresta
 */
@RunWith(MockitoJUnitRunner.class)
public class CsvCityCodeServiceTest {

    @InjectMocks
    private CsvCityCodeService csvCityCodeService;

    @Test
    public void testGetCatastoCodeFromName() {
        String result = csvCityCodeService.getCatastoCodeFromName("Roma");
        Assert.assertEquals("H501", result);
    }

    @Test(expected = CityServiceException.class)
    public void testGetCatastoCodeFromNameCaseNotFound() {
        csvCityCodeService.getCatastoCodeFromName("XXXXX");
    }

    @Test
    public void testGetNameFromCatastoCode() {
        String result = csvCityCodeService.getNameFromCatastoCode("F205");
        Assert.assertEquals("MILANO", result);
    }

    @Test(expected = CityServiceException.class)
    public void testGetNameFromCatastoCodeCaseNotFound() {
        csvCityCodeService.getNameFromCatastoCode("XXXXX");
    }
}
