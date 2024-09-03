package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Produit;
import services.ServicesProduit;

import java.io.IOException;

public class Promo {

    @FXML
    private FlowPane cardlyout; // Assuming this is where you want to add your product cards

    private ServicesProduit sp = new ServicesProduit();

    @FXML
    private void initialize() {
        loadData(); // Load data when the controller is initialized
    }

    private void loadData() {
        // Load promo products from the service
        ObservableList<Produit> listeprod = sp.listpromo();

        // Display the products in the view
        for (Produit produit : listeprod) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cc.fxml"));
                Pane cardView = loader.load();
                CardviewClient controller = loader.getController();
                controller.setData(produit);
                cardlyout.getChildren().add(cardView);
            } catch (IOException e) {
                e.printStackTrace(); // Use printStackTrace for better debugging
            }
        }
    }
    public void favor(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/favorite.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
    public void prodi(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/viewClient.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
    public void cati(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/viewclientcat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
}
