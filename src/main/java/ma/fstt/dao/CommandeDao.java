package ma.fstt.dao;

import ma.fstt.entities.Commande;
import java.util.List;

public interface CommandeDao {
    List<Commande> findAll();
    Commande findById(Long id);
    Commande save(Commande c);
    Commande update(Commande c);
    boolean delete(Long id);
}
