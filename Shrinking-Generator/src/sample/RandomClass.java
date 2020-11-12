package sample;

import java.text.MessageFormat;
import java.util.*;

class RandomClass {

    private static String sample = "";
    private static Random generator = new Random();

    public static String generateSample(int lenght) {
        sample ="";
        for (int i = 0; i < lenght; i++) {
            int generatedValue = generator.nextInt(2);
            sample = sample + generatedValue;

        }
        return sample;
    }

}
