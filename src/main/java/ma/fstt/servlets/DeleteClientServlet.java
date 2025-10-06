package ma.fstt.servlets;

import ma.fstt.dao.ClientDao;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteClientServlet", urlPatterns = "/clients/delete")
public class DeleteClientServlet extends HttpServlet {

    @Inject
    private ClientDao clientDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            clientDao.delete(Long.valueOf(idStr));
        }
        resp.sendRedirect(req.getContextPath() + "/clients");
    }
}

