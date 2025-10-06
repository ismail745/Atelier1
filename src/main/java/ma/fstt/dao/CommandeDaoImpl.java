package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Commande;
import ma.fstt.entities.Client;
import ma.fstt.entities.LigneDeCommande;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommandeDaoImpl implements CommandeDao {

    @Override
    public List<Commande> findAll() {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT id, client_id, date_commande FROM commandes";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Commande com = new Commande();
                com.setId(rs.getLong("id"));
                Client cl = new Client(); cl.setId(rs.getLong("client_id"));
                com.setClient(cl);
                com.setDateCommande(rs.getDate("date_commande").toLocalDate());
                // lines can be loaded lazily by LigneDeCommandeDao
                list.add(com);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    @Override
    public Commande findById(Long id) {
        String sql = "SELECT id, client_id, date_commande FROM commandes WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Commande com = new Commande();
                    com.setId(rs.getLong("id"));
                    Client cl = new Client(); cl.setId(rs.getLong("client_id"));
                    com.setClient(cl);
                    com.setDateCommande(rs.getDate("date_commande").toLocalDate());
                    return com;
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    @Override
    public Commande save(Commande c) {
        String sql = "INSERT INTO commandes(client_id, date_commande) VALUES(?, ?)";
        try (Connection conn = JdbcUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getClient().getId());
            ps.setDate(2, Date.valueOf(c.getDateCommande()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) c.setId(keys.getLong(1)); }
            return c;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Commande update(Commande c) {
        String sql = "UPDATE commandes SET client_id = ?, date_commande = ? WHERE id = ?";
        try (Connection conn = JdbcUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, c.getClient().getId());
            ps.setDate(2, Date.valueOf(c.getDateCommande()));
            ps.setLong(3, c.getId());
            ps.executeUpdate();
            return c;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM commandes WHERE id = ?";
        try (Connection conn = JdbcUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
