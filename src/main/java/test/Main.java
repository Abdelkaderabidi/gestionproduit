package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Categorie;
import models.Produit;
import services.ServicesCategorie;
import services.ServicesProduit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        ServicesProduit sp = new ServicesProduit();
        Produit p1 = new Produit(14,"saaaa","hhhhdgdg",288,"imagkke",28F,3);
        /*sp.add(p1);*/
        ServicesCategorie sc = new ServicesCategorie();
        Categorie c1 = new Categorie("saif22");
        /*System.out.println(sp.getAll());*/
       /* System.out.println(sc.getProduitByCategorie(3));*/
        /*sc.modifier_categorie(1,"saiffff");*/
        System.out.println(sc.getAllCat());
        /*sc.addCat(c1);*/
        /*sc.supprimer_categorie(1);*/
        /*sp.supprimer_produit(p1);*/
        /*sp.modifier_produit(p1);*/
        System.out.println(sp.listProduit());

        /*sp.modifier_card(p1);
        System.out.println(sp.listProduit());*/
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/front_client.fxml"));
        try {
            Parent root = loader.load();
            Scene sc = new Scene(root);
            primaryStage.setTitle("gestion de produit");
            primaryStage.setScene(sc);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
