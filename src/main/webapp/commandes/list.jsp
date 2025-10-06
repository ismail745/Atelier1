<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/nav.jspf" %>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1>Liste des commandes</h1>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/commandes?form">Nouvelle commande</a>
    </div>
    <table class="table table-striped">
        <thead>
            <tr><th>ID</th><th>Client ID</th><th>Date</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${commandes}">
            <tr>
                <td>${c.id}</td>
                <td>${c.client.id}</td>
                <td>${c.dateCommande}</td>
                <td class="table-actions">
                    <a class="btn btn-sm btn-info" href="${pageContext.request.contextPath}/commandes?view=${c.id}">Voir</a>
                    <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/commandes/form.jsp?id=${c.id}">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/commandes/delete?id=${c.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>