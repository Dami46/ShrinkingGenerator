package sample.Generator;

public class LFSR {
    private boolean[] lfsr;
    private int tap;
    static XorTable xorTable = new XorTable();


    public LFSR(String seed, int tap) {
        lfsr = new boolean[seed.length()];
        this.tap = tap;

        for (int i = 0; i < seed.length(); i++) {
            if (seed.charAt(i) == 48) {
                lfsr[i] = false;
            } else {
                lfsr[i] = true;
            }
        }
    }

    public int step() {
        boolean newBit = false;
        boolean tmpXorResult = false;
        for (int i = 0; i < xorTable.xorMap.get(tap).size() - 1; i++) {
            tmpXorResult = lfsr[xorTable.xorMap.get(tap).get(i)]^ lfsr[xorTable.xorMap.get(tap).get(i+1)];
        }
        newBit = lfsr[0] ^ tmpXorResult;
       // System.out.println("Wynik po xor " + newBit);

        for (int i = 0; i < lfsr.length - 1; i++) {
            lfsr[i] = lfsr[i + 1];
        }
        lfsr[lfsr.length - 1] = newBit;
        //System.out.println("Wynik po xor " + newBit);

        return !newBit ? 0 : 1;
    }

    public String toString() {
        String representation = "";
        for (int i = 0; i < lfsr.length; i++) {
            representation += lfsr[i] == false ? 0 : 1;
        }
        return representation;
    }
}
