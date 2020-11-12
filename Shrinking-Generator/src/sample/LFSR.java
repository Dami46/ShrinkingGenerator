package sample;

public class LFSR {
    private boolean[] lfsr;
    private int tap;
    static XorTable xorTable = new XorTable();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        xorTable.implementXorMap();
        String LFSRASeed = RandomClass.generateSample(21);
        String LFSRSSeed = RandomClass.generateSample(22);
        LFSR lfsrA = new LFSR(LFSRASeed, 21);
        LFSR lfsrS = new LFSR(LFSRSSeed, 22);
        Controller.generationRunTest(lfsrA,lfsrS);
        while (Controller.outputString.length() < 1000000) {
            Controller.generationRunTest(lfsrA,lfsrS);
          //  System.out.println("Wynik generacji: " + Controller.outputString);
           // System.out.println(Controller.outputString.length());
        }
        if(Controller.outputString.length() != 1000000) {
            Controller.outputString = Controller.outputString.substring(0,1000000);
        }
        System.out.println(Controller.outputString);
        //System.out.println(Controller.outputString);

     /*   String seed = RandomClass.generateSample(20);
        System.out.println("Seed " + seed);
        LFSR lfsr = new LFSR(seed, 20);
        for (int i = 0; i < 10; i++) {
            int bit = lfsr.step();
            System.out.println(lfsr + " " + bit);
        }*/
    }

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
      //  System.out.println("Size XORTable " + xorTable.xorMap.get(tap).size());
        for(int i=0; i<xorTable.xorMap.get(tap).size();i++) {
            newBit = lfsr[0] ^ lfsr[xorTable.xorMap.get(tap).get(i)];
        }

        for (int i = 0; i < lfsr.length - 1; i++) {
            lfsr[i] = lfsr[i + 1];
        }
        lfsr[lfsr.length - 1] = newBit;

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
