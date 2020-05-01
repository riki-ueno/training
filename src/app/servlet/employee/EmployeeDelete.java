package app.servlet.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.EmployeeDAO;

/**
 * Servlet implementation class EmployeeDelete
 */
@WebServlet("/api/employee/delete")
public class EmployeeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		boolean result = new EmployeeDAO().delete(id);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}
}
