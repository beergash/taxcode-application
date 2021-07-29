package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.CityServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Csv Reader implementation service to retrieve city data
 *
 * @author A.Aresta
 */
@Primary
@Service
@Qualifier("csvCityCodeService")
public class CsvCityCodeService implements CityCodeService {

    /**
     * Get catasto code from city name reading from an internal csv file
     * containig all domain data
     * @param name
     * @return catasto code
     * @throws CityServiceException
     */
    @Override
    public String getCatastoCodeFromName(String name) throws CityServiceException {
        return readCatastoCsvAndGetData(name, 0, 1);
    }

    /**
     * Get city name from catasto code reading from an internal csv file
     * containig all domain data
     * @param
     * @return city name
     * @throws CityServiceException
     */
        @Override
        public String getNameFromCatastoCode(String catastoCode) throws CityServiceException {
            return readCatastoCsvAndGetData(catastoCode, 1, 0);
        }

    /**
     * reads csv file containing all catasto codes domain data and returns catasto code or city name
     * based on input/output indices indicated.
     * If inputIndex is 0 and outputIndex 1, Gets catasto code receiving city name as input,
     * If inputIndex is 1 and outputIndex 0, Gets city name receiving catasto code as input
     * @param input
     * @param inputIndex
     * @param outputIndex
     * @return
     * @throws CityServiceException
     */
    private String readCatastoCsvAndGetData(String input, int inputIndex, int outputIndex)  throws CityServiceException {
        try (InputStream is = this.getClass().getResourceAsStream("/catasto_codes.csv")) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] lineSplit = line.split(";");
                    if (input.equalsIgnoreCase(lineSplit[inputIndex])) {
                        return lineSplit[outputIndex];
                    }
                }
            }
        } catch (IOException e) {
            throw new CityServiceException("Unable to read catasto code csv file", e);
        }
        throw new CityServiceException(String.format("Not found city data for input %s", input));
    }
    }
