package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RegistryDataService {

    private static final String PADDING_CHARACTER = "X";
    private final List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

    public String getSubsetCharactersFromName(String name) {
        Optional.ofNullable(name)
                .orElseThrow(() -> new ValidationException("Name or surname must not be null!"));
        String consonantsName = "";
        String vowelsName = "";
        for (char c: name.toLowerCase().toCharArray()) {
            if (vowels.contains(c)) {
                vowelsName += c;
            } else {
                consonantsName += c;
            }
        }
        String firstThreeCharacters = (consonantsName + vowelsName)
                .toUpperCase()
                .substring(0, Math.min((consonantsName + vowelsName).length(), 3));
        return StringUtils.rightPad(firstThreeCharacters, 3, PADDING_CHARACTER);
    }
}
