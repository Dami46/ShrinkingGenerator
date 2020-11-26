package sample.Tests;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests {

    public static int onesCounter;
    public static int repeatCounter;
    private static LinkedHashMap<Integer, Integer[]> stringFreq;
    private static LinkedHashMap<String, Integer> combinations;

    public Tests() {
        this.stringFreq = new LinkedHashMap<Integer, Integer[]>() {{
            put(1, new Integer[]{2315, 2685});
            put(2, new Integer[]{1114, 1386});
            put(3, new Integer[]{527, 723});
            put(4, new Integer[]{240, 384});
            put(5, new Integer[]{103, 209});
            put(6, new Integer[]{103, 209});
        }};
        this.combinations = new LinkedHashMap<String, Integer>() {{
            put("0000", 1);
            put("0001", 1);
            put("0010", 1);
            put("0011", 1);
            put("0100", 1);
            put("0101", 1);
            put("0110", 1);
            put("0111", 1);
            put("1000", 1);
            put("1001", 1);
            put("1010", 1);
            put("1011", 1);
            put("1100", 1);
            put("1101", 1);
            put("1110", 1);
            put("1111", 1);
        }};
    }

    public static boolean singleBitsTest() {
        onesCounter = 0;
        for (int i = 0; i < TestsController.stringToTest.length(); i++) {
            if (TestsController.stringToTest.charAt(i) == '1') {
                onesCounter = onesCounter + 1;
            }
        }
        if (onesCounter > 9725 && onesCounter < 10275) {
            return true;
        }
        return false;
    }

    private static ArrayList<String> findAll(String data, String pa) {

        Pattern pattern = Pattern.compile(pa);
        Matcher matcher = pattern.matcher(data);

        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();
            result.add(matchResult.group());
        }
        return result;
    }

    public static int seriesTest(String pattern) {
        LinkedHashMap<Integer, Integer> strFreq = new LinkedHashMap<>();
        ArrayList<String> matches = findAll(TestsController.stringToTest, pattern);

        for (String str : matches) {
            int strLen = str.length();
            if (strLen > 6) {
                strLen = 6;
            }
            if (!strFreq.containsKey(strLen)) {
                int countElem = Collections.frequency(matches, str);
                strFreq.put(strLen, countElem);
            }
        }

        int proper = 0;
        for (Map.Entry<Integer, Integer> entry : strFreq.entrySet()) {
            int occurenceNum = entry.getValue();
            Integer[] occurenceLimit = stringFreq.get(entry.getKey());
            if (occurenceNum >= occurenceLimit[0] && occurenceNum <= occurenceLimit[1]) {
                proper++;
            }
        }
        return proper;
    }

    public static boolean seriesTestBool(Integer properZeros, Integer properOnes) {
        if (properOnes + properZeros != 12) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean longSeriesTest() {
        repeatCounter = 0;
        int tmpCounter = 1;
        boolean result = true;
        for (int i = 0; i < TestsController.stringToTest.length() - 1; i++) {
            if (TestsController.stringToTest.charAt(i) == TestsController.stringToTest.charAt(i + 1)) {
                tmpCounter = tmpCounter + 1;
                if (repeatCounter < tmpCounter) {
                    repeatCounter = tmpCounter;
                }
                if (tmpCounter >= 26) {
                    result = false;
                }
            } else {
                tmpCounter = 1;
            }
        }
        return result;
    }

    public static double pokerTest() {
        for (int i = 0; i < TestsController.stringToTest.length() - 4; i += 4) {
            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                tmp.append(TestsController.stringToTest.charAt(i + j));
            }
            int occ = combinations.get(tmp.toString());
            combinations.replace(tmp.toString(), occ + 1);
        }

        int result = 0;
        List<Integer> valuesList = new ArrayList<>(combinations.values());
        for (Integer i : valuesList) {
            result += (int) Math.pow(i, 2);
        }
        return ((((double) 16 / 5000) * result) - 5000);
    }


    public static boolean pokerTestBool(Double x) {
        if (x > 2.16 && x < 46.17) {
            return true;
        } else {
            return false;
        }
    }
}
