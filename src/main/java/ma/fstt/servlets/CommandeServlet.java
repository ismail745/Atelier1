package ma.fstt.servlets;

import ma.fstt.dao.CommandeDao;
import ma.fstt.dao.ClientDao;
import ma.fstt.dao.LigneDeCommandeDao;
import ma.fstt.dao.ProduitDao;
import ma.fstt.entities.Commande;
import ma.fstt.entities.Client;
import ma.fstt.entities.LigneDeCommande;
import ma.fstt.entities.Produit;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "CommandeServlet", urlPatterns = "/commandes")
public class CommandeServlet extends HttpServlet {

    @Inject
    private CommandeDao commandeDao;

    @Inject
    private LigneDeCommandeDao ligneDao;

    @Inject
    private ProduitDao produitDao;

    @Inject
    private ClientDao clientDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String viewId = req.getParameter("view");
        String showForm = req.getParameter("form");
        if (viewId != null && !viewId.isEmpty()) {
            Long id = Long.valueOf(viewId);
            Commande com = commandeDao.findById(id);
            if (com == null) {
                resp.sendRedirect(req.getContextPath() + "/commandes");
                return;
            }
            req.setAttribute("commande", com);
            req.setAttribute("lignes", ligneDao.findByCommandeId(id));
            req.setAttribute("produits", produitDao.findAll());
            req.getRequestDispatcher("/commandes/view.jsp").forward(req, resp);
        } else if (showForm != null) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                Commande com = commandeDao.findById(Long.valueOf(idStr));
                req.setAttribute("commande", com);
            }
            req.setAttribute("clients", clientDao.findAll());
            req.getRequestDispatcher("/commandes/form.jsp").forward(req, resp);
        } else {
            List<Commande> list = commandeDao.findAll();
            req.setAttribute("commandes", list);
            req.getRequestDispatcher("/commandes/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("addLine".equals(action)) {
            Long commandeId = Long.valueOf(req.getParameter("commandeId"));
            Long produitId = Long.valueOf(req.getParameter("produitId"));
            int quantite = Integer.parseInt(req.getParameter("quantite"));
            // prevent duplicate product line in the same order
            List<LigneDeCommande> existing = ligneDao.findByCommandeId(commandeId);
            boolean alreadyExists = existing.stream().anyMatch(l -> l.getProduit() != null && produitId.equals(l.getProduit().getId()));
            if (alreadyExists) {
                resp.sendRedirect(req.getContextPath() + "/commandes?view=" + commandeId + "&err=duplicate");
                return;
            }
            Produit p = produitDao.findById(produitId);
            LigneDeCommande l = new LigneDeCommande();
            l.setCommandeId(commandeId);
            l.setProduit(p);
            l.setQuantite(quantite);
            l.setPrixUnitaire(p.getPrix());
            ligneDao.save(l);
            resp.sendRedirect(req.getContextPath() + "/commandes?view=" + commandeId);
            return;
        } else if ("deleteLine".equals(action)) {
            Long commandeId = Long.valueOf(req.getParameter("commandeId"));
            Long ligneId = Long.valueOf(req.getParameter("ligneId"));
            ligneDao.delete(ligneId);
            resp.sendRedirect(req.getContextPath() + "/commandes?view=" + commandeId);
            return;
        }

        String clientIdStr = req.getParameter("clientId");
        String dateStr = req.getParameter("dateCommande");
        String idStr = req.getParameter("id");
        Long clientId = clientIdStr == null || clientIdStr.isEmpty() ? null : Long.valueOf(clientIdStr);
        LocalDate date = dateStr == null || dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
        if (idStr == null || idStr.isEmpty()) {
            Commande c = new Commande();
            c.setClient(new Client(clientId, null, null, null));
            c.setDateCommande(date);
            commandeDao.save(c);
        } else {
            Commande c = new Commande(Long.valueOf(idStr), new Client(clientId, null, null, null), date);
            commandeDao.update(c);
        }
        resp.sendRedirect(req.getContextPath() + "/commandes");
    }
}
