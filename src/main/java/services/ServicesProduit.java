package services;

import interfaces.IProduit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Produit;
import utils.MyDataBase;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


import java.sql.SQLException;

public class ServicesProduit implements IProduit<Produit> {
    private Connection cnx;
    public ServicesProduit(){cnx = MyDataBase.getInstance().getCnx();}

    @Override
    public void add(Produit produit) {

        try {
            String qry = "INSERT INTO `produit`(`nom_prod`, `quantite`, `description`, `prix`, `image`, `id_cat`, `fav`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,produit.getNom_prod());
            stm.setInt(2,produit.getQuantite());
            stm.setString(3,produit.getDescription());
            stm.setFloat(4,produit.getPrix());
            stm.setString(5,produit.getImage());
            stm.setInt(6,produit.getCategorie());
            stm.setInt(7,produit.getFav());

            stm.executeUpdate();
            System.out.println("produit ajoutÃ© !");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public ArrayList<Produit> getAll() {                            // affichage les produit
        String qry = "SELECT * FROM `produit`";
        ArrayList<Produit> produits = new ArrayList<>();
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs =stm.executeQuery(qry);
            while(rs.next()){
                Produit p =new Produit();
                p.setRef_prod(rs.getInt("ref_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setQuantite(rs.getInt("quantite"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setImage(rs.getString("image"));
                p.setCategorie(rs.getInt("id_cat"));
                produits.add(p);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return produits;
    }

    public ObservableList<Produit> listFavoriteProducts() {
        ObservableList<Produit> mylist = FXCollections.observableArrayList();
        try {
            String qry = "SELECT * FROM `produit` WHERE fav > 0";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Produit p = new Produit();
                p.setRef_prod(rs.getInt("ref_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setQuantite(rs.getInt("quantite"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setImage(rs.getString("image"));
                p.setCategorie(rs.getInt("id_cat"));

                mylist.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mylist;
    }
    public ObservableList<Produit> listpromo() {
        ObservableList<Produit> mylist = FXCollections.observableArrayList();
        try {
            String qry = "SELECT * FROM `produit` WHERE en_promo = 1";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Produit p = new Produit();
                p.setRef_prod(rs.getInt("ref_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setQuantite(rs.getInt("quantite"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setImage(rs.getString("image"));
                p.setCategorie(rs.getInt("id_cat"));

                mylist.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mylist;
    }

    public ArrayList<Produit> afficherProduits() {
        String qry = "SELECT * FROM `produit`";
        ArrayList<Produit> produits = new ArrayList<>();
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Produit p = new Produit();
                p.setRef_prod(rs.getInt("ref_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setQuantite(rs.getInt("quantite"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setImage(rs.getString("image"));
                p.setCategorie(rs.getInt("id_cat"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produits;
    }


    public ArrayList<Produit> getprod(int idcat) {
        String qry = "SELECT * FROM `produit` WHERE  id_cat = ?";
        ArrayList<Produit> produits = new ArrayList<>();
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, idcat);
            ResultSet rs =stm.executeQuery(qry);
            while(rs.next()){
                Produit p =new Produit();
                p.setRef_prod(rs.getInt(1));
                p.setNom_prod(rs.getString(2));
                p.setQuantite(rs.getInt(3));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat(5));
                p.setImage(rs.getString("image"));
                p.setCategorie(rs.getInt(7));
                produits.add(p);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(produits);
        return produits;
    }

    @Override
    public void update(Produit produit) {

    }

    @Override
    public boolean delete(Produit produit) {
        return false;
    }





    public ObservableList<Produit> listProduit(){
        ObservableList<Produit> mylist = FXCollections.observableArrayList();
        try {
            String qry = "SELECT * FROM `produit`";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while(rs.next()){

                Produit p = new Produit();
                p.setRef_prod(rs.getInt("ref_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setQuantite(rs.getInt("quantite"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setImage(rs.getString("image"));
                p.setCategorie(rs.getInt("id_cat"));

                mylist.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mylist;
    }

    public void supprimer_produit(Produit produit){
        try {
            String qry = "DELETE FROM `produit` WHERE ref_prod =  " + produit.getRef_prod();
            Statement stm = cnx.createStatement();
            stm.executeUpdate(qry);
            System.out.println("Produit supprimÃ© !");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void supprimerProduitByCategorie(int idCatégorie) {
        String qry = "DELETE FROM `produit` WHERE id_cat = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, idCatégorie);
            stm.executeUpdate();
            System.out.println("Produits associés à la catégorie supprimés !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Produit> SearchProd(String entry) {
        ObservableList<Produit> mylist = FXCollections.observableArrayList();

        try {
            String qry = "SELECT * FROM produit WHERE  nom_prod LIKE ? or quantite LIKE ? or description LIKE ? or prix LIKE ? or image LIKE ? or id_cat LIKE ?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,"%" + entry + "%");
            stm.setString(2,"%" + entry + "%");
            stm.setString(3,"%" + entry + "%");
            stm.setString(4,"%" + entry + "%");
            stm.setString(5,"%" + entry + "%");
            stm.setString(6,"%" + entry + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Produit p = new Produit();
                p.setRef_prod(rs.getInt("ref_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setQuantite(rs.getInt("quantite"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setImage(rs.getString("image"));
                p.setCategorie(rs.getInt("id_cat"));

                mylist.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return mylist;
    }

    public  void modifier_produit_card(Produit produit){
        try {
            String qry = "UPDATE produit SET nom_prod =?, quantite = ?, description = ?, prix = ?,image = ? WHERE ref_prod=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,produit.getNom_prod());
            stm.setInt(2,produit.getQuantite());
            stm.setString(3,produit.getDescription());
            stm.setFloat(4,produit.getPrix());
            stm.setString(5,produit.getImage());
            stm.setInt(6,produit.getRef_prod());
            stm.executeUpdate();
            System.out.println("Produit modifiÃ© !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public  void modifier_produit(Produit produit){
        try {
            String qry = "UPDATE produit SET nom_prod =?, quantite = ?, description = ?, prix = ?,image = ?,id_cat = ?  WHERE ref_prod=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,produit.getNom_prod());
            stm.setInt(2,produit.getQuantite());
            stm.setString(3,produit.getDescription());
            stm.setFloat(4,produit.getPrix());
            stm.setString(5,produit.getImage());
            stm.setInt(6,produit.getCategorie());
            stm.setInt(7,produit.getRef_prod());
            stm.executeUpdate();
            System.out.println("Produit modifier!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier_produit1(Produit produit) {
        PreparedStatement statement = null;
        try {
            Connection connection = MyDataBase.getInstance().getCnx();

            // Check if the connection is valid
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is not available.");
            }

            String query = "UPDATE produit SET en_promo = ? WHERE ref_prod = ?";
            statement = connection.prepareStatement(query);

            // Set the values for the query
            statement.setInt(1, produit.isEn_promo() ? 1 : 0);
            statement.setInt(2, produit.getRef_prod());

            // Execute the update
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ensure resources are closed but not the connection
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void supprimer_produit1(int produitId) {
        // Your implementation to delete a product from the database
        String query = "DELETE FROM produit WHERE ref_prod = ?";
        try (Connection conn = MyDataBase.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, produitId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public int prodparcat(int id){
        int n = 0;
        try {
            String qry ="SELECT COUNT(*) FROM produit INNER JOIN catégorie ON produit.id_cat = catégorie.id_cat WHERE catégorie.id_cat = "+id;
            PreparedStatement stm = cnx.prepareStatement(qry);
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                n=rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return n;
    }

    public void modifier_card(Produit produit , int ref_prod) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gestionproduit", "root", "");

            // Créer la requête SQL pour mettre à jour les informations du produit
            String query = "UPDATE produit SET nom_prod =?, quantite = ?, description = ?, prix = ?,image = ? WHERE ref_prod="+ref_prod;
            stmt = conn.prepareStatement(query);
            stmt.setString(1, produit.getNom_prod());
            stmt.setString(3, produit.getDescription());
            stmt.setInt(2, produit.getQuantite());
            stmt.setFloat(4, produit.getPrix());
            stmt.setString(5,produit.getImage());




            // Exécuter la requête
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produit mis à jour avec succès dans la base de données.");
            } else {
                System.out.println("Échec de la mise à jour du produit dans la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du produit dans la base de données : " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

    }

    public void modifier_image_par_ref(int ref_prod, File nouvelleImage) {
        Connection conn = null;
        PreparedStatement stmt = null;
        FileInputStream fis = null; // Déclarer le FileInputStream en dehors du try pour qu'il soit accessible dans le bloc finally

        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gestionproduit", "root", "");

            // Créer la requête SQL pour mettre à jour l'image du produit
            String query = "UPDATE produit SET image=? WHERE ref_prod=?";
            stmt = conn.prepareStatement(query);

            // Lire le contenu du fichier image
            fis = new FileInputStream(nouvelleImage);
            // Assigner le contenu du fichier image comme un BLOB à la requête
            stmt.setBinaryStream(1, fis);
            // Assigner la référence du produit à la requête
            stmt.setInt(2, ref_prod);

            // Exécuter la requête
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Image du produit mise à jour avec succès dans la base de données.");
            } else {
                System.out.println("Échec de la mise à jour de l'image du produit dans la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'image du produit dans la base de données : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier image : " + e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du flux FileInputStream : " + e.getMessage());
            }
        }
    }



}
