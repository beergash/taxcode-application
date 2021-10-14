package it.beergash.taxcodeApplication.service;

import it.beergash.taxcodeApplication.data.entity.City;
import it.beergash.taxcodeApplication.data.repository.CityRepository;
import it.beergash.taxcodeApplication.exception.CityServiceException;
import it.beergash.taxcodeApplication.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Database implementation service to retrieve city data
 *
 * @author A.Aresta
 */
@Service
@Qualifier("databaseCityCodeService")
public class DatabaseCityCodeService implements CityCodeService {

    @Autowired
    private CityRepository cityRepository;

    /**
     * Retrieves catasto code from city name
     * @param name
     * @return catasto code
     * @throws CityServiceException
     */
    @Override
    public String getCatastoCodeFromName(String name) throws CityServiceException {
        Optional.ofNullable(name)
                .orElseThrow(() -> new ValidationException("City name must not be null!"));
        City city = cityRepository.findByName(name.toUpperCase());
        if (city == null || StringUtils.isEmpty(city.getCatastoCode())) {
            throw new CityServiceException(String.format("Not found catasto code for city %s", name));
        }
        return city.getCatastoCode();
    }

    /**
     * Retrieves city name from catasto code
     * @param
     * @return city name
     * @throws CityServiceException
     */
    @Override
    public String getNameFromCatastoCode(String catastoCode) throws CityServiceException {
        Optional.ofNullable(catastoCode)
                .orElseThrow(() -> new ValidationException("Catasto code must not be null!"));
        City city = cityRepository.findByCatastoCode(catastoCode);

        if (city == null || StringUtils.isEmpty(city.getName())) {
            throw new CityServiceException(String.format("Not found city for catasto code %s", catastoCode));
        }
        return city.getName();
    }
}
