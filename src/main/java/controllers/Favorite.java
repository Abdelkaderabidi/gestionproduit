package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Produit;
import services.ServicesProduit;

import java.io.IOException;

public class Favorite {



    @FXML
    private Button cat0;

    @FXML
    private Button cat12;

    @FXML
    private Button cat121;

    @FXML
    private Button prod0;

    @FXML
    private FlowPane cardlyout; // Assuming this is where you want to add your product cards

    @FXML
    private void initialize() {
        loadData(); // Load data when the controller is initialized
    }

    ServicesProduit sp = new ServicesProduit();






    private void loadData() {
        // Load favorite products from the service
        ObservableList<Produit> listeprod = sp.listFavoriteProducts();

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










    public void cat0(ActionEvent event) throws IOException {
        // Load the afficherCat.fxml file
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/afficherCat.fxml"));

        // Get the current stage from the event source (the button)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new FXML file
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }

    public void prod0(ActionEvent event) throws IOException {
        // Load the afficherCat.fxml file
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/viewClient.fxml"));

        // Get the current stage from the event source (the button)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new FXML file
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
}
