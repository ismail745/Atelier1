<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*" %>
<%@ page import="ma.fstt.dao.ClientDaoImpl" %>
<%
    String id = request.getParameter("id");
    if (id != null) {
        new ClientDaoImpl().delete(Long.valueOf(id));
    }
    response.sendRedirect(request.getContextPath() + "/clients");
%>