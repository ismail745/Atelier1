package ma.fstt.servlets;

import ma.fstt.dao.ProduitDao;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteProduitServlet", urlPatterns = "/produits/delete")
public class DeleteProduitServlet extends HttpServlet {

    @Inject
    private ProduitDao produitDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            produitDao.delete(Long.valueOf(idStr));
        }
        resp.sendRedirect(req.getContextPath() + "/produits");
    }
}
