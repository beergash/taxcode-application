package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.CityServiceException;

/**
 * Interface to retrieve city data
 *
 * @author A.Aresta
 */
public interface CityCodeService {

    /**
     * get Catasto code from city name
     * @param name
     * @return catasto code
     * @throws CityServiceException
     */
    public String getCatastoCodeFromName(String name) throws CityServiceException;

    /**
     * get city name from catasto code
     * @param
     * @return city name
     * @throws CityServiceException
     */
    public String getNameFromCatastoCode(String catastoCode) throws CityServiceException;

}
