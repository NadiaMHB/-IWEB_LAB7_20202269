package org.example.lab07.daos;

import org.example.lab07.beans.Employee;

import java.sql.*;
import java.util.ArrayList;

public class DaoEmployee {

    private final String url = "jdbc:mysql://localhost:3306/hr";
    private final String username = "root";
    private final String password = "1234";

    public ArrayList<Employee> listar() {
        ArrayList<Employee> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "SELECT employee_id, CONCAT(first_name, ' ', last_name) AS full_name, email, hire_date, job_id FROM employees";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setFullNameEmployee(rs.getString("full_name"));
                employee.setEmail(rs.getString("email"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setJobId(rs.getString("job_id"));

                lista.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public Employee buscarPorId(int id) {
        Employee employee = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "SELECT employee_id, CONCAT(first_name, ' ', last_name) AS full_name, email, hire_date, job_id FROM employees WHERE employee_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee();
                    employee.setEmployeeId(rs.getInt("employee_id"));
                    employee.setFullNameEmployee(rs.getString("full_name"));
                    employee.setEmail(rs.getString("email"));
                    employee.setHireDate(rs.getDate("hire_date"));
                    employee.setJobId(rs.getString("job_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

    public void crear(Employee employee) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "INSERT INTO employees (first_name, last_name, email, hire_date, job_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String[] nameParts = employee.getFullNameEmployee().split(" ", 2);
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, employee.getEmail());
            pstmt.setDate(4, new java.sql.Date(employee.getHireDate().getTime()));
            pstmt.setString(5, employee.getJobId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void actualizar(Employee employee) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, hire_date = ?, job_id = ? WHERE employee_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String[] nameParts = employee.getFullNameEmployee().split(" ", 2);
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, employee.getEmail());
            pstmt.setDate(4, new java.sql.Date(employee.getHireDate().getTime()));
            pstmt.setString(5, employee.getJobId());
            pstmt.setInt(6, employee.getEmployeeId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para borrar un empleado
    public boolean borrar(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            // Verificar si el empleado es un manager
            String checkSql = "SELECT COUNT(*) AS count FROM employees WHERE manager_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt("count") > 0) {
                        return false; // No se puede eliminar porque es un manager
                    }
                }
            }

            // Eliminar el empleado
            String deleteSql = "DELETE FROM employees WHERE employee_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
