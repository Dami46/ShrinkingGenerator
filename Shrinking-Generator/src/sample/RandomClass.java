package sample;

import java.util.Random;

class RandomClass {

    private static String sample = "";
    private static Random generator = new Random();

    public static String generateSample(int lenght) {
        sample = "";
        for (int i = 0; i < lenght; i++) {
            int generatedValue = generator.nextInt(2);
            sample = sample + generatedValue;
        }

        if (!sample.contains("0")) {
            sample = sample.substring(0, sample.length() - 1);
            sample = sample + "0";
        }
        if(!sample.contains("1")) {
            sample = sample.substring(0, sample.length() - 1);
            sample = sample + "1";
        }

        return sample;
    }

}
