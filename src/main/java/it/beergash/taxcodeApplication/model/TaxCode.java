package it.beergash.taxcodeApplication.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Tax Code Domain
 *
 * @author A.Aresta
 */
public class TaxCode {

    @NotNull
    @Size(min = 16, max = 16)
    private String taxCode;

    public TaxCode() {
    }

    public TaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}
