package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.CityServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Csv Reader implementation service to retrieve city data
 *
 * @author A.Aresta
 */
@Service
@Qualifier("csvCityCodeService")
public class CsvCityCodeService implements CityCodeService {

    @Override
    public String getCatastoCodeFromName(String name) throws CityServiceException {
        return readCatastoCsvAndGetData(name, 0, 1);
    }

        @Override
        public String getNameFromCatastoCode(String catastoCode) throws CityServiceException {
            return readCatastoCsvAndGetData(catastoCode, 1, 0);
        }

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
