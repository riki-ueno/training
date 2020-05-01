package app.model;

public class EmployeeToken {
	private int id;
	private String employeeId;
	private Employee employee;
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
