package app.model;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

public class EmployeeDetail {
	private String employeeId;
	private String name;
	private String sex;
	private int age;
	private String postalCode;
	private String prefName;
	private String address;
	private String password;
	private int departmentId;
	private Department department;
	private int roleId;
	private Role role;
	private Date joinedAt;
	private Date retiredAt;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
	}

	public Date getRetiredAt() {
		return retiredAt;
	}

	public void setRetiredAt(Date retiredAt) {
		this.retiredAt = retiredAt;
	}

	static public EmployeeDetail build(HttpServletRequest request) {
		EmployeeDetail employeeDetail = new EmployeeDetail();

		String employeeId = request.getParameter("employeeId");
		String name        = request.getParameter("name");
		String sex         =request.getParameter("sex");
		String age         = request.getParameter("age");
		String postalCode = request.getParameter("postalCode");
		String prefName   =request.getParameter("prefName");
		String address    = request.getParameter("address");
		String password   =request.getParameter("password");
		String departmentId =request.getParameter("departmentId");
		String roleId     = request.getParameter("roleId");
		String joinedAt   =request.getParameter("joinedAt");
		String retiredAt  = request.getParameter("retiredAt");

		employeeDetail.setEmployeeId(employeeId);
		employeeDetail.setName(name);
		employeeDetail.setSex(sex);
		if (NumberUtils.isDigits(age)) employeeDetail.setAge(Integer.parseInt(age));
		employeeDetail.setPostalCode(postalCode);
		employeeDetail.setPrefName(prefName);
		employeeDetail.setAddress(address);
		employeeDetail.setPassword(password);
		employeeDetail.setSex(sex);
		if (NumberUtils.isDigits(departmentId)) employeeDetail.setDepartmentId(Integer.parseInt(departmentId));
		if (NumberUtils.isDigits(roleId)) employeeDetail.setRoleId(Integer.parseInt(roleId));
		if (joinedAt != null && !joinedAt.isEmpty()) employeeDetail.setJoinedAt(Date.valueOf(joinedAt));
		if (retiredAt != null && !retiredAt.isEmpty()) employeeDetail.setRetiredAt(Date.valueOf(retiredAt));
		return employeeDetail;
	}
}
