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
import app.model.Employee;
import app.model.EmployeeDetail;

/**
 * Servlet implementation class EmployeePassword
 */
@WebServlet("/api/member/employee/password")
public class EmployeePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String employeeId = (String) request.getSession(false).getAttribute("employeeId");
		String password = request.getParameter("password");
		String passwordConfirmation = request.getParameter("passwordConfirmation");

		boolean result = false;

		if (password != null && passwordConfirmation != null && password.length() > 0 && passwordConfirmation.length() > 0 && password.equals(passwordConfirmation)) {
			EmployeeDetail employeeDetail = new EmployeeDetail();
			employeeDetail.setPassword(password);
			Employee employee = new Employee();
			employee.setId(employeeId);
			employee.setEmployeeDetail(employeeDetail);
			result = new EmployeeDAO().updatePassword(employee);
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}

}
