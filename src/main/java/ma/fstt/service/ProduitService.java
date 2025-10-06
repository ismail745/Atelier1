package ma.fstt.service;

import ma.fstt.entities.Produit;
import java.util.List;

public interface ProduitService {
    List<Produit> listAll();
    Produit findById(Long id);
    Produit create(Produit p);
    Produit update(Produit p);
    boolean delete(Long id);
}
