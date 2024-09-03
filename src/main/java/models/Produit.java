package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Produit {
    private int ref_prod;

    private String nom_prod;
    private String description;
    private int quantite;

    private int fav;

    private String image;


    private float prix;


    private int categorie;

    private boolean en_promo;

    private double prix_apres_promo ;

    public Produit() {
    }



    public Produit(int ref_prod, String nom_prod, String description, int quantite, String image, float prix, int categorie) {
        this.ref_prod = ref_prod;
        this.nom_prod = nom_prod;
        this.description = description;
        this.quantite = quantite;
        this.image = image;
        this.prix = prix;
        this.categorie = categorie;
    }



    public Produit(String nom_prod, String description, int quantite, float prix) {
        this.nom_prod = nom_prod;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
    }


    public Produit(String nom_prod, String description, int quantite, String image, float prix, int categorie) {
        this.nom_prod = nom_prod;
        this.description = description;
        this.quantite = quantite;
        this.image = image;
        this.prix = prix;
        this.categorie = categorie;
    }

    public double getPrix_apres_promo() {
        return prix_apres_promo;
    }

    public void setPrix_apres_promo(double prix_apres_promo) {
        this.prix_apres_promo = prix_apres_promo;
    }

    public Produit(double prix_apres_promo) {
        this.prix_apres_promo = prix_apres_promo;
    }

    public Produit(int fav) {
        this.fav = fav;
    }

    public Produit(int ref_prod, String nom_prod, String description, int quantite, int fav, String image, float prix, int categorie) {
        this.ref_prod = ref_prod;
        this.nom_prod = nom_prod;
        this.description = description;
        this.quantite = quantite;
        this.fav = fav;
        this.image = image;
        this.prix = prix;
        this.categorie = categorie;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public int getRef_prod() {
        return ref_prod;
    }

    public void setRef_prod(int ref_prod) {
        this.ref_prod = ref_prod;
    }

    public String getNom_prod() {
        return nom_prod;
    }

    public void setNom_prod(String nom_prod) {
        this.nom_prod = nom_prod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public Produit(String nom_prod, String description, int quantite, float prix, int categorie) {
        this.nom_prod = nom_prod;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Produit(String nom_prod, String description, int quantite, String image, float prix) {
        this.nom_prod = nom_prod;
        this.description = description;
        this.quantite = quantite;
        this.image = image;
        this.prix = prix;
    }
    public ImageView getImageView() {
        ImageView imageView = new ImageView();
        if (image != null && !image.isEmpty()) {
            try {
                // Convert the relative path to a valid file URL
                Image img = new Image(getClass().getResource("/" + image).toExternalForm());
                imageView.setImage(img);
                imageView.setFitHeight(100); // Adjust size if necessary
                imageView.setFitWidth(100);  // Adjust size if necessary
                imageView.setPreserveRatio(true);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        } else {
            System.out.println("No image path provided.");
        }
        return imageView;
    }

    public boolean isEn_promo() {
        return en_promo;
    }

    public void setEn_promo(boolean en_promo) {
        this.en_promo = en_promo;
    }

    public Produit(boolean en_promo) {
        this.en_promo = en_promo;
    }

    public Produit(int ref_prod, String nom_prod, String description, int quantite, int fav, String image, float prix, int categorie, boolean en_promo) {
        this.ref_prod = ref_prod;
        this.nom_prod = nom_prod;
        this.description = description;
        this.quantite = quantite;
        this.fav = fav;
        this.image = image;
        this.prix = prix;
        this.categorie = categorie;
        this.en_promo = en_promo;
        this.prix_apres_promo=prix_apres_promo;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "ref_prod=" + ref_prod +
                ", nom_prod='" + nom_prod + '\'' +
                ", description='" + description + '\'' +
                ", quantite=" + quantite +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                ", categorie=" + categorie +
                ", en_promo" + en_promo +
                "}";

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit produit)) return false;
        return getRef_prod() == produit.getRef_prod() && getQuantite() == produit.getQuantite() && Float.compare(getPrix(), produit.getPrix()) == 0 && getCategorie() == produit.getCategorie() && Objects.equals(getNom_prod(), produit.getNom_prod()) && Objects.equals(getDescription(), produit.getDescription()) && Objects.equals(getImage(), produit.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRef_prod(), getNom_prod(), getDescription(), getQuantite(), getImage(), getPrix(), getCategorie());
    }
}
