package ma.fstt.dao;

import ma.fstt.entities.Client;
import java.util.List;

public interface ClientDao {
    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    Client update(Client client);
    boolean delete(Long id);
}

