package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxCodeService {

    @Autowired
    private RegistryDataService registryDataService;

    public String generateTaxCodeFromPersonalData(Person person) {
        String surnameSubset = registryDataService.getSubsetCharactersFromName(person.getSurname());
        String nameSubset = registryDataService.getSubsetCharactersFromName(person.getName());
        return surnameSubset +
               nameSubset;
    }

    // todo
    public Person getPersonalDataFromTaxCode(String taxCode) {
        return null;
    }
}
