package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Produit;
import services.ServicesProduit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Viewclient implements Initializable {

    @FXML
    private FlowPane cardlyout;

    @FXML
    private Button search_btn;

    @FXML
    private Slider prixSlider;

    @FXML
    private Text maxPrix;

    @FXML
    private TextField search_text;

    private ServicesProduit sp = new ServicesProduit();

    public static int cat_id = 0;

    @FXML
    void SearchProducts(ActionEvent event) {
        String searchEntry = search_text.getText();
        if (searchEntry.isEmpty()) {
            // If the search entry is empty, reload all products
            cardlyout.getChildren().clear();
            loadData();
        } else {
            List<Produit> searchResults = sp.SearchProd(searchEntry);
            cardlyout.getChildren().clear();
            if (!searchResults.isEmpty()) {
                for (Produit produit : searchResults) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                        Pane produitCard = fxmlLoader.load();
                        CardviewClient cardviewClient = fxmlLoader.getController();
                        cardviewClient.setData(produit);
                        cardlyout.getChildren().add(produitCard);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                // Show a prompt when no search results are found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Results");
                alert.setHeaderText("No results found.");
                alert.setContentText("Click OK to display all products.");

                ButtonType okButton = new ButtonType("OK");
                ButtonType cancelButton = new ButtonType("Cancel");

                alert.getButtonTypes().setAll(okButton, cancelButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == okButton) {
                        // If the OK button is clicked, reload all products
                        cardlyout.getChildren().clear();
                        loadData();
                    }
                });
            }
        }
    }

    @FXML
    void back_to_front(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/front_client.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void go_to_categorie(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/viewclientcat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void reload_page(ActionEvent event) {
        cardlyout.getChildren().clear();
        loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData(); // Ensure data is loaded when view initializes

        prixSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            maxPrix.setText(String.valueOf(newValue.intValue()));
            cardlyout.getChildren().clear();
            ArrayList<Produit> listeprod = sp.getAll();
            cardlyout.toFront();
            cardlyout.setHgap(15);
            cardlyout.setVgap(15);
            if (listeprod.isEmpty()) {
                System.out.println("La liste des produits est vide.");
            } else {
                for (Produit produit : listeprod) {
                    try {
                        if (cat_id != 0) {
                            if (produit.getCategorie() == cat_id) {
                                if (produit.getPrix() < newValue.intValue()) {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                                    Pane cardView = loader.load();
                                    CardviewClient controller = loader.getController();
                                    controller.setData(produit);
                                    cardlyout.getChildren().add(cardView);
                                }
                            }
                        } else {
                            if (produit.getPrix() < newValue.intValue()) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                                Pane cardView = loader.load();
                                CardviewClient controller = loader.getController();
                                controller.setData(produit);
                                cardlyout.getChildren().add(cardView);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
    }

    public void fav(ActionEvent event) throws IOException {
        // Load the afficherCat.fxml file
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/favorite.fxml"));

        // Get the current stage from the event source (the button)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new FXML file
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
    public void pro(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/promo.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }

    public void cat1(ActionEvent event) throws IOException {
        // Load the afficherCat.fxml file
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/afficherCat.fxml"));

        // Get the current stage from the event source (the button)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new FXML file
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }

    private void loadData() {
        cardlyout.getChildren().clear();
        List<Produit> produits = sp.listProduit();
        cardlyout.setHgap(15);
        cardlyout.setVgap(15);
        if (produits.isEmpty()) {
            System.out.println("La liste des produits est vide.");
        } else {
            for (Produit produit : produits) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                    Pane cardView = loader.load();
                    CardviewClient controller = loader.getController();
                    controller.setData(produit);
                    cardlyout.getChildren().add(cardView);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
