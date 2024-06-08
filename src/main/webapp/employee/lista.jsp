<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.lab07.beans.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="lista" scope="request" type="java.util.ArrayList<org.example.lab07.beans.Employee>" />
<html>
<head>
    <title>Empleados</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="clearfix mt-3 mt-2">
        <a href="<%=request.getContextPath()%>/EmployeeServlet">
            <h1 class="float-start link-dark">Lista de Empleados</h1>
        </a>
        <a class="btn btn-primary float-end mt-1" href="<%=request.getContextPath() %>/EmployeeServlet?action=new">Crear empleado</a>
        <a class="btn btn-secondary float-end mt-1 me-2" href="<%=request.getContextPath() %>/JobServlet?action=lista">Lista de trabajos</a>
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
            <th>Nombre Completo</th>
            <th>Email</th>
            <th>Fecha de Contratación</th>
            <th>ID del Trabajo</th>
            <th></th>
            <th></th>
        </tr>
        <% for (Employee employee : lista) { %>
        <tr>
            <td><%=employee.getEmployeeId() %></td>
            <td><%=employee.getFullNameEmployee() %></td>
            <td><%=employee.getEmail() %></td>
            <td><%=employee.getHireDate() %></td>
            <td><%=employee.getJobId() %></td>
            <td><a class="btn btn-success" href="<%=request.getContextPath()%>/EmployeeServlet?action=edit&id=<%= employee.getEmployeeId() %>">Editar</a></td>
            <td><a onclick="return confirm('¿Está seguro de borrar?')" class="btn btn-danger" href="<%=request.getContextPath()%>/EmployeeServlet?action=del&id=<%= employee.getEmployeeId() %>">Borrar</a></td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
