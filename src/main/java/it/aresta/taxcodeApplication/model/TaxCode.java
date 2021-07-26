package it.aresta.taxcodeApplication.model;

public class TaxCodeResponse {

    private String taxCode;

    public TaxCodeResponse(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}
