package sample;

import java.util.Arrays;

/**
 * Created by Lukado on 3. 5. 2016.
 */
public class Vektor {
    public int[] pole;
    private int cislo;

    public Vektor(int[] pole, int cislo){
        this.pole = pole;
        this.cislo = cislo;
    }

    @Override
    public String toString() {
        return "Vektor{" +
                "\npole=" + Arrays.toString(pole) +
                "\ncislo=" + cislo +
                "\n}";
    }

    public int getCislo() {
        return cislo;
    }
}
