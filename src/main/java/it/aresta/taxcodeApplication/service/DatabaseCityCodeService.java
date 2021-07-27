package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.data.entity.City;
import it.aresta.taxcodeApplication.data.repository.CityRepository;
import it.aresta.taxcodeApplication.exception.CityServiceException;
import it.aresta.taxcodeApplication.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Database implementation service to retrieve city data
 *
 * @author A.Aresta
 */
@Primary
@Service
@Qualifier("databaseCityCodeService")
public class DatabaseCityCodeService implements CityCodeService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public String getCatastoCodeFromName(String name) throws CityServiceException {
        Optional.ofNullable(name)
                .orElseThrow(() -> new ValidationException("City name must not be null!"));
        City city = cityRepository.findByName(name.toUpperCase());
        if (city == null || StringUtils.isEmpty(city.getName())) {
            throw new CityServiceException(String.format("Not found catasto code for city %s", name));
        }
        return city.getCatastoCode();
    }
    @Override
    public String getNameFromCatastoCode(String catastoCode) throws CityServiceException {
        Optional.ofNullable(catastoCode)
                .orElseThrow(() -> new ValidationException("Catasto code must not be null!"));
        City city = cityRepository.findByCatastoCode(catastoCode);
        return city.getName();
    }
}
