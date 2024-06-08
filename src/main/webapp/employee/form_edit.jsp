<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.lab07.beans.Employee" %>
<%@ page import="org.example.lab07.beans.Job" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="employee" scope="request" type="org.example.lab07.beans.Employee" />
<jsp:useBean id="jobList" scope="request" type="java.util.ArrayList<org.example.lab07.beans.Job>" />
<html>
<head>
    <title>Editar Empleado</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1 class="mt-3">Editar Empleado</h1>
    <form action="EmployeeServlet?action=e" method="post">
        <input type="hidden" name="id" value="${employee.getEmployeeId()}">
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Nombre Completo" value="${employee.getFullNameEmployee()}">
            <label for="fullName">Nombre Completo</label>
        </div>
        <div class="form-floating mb-3">
            <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${employee.getEmail()}">
            <label for="email">Email</label>
        </div>
        <div class="form-floating mb-3">
            <input type="date" class="form-control" id="hireDate" name="hireDate" placeholder="Fecha de Contratación" value="${employee.getHireDate()}">
            <label for="hireDate">Fecha de Contratación</label>
        </div>
        <div class="form-floating mb-3">
            <select class="form-select" id="jobId" name="jobId">
                <% for (Job job : jobList) { %>
                <option value="<%= job.getJobId() %>" <%= job.getJobId().equals(employee.getJobId()) ? "selected" : "" %>><%= job.getJobTitle() %></option>
                <% } %>
            </select>
            <label for="jobId">ID del Trabajo</label>
        </div>
        <button type="submit" class="btn btn-primary">Actualizar Empleado</button>
    </form>
</div>
</body>
</html>
