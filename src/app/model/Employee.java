package app.model;

import javax.servlet.http.HttpServletRequest;

public class Employee {
	private String id;
	private EmployeeDetail employeeDetail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmployeeDetail getEmployeeDetail() {
		return employeeDetail;
	}

	public void setEmployeeDetail(EmployeeDetail employeeDetail) {
		this.employeeDetail = employeeDetail;
	}

	static public Employee build(HttpServletRequest request) {
		Employee employee = new Employee();

		String id = request.getParameter("id");

		employee.setId(id);

		return employee;
	}
}
