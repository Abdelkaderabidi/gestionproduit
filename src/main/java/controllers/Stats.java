package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats {

    @FXML
    private LineChart<String, Number> favLineChart;

    @FXML
    public void initialize() {
        // Populate the chart with data when the view is initialized
        loadFavData();
    }

    private void loadFavData() {
        String query = "SELECT nom_prod, fav FROM produit";

        try (Connection connection = MyDataBase.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Create a series to hold the data
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Product Favorites");

            // Loop through the result set and add data to the series
            while (resultSet.next()) {
                String productName = resultSet.getString("nom_prod");
                int favCount = resultSet.getInt("fav");
                series.getData().add(new XYChart.Data<>(productName, favCount));
            }

            // Add the series to the chart
            favLineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading data for the chart: " + e.getMessage());
        }
    }

    public void back1(ActionEvent event) throws IOException {
        Parent afficherCatParent = FXMLLoader.load(getClass().getResource("/AfficherProd.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(afficherCatParent);
        stage.setScene(scene);
        stage.show();
    }
}
