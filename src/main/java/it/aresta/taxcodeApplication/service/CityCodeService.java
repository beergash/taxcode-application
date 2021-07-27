package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.CityServiceException;

/**
 * Interface to retrieve city data
 *
 * @author A.Aresta
 */
public interface CityCodeService {

    public String getCatastoCodeFromName(String name) throws CityServiceException;

    public String getNameFromCatastoCode(String catastoCode) throws CityServiceException;

}
