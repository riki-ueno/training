package app.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.EmployeeDAO;
import app.model.Employee;

@WebServlet("/api/auth/self")
public class CurrentEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CurrentEmployee() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		String employeeId = (String) session.getAttribute("employeeId");

		Employee employee = new EmployeeDAO().get(employeeId);

		HashMap<String, String> selfData = new HashMap<String, String>() {
			{
				put("employeeId", employee.getId());
				put("roleName", employee.getEmployeeDetail().getRole().getName());
			}
		};

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(selfData));
	}

}
