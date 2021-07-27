package it.aresta.taxcodeApplication.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity maps CITIES domain
 *
 * @author A.Aresta
 */
@Entity(name = "CITIES")
public class City {

    @Id
    private Integer id;

    private String name;

    private String cityCode;

    private String catastoCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCatastoCode() {
        return catastoCode;
    }

    public void setCatastoCode(String catastoCode) {
        this.catastoCode = catastoCode;
    }
}
