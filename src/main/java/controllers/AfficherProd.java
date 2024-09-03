package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Produit;
import services.ServicesProduit;

import java.io.IOException;

public class AfficherProd {

    @FXML
    private TableView<Produit> produitTableView;

    @FXML
    private TableColumn<Produit, String> nomProdColumn;

    @FXML
    private Slider prixSlider;

    @FXML
    private Text maxPrix;

    public static int cat_id=0;

    @FXML
    private TableColumn<Produit, String> descProdColumn;

    @FXML
    private TableColumn<Produit, Integer> quantiteColumn;

    @FXML
    private TableColumn<Produit, String> imageColumn;

    @FXML
    private TableColumn<Produit, Double> prixColumn;

    @FXML
    private TableColumn<Produit, Void> action;

    @FXML
    private TableColumn<Produit, Void> promoColumn;

    @FXML
    private TextField search_text;

    @FXML
    private Button search_btn;

    private ObservableList<Produit> allProduits; // Store the original list of products

    private ServicesProduit servicesProduit = new ServicesProduit();

    @FXML
    public void initialize() {
        // Initialize allProduits and set up the TableView
        allProduits = FXCollections.observableArrayList(servicesProduit.afficherProduits());
        produitTableView.setItems(allProduits);

        // Set up the columns
        nomProdColumn.setCellValueFactory(new PropertyValueFactory<>("nom_prod"));
        descProdColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Set up the image column
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageColumn.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);

                if (empty || imageName == null || imageName.isEmpty()) {
                    setGraphic(null);
                } else {
                    String imagePath = "file:src/main/resources/img/" + imageName;
                    try {
                        Image image = new Image(imagePath, true);
                        imageView.setImage(image);
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + e.getMessage());
                        setGraphic(null);
                    }
                }
            }
        });

        // Set up the action column
        action.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button("Details");
            private final Button modifierButton = new Button("Modifier");
            private final Button supprimerButton = new Button("Supprimer");

            {
                detailsButton.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    System.out.println("Details for: " + produit.getNom_prod());
                });

                modifierButton.setOnAction(event -> {
                    try {
                        Produit produit = getTableView().getItems().get(getIndex());

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/update_prod.fxml"));
                        Parent root = loader.load();

                        Update_prod updateProdController = loader.getController();
                        updateProdController.setProduit(produit);

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                supprimerButton.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    if (produit != null) {
                        // Confirm the deletion with the user
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to delete " + produit.getNom_prod() + "?");

                        // Show the confirmation dialog
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                // Delete from the database
                                servicesProduit.supprimer_produit1(produit.getRef_prod()); // Assuming you have a method that takes a product reference

                                // Remove from the TableView
                                produitTableView.getItems().remove(produit);

                                // Optionally, refresh the TableView or reload the product list
                                allProduits.remove(produit);
                                produitTableView.setItems(allProduits);
                            }
                        });
                    }
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5, detailsButton, modifierButton, supprimerButton);
                    setGraphic(hbox);
                }
            }
        });

        // Set up the promo column
        promoColumn.setCellFactory(param -> new TableCell<>() {
            private final Button promoButton = new Button();

            {
                promoButton.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    togglePromotion(produit);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView() == null) {
                    setGraphic(null);
                } else {
                    Produit produit = getTableView().getItems().get(getIndex());
                    promoButton.setText(produit.isEn_promo() ? "No" : "Yes");
                    setGraphic(promoButton);
                }
            }
        });

        // Add listener to update filter when slider value changes
        prixSlider.valueProperty().addListener((obs, oldValue, newValue) -> filterByPrice());

        // Add listener to search text field
        search_btn.setOnAction(event -> performSearch());
    }

    private void togglePromotion(Produit produit) {
        produit.setEn_promo(!produit.isEn_promo());
        servicesProduit.modifier_produit1(produit);
        produitTableView.refresh();
    }

    private void performSearch() {
        String searchText = search_text.getText().toLowerCase();
        ObservableList<Produit> filteredProducts = allProduits.filtered(produit ->
                produit.getNom_prod().toLowerCase().contains(searchText));
        produitTableView.setItems(filteredProducts);
    }

    private void filterByPrice() {
        double maxPrice = prixSlider.getValue();
        if (maxPrix != null) {
            maxPrix.setText(String.format("Max Price: %.2f", maxPrice));
        }

        ObservableList<Produit> filteredProduits = FXCollections.observableArrayList();
        for (Produit produit : allProduits) {
            if (produit.getPrix() <= maxPrice) {
                filteredProduits.add(produit);
            }
        }
        produitTableView.setItems(filteredProduits);
    }

    public void back(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/frontPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }

    public void cate(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/afficherCat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }

    public void stat(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/stats.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
    public void ajj(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/produit.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
}
