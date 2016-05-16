package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /** Sirka okna */
    static final int width = 600;
    /** Vyska okna */
    static final int height = 500;
    /** Medium ktere ovlada simulaci */
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("UIR seminarka");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setResizable(false);
        primaryStage.show();
        Main.primaryStage = primaryStage;
    }

    /**
     * Program pro klasifikaci ručně psaných číslic
     * @param args
     */
    public static void main(String[] args) {
        launch(args);

//        if (args.length==1) {
//            launch(args);
//        }else if(args.length==5){
//            System.out.println(args[0]);
//        }else{
//            sendForHelp();
//        }
    }

//    public static void sendForHelp(){
//        System.out.println("Špatně zadaný vstup\n" +
//                "Nápověda:\n\n" +
//                "\tSpuštění s jedním parametrem:\n" +
//                "\t\tnázev_modelu\n\n" +
//                "\tSpuštění s více parametry:\n" +
//                "\t\ttrénovací_množina\n" +
//                "\t\ttestovací_množina\n" +
//                "\t\tparametrizační_algoritmus\n" +
//                "\t\tklasifikační_algoritmus\n" +
//                "\t\tnázev_modelu\n");
//        System.out.println("Stiskněte klávesu pro ukončení");
//    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
}
