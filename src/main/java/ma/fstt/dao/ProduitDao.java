package ma.fstt.dao;

import ma.fstt.entities.Produit;
import java.util.List;

public interface ProduitDao {
    List<Produit> findAll();
    Produit findById(Long id);
    Produit save(Produit p);
    Produit update(Produit p);
    boolean delete(Long id);
}
