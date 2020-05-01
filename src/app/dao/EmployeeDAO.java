package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.model.Department;
import app.model.Employee;
import app.model.EmployeeDetail;
import app.model.Role;

public class EmployeeDAO {
	private final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER_NAME = "webapp2";
	private final String PASSWORD  = "webapp2";

	public EmployeeDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバーのロードに失敗しました。詳細:[%s]",e.getMessage()));
		}
	}

	public List<Employee> all() {
		String sql = "SELECT id, name FROM employees RIGHT JOIN employee_details ON employees.id = employee_details.employee_id ORDER BY id";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			ResultSet rs = pstmt.executeQuery();
			List<Employee> employeeList = new ArrayList<>();

			while (rs.next()) {
				Employee employee = new Employee();
				EmployeeDetail employeeDetail = new EmployeeDetail();
				employee.setId(rs.getString("id"));
				employeeDetail.setName(rs.getString("name"));
				employee.setEmployeeDetail(employeeDetail);
				employeeList.add(employee);
			}

			return employeeList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public Employee get(String id) {
		String sql =
				"SELECT "
					+ " employees.id employees_id, employee_details.name employee_details_name, sex, age, password, postal_code, pref_name, address, department_id, role_id, roles.name roles_name, joined_at, retired_at "
				+ " FROM "
					+ " employees "
				+ " RIGHT JOIN "
					+ " employee_details ON employees.id = employee_details.employee_id "
				+ " LEFT JOIN "
					+ " roles ON employee_details.role_id = roles.id "
				+ " WHERE employee_id = ? ";
		System.out.println(sql);
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			Employee employee = new Employee();
			EmployeeDetail employeeDetail = new EmployeeDetail();
			Role role = new Role();

			while (rs.next()) {
				role.setId(rs.getInt("role_id"));
				role.setName(rs.getString("roles_name"));
				employeeDetail.setEmployeeId(rs.getString("employees_id"));
				employeeDetail.setName(rs.getString("employee_details_name"));
				employeeDetail.setSex(rs.getString("sex"));
				employeeDetail.setAge(rs.getInt("age"));
				employeeDetail.setPassword(rs.getString("password"));
				employeeDetail.setPostalCode(rs.getString("postal_code"));
				employeeDetail.setPrefName(rs.getString("pref_name"));
				employeeDetail.setAddress(rs.getString("address"));
				employeeDetail.setDepartmentId(rs.getInt("department_id"));
				employeeDetail.setRoleId(rs.getInt("role_id"));
				employeeDetail.setRole(role);
				employeeDetail.setJoinedAt(rs.getDate("joined_at"));
				employeeDetail.setRetiredAt(rs.getDate("retired_at"));
				employee.setId(rs.getString("employees_id"));
				employee.setEmployeeDetail(employeeDetail);
			}

			return employee;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public Employee get(HashMap<String, String> searchCondition) {
		String sql =
				"SELECT "
					+ " employees.id employees_id, employee_details.name employee_details_name, sex, age, password, postal_code, pref_name, address, department_id, role_id, roles.name roles_name, joined_at, retired_at "
				+ " FROM "
					+ " employees "
				+ " RIGHT JOIN "
					+ " employee_details ON employees.id = employee_details.employee_id "
				+ " LEFT JOIN "
					+ " roles ON employee_details.role_id = roles.id ";
		String searchConditionQuery = String.join(" AND ", searchCondition.keySet());
		if (searchConditionQuery.length() != 0) sql += (" WHERE " + searchConditionQuery);

		System.out.println(sql);

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			int indexCount = 1;
			for (String key : searchCondition.keySet()) {
				pstmt.setString(indexCount, searchCondition.get(key));
				indexCount++;
			}

			ResultSet rs = pstmt.executeQuery();

			Employee employee = new Employee();
			EmployeeDetail employeeDetail = new EmployeeDetail();
			Role role = new Role();

			if (rs.next()) {
				role.setId(rs.getInt("role_id"));
				role.setName(rs.getString("roles_name"));
				employeeDetail.setEmployeeId(rs.getString("employees_id"));
				employeeDetail.setName(rs.getString("employee_details_name"));
				employeeDetail.setSex(rs.getString("sex"));
				employeeDetail.setAge(rs.getInt("age"));
				employeeDetail.setPassword(rs.getString("password"));
				employeeDetail.setPostalCode(rs.getString("postal_code"));
				employeeDetail.setPrefName(rs.getString("pref_name"));
				employeeDetail.setAddress(rs.getString("address"));
				employeeDetail.setDepartmentId(rs.getInt("department_id"));
				employeeDetail.setRoleId(rs.getInt("role_id"));
				employeeDetail.setRole(role);
				employeeDetail.setJoinedAt(rs.getDate("joined_at"));
				employeeDetail.setRetiredAt(rs.getDate("retired_at"));
				employee.setId(rs.getString("employees_id"));
				employee.setEmployeeDetail(employeeDetail);
			}

			return employee;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public List<Employee> get_by(HashMap<String, String> searchCondition) {
		String sql = "SELECT employee_details.employee_id, employee_details.name employee_name, departments.name department_name FROM employees RIGHT JOIN employee_details ON employees.id = employee_details.employee_id LEFT JOIN departments ON employee_details.department_id = departments.id";
		String searchConditionQuery = String.join(" AND ", searchCondition.keySet());
		if (searchConditionQuery.length() != 0) sql += (" WHERE " + searchConditionQuery);

		System.out.println(sql);

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			int indexCount = 1;
			for (String key : searchCondition.keySet()) {
				pstmt.setString(indexCount, searchCondition.get(key));
				indexCount++;
			}

			ResultSet rs = pstmt.executeQuery();
			List<Employee> employeeList = new ArrayList<>();

			while (rs.next()) {
				Employee employee = new Employee();
				EmployeeDetail employeeDetail = new EmployeeDetail();
				Department department = new Department();
				employee.setId(rs.getString("employee_id"));
				employeeDetail.setName(rs.getString("employee_name"));
				department.setName("department_name");
				employeeDetail.setDepartment(department);
				employee.setEmployeeDetail(employeeDetail);
				employeeList.add(employee);
			}

			return employeeList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean create(Employee employee) {
		String insert_employee_sql = "INSERT INTO employees(id) VALUES(?)";
		String insert_employee_detail_sql = "INSERT INTO employee_details(employee_id, name, age, sex, postal_code, pref_name, address, department_id, role_id, joined_at, retired_at) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (
				Connection con = createConnection();
		) {
			con.setAutoCommit(false);
			PreparedStatement pstmt1 = createPreparedStatement(insert_employee_sql);
			PreparedStatement pstmt2 = createPreparedStatement(insert_employee_detail_sql);

			pstmt1.setString(1, employee.getId());
			int resultCount = pstmt1.executeUpdate();

			pstmt2.setString(1, employee.getId());
			pstmt2.setString(2, employee.getEmployeeDetail().getName());
			pstmt2.setInt(3, employee.getEmployeeDetail().getAge());
			pstmt2.setString(4, employee.getEmployeeDetail().getSex());
			pstmt2.setString(5, employee.getEmployeeDetail().getPostalCode());
			pstmt2.setString(6, employee.getEmployeeDetail().getPrefName());
			pstmt2.setString(7, employee.getEmployeeDetail().getAddress());
			pstmt2.setInt(8, employee.getEmployeeDetail().getDepartmentId());
			pstmt2.setInt(9, employee.getEmployeeDetail().getRoleId());
			if (employee.getEmployeeDetail().getJoinedAt() == null) {
				pstmt2.setNull(10, java.sql.Types.NULL);
			} else {
				pstmt2.setDate(10, employee.getEmployeeDetail().getJoinedAt());
			}
			if (employee.getEmployeeDetail().getRetiredAt() == null) {
				pstmt2.setNull(11, java.sql.Types.NULL);
			} else {
				pstmt2.setDate(11, employee.getEmployeeDetail().getRetiredAt());
			}

			resultCount += pstmt2.executeUpdate();

			con.commit();

			if (resultCount != 2) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("挿入処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean update(Employee employee) {
		String sql = "UPDATE employee_details SET name = ?, age = ?, sex = ?, postal_code = ?, pref_name = ?, address = ?, department_id = ?, role_id = ?, joined_at = ?, retired_at = ? WHERE employee_id = ?";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, employee.getEmployeeDetail().getName());
			pstmt.setInt(2, employee.getEmployeeDetail().getAge());
			pstmt.setString(3, employee.getEmployeeDetail().getSex());
			pstmt.setString(4, employee.getEmployeeDetail().getPostalCode());
			pstmt.setString(5, employee.getEmployeeDetail().getPrefName());
			pstmt.setString(6, employee.getEmployeeDetail().getAddress());
			pstmt.setInt(7, employee.getEmployeeDetail().getDepartmentId());
			pstmt.setInt(8, employee.getEmployeeDetail().getRoleId());
			if (employee.getEmployeeDetail().getJoinedAt() == null) {
				pstmt.setNull(9, java.sql.Types.NULL);
			} else {
				pstmt.setDate(9, employee.getEmployeeDetail().getJoinedAt());
			}
			if (employee.getEmployeeDetail().getRetiredAt() == null) {
				pstmt.setNull(10, java.sql.Types.NULL);
			} else {
				pstmt.setDate(10, employee.getEmployeeDetail().getRetiredAt());
			}
			pstmt.setString(11, employee.getId());

			int resultCount = pstmt.executeUpdate();

			if (resultCount != 1) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("更新処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean delete(String id) {
		String sql = "DELETE FROM employees WHERE id = ?";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, id);

			int resultCount = pstmt.executeUpdate();

			if (resultCount != 1) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("更新処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	private PreparedStatement createPreparedStatement(String sql) throws SQLException {
		Connection con = createConnection();
		return con.prepareStatement(sql);
	}

	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
	}
}
