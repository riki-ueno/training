package app.servlet.department;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.DepartmentDAO;
import app.model.Department;

/**
 * Servlet implementation class DepartmentCreate
 */
@WebServlet("/api/department/create")
public class DepartmentCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DepartmentCreate() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Department department = Department.build(request);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(new DepartmentDAO().create(department)));
	}

}
