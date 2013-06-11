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
            new Object[]{"IV", 4},
            new Object[]{"XIV", 14},
            new Object[]{"XCI", 91}
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
        private final Map<String, Integer> values;

        public Roman(String numeral) {
            this.numeral = numeral;
            this.values = new HashMap<String, Integer>();
            values.put("I", 1);
            values.put("V", 5);
            values.put("X", 10);
            values.put("C", 100);
        }

        public int toArabic() {
            if (numeral.isEmpty()) {
                return 0;
            }

            if (numeral.length() == 1) {
                return valueAt(0);
            }

            int firstValue = valueAt(0);
            int secondValue = valueAt(1);
            int nextIndex = 1;

            if (secondValue > firstValue) {
                nextIndex = 2;
                firstValue = secondValue - firstValue;
            }

            String remainingNumerals = numeral.substring(nextIndex);
            return firstValue + new Roman(remainingNumerals).toArabic();
        }

        private int valueAt(int index) {
            return valueOf(numeral.substring(index, index+1));
        }

        private int valueOf(String singleNumeral) {
            return values.get(singleNumeral);
        }
    }
}
