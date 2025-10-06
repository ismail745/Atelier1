package ma.fstt.entities;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LigneDeCommande {
    private Long id;
    private Long commandeId;
    private Produit produit;
    private int quantite;
    private double prixUnitaire;

    public LigneDeCommande() {}

    public LigneDeCommande(Long id, Produit produit, int quantite, double prixUnitaire) {
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
}
