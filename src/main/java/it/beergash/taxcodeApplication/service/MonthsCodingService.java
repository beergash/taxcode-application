package it.beergash.taxcodeApplication.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Service provides month transcodes compliant on taxcode generation rule
 *
 * @author A.Aresta
 */
@Service
public class MonthsCodingService {

    private Map<Integer, String> monthsCodes = new HashMap<>();

    @PostConstruct
    public void init() {
        monthsCodes.put(1, "A");
        monthsCodes.put(2, "B");
        monthsCodes.put(3, "C");
        monthsCodes.put(4, "D");
        monthsCodes.put(5, "E");
        monthsCodes.put(6, "H");
        monthsCodes.put(7, "L");
        monthsCodes.put(8, "M");
        monthsCodes.put(9, "P");
        monthsCodes.put(10, "R");
        monthsCodes.put(11, "S");
        monthsCodes.put(12, "T");
    }

    /**
     * Gets Transcoding map between month index and correspondant character
     * @return
     */
    public Map<Integer, String> getMonthsCodes() {
        return monthsCodes;
    }
}
