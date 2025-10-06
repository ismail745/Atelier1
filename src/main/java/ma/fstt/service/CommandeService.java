package ma.fstt.service;

import ma.fstt.entities.Commande;
import java.util.List;

public interface CommandeService {
    List<Commande> listAll();
    Commande findById(Long id);
    Commande create(Commande c);
    Commande update(Commande c);
    boolean delete(Long id);
}
