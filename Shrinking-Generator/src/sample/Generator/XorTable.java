package sample.Generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class XorTable {
    public HashMap<Integer, List<Integer>> xorMap = new HashMap<>();

    public void implementXorMap() {
        xorMap.put(20, Arrays.asList(16, 19));
        xorMap.put(21, Arrays.asList(18, 20));
        xorMap.put(22, Arrays.asList(20, 21));
        xorMap.put(23, Arrays.asList(17, 22));
        xorMap.put(24, Arrays.asList(19, 20, 22, 23));
        xorMap.put(25, Arrays.asList(21, 24));
        xorMap.put(26, Arrays.asList(19, 23, 24, 25));
        xorMap.put(27, Arrays.asList(21, 24, 25, 26));
        xorMap.put(28, Arrays.asList(24, 27));
    }
}
