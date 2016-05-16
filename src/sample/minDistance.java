package sample;

import java.util.ArrayList;

/**
 * Created by Lukado on 5. 5. 2016.
 */
public class minDistance {

    private int vys;
    private ArrayList<Vektor> prumVektory;

    /**
     * Algoritmus na výpočet minimální vzdálenosti
     * @param vektory list trénovacích cifer
     * @param hledany hledaná cifra
     * @param pocetAdresaru pocet adresaru
     */
    public minDistance(ArrayList<Vektor> vektory, Vektor hledany, Integer pocetAdresaru) {
        spoctiTeziste(vektory, pocetAdresaru);

        double[][] pole = new double[prumVektory.size()][2];

        for (int i=0;i<prumVektory.size();i++){
            double eukl=0;
            for (int j=0;j<prumVektory.get(i).pole.length;j++){
                eukl += Math.pow(prumVektory.get(i).pole[j]-hledany.pole[j], 2);
            }
            pole[i][0] = Math.sqrt(eukl);
            pole[i][1] = prumVektory.get(i).getCislo();
        }
        vys = getMin(pole);
    }

    /**
     * Metoda na spočtení těžiště
     * @param vektory list trénovacích cifer
     * @param pocetAdresaru pocet adresaru
     */
    public void spoctiTeziste(ArrayList<Vektor> vektory, Integer pocetAdresaru){
        int[] prumPole = new int[256];
        prumVektory = new ArrayList<>();
        int x=0;

        for(int i=0;i<=vektory.size();i++) {

            if (i % pocetAdresaru == 0 && i!=0) {
                for (int j = 0; j<prumPole.length;j++){
                    prumPole[j] = (int) Math.round(prumPole[j]/(double)pocetAdresaru);
                }
                prumVektory.add(new Vektor(prumPole, x));
                prumPole = new int[256];
                x++;
            }

            if (i!=vektory.size()) {
                for (int j = 0; j < vektory.get(i).pole.length; j++) {
                    prumPole[j] += vektory.get(i).pole[j];
                }
            }
        }
    }

    /**
     * Nejmenší vzdálenost, nachází výsledek
     * @param pole pole euklidovských vzdáleností
     * @return vysledek
     */
    public int getMin(double[][] pole){
        double min = Double.MAX_VALUE;
        int cislo = 0;

        for (int i = 0; i < pole.length; i++) {
            if (pole[i][0] < min) {
                min = pole[i][0];
                cislo = (int)pole[i][1];
            }
        }
        return cislo;
    }

    /**
     * Vraci vysledek
     * @return vysledek
     */
    public int getNumber() {
        return vys;
    }
}
