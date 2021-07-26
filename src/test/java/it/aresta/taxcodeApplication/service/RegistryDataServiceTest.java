package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistryDataServiceTest {

    @InjectMocks
    private RegistryDataService registryDataService;

    @Test
    public void testGetSubsetNameNormalCase() {
        String name = "Kevin";
        String subset = registryDataService.getSubsetCharactersFromName(name);
        Assert.assertEquals(subset, "KVN");
    }

    @Test
    public void testGetSubsetInsufficientConsonants() {
        String name = "Mario";
        String subset = registryDataService.getSubsetCharactersFromName(name);
        Assert.assertEquals(subset, "MRA");
    }

    @Test
    public void testGetSubsetNameCaseNameShorter3() {
        String name = "AS";
        String subset = registryDataService.getSubsetCharactersFromName(name);
        Assert.assertEquals("SAX", subset);
    }

    @Test(expected = ValidationException.class)
    public void testGetSubsetNameCaseNameNull() {
        registryDataService.getSubsetCharactersFromName(null);
    }
}
