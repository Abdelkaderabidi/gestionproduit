package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import models.Produit;
import utils.MyDataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardviewClient {

    @FXML
    private Label desc_card;

    @FXML
    private Button id_acheter;

    @FXML
    private Label nom_card;

    @FXML
    private Label prix_card;

    @FXML
    private ImageView prod_image;

    @FXML
    private HBox produit_Hbox;

    @FXML
    private Label quant_card;

    private Produit produit;
    private int ref_prod;

    @FXML
    private FlowPane cardlyout;

    @FXML
    private void acheter_prod() {
        // Retrieve the current fav count from the database
        int currentFavCount = getCurrentFavCount(produit.getRef_prod());

        // Increment the fav count
        int newFavCount = currentFavCount + 1;

        // Update the produit in the database
        updateProduitInDatabase(produit.getRef_prod(), newFavCount);

        System.out.println("Product favorited. New fav count: " + newFavCount);
    }

    // Method to get the current fav count from the database
    private int getCurrentFavCount(int ref_prod) {
        String query = "SELECT fav FROM produit WHERE ref_prod = ?";
        int favCount = 0;

        try (Connection connection = MyDataBase.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Check if the connection is valid
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is not available.");
            }

            // Set the parameter and execute the query
            statement.setInt(1, ref_prod);
            try (ResultSet resultSet = statement.executeQuery()) {
                // Retrieve the current fav count
                if (resultSet.next()) {
                    favCount = resultSet.getInt("fav");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving the fav count: " + e.getMessage());
        }

        return favCount;
    }

    private void updateProduitInDatabase(int ref_prod, int newFavCount) {
        String query = "UPDATE produit SET fav = ? WHERE ref_prod = ?";

        try (Connection connection = MyDataBase.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Check if the connection is valid
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is not available.");
            }

            // Create and set up the PreparedStatement
            statement.setInt(1, newFavCount);
            statement.setInt(2, ref_prod);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Database updated successfully.");
            } else {
                System.out.println("No rows updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating the database: " + e.getMessage());
        }
    }

    public void setData(Produit produit) {
        if (produit == null) {
            return;
        }

        this.produit = produit;
        this.ref_prod = produit.getRef_prod();

        nom_card.setText(produit.getNom_prod());
        prix_card.setText(String.valueOf(produit.getPrix()) + "DT");
        quant_card.setText(String.valueOf(produit.getQuantite()));
        desc_card.setText(produit.getDescription());
        produit_Hbox.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.3), 10, 0 , 0 ,10);");

        if (produit.getImage() != null) {
            String image_directory_path = "src/main/resources/img/";
            String full_path = image_directory_path + produit.getImage();
            System.out.println(full_path);
            try {
                Image img = new Image(new File(full_path).toURI().toString());
                prod_image.setImage(img);
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        }
        if (produit.getQuantite() < 5) {
            quant_card.setStyle("-fx-text-fill: red;"); // Change text color to red to indicate insufficient stock
            quant_card.setText(quant_card.getText() + " (Produit limité )");
        }
    }
}
