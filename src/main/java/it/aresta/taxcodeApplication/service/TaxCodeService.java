package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.model.GenderEnum;
import it.aresta.taxcodeApplication.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Provides services to generate taxCode
 * @author A.Aresta
 */
@Service
public class TaxCodeService {

    @Autowired
    private RegistryDataService registryDataService;

    @Autowired
    private CharacterControlService characterControlService;

    @Autowired
    @Qualifier("csvCityCodeService")
    private CityCodeService cityCodeService;

    @Autowired
    private AgenziaEntrateService agenziaEntrateService;

    /**
     * Generates taxCode from person data applying taxcode rule
     * @param person
     * @return
     */
    public String generateTaxCodeFromPersonalData(Person person) {
        String taxCode = registryDataService.getSubsetCharactersFromName(person.getSurname(), false) +
                registryDataService.getSubsetCharactersFromName(person.getName(), true) +
                registryDataService.getBirthYearSubset(person.getBirthDate()) +
                registryDataService.getMonthSubset(person.getBirthDate()) +
                registryDataService.getBirthDaySubset(person.getBirthDate(), GenderEnum.valueOf(person.getGender())) +
                cityCodeService.getCatastoCodeFromName(person.getBirthCity());
        String characterControl = characterControlService.calculateCharacterControl(taxCode);
        if (agenziaEntrateService.isExistingTaxCode(taxCode)) {
            String replacesTaxCode = characterControlService.replaceNumbersForOmocodiaCases(taxCode);
            characterControl = characterControlService.calculateCharacterControl(replacesTaxCode);
        }
        return taxCode + characterControl;
    }

    /**
     * Retrieves personal data (birth date, birth city and gender) from tax code
     * @param taxCode
     * @return
     */
    public Person getPersonalDataFromTaxCode(String taxCode) {
        Person person = new Person();
        Date birthDate = registryDataService.getBirthDateFromTaxCode(taxCode);
        String gender = registryDataService.getGenderFromTaxCode(taxCode);
        String birthCity = cityCodeService.getNameFromCatastoCode(taxCode.substring(11,15));
        person.setBirthDate(birthDate);
        person.setGender(gender);
        person.setBirthCity(birthCity);
        return person;
    }
}
