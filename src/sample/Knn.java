package sample;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Lukado on 6. 5. 2016.
 */
public class Knn {

    int vys=0;
    //zde je N-tý prvek, lze měnit
    int n = 5;

    /**
     * K-nearest-neighbor algoritmus
     * @param vektory list trénovacích cifer
     * @param hledany hledaná cifra
     */
    public Knn(ArrayList<Vektor> vektory, Vektor hledany) {
        TreeMap<Double, Integer> pole = new TreeMap<>();

        for (int i=0;i<vektory.size();i++){
            double eukl=0;
            for (int j=0;j<vektory.get(i).pole.length;j++){
                eukl += Math.pow(vektory.get(i).pole[j]-hledany.pole[j], 2);
            }
            pole.put(Math.sqrt(eukl), vektory.get(i).getCislo());
        }
        vys = getMin(pole, n);
    }

    /**
     * Nejmenší vzdálenost, nachází výsledek
     * @param map pole euklidovských vzdáleností
     * @param n pocet N prvku
     * @return vysledek
     */
    public int getMin(TreeMap<Double, Integer> map, int n){
        int[] pole = new int[10];
        int cislo = -1;
        int count = 0;
        for (Map.Entry<Double,Integer> prvek : map.entrySet()){
            if (count>=n) break;
                pole[prvek.getValue()]++;
            count++;
        }
        count = 1;
        for (int i = 0; i < pole.length; i++) {
            if (pole[i] > count) {
                count = pole[i];
                cislo = i;
            }
        }
        if (cislo != -1) return cislo;
        else return map.firstEntry().getValue();
    }

    /**
     * Vraci vysledek
     * @return vysledek
     */
    public int getNumber() {
        return vys;
    }
}
