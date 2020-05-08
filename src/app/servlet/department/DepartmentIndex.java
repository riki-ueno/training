package app.servlet.department;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.DepartmentDAO;
import app.model.Department;

/**
 * Servlet implementation class Index
 */
@WebServlet("/api/department/index")
public class DepartmentIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DepartmentIndex() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Department> departmentList = new DepartmentDAO().all();

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(departmentList));
	}
}
