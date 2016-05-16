package sample;

import javafx.scene.image.Image;

/**
 * Created by Lukado on 6. 5. 2016.
 */
public class BagOfWords {

    int[] pole;

    /**
     * BOW algoritmus pro výpočet příznaků reprezentující číslice
     * @param img obrázek s cifrou
     */
    public BagOfWords(Image img) {
        {
            pole = new int[256];

            for (int y = 0; y < img.getHeight(); ++y) {
                int i = 0;
                for (int x = 0; x < img.getWidth(); ++x) {
                    int rgb = img.getPixelReader().getArgb(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = (rgb & 0xFF);

                    int grayLevel = (r + g + b) / 3;
                    if (grayLevel<20){
                        i++;
                    }
                }
                if (i!=0 && y!=0 && y!=1 && y!=img.getHeight()-1 && y!=img.getHeight()-2) pole[i]++;

            }

            for (int y = 0; y < img.getHeight(); ++y) {
                int i = 0;
                for (int x = 0; x < img.getWidth(); ++x) {
                    int rgb = img.getPixelReader().getArgb(y, x);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = (rgb & 0xFF);

                    int grayLevel = (r + g + b) / 3;
                    if (grayLevel<20){
                        i++;
                    }
                }
                if (i!=0 && y!=0 && y!=1 && y!=img.getHeight()-1 && y!=img.getHeight()-2) pole[i+128]++;
            }
        }
    }

    /**
     * Vrací vektor
     * @return pole
     */
    public int[] getPole() {
        return pole;
    }
}
