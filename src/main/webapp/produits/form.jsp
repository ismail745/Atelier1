<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/nav.jspf" %>
<div class="container mt-4">
    <h1>Formulaire Produit</h1>
    <c:set var="produit" value="${produit}" />
    <form action="${pageContext.request.contextPath}/produits" method="post" class="row g-3">
        <input type="hidden" name="id" value="${produit.id}" />
        <div class="col-md-6">
            <label class="form-label">Nom</label>
            <input class="form-control" type="text" name="nom" value="${produit.nom}" />
        </div>
        <div class="col-md-6">
            <label class="form-label">Prix</label>
            <input class="form-control" type="number" step="0.01" name="prix" value="${produit.prix}" />
        </div>
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Enregistrer</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/produits">Annuler</a>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>