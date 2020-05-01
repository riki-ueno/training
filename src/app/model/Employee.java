package app.model;

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
}
