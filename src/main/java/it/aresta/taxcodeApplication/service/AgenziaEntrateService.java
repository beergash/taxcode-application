package it.aresta.taxcodeApplication.service;

import org.springframework.stereotype.Service;

/**
 * Exposes api from external Agenzia Entrate services
 *
 * @author A.Aresta
 */
@Service
public class AgenziaEntrateService {

    /**
     * Tells if taxCode already exists
     * @param taxCode
     * @return
     */
    public boolean isExistingTaxCode(String taxCode) {
        return false;
    }
}
