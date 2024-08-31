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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Produit;
import services.API_SMS;
import services.Apisms;
import services.EmailSender;
import services.ServicesProduit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Viewclient implements Initializable {

    @FXML
    private AnchorPane afficherprod;

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
    private Produit produit;
    ServicesProduit sp = new ServicesProduit();

    public static int cat_id = 0;


    @FXML
    void SearchProducts(ActionEvent event) {
        String searchEntry = search_text.getText();
        if (searchEntry.isEmpty()) {
            // If the search entry is empty, reload all products
            cardlyout.getChildren().clear();
            loadData();

        } else {
            ServicesProduit sp = new ServicesProduit();
            List<Produit> searchResults = sp.SearchProd(searchEntry);
            cardlyout.getChildren().clear();
            if (!searchResults.isEmpty()) {
                for (Produit produit : searchResults) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/cardviewclient.fxml"));
                        HBox produitCard;
                        produitCard = fxmlLoader.load();
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

    private void loadData() {
        // Charger les données des produits depuis le service
        ObservableList<Produit> listeprod = sp.listProduit();

        // Afficher les produits dans la vue
        for (Produit produit : listeprod) {
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

    @FXML
    void back_to_front(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/front_client.fxml"));
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
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/viewclientcat.fxml"));
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
        // Recharge les données des produits et les réaffiche dans l'interface
        loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(AfficherProd.cat_id);
        float maxPrice = 0;
        prixSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            maxPrix.setText(String.valueOf(newValue.intValue()));
            cardlyout.getChildren().clear();
            ArrayList<Produit> listeprod = sp.getAll();
            cardlyout.toFront();
            cardlyout.setHgap(15);
            cardlyout.setVgap(15);
            if (listeprod.isEmpty()) {
                System.out.println("La liste des produits est vide.");
            }
            else {
                System.out.println("Nombre de produits récupérés depuis la base de données : " + listeprod.size());

                // Créer et afficher une CardView pour chaque produit
                String nomdProd = "Les Stocks des Produits  : ";
                String to = "abdelkader.abidi0000@gmail.com";
                StringBuilder bodyBuilder = new StringBuilder(); // Pour construire le corps de l'e-mail
                for (Produit produit : listeprod) {
                    if (produit.getQuantite() < 5){
                        nomdProd = nomdProd + " " + produit.getNom_prod();
                    }
                    try {
                        if (cat_id != 0) {
                            if (produit.getCategorie() == cat_id) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                                Pane cardView = loader.load();
                                CardviewClient controller = loader.getController();
                                if(produit.getPrix() < newValue.intValue())
                                {
                                    controller.setData(produit); // Appel de la méthode setData
                                    cardlyout.getChildren().add(cardView);
                                    // Vérifier si le prix du produit est inférieur à 2.0


                                }
                            }
                        } else {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                            Pane cardView = loader.load();
                            CardviewClient controller = loader.getController();
                            if(produit.getPrix() < newValue.intValue())
                            {
                                controller.setData(produit); // Appel de la méthode setData
                                cardlyout.getChildren().add(cardView);
                                // Vérifier si le prix du produit est inférieur à 2.0


                            }
                        }

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

                // Vérifiez si des produits ont une quantité inférieure à 5 et envoyez un SMS
                if (!nomdProd.equals("Les Stocks des Produits  : ")) {
                    API_SMS.sendSMS(nomdProd);
                }

            }
        });
        ArrayList<Produit> listeprod = sp.getAll();
        Produit prod;
        cardlyout.toFront();
        cardlyout.setHgap(15);
        cardlyout.setVgap(15);
        if (listeprod.isEmpty()) {
            System.out.println("La liste des produits est vide.");
        }
        else {
            String to = "abdelkader.abidi0000@gmail.com";
            StringBuilder bodyBuilder = new StringBuilder(); // Pour construire le corps de l'e-mail

            for (Produit produit : listeprod) {
                if(produit.getPrix() > maxPrice)
                {
                    maxPrice = produit.getPrix();
                }
                try {
                    if (cat_id != 0) {
                        if ((produit.getCategorie() == cat_id)) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                            Pane cardView = loader.load();
                            CardviewClient controller = loader.getController();
                            controller.setData(produit); // Appel de la méthode setData
                            cardlyout.getChildren().add(cardView);
                            // Vérifier si le prix du produit est inférieur à 2.0

                        }
                    } else {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardviewclient.fxml"));
                        Pane cardView = loader.load();
                        CardviewClient controller = loader.getController();
                        controller.setData(produit); // Appel de la méthode setData
                        cardlyout.getChildren().add(cardView);
                        System.out.println(produit.getImage());
                        System.out.println("-------------------");
                        // Vérifier si le prix du produit est inférieur à 2.0

                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            // Envoyer l'e-mail une seule fois avec tous les noms des produits inférieurs à 2.0

            if (bodyBuilder.length() > 0) {
                EmailSender.VerificationCodeSender(to, bodyBuilder.toString());
            }


        }
        prixSlider.setMax(maxPrice);
        prixSlider.setValue(maxPrice);
        maxPrix.setText(String.valueOf(maxPrice));
    }
}




