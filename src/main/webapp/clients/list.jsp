<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/nav.jspf" %>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1>Liste des clients</h1>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/clients/form.jsp">Nouveau client</a>
    </div>
    <table class="table table-striped">
        <thead>
            <tr><th>ID</th><th>Nom</th><th>Email</th><th>Telephone</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${clients}">
            <tr>
                <td>${c.id}</td>
                <td>${c.nom}</td>
                <td>${c.email}</td>
                <td>${c.telephone}</td>
                <td class="table-actions">
                    <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/clients/form.jsp?id=${c.id}">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/clients/delete?id=${c.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>