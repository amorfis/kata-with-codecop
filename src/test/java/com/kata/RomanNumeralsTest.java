package com.kata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class RomanNumeralsTest {

    @Parameterized.Parameters
    public static List<Object[]> pairs() {
        return Arrays.asList(
            new Object[]{"I", 1},
            new Object[]{"II", 2},
            new Object[]{"III", 3},
            new Object[]{"V", 5},
            new Object[]{"X", 10},
            new Object[]{"VI", 6},
            new Object[]{"VII", 7},
            new Object[]{"VIII", 8},
            new Object[]{"XI", 11},
            new Object[]{"XV", 15},
            new Object[]{"IX", 9},
            new Object[]{"IV", 4}
        );
    }

    private final String numeral;
    private final int arabic;

    public RomanNumeralsTest(String numeral, int arabic) {
        this.numeral = numeral;
        this.arabic = arabic;
    }

    @Test
    public void shouldTranslateRomanToArabicNumber() {
        int value = new Roman(numeral).toArabic();
        assertThat(value, is(equalTo(arabic)));
    }

    private static class Roman {

        private final String numeral;
        private final Map<String, Integer> lookup;

        public Roman(String numeral) {
            this.numeral = numeral;
            this.lookup = new HashMap<String, Integer>();
            lookup.put("I", 1);
            lookup.put("V", 5);
            lookup.put("X", 10);
        }

        public int toArabic() {
            if (numeral.isEmpty()) {
                return 0;
            }

            String firstNumeral = numeral.substring(0, 1);
            int firstValue = toArabic(firstNumeral);

            if (numeral.length() == 2) {
                String secondNumeral = numeral.substring(1, 2);
                int secondValue = toArabic(secondNumeral);
                if (secondValue > firstValue) {
                    return secondValue - firstValue;
                }
            }

            String remainingNumerals = numeral.substring(1);
            return firstValue + new Roman(remainingNumerals).toArabic();
        }

        private int toArabic(String numeral) {
            return lookup.get(numeral);
        }
    }
}
