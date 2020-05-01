package app.servlet.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

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
 * Servlet implementation class EmployeeEdit
 */
@WebServlet("/api/employee/edit")
public class EmployeeEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEdit() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Employee employee;

		if (id == null || id.length() == 0) {
			employee = new Employee();
		} else {
			employee = new EmployeeDAO().get(id);
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(employee));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		EmployeeDAO employeeDAO = new EmployeeDAO();
		Employee employee = buildEmployee(request);

		System.out.println(new ObjectMapper().writeValueAsString(employee));

		boolean result;

		if ("create".equals(method)) {
			result = employeeDAO.create(employee);
		} else if ("update".equals(method)) {
			result = employeeDAO.update(employee);
		} else {
			result = false;
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(result));
	}

	private Employee buildEmployee(HttpServletRequest request) {
		Employee employee = new Employee();
		EmployeeDetail employeeDetail = new EmployeeDetail();
		employee.setId(request.getParameter("id"));
		employeeDetail.setEmployeeId(request.getParameter("employeeId"));
		employeeDetail.setName(request.getParameter("name"));
		employeeDetail.setAge(Integer.parseInt(request.getParameter("age")));
		employeeDetail.setSex(request.getParameter("sex"));
		employeeDetail.setPostalCode(request.getParameter("postalCode"));
		employeeDetail.setPrefName(request.getParameter("prefName"));
		employeeDetail.setAddress(request.getParameter("address"));
		employeeDetail.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
		if (request.getParameter("joinedAt") != null && request.getParameter("joinedAt").length() != 0)
			employeeDetail.setJoinedAt(Date.valueOf(request.getParameter("joinedAt")));
		if (request.getParameter("retiredAt") != null && request.getParameter("retiredAt").length() != 0)
			employeeDetail.setRetiredAt(Date.valueOf(request.getParameter("retiredAt")));
		employee.setEmployeeDetail(employeeDetail);
		return employee;
	}
}
