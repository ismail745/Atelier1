<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Gestion Commandes - Accueil</title>
<%--  <style>--%>
<%--    body { font-family: Arial, sans-serif; margin: 2rem; }--%>
<%--    .card { border: 1px solid #ddd; padding: 1rem; margin: .5rem 0; border-radius: 6px; }--%>
<%--    .actions a { margin-right: 1rem; }--%>
<%--  </style>--%>
</head>
<body>
  <div class="card">
    <html>
    <head>
      <%@ include file="/WEB-INF/jspf/head.jspf" %>
      <title>Gestion Commandes - Accueil</title>
    </head>
    <body>
      <%@ include file="/WEB-INF/jspf/nav.jspf" %>
      <div class="container mt-4">
        <h1 class="mb-4">Application de gestion des commandes</h1>

        <div class="row g-3">
          <div class="col-md-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Clients</h5>
                <p class="card-text">Gérer les clients (ajout / modification / suppression / consultation)</p>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/clients">Liste des clients</a>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Produits</h5>
                <p class="card-text">Gérer le catalogue de produits</p>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/produits">Liste des produits</a>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Commandes</h5>
                <p class="card-text">Gérer les commandes et les lignes de commande</p>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/commandes">Liste des commandes</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
    </html>