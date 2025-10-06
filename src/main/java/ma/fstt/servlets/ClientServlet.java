package ma.fstt.servlets;

import ma.fstt.dao.ClientDao;
import ma.fstt.entities.Client;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClientServlet", urlPatterns = "/clients")
public class ClientServlet extends HttpServlet {

    @Inject
    private ClientDao clientDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> clients = clientDao.findAll();
        req.setAttribute("clients", clients);
        req.getRequestDispatcher("/clients/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("nom");
        String email = req.getParameter("email");
        String telephone = req.getParameter("telephone");
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            Client c = new Client(null, nom, email, telephone);
            clientDao.save(c);
        } else {
            Long id = Long.valueOf(idStr);
            Client c = new Client(id, nom, email, telephone);
            clientDao.update(c);
        }
        resp.sendRedirect(req.getContextPath() + "/clients");
    }
}
