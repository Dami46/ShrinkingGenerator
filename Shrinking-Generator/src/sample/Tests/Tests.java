package sample.Tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests {

    public static int onesCounter;
    public static int repeatCounter;

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

    public static boolean seriesTest() {
        return true;
    }


    public static boolean longSeriesTest() {
        repeatCounter = 0;
        int tmpCounter = 1;
        boolean result = false;
        int  tmpRepeatCounter =0;
        for (int i = 0; i < TestsController.stringToTest.length()-1; i++) {
            if (TestsController.stringToTest.charAt(i) == TestsController.stringToTest.charAt(i+1)) {
                tmpCounter =  tmpCounter + 1;
                if(tmpCounter >= 26) {
                   result = true;
                   tmpRepeatCounter = tmpCounter;
                    if(repeatCounter < tmpRepeatCounter) {
                        repeatCounter = tmpRepeatCounter;
                    }
                }
            } else {
                tmpCounter = 1;
            }
        }
        return result;
    }

    public static boolean pokerTest() {
        return false;
    }
}
