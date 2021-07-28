package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.data.entity.City;
import it.aresta.taxcodeApplication.data.repository.CityRepository;
import it.aresta.taxcodeApplication.exception.CityServiceException;
import it.aresta.taxcodeApplication.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class of @{@link DatabaseCityCodeService}
 *
 * @author A.Aresta
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseCityCodeServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private DatabaseCityCodeService dbCityCodeService;

    @Test
    public void testGetCatastoCodeFromName() {
        final String name = "MOCK_CITY";
        final String mockCatastoCode = "TEST_CATASTO_CODE";
        City city = new City();
        city.setName(name);
        city.setCatastoCode(mockCatastoCode);
        Mockito.when(cityRepository.findByName(name))
                .thenReturn(city);
        String result = dbCityCodeService.getCatastoCodeFromName(name);
        Assert.assertEquals(mockCatastoCode, result);
    }

    @Test(expected = CityServiceException.class)
    public void testGetCatastoCodeFromNameCaseNotFound() {
        dbCityCodeService.getCatastoCodeFromName("XXXXX");
    }

    @Test(expected = ValidationException.class)
    public void testGetCatastoCodeFromNameCaseInputNull() {
        dbCityCodeService.getCatastoCodeFromName(null);
    }

    @Test
    public void testGetNameFromCatastoCode() {
        final String mockName = "MOCK_CITY";
        final String catastoCode = "MOCK_CATASTO";
        City city = new City();
        city.setName(mockName);
        city.setCatastoCode(catastoCode);
        Mockito.when(cityRepository.findByCatastoCode(catastoCode))
                .thenReturn(city);
        String result = dbCityCodeService.getNameFromCatastoCode(catastoCode);
        Assert.assertEquals(mockName, result);
    }

    @Test(expected = CityServiceException.class)
    public void testGetNameFromCatastoCodeCaseNotFound() {
        dbCityCodeService.getNameFromCatastoCode("XXXXX");
    }

    @Test(expected = ValidationException.class)
    public void testGetNameFromCatastoCodeCaseInputNull() {
        dbCityCodeService.getNameFromCatastoCode(null);
    }
}
