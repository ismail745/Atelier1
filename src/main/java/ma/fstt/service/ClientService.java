package ma.fstt.service;

import ma.fstt.entities.Client;
import java.util.List;

public interface ClientService {
    List<Client> listAll();
    Client findById(Long id);
    Client create(Client client);
    Client update(Client client);
    boolean delete(Long id);
}
