<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.lab07.beans.Job" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="lista" scope="request" type="java.util.ArrayList<org.example.lab07.beans.Job>" />
<html>
<head>
    <title>Trabajos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="clearfix mt-3 mt-2">
        <a href="<%=request.getContextPath()%>/JobServlet">
            <h1 class="float-start link-dark">Lista de Trabajos</h1>
        </a>
        <a class="btn btn-primary float-end mt-1" href="<%=request.getContextPath() %>/JobServlet?action=new">Crear trabajo</a>
        <a class="btn btn-secondary float-end mt-1 me-2" href="<%=request.getContextPath() %>/EmployeeServlet">Lista de empleados</a>
    </div>
    <hr/>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="alert alert-danger" role="alert">
        <%= errorMessage %>
    </div>
    <%
        }
    %>
    <table class="table table-striped mt-3">
        <tr class="table-primary">
            <th>ID</th>
            <th>Título del Trabajo</th>
            <th>Salario Mínimo</th>
            <th>Salario Máximo</th>
            <th></th>
            <th></th>
        </tr>
        <% for (Job job : lista) { %>
        <tr>
            <td><%=job.getJobId() %></td>
            <td><%=job.getJobTitle() %></td>
            <td><%=job.getMinSalary() %></td>
            <td><%=job.getMaxSalary() %></td>
            <td><a class="btn btn-success" href="<%=request.getContextPath()%>/JobServlet?action=edit&id=<%= job.getJobId() %>">Editar</a></td>
            <td><a onclick="return confirm('¿Está seguro de borrar?')" class="btn btn-danger" href="<%=request.getContextPath()%>/JobServlet?action=del&id=<%= job.getJobId() %>">Borrar</a></td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
