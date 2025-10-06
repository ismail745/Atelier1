<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/nav.jspf" %>
<div class="container mt-4">
    <h1>Détails commande #${commande.id}</h1>
    <div class="mb-3">
        <div><strong>Client ID:</strong> ${commande.client.id}</div>
        <div><strong>Date:</strong> ${commande.dateCommande}</div>
    </div>

    <h3>Lignes</h3>
    <table class="table table-bordered">
        <thead>
        <tr><th>ID</th><th>Produit</th><th>Quantité</th><th>Prix unitaire</th><th>Total</th><th></th></tr>
        </thead>
        <tbody>
        <c:forEach var="l" items="${lignes}">
            <tr>
                <td>${l.id}</td>
                <td>${l.produit.id}</td>
                <td>${l.quantite}</td>
                <td>${l.prixUnitaire}</td>
                <td>${l.quantite * l.prixUnitaire}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/commandes" method="post" style="display:inline-block">
                        <input type="hidden" name="action" value="deleteLine" />
                        <input type="hidden" name="commandeId" value="${commande.id}" />
                        <input type="hidden" name="ligneId" value="${l.id}" />
                        <button type="submit" class="btn btn-sm btn-danger">Supprimer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h4>Ajouter une ligne</h4>
    <c:if test="${param.err == 'duplicate'}">
        <div class="alert alert-warning">Ce produit est déjà présent dans cette commande.</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/commandes" method="post" class="row g-3">
        <input type="hidden" name="action" value="addLine" />
        <input type="hidden" name="commandeId" value="${commande.id}" />
        <div class="col-md-6">
            <label class="form-label">Produit</label>
            <select class="form-select" name="produitId">
                <c:forEach var="p" items="${produits}">
                    <option value="${p.id}">${p.nom} - ${p.prix}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-3">
            <label class="form-label">Quantité</label>
            <input class="form-control" type="number" name="quantite" value="1" min="1" />
        </div>
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Ajouter</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/commandes">Retour</a>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>


