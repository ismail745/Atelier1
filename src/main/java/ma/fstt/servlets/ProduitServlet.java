package ma.fstt.servlets;

import ma.fstt.dao.ProduitDao;
import ma.fstt.dao.ProduitDaoImpl;
import ma.fstt.entities.Produit;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProduitServlet", urlPatterns = "/produits")
public class ProduitServlet extends HttpServlet {

    @Inject
    private ProduitDao produitDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Produit> produits = produitDao.findAll();
        req.setAttribute("produits", produits);
        req.getRequestDispatcher("/produits/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("nom");
        String prixStr = req.getParameter("prix");
        String idStr = req.getParameter("id");
        double prix = prixStr == null || prixStr.isEmpty() ? 0.0 : Double.parseDouble(prixStr);
        if (idStr == null || idStr.isEmpty()) {
            Produit p = new Produit(null, nom, prix);
            produitDao.save(p);
        } else {
            Produit p = new Produit(Long.valueOf(idStr), nom, prix);
            produitDao.update(p);
        }
        resp.sendRedirect(req.getContextPath() + "/produits");
    }
}
