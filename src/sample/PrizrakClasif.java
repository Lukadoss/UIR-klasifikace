package sample;

import javafx.scene.image.Image;

/**
 * Created by Lukado on 11. 5. 2016.
 */
public class PrizrakClasif {

    int[] pole;

    /**
     * Vlastní algoritmus pro výpočet příznaků reprezentující číslice.
     * Zakomentovaný kód sloužil ke kontrole funkčnosti, vykreslí do souboru "obr.txt" danou cifru.
     * @param img obrázek s cifrou
     */
    public PrizrakClasif(Image img) {
        pole = new int[256];
//        PrintWriter writer = null;
//        try {
//            writer = new PrintWriter("obr.txt", "UTF-8");
//        } catch (FileNotFoundException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        assert writer != null;

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
//                    writer.append('x');
                } else{
//                    writer.append(' ');
                }
            }
//            writer.println();
            if (i>2 && i<120 && y!=0 && y!=1 && y!=img.getHeight()-1 && y!=img.getHeight()-2) pole[y] = i;
        }
//        writer.close();

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
            if (i>2 && i<120 && y!=0 && y!=1 && y!=img.getHeight()-1 && y!=img.getHeight()-2) pole[y+128] = i;
        }
    }

    /**
     * Vraci vektor
     * @return pole
     */
    public int[] getPole() {return pole;}
}
