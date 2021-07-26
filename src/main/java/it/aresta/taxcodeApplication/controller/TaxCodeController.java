package it.aresta.taxcodeApplication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.aresta.taxcodeApplication.model.Person;
import it.aresta.taxcodeApplication.model.TaxCode;
import it.aresta.taxcodeApplication.service.TaxCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "TaxCodeController")
public class TaxCodeController {

    @Autowired
    private TaxCodeService taxCodeService;

    @PostMapping(value = "get-taxCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Genera il codice fiscale a partire dai dati anagrafici", response = TaxCode.class)
    public TaxCode getTaxCodeFromPersonalData(@RequestBody Person person) {
            String taxCode = taxCodeService.generateTaxCodeFromPersonalData(person);
            return new TaxCode(taxCode);
    }
}
