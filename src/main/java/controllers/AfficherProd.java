package controllers;

import javafx.collections.ObservableList;
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
import javafx.event.ActionEvent;
import services.API_SMS;
import services.Apisms;
import services.EmailSender;
import services.ServicesProduit;
import utils.MyDataBase;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProd implements Initializable {

    private Produit produit;
    private Connection connection;
    ServicesProduit sp = new ServicesProduit();
    @FXML
    private AnchorPane afficherprod;

    @FXML
    private FlowPane cardlyout;

    @FXML
    private TextField search_text;
    public static int cat_id = 0;


    @FXML
    private Slider prixSlider;

    @FXML
    private Text maxPrix;

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
                        fxmlLoader.setLocation(getClass().getResource("/cardview.fxml"));
                        HBox produitCard;
                        produitCard = fxmlLoader.load();
                        CardViewProd produitCardController = fxmlLoader.getController();
                        produitCardController.setData(produit);
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
    void back_to_add(ActionEvent event) {
        try {
            cat_id = 0;
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/produit.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public AfficherProd() {
        connection = MyDataBase.getInstance().getCnx();
    }

    @FXML
    private Button reload_id;

    @FXML
    void reload_page(ActionEvent event) throws IOException {
        cardlyout.getChildren().clear();
        // Recharge les données des produits et les réaffiche dans l'interface
        loadData();
    }

    private void loadData() {
        // Charger les données des produits depuis le service
        ObservableList<Produit> listeprod = sp.listProduit();

        // Afficher les produits dans la vue
        for (Produit produit : listeprod) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardview.fxml"));
                Pane cardView = loader.load();
                CardViewProd controller = loader.getController();
                controller.setData(produit);
                cardlyout.getChildren().add(cardView);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Your existing initialization code
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
            } else {
                System.out.println("Nombre de produits récupérés depuis la base de données : " + listeprod.size());

                String nomdProd = "Les Stocks des Produits : ";

                for (Produit produit : listeprod) {
                    if (produit.getQuantite() < 5) {
                        nomdProd += " " + produit.getNom_prod();

                    }
                    try {
                        if (cat_id != 0) {
                            if (produit.getCategorie() == cat_id && produit.getPrix() < newValue.intValue()) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardview.fxml"));
                                Pane cardView = loader.load();
                                CardViewProd controller = loader.getController();
                                controller.setData(produit);
                                cardlyout.getChildren().add(cardView);
                                if (produit.getPrix() < 2) {
                                    String emailBody = "Le produit " + produit.getNom_prod() + " a un prix inférieur à 2.";
                                    EmailSender.sendEmail(EmailSender.SMTP_USERNAME, "abdelkader.abidi0000@gmail.com", "Alerte de Prix Bas", emailBody);
                                }
                            }
                        } else {
                            if (produit.getPrix() < newValue.intValue()) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardview.fxml"));
                                Pane cardView = loader.load();
                                CardViewProd controller = loader.getController();
                                controller.setData(produit);
                                cardlyout.getChildren().add(cardView);

                            }
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (!nomdProd.equals("Les Stocks des Produits : ")) {
                    API_SMS.sendSMS(nomdProd);

                }
            }
        });

        ArrayList<Produit> listeprod = sp.getAll();
        cardlyout.toFront();
        cardlyout.setHgap(15);
        cardlyout.setVgap(15);
        if (listeprod.isEmpty()) {
            System.out.println("La liste des produits est vide.");
        } else {
            System.out.println("Nombre de produits récupérés depuis la base de données : " + listeprod.size());

            String nomdProd = "Les Stocks des Produits : ";

            for (Produit produit : listeprod) {
                if (produit.getPrix() > maxPrice) {
                    maxPrice = produit.getPrix();
                }
                if (produit.getQuantite() < 5) {
                    nomdProd += " " + produit.getNom_prod();
                }
                try {
                    if (cat_id != 0) {
                        if (produit.getCategorie() == cat_id) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardview.fxml"));
                            Pane cardView = loader.load();
                            CardViewProd controller = loader.getController();
                            controller.setData(produit);
                            cardlyout.getChildren().add(cardView);
                            if (produit.getPrix() < 2) {
                                String emailBody = "Le produit " + produit.getNom_prod() + " a un prix inférieur à 2.";
                                EmailSender.sendEmail(EmailSender.SMTP_USERNAME, "abdelkader.abidi0000@gmail.com", "Alerte de Prix Bas", emailBody);
                            }
                        }
                    } else {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardview.fxml"));
                        Pane cardView = loader.load();
                        CardViewProd controller = loader.getController();
                        controller.setData(produit);
                        cardlyout.getChildren().add(cardView);
                        if (produit.getPrix() < 2) {
                            String emailBody = " Cher Client Le produit " + produit.getNom_prod() + "a un prix inferieur a 2 DT exploiter l'opportunité  dans un clic !!! "+
                                    "Merci Pour Votre visite " ;


                            EmailSender.sendEmail(EmailSender.SMTP_USERNAME, "abdelkader.abidi0000@gmail.com", "Alerte de Prix Bas", emailBody);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (!nomdProd.equals("Les Stocks des Produits : ")) {
                API_SMS.sendSMS(nomdProd);
            }
        }
        prixSlider.setMax(maxPrice);
        prixSlider.setValue(maxPrice);
        maxPrix.setText(String.valueOf(maxPrice));
    }
}



