package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientDaoImpl implements ClientDao {

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, nom, email, telephone FROM clients";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Client cl = new Client(rs.getLong("id"), rs.getString("nom"), rs.getString("email"), rs.getString("telephone"));
                clients.add(cl);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    @Override
    public Client findById(Long id) {
        String sql = "SELECT id, nom, email, telephone FROM clients WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(rs.getLong("id"), rs.getString("nom"), rs.getString("email"), rs.getString("telephone"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Client save(Client client) {
        String sql = "INSERT INTO clients(nom, email, telephone) VALUES(?, ?, ?)";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getTelephone());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    client.setId(keys.getLong(1));
                }
            }
            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client update(Client client) {
        String sql = "UPDATE clients SET nom = ?, email = ?, telephone = ? WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getTelephone());
            ps.setLong(4, client.getId());
            ps.executeUpdate();
            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection c = JdbcUtils.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
