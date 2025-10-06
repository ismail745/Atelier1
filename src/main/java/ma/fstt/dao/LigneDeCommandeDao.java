package ma.fstt.dao;

import ma.fstt.entities.LigneDeCommande;
import java.util.List;

public interface LigneDeCommandeDao {
    List<LigneDeCommande> findByCommandeId(Long commandeId);
    LigneDeCommande save(LigneDeCommande l);
    boolean delete(Long id);
}
