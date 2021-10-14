package it.beergash.taxcodeApplication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.beergash.taxcodeApplication.model.Person;
import it.beergash.taxcodeApplication.model.TaxCode;
import it.beergash.taxcodeApplication.service.TaxCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest Controller exposing taxcode apis
 *
 * @author A.Aresta
 */
@RestController
@Api(tags = "TaxCodeController")
public class TaxCodeController {

    @Autowired
    private TaxCodeService taxCodeService;

    /**
     * Generates taxcode by personal data
     * @param person
     * @return
     */
    @PostMapping(value = "generate-taxCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Genera il codice fiscale a partire dai dati anagrafici", response = TaxCode.class)
    public TaxCode getTaxCodeFromPersonalData(@Valid @RequestBody Person person) {
            String taxCode = taxCodeService.generateTaxCodeFromPersonalData(person);
            return new TaxCode(taxCode);
    }

    /**
     * extract informations (birth year, birth city, gender) from taxcode
     * @param taxCode
     * @return
     */
    @PostMapping(value = "get-personal-data-from-taxcode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Estrae i dati anagrafici dal codice fiscale", response = String.class)
    public Person extractPersonalDataFromTaxCode(@Valid  @RequestBody TaxCode taxCode) {
        return taxCodeService.getPersonalDataFromTaxCode(taxCode.getTaxCode());
    }
}
