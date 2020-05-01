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
 * Servlet implementation class DepartmentEdit
 */
@WebServlet("/api/department/edit")
public class DepartmentEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Department department;

		if (id == null || id.length() == 0) {
			department = new Department();
		} else {
			department = new DepartmentDAO().get(Integer.parseInt(id));
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(department));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String method = request.getParameter("method");

		DepartmentDAO departmentDAO = new DepartmentDAO();
		Department department = new Department();
		department.setName(name);

		boolean result = false;

		if ("create".equals(method)) {
			result = departmentDAO.create(department);
		} else if ("update".equals(method)){
			department.setId(Integer.parseInt(id));
			result = departmentDAO.update(department);
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}

}
