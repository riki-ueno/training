package app.servlet.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.EmployeeDAO;
import app.model.Employee;

/**
 * Servlet implementation class EmployeeSearch
 */
@WebServlet("/api/employee/search")
public class EmployeeSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeSearch() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getRequestURI());
		String departmentId = request.getParameter("departmentId");
		String employeeId = request.getParameter("employeeId");
		String nameInclude = request.getParameter("nameInclude");

		HashMap<String, String> searchCondition = new HashMap<String, String>();
		if (!(departmentId == null) && !(departmentId.length() == 0)) searchCondition.put("employee_details.department_id = ?", departmentId);
		if (!(employeeId == null) && !(employeeId.length() == 0)) searchCondition.put("employee_details.employee_id = ?", employeeId);
		if (!(nameInclude == null) && !(nameInclude.length() == 0)) searchCondition.put("employee_details.name LIKE ?", "%" + nameInclude + "%");

		System.out.println(searchCondition);

		List<Employee> employeeList = new EmployeeDAO().get_by(searchCondition);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(employeeList));
	}
}
