package sample;

import javafx.scene.image.Image;

/**
 * Created by Lukado on 13. 5. 2016.
 */
public class LBP {

    private int[] pole;
    //ořez od okrajů. Minimálně = 1
    private int okraj = 4;

    /**
     * LBP algoritmus pro výpočet příznaků reprezentující číslice
     * @param img obrázek s cifrou
     */
    public LBP(Image img) {
        {
            pole = new int[256];
            int line;
            for (int y = okraj; y < img.getHeight()-okraj; ++y) {
                line = 0;
                for (int x = okraj; x < img.getWidth()-okraj; ++x) {
                    int rgb = img.getPixelReader().getArgb(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = (rgb & 0xFF);

                    int grayLevel = (r + g + b) / 3;

                    line += countSurroundings(grayLevel, img, x, y);
                }
                pole[y] = line;
            }

            for (int y = okraj; y < img.getHeight()-okraj; ++y) {
                line = 0;
                for (int x = okraj; x < img.getWidth()-okraj; ++x) {
                    int rgb = img.getPixelReader().getArgb(y, x);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = (rgb & 0xFF);

                    int grayLevel = (r + g + b) / 3;

                    line += countSurroundings(grayLevel, img, x, y);
                }
                pole[y+128] = line;
            }
        }
    }

    /**
     * Metoda pro výpočet okolí momentálního pixelu
     * @param mid rgb hodnota momentálního pixelu
     * @param img obrázek s cifrou
     * @param x x-ová souřadnice
     * @param y y-ová souřadnice
     * @return převedené decimální číslo
     */
    private int countSurroundings(int mid, Image img, int x, int y) {
        String str = "";

        for (int i=0;i<8;i++){
            int r,g,b;
            if (i<3) {
                int rgb = img.getPixelReader().getArgb(x - 1 + i, y - 1);
                r = (rgb >> 16) & 0xFF;
                g = (rgb >> 8) & 0xFF;
                b = (rgb & 0xFF);
            }else if(i<5){
                int rgb = img.getPixelReader().getArgb(x + 1, y - 3 + i);
                r = (rgb >> 16) & 0xFF;
                g = (rgb >> 8) & 0xFF;
                b = (rgb & 0xFF);
            }else if(i<7){
                int rgb = img.getPixelReader().getArgb(x + 5 - i, y + 1);
                r = (rgb >> 16) & 0xFF;
                g = (rgb >> 8) & 0xFF;
                b = (rgb & 0xFF);
            }else{
                int rgb = img.getPixelReader().getArgb(x - 1, y - 7 + i);
                r = (rgb >> 16) & 0xFF;
                g = (rgb >> 8) & 0xFF;
                b = (rgb & 0xFF);
            }
            int grayLevel = (r + g + b) / 3;

            if (grayLevel>mid) str += "1";
            else str += "0";
        }
        return Integer.parseInt(str, 2);
    }

    /**
     * Vrací vektor
     * @return pole
     */
    public int[] getPole() {
        return pole;
    }
}
