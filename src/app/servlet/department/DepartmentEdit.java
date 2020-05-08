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
		Department department = new DepartmentDAO().get(Integer.parseInt(id));

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(department));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Department department = Department.build(request);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(new DepartmentDAO().update(department)));
	}

}
