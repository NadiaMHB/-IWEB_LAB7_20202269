package org.example.lab07.servlets;

import org.example.lab07.beans.Job;
import org.example.lab07.daos.DaoJobs;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "JobServlet", value = "/JobServlet")
public class JobServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        DaoJobs daoJobs = new DaoJobs();

        switch (action) {
            case "lista":
                ArrayList<Job> list = daoJobs.listar();
                request.setAttribute("lista", list);
                RequestDispatcher rd = request.getRequestDispatcher("job/job_list.jsp");
                rd.forward(request, response);
                break;
            case "new":
                request.getRequestDispatcher("job/job_form_new.jsp").forward(request, response);
                break;
            case "edit":
                String id = request.getParameter("id");
                Job job = daoJobs.buscarPorId(id);

                if (job != null) {
                    request.setAttribute("job", job);
                    request.getRequestDispatcher("job/job_form_edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/JobServlet");
                }
                break;
            case "del":
                String idd = request.getParameter("id");
                boolean isDeleted = daoJobs.borrar(idd);
                if (!isDeleted) {
                    request.setAttribute("errorMessage", "No se puede eliminar el puesto de trabajo porque hay empleados asignados.");
                }
                ArrayList<Job> listWithError = daoJobs.listar();
                request.setAttribute("lista", listWithError);
                RequestDispatcher rdWithError = request.getRequestDispatcher("job/job_list.jsp");
                rdWithError.forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        DaoJobs daoJobs = new DaoJobs();

        String action = request.getParameter("action") == null ? "crear" : request.getParameter("action");

        switch (action) {
            case "crear":
                String jobId = request.getParameter("jobId");
                String jobTitle = request.getParameter("jobTitle");
                int minSalary = Integer.parseInt(request.getParameter("minSalary"));
                int maxSalary = Integer.parseInt(request.getParameter("maxSalary"));

                Job job = new Job();
                job.setJobId(jobId);
                job.setJobTitle(jobTitle);
                job.setMinSalary(minSalary);
                job.setMaxSalary(maxSalary);

                daoJobs.crear(job);
                response.sendRedirect(request.getContextPath() + "/JobServlet");
                break;
            case "e":
                String id = request.getParameter("id");
                String jobTitleEdit = request.getParameter("jobTitle");
                int minSalaryEdit = Integer.parseInt(request.getParameter("minSalary"));
                int maxSalaryEdit = Integer.parseInt(request.getParameter("maxSalary"));

                Job jobEdit = new Job();
                jobEdit.setJobId(id);
                jobEdit.setJobTitle(jobTitleEdit);
                jobEdit.setMinSalary(minSalaryEdit);
                jobEdit.setMaxSalary(maxSalaryEdit);

                daoJobs.actualizar(jobEdit);
                response.sendRedirect(request.getContextPath() + "/JobServlet");
                break;
        }
    }
}
