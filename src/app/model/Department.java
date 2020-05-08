package app.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

public class Department {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	static public Department build(HttpServletRequest request) {
		Department department = new Department();

		String id = request.getParameter("id");
		String name = request.getParameter("name");

		if (NumberUtils.isDigits(id)) 	department.setId(Integer.parseInt(id));
		department.setName(name);

		return department;
	}
}
