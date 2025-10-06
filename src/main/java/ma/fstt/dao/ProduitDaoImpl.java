package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProduitDaoImpl implements ProduitDao {

    @Override
    public List<Produit> findAll() {
        List<Produit> list = new ArrayList<>();
        String sql = "SELECT id, nom, prix FROM produits";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Produit(rs.getLong("id"), rs.getString("nom"), rs.getDouble("prix")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Produit findById(Long id) {
        String sql = "SELECT id, nom, prix FROM produits WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Produit(rs.getLong("id"), rs.getString("nom"), rs.getDouble("prix"));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    @Override
    public Produit save(Produit p) {
        String sql = "INSERT INTO produits(nom, prix) VALUES(?, ?)";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) p.setId(keys.getLong(1)); }
            return p;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Produit update(Produit p) {
        String sql = "UPDATE produits SET nom = ?, prix = ? WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.setLong(3, p.getId());
            ps.executeUpdate();
            return p;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM produits WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
