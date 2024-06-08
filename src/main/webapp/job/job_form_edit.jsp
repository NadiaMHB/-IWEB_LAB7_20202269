<%@ page import="org.example.lab07.beans.Job" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="job" scope="request" type="org.example.lab07.beans.Job" />
<html>
<head>
    <title>Editar Trabajo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1 class="mt-3">Editar Trabajo</h1>
    <form action="JobServlet?action=e" method="post">
        <input type="hidden" name="id" value="${job.getJobId()}">
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="jobTitle" name="jobTitle" placeholder="Título del Trabajo" value="${job.getJobTitle()}">
            <label for="jobTitle">Título del Trabajo</label>
        </div>
        <div class="form-floating mb-3">
            <input type="number" class="form-control" id="minSalary" name="minSalary" placeholder="Salario Mínimo" value="${job.getMinSalary()}">
            <label for="minSalary">Salario Mínimo</label>
        </div>
        <div class="form-floating mb-3">
            <input type="number" class="form-control" id="maxSalary" name="maxSalary" placeholder="Salario Máximo" value="${job.getMaxSalary()}">
            <label for="maxSalary">Salario Máximo</label>
        </div>
        <button type="submit" class="btn btn-primary">Actualizar Trabajo</button>
    </form>
</div>
</body>
</html>
