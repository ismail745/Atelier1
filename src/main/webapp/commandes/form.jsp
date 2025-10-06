<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/nav.jspf" %>
<div class="container mt-4">
    <h1>Formulaire Commande</h1>
    <c:set var="commande" value="${commande}" />
    <form action="${pageContext.request.contextPath}/commandes" method="post" class="row g-3">
        <input type="hidden" name="id" value="${commande.id}" />
        <div class="col-md-6">
            <label class="form-label">Client</label>
            <select class="form-select" name="clientId">
                <c:forEach var="cl" items="${clients}">
                    <option value="${cl.id}" ${commande.client.id == cl.id ? 'selected' : ''}>${cl.nom} (${cl.email})</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-6">
            <label class="form-label">Date</label>
            <input class="form-control" type="date" name="dateCommande" value="${commande.dateCommande}" />
        </div>
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Enregistrer</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/commandes">Annuler</a>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>