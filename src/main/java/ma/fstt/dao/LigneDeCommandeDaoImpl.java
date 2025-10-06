package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.LigneDeCommande;
import ma.fstt.entities.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LigneDeCommandeDaoImpl implements LigneDeCommandeDao {

    @Override
    public List<LigneDeCommande> findByCommandeId(Long commandeId) {
        List<LigneDeCommande> list = new ArrayList<>();
        String sql = "SELECT id, produit_id, quantite, prix_unitaire FROM lignes_de_commande WHERE commande_id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LigneDeCommande l = new LigneDeCommande();
                    l.setId(rs.getLong("id"));
                    Produit p = new Produit(); p.setId(rs.getLong("produit_id"));
                    l.setProduit(p);
                    l.setQuantite(rs.getInt("quantite"));
                    l.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                    list.add(l);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    @Override
    public LigneDeCommande save(LigneDeCommande l) {
        String sql = "INSERT INTO lignes_de_commande(commande_id, produit_id, quantite, prix_unitaire) VALUES(?, ?, ?, ?)";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // caller should set commandeId on the entity before saving
            ps.setLong(1, l.getCommandeId());
            ps.setLong(2, l.getProduit().getId());
            ps.setInt(3, l.getQuantite());
            ps.setDouble(4, l.getPrixUnitaire());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) l.setId(keys.getLong(1)); }
            return l;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM lignes_de_commande WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
