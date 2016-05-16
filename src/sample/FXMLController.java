package sample;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Lukado on 15. 4. 2016.
 */
public class FXMLController implements Initializable {
    @FXML
    public Button reset;
    @FXML
    private Canvas canvas;
    @FXML
    public Pane pane;
    @FXML
    public TextField text;
    @FXML
    public TextArea area;
    @FXML
    public Button testData;
    @FXML
    public ChoiceBox<String> box;
    @FXML
    public ChoiceBox<String> box1;

    public GraphicsContext gc;
    public ArrayList<Vektor> vektory;

    /**
     * Proměnné atributy
     */
    public int tolerance = 230;
    public int pocetAdresaru = 5;

    /**
     * Reset plátna
     * @param event
     */
    public void canvasReset(ActionEvent event){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Uložení plátna do obrázku 128x128
     * @param actionEvent
     * @throws IOException
     */
    public void saveImage(ActionEvent actionEvent) throws IOException {
        Image img = saveImg();
        if (box1.getSelectionModel().getSelectedIndex()!=0){
            try {
                loadLearnData("firstLoad");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int x = useParamAlgorithm(new Vektor(useClasifAlgorithm(img), -1));
        text.setText(""+x);
    }

    /**
     * Načítá trénovací data, seřadí a ukládá je do ArrayListu
     */
    private void loadLearnData(String path) throws Exception {
        vektory = new ArrayList<>();
        BufferedReader br = null;

        if (path.equals("firstLoad")) {
            for (int i = 0; i < pocetAdresaru; i++) {
                for (int j = 0; j < 10; j++) {
                    br = new BufferedReader(new FileReader("test" + (i + 1) + "/" + j + "/" + j + ".pgm"));
                    vektory.add(new Vektor(useClasifAlgorithm(convertPGM(br)), j));
                }
            }
            vektory.sort((o1, o2) -> Integer.compare(o1.getCislo(), o2.getCislo()));
        }
        else{
            for (int j=0;j<10;j++) {
                if (new File(path + "/" + j + "/" + j + ".pgm").exists()) {
                        br = new BufferedReader(new FileReader(path + "/" + j + "/" + j + ".pgm"));
                } else if (new File(path + "/" + j + "/c" + j + ".pgm").exists()) {
                        br = new BufferedReader(new FileReader(path + "/" + j + "/c" + j + ".pgm"));
                } else {
                    System.out.println("Špatná cesta k číslu " + j);
                    break;
                }
                vektory.add(new Vektor(useClasifAlgorithm(convertPGM(br)), j));
            }
            pocetAdresaru = 1;
        }
    }

    /**
     * Načítá testovací data a porovnává s trénovacími daty. Vypisuje výsledek do textboxu
     * @param path cesta k datům
     */
    private void loadTestData(String path) {
        area.clear();
        int procenta=0;
        BufferedReader br = null;
            for (int j=0;j<10;j++) {
                if (new File(path+"/"+j+"/"+j+".pgm").exists()) {
                    try {
                        br = new BufferedReader(new FileReader(path + "/" + j + "/" + j + ".pgm"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else if(new File(path+"/"+j+"/c"+j+".pgm").exists()){
                    try {
                        br = new BufferedReader(new FileReader(path + "/" + j + "/c" + j + ".pgm"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Špatná cesta k číslu "+j);
                    break;
                }
                int cislo = 0;
                try {
                    cislo = useParamAlgorithm(new Vektor(useClasifAlgorithm(convertPGM(br)), j));
                } catch (Exception e) {
                    System.out.println("Špatně zpracovaný soubor, pravděpodobně byl vytvořen v Gimpu...");
                    area.setText("Špatně zpracovaný soubor, pravděpodobně byl vytvořen v Gimpu...");
                    break;
                }
                area.appendText(j+" = "+cislo+"\n");
                if (cislo==j) procenta++;
            }
        area.appendText("\nÚspěšnost = "+procenta*10+"%");
    }

    /**
     * Převádí PGM soubory na JavaFX image typy
     * @param br
     * @return JavaFX Image
     */
    private Image convertPGM(BufferedReader br) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int i = 0;
            String line = br.readLine();
            while (line != null) {
                if (i > 3) {
                    stringBuilder.append(line);
                }
                line = br.readLine();
                i++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] split = stringBuilder.toString().split(" ");
        BufferedImage bi = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < 128; i++) {
                for (int j = 0; j < 128; j++) {
                    if (Integer.parseInt(split[i * 128 + j]) < tolerance) {
                        bi.setRGB(j, i, 0);
                    } else {
                        bi.setRGB(j, i, Color.WHITE.getRGB());
                    }
                }
            }
        return SwingFXUtils.toFXImage(bi, null);
    }

    /**
     * Ukládá nakreslený obrázek na plátně
     * @return JavaFX image
     */
    private Image saveImg() {
        WritableImage wim = new WritableImage(Main.width, Main.height);
        canvas.snapshot(null, wim);

        BufferedImage originalImage = SwingFXUtils.fromFXImage(wim, null);
        BufferedImage resizedImage = new BufferedImage(128, 128, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 128, 128, null);
        g.dispose();

        return SwingFXUtils.toFXImage(resizedImage, null);
    }

    /**
     * Metoda pro incializaci kreslícího plátna a načtení testovacích dat
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        box.setTooltip(new Tooltip("Vyberte druh klasifikace"));
        box.setItems(FXCollections.observableArrayList("K-NN", "Minimální vzdálenost"));
        box.setValue("K-NN");

        box1.setTooltip(new Tooltip("Vyberte druh algoritmu tvorby příznaků"));
        box1.setItems(FXCollections.observableArrayList("Přízraková klasifikace", "Bag of Words", "LBP"));
        box1.setValue("Přízraková klasifikace");

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#000"));
        gc.setStroke(Paint.valueOf("#000"));

        canvas.setOnMouseDragged(event -> {
            gc.lineTo(event.getX(), event.getY());
            gc.setLineWidth(10);
            gc.setLineJoin(StrokeLineJoin.ROUND);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setStroke(Paint.valueOf("#000"));
            gc.stroke();
        });
        canvas.setOnMousePressed(event -> {
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
        });
        canvas.setOnMouseReleased(event -> gc.closePath());

        try {
            loadLearnData("firstLoad");
        } catch (Exception e) {
            System.out.println("Špatně zpracovaný soubor, pravděpodobně byl vytvořen v Gimpu...");
            area.setText("Špatně zpracovaný soubor, pravděpodobně byl vytvořen v Gimpu...");
        }
    }

    /**
     * Otevírá složku s testovacími daty a posílá dál pro zpracování
     * @param actionEvent
     */
    public void readTestData(ActionEvent actionEvent) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Open Resource File");
        File defaultDir = new File("V:/Skola/Druhak/UIR/SP/semestralka");
        fileChooser.setInitialDirectory(defaultDir);
        File selectedDirectory = fileChooser.showDialog(Main.getPrimaryStage());
        if (selectedDirectory!=null){
            loadTestData(selectedDirectory.getAbsolutePath());
        }
    }

    /**
     * Otevírá složku s klasifikačnímy daty a posílá dál pro zpracování
     * @param actionEvent
     */
    public void readLearnData(ActionEvent actionEvent) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Open Resource File");
        File defaultDir = new File("V:/Skola/Druhak/UIR/SP/semestralka");
        fileChooser.setInitialDirectory(defaultDir);
        File selectedDirectory = fileChooser.showDialog(Main.getPrimaryStage());
        if (selectedDirectory!=null){
            try {
                loadLearnData(selectedDirectory.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Špatně zpracovaný soubor, pravděpodobně byl vytvořen v Gimpu...");
                area.setText("Špatně zpracovaný soubor, pravděpodobně byl vytvořen v Gimpu...");
            }
        }
    }

    /**
     * Použití vybraného parametrického algoritmu
     * @param v hledaný vektor
     * @return vysledné číslo
     */
    public int useParamAlgorithm(Vektor v){
        switch (box.getSelectionModel().getSelectedIndex()) {
            case 0:
                return new Knn(vektory, v).getNumber();
            case 1:
                new minDistance(vektory, v, pocetAdresaru).getNumber();
            default:
                return new Knn(vektory, v).getNumber();
        }
    }

    /**
     * Použití vybraného klasifikačního algoritmu
     * @param img JavaFX image
     * @return pole hledaného obrázku
     */
    public int[] useClasifAlgorithm(Image img){
        switch (box1.getSelectionModel().getSelectedIndex()){
            case 0:
                return new PrizrakClasif(img).getPole();
            case 1:
                return new BagOfWords(img).getPole();
            case 2:
                return new LBP(img).getPole();
            default:
                return new PrizrakClasif(img).getPole();
        }
    }
}
