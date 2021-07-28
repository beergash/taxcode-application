package it.aresta.taxcodeApplication.service;

import it.aresta.taxcodeApplication.exception.ValidationException;
import it.aresta.taxcodeApplication.model.GenderEnum;
import it.aresta.taxcodeApplication.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service provides taxcode subsets from personal data for registry data (name, surname, birth date)
 *
 * @author A.Aresta
 */
@Service
public class RegistryDataService {

    public static final int MIN_RANGE_BIRTH_DAY_FEMALE = 41;
    public static final int MAX_RANGE_BIRTH_DAY_FEMALE = 71;
    @Autowired
    private MonthsCodingService monthsCodingService;

    private static final String PADDING_CHARACTER = "X";
    private final List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

    /**
     * Returns string based of tax code rule appying for name and surnames.
     * Takes the first three consonants, if they are not enough, adding vowels.
     * At the end if the are not enough consonants and vowels (name is shorter than 3), addes X characters padding
     * Examples: ROSSI ---> RSS,
     * ROSA ---> RSA,
     * FO --> FOX
     * @param name
     * @return
     */
    public String getSubsetCharactersFromName(String name, boolean isName) {
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
        if (isName && consonantsName.length() >= 4) {
            consonantsName = String.valueOf(consonantsName.charAt(0)) + consonantsName.charAt(2) + consonantsName.charAt(3);
        }
        String firstThreeCharacters = (consonantsName + vowelsName)
                .toUpperCase()
                .substring(0, Math.min((consonantsName + vowelsName).length(), 3));
        return StringUtils.rightPad(firstThreeCharacters, 3, PADDING_CHARACTER);
    }

    /**
     * Get Last 2 chars of the year
     * @param date
     * @return
     */
    public String getBirthYearSubset(Date date) {
        Optional.ofNullable(date)
                .orElseThrow(() -> new ValidationException("Name or surname must not be null!"));
        String year = String.valueOf(DateUtils.extractYear(date));
        return year.substring(year.length() - 2);
    }

    /**
     * Get day from birthdate adding 40 for females
     * @param date
     * @return
     */
    public String getBirthDaySubset(Date date, GenderEnum gender) {
        int day = DateUtils.extractDay(date);
        int genderRuleDay = GenderEnum.M.equals(gender) ? day : day + 40;
        return StringUtils.leftPad(String.valueOf(genderRuleDay), 2, "0");
    }

    /**
     * get String month base on tax code months coding
     * @param date
     * @return
     */
    public String getMonthSubset(Date date) {
        int month = DateUtils.extraxtMonth(date);
        String code = monthsCodingService.getMonthsCodes().get(month);
        Optional.ofNullable(code)
                .orElseThrow(() -> new ValidationException(String.format("Transcodifica del mese andata in errore, con la data %s", date)));
        return code;
    }

    /**
     * Retrieves birth date from taxCode. Knowing only last 2 characters of birth year, let's considering only
     * birth year younger than 100
     * @param taxCode
     * @return
     */
    public Date getBirthDateFromTaxCode(String taxCode) {
        String yearString = taxCode.substring(6, 8);
        String currentYear = String.valueOf(DateUtils.extractYear(new Date()));
        int currentYearLast2Numbers = Integer.valueOf(currentYear.substring(2,4));
        String prefixYear = Integer.valueOf(yearString) >= currentYearLast2Numbers ? "19" : "20";
        int year = Integer.valueOf(prefixYear + yearString);
        String monthChar = taxCode.substring(8, 9);
        int month = monthsCodingService.getMonthsCodes().entrySet().stream()
                .filter(m -> m.getValue().equalsIgnoreCase(monthChar))
                .findFirst()
                .orElseThrow(() -> new ValidationException(String.format("Not valid month character %s", monthChar)))
                .getKey();
        int day = Integer.valueOf(taxCode.substring(9,11));
        if (day >=MIN_RANGE_BIRTH_DAY_FEMALE && day <=MAX_RANGE_BIRTH_DAY_FEMALE) {
            day = day - 40;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month -1, day);
        return cal.getTime();
    }

    /**
     * Retrieves gender from birth day
     * @param taxCode
     * @return
     */
    public String getGenderFromTaxCode(String taxCode) {
        int birthDay = Integer.valueOf(taxCode.substring(9,11));
        if (birthDay > MAX_RANGE_BIRTH_DAY_FEMALE) {
            throw new ValidationException(String.format("Not valid birth day from taxCode %d", birthDay));
        }
        return birthDay > MIN_RANGE_BIRTH_DAY_FEMALE && birthDay <= MAX_RANGE_BIRTH_DAY_FEMALE ? GenderEnum.F.name() : GenderEnum.M.name();
    }
}
