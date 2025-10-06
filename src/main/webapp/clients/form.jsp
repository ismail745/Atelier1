<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/nav.jspf" %>
<div class="container mt-4">
    <h1>Formulaire Client</h1>
    <c:set var="client" value="${client}" />
    <form action="${pageContext.request.contextPath}/clients" method="post" class="row g-3">
        <input type="hidden" name="id" value="${client.id}" />
        <div class="col-md-6">
            <label class="form-label">Nom</label>
            <input class="form-control" type="text" name="nom" value="${client.nom}" />
        </div>
        <div class="col-md-6">
            <label class="form-label">Email</label>
            <input class="form-control" type="email" name="email" value="${client.email}" />
        </div>
        <div class="col-md-6">
            <label class="form-label">Telephone</label>
            <input class="form-control" type="text" name="telephone" value="${client.telephone}" />
        </div>
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Enregistrer</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/clients">Annuler</a>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>