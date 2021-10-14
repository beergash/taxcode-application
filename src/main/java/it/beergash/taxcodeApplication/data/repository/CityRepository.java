package it.beergash.taxcodeApplication.data.repository;

import it.beergash.taxcodeApplication.data.entity.City;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository Service for entity @{@link City}
 *
 * @author A.Aresta
 */
public interface CityRepository extends CrudRepository<City, Integer> {

    /**
     * Returns @{@link City} searching by name
     * @param name
     * @return
     */
    public City findByName(String name);

    /**
     * Returns @{@link City} searching by city code
     * @param cityCode
     * @return
     */
    public City findByCityCode(String cityCode);


    /**
     * Returns @{@link City} searching by catasto code
     * @param catastoCode
     * @return
     */
    public City findByCatastoCode(String catastoCode);
}
