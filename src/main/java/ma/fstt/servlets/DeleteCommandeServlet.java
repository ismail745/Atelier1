package ma.fstt.servlets;

import ma.fstt.dao.CommandeDao;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteCommandeServlet", urlPatterns = "/commandes/delete")
public class DeleteCommandeServlet extends HttpServlet {

    @Inject
    private CommandeDao commandeDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            commandeDao.delete(Long.valueOf(idStr));
        }
        resp.sendRedirect(req.getContextPath() + "/commandes");
    }
}
