package it.beergash.taxcodeApplication.service;

import it.beergash.taxcodeApplication.exception.ValidationException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for execution of the generation of control characer algorithm
 *
 * @author A.Aresta
 */
@Service
public class CharacterControlService {

    public static final int REMAINDER_DIVISOR_COEFFICIENT = 26;
    private Map<String, Integer> evenTransCodes = new HashMap<>();
    private Map<String, Integer> oddTransCodes = new HashMap<>();
    private Map<Integer, String> remainderTransCodes = new HashMap<>();
    private Map<Integer, String> omocodiaTransCodes = new HashMap<>();


    /**
     * initializes trancodes maps necessary for the Control character algorithm
     */
    @PostConstruct
    public void init() {
        /// PUT ODD VALUES ////
        oddTransCodes.put("0", 1);
        oddTransCodes.put("1", 0);
        oddTransCodes.put("2", 5);
        oddTransCodes.put("3", 7);
        oddTransCodes.put("4", 9);
        oddTransCodes.put("5", 13);
        oddTransCodes.put("6", 15);
        oddTransCodes.put("7", 17);
        oddTransCodes.put("8", 19);
        oddTransCodes.put("9", 21);
        oddTransCodes.put("A", 1);
        oddTransCodes.put("B", 0);
        oddTransCodes.put("C", 5);
        oddTransCodes.put("D", 7);
        oddTransCodes.put("E", 9);
        oddTransCodes.put("F", 13);
        oddTransCodes.put("G", 15);
        oddTransCodes.put("H", 17);
        oddTransCodes.put("I", 19);
        oddTransCodes.put("J", 21);
        oddTransCodes.put("K", 2);
        oddTransCodes.put("L", 4);
        oddTransCodes.put("M", 18);
        oddTransCodes.put("N", 20);
        oddTransCodes.put("O", 11);
        oddTransCodes.put("P", 3);
        oddTransCodes.put("Q", 6);
        oddTransCodes.put("R", 8);
        oddTransCodes.put("S", 12);
        oddTransCodes.put("T", 14);
        oddTransCodes.put("U", 16);
        oddTransCodes.put("V", 10);
        oddTransCodes.put("W", 22);
        oddTransCodes.put("X", 25);
        oddTransCodes.put("Y", 24);
        oddTransCodes.put("Z", 23);

        /// PUT EVEN VALUES ////
        evenTransCodes.put("0", 0);
        evenTransCodes.put("1", 1);
        evenTransCodes.put("2", 2);
        evenTransCodes.put("3", 3);
        evenTransCodes.put("4", 4);
        evenTransCodes.put("5", 5);
        evenTransCodes.put("6", 6);
        evenTransCodes.put("7", 7);
        evenTransCodes.put("8", 9);
        evenTransCodes.put("9", 9);
        evenTransCodes.put("A", 0);
        evenTransCodes.put("B", 1);
        evenTransCodes.put("C", 2);
        evenTransCodes.put("D", 3);
        evenTransCodes.put("E", 4);
        evenTransCodes.put("F", 5);
        evenTransCodes.put("G", 6);
        evenTransCodes.put("H", 7);
        evenTransCodes.put("I", 8);
        evenTransCodes.put("J", 9);
        evenTransCodes.put("K", 10);
        evenTransCodes.put("L", 11);
        evenTransCodes.put("M", 12);
        evenTransCodes.put("N", 13);
        evenTransCodes.put("O", 14);
        evenTransCodes.put("P", 15);
        evenTransCodes.put("Q", 16);
        evenTransCodes.put("R", 17);
        evenTransCodes.put("S", 18);
        evenTransCodes.put("T", 19);
        evenTransCodes.put("U", 20);
        evenTransCodes.put("V", 21);
        evenTransCodes.put("W", 22);
        evenTransCodes.put("X", 23);
        evenTransCodes.put("Y", 24);
        evenTransCodes.put("Z", 25);

        /// PUT REST VALUES
        remainderTransCodes.put(0, "A");
        remainderTransCodes.put(1, "B");
        remainderTransCodes.put(2, "C");
        remainderTransCodes.put(3, "D");
        remainderTransCodes.put(4, "E");
        remainderTransCodes.put(5, "F");
        remainderTransCodes.put(6, "G");
        remainderTransCodes.put(7, "H");
        remainderTransCodes.put(8, "I");
        remainderTransCodes.put(9, "J");
        remainderTransCodes.put(10, "K");
        remainderTransCodes.put(11, "L");
        remainderTransCodes.put(12, "M");
        remainderTransCodes.put(13, "N");
        remainderTransCodes.put(14, "O");
        remainderTransCodes.put(15, "P");
        remainderTransCodes.put(16, "Q");
        remainderTransCodes.put(17, "R");
        remainderTransCodes.put(18, "S");
        remainderTransCodes.put(19, "T");
        remainderTransCodes.put(20, "U");
        remainderTransCodes.put(21, "V");
        remainderTransCodes.put(22, "W");
        remainderTransCodes.put(23, "X");
        remainderTransCodes.put(24, "Y");
        remainderTransCodes.put(25, "Z");

        /// PUT OMOCODIA CODES
        omocodiaTransCodes.put(0, "L");
        omocodiaTransCodes.put(1, "M");
        omocodiaTransCodes.put(2, "N");
        omocodiaTransCodes.put(3, "P");
        omocodiaTransCodes.put(4, "Q");
        omocodiaTransCodes.put(5, "R");
        omocodiaTransCodes.put(6, "S");
        omocodiaTransCodes.put(7, "T");
        omocodiaTransCodes.put(8, "U");
        omocodiaTransCodes.put(9, "V");
    }

    /**
     * determines character control from taxCode compliant on TaxCode generation rule
     * @param taxCode
     * @return
     */
    public String calculateCharacterControl(String taxCode) {
        Optional.ofNullable(taxCode)
                .orElseThrow(() -> new ValidationException("TaxCode null in character control function"));
        List<String> oddValues = new ArrayList<>();
        List<String> evenValues = new ArrayList<>();
        for (int i=0; i<taxCode.length(); i++) {
            String currentChar = String.valueOf(taxCode.charAt(i));
            if ((i+1)%2 == 0) {
                evenValues.add(currentChar);
            } else {
                oddValues.add(currentChar);
            }
        }
        int oddTransCodedSum = oddValues.stream()
                .map(s -> oddTransCodes.get(s))
                .collect(Collectors.summingInt(Integer::intValue));
        int evenTransCodedSum = evenValues.stream()
                .map(s -> evenTransCodes.get(s))
                .collect(Collectors.summingInt(Integer::intValue));
        int remainder = (oddTransCodedSum + evenTransCodedSum)% REMAINDER_DIVISOR_COEFFICIENT;
        return remainderTransCodes.get(remainder);
    }

    public String replaceNumbersForOmocodiaCases(String taxCode) {
        Optional.ofNullable(taxCode)
                .orElseThrow(() -> new ValidationException("TaxCode null in replacing function for omocodia cases"));
        StringBuilder result = new StringBuilder("");
        for (int i=0; i<taxCode.length(); i++) {
            String s = String.valueOf(taxCode.charAt(i));
            String converted = NumberUtils.isCreatable(s) ? omocodiaTransCodes.get(Integer.valueOf(s)) : s;
            result.append(converted);
        }
        return result.toString();
        }
}
