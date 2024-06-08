package org.example.lab07.servlets;

import org.example.lab07.beans.Employee;
import org.example.lab07.beans.Job;
import org.example.lab07.daos.DaoEmployee;
import org.example.lab07.daos.DaoJobs;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        DaoEmployee daoEmployee = new DaoEmployee();
        DaoJobs daoJobs = new DaoJobs();  // Agregamos DaoJobs para manejar los trabajos

        switch (action) {
            case "lista":
                ArrayList<Employee> list = daoEmployee.listar();
                request.setAttribute("lista", list);
                RequestDispatcher rd = request.getRequestDispatcher("employee/lista.jsp");
                rd.forward(request, response);
                break;
            case "new":
                // Obtener la lista de trabajos y añadirla a la solicitud
                ArrayList<Job> jobList = daoJobs.listar();
                request.setAttribute("jobList", jobList);
                request.getRequestDispatcher("employee/form_new.jsp").forward(request, response);
                break;
            case "edit":
                String id = request.getParameter("id");
                Employee employee = daoEmployee.buscarPorId(Integer.parseInt(id));

                if (employee != null) {
                    ArrayList<Job> jobListEdit = daoJobs.listar();  // Obtener lista de trabajos para la edición
                    request.setAttribute("jobList", jobListEdit);
                    request.setAttribute("employee", employee);
                    request.getRequestDispatcher("employee/form_edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
                }
                break;
            case "del":
                String idd = request.getParameter("id");
                boolean isDeleted = daoEmployee.borrar(Integer.parseInt(idd));
                if (!isDeleted) {
                    request.setAttribute("errorMessage", "No se puede eliminar el empleado porque es un manager de otros empleados.");
                }
                ArrayList<Employee> listWithError = daoEmployee.listar();
                request.setAttribute("lista", listWithError);
                RequestDispatcher rdWithError = request.getRequestDispatcher("employee/lista.jsp");
                rdWithError.forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        DaoEmployee daoEmployee = new DaoEmployee();

        String action = request.getParameter("action") == null ? "crear" : request.getParameter("action");

        switch (action) {
            case "crear":
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String hireDate = request.getParameter("hireDate");
                String jobId = request.getParameter("jobId");

                Employee employee = new Employee();
                employee.setFullNameEmployee(fullName);
                employee.setEmail(email);
                employee.setHireDate(Date.valueOf(hireDate));
                employee.setJobId(jobId);

                daoEmployee.crear(employee);
                response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
                break;
            case "e":
                int id = Integer.parseInt(request.getParameter("id"));
                String fullNameEdit = request.getParameter("fullName");
                String emailEdit = request.getParameter("email");
                String hireDateEdit = request.getParameter("hireDate");
                String jobIdEdit = request.getParameter("jobId");

                Employee employeeEdit = new Employee();
                employeeEdit.setEmployeeId(id);  // Esto se mantiene para la referencia, pero no se edita en el formulario
                employeeEdit.setFullNameEmployee(fullNameEdit);
                employeeEdit.setEmail(emailEdit);
                employeeEdit.setHireDate(Date.valueOf(hireDateEdit));
                employeeEdit.setJobId(jobIdEdit);

                daoEmployee.actualizar(employeeEdit);
                response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
                break;
        }
    }
}
