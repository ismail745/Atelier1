<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/nav.jspf" %>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1>Liste des produits</h1>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/produits/form.jsp">Nouveau produit</a>
    </div>
    <table class="table table-striped">
        <thead>
            <tr><th>ID</th><th>Nom</th><th>Prix</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${produits}">
            <tr>
                <td>${p.id}</td>
                <td>${p.nom}</td>
                <td>${p.prix}</td>
                <td class="table-actions">
                    <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/produits/form.jsp?id=${p.id}">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/produits/delete?id=${p.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>