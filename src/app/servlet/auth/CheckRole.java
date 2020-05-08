package app.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.EmployeeDAO;
import app.model.Employee;

/**
 * Servlet implementation class CheckRole
 */
@WebServlet("/api/authentication/role")
public class CheckRole extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckRole() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String employeeId = (String) request.getSession(false).getAttribute("employeeId");

		Employee employee = new EmployeeDAO().get(employeeId);

		HashMap<String, String> res = new HashMap<String, String>() {
			{
				put("employeeId", employee.getId());
				put("roleName", employee.getEmployeeDetail().getRole().getName());
			}
		};

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(res));
	}
}
