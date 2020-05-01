package app.servlet.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.EmployeeDAO;
import app.dao.EmployeeTokenDAO;
import app.model.Employee;
import app.model.EmployeeToken;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String employeeId  = request.getParameter("employeeId");
		String password    = request.getParameter("password");
		HttpSession session = request.getSession(true);

		boolean result;

		if (session.getAttribute("token") != null) {
			result = new EmployeeTokenDAO().existsToken((String) session.getAttribute("token"));
		} else if (employeeId != null && employeeId.length() != 0 && password != null && password.length() != 0) {
			HashMap<String, String> searchCondition = new HashMap<String, String>(){
				{
					put("employees.id = ?", employeeId);
					put("password = ?", DigestUtils.sha256Hex(password));
				}
			};
			Employee employee = new EmployeeDAO().get(searchCondition);

			if (employee.getId() != null) {
				EmployeeToken employeeToken = new EmployeeToken();
				employeeToken.setEmployeeId(employee.getId());
				employeeToken.setToken(getToken());

				if (new EmployeeTokenDAO().create(employeeToken)) {
					session.setAttribute("token", employee.getEmployeeDetail().getPassword());
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		} else {
			result = false;
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}

	protected String getToken() {
	    byte token[] = new byte[32];
	    StringBuffer buf = new StringBuffer();
	    SecureRandom random = null;

	    try {
	      random = SecureRandom.getInstance("SHA1PRNG");
	      random.nextBytes(token);

	      for (int i = 0; i < token.length; i++) {
	        buf.append(String.format("%02x", token[i]));
	      }

	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }

	    return buf.toString();
	  }
}

