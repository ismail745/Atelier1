package ma.fstt.entities;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Commande {
    private Long id;
    private Client client;
    private LocalDate dateCommande;
    private List<LigneDeCommande> lignes = new ArrayList<>();

    public Commande() {}

    public Commande(Long id, Client client, LocalDate dateCommande) {
        this.id = id;
        this.client = client;
        this.dateCommande = dateCommande;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }

    public List<LigneDeCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneDeCommande> lignes) { this.lignes = lignes; }
}
