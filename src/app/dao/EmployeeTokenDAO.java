package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import app.model.Employee;
import app.model.EmployeeDetail;
import app.model.EmployeeToken;

public class EmployeeTokenDAO {
	private final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER_NAME = "webapp2";
	private final String PASSWORD  = "webapp2";

	public EmployeeTokenDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバーのロードに失敗しました。詳細:[%s]",e.getMessage()));
		}
	}

	public boolean create(EmployeeToken employeeToken) {
		String sql = "INSERT INTO employee_tokens(employee_id, token) VALUES(?, ?)";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, employeeToken.getEmployeeId());
			pstmt.setString(2, employeeToken.getToken());

			int resultCount = pstmt.executeUpdate();

			if (resultCount != 1) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("挿入処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean existsToken(String token) {
		String sql = "SELECT * FROM employee_tokens where token = ?";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, token);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(String.format("挿入処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public EmployeeToken get_by(HashMap<String, String> searchCondition) {
		String sql = "SELECT "
					+ " employee_tokens.id employee_tokens_id, employee_tokens.employee_id employee_tokens_employee_id, employee_tokens.token, employee_details.name, employee_details.SEX, employee_details.AGE, employee_details.POSTAL_CODE, employee_details.PREF_NAME, employee_details.ADDRESS, employee_details.DEPARTMENT_ID, employee_details.ROLE_ID, EMPLOYEE_DETAILS.JOINED_AT,EMPLOYEE_DETAILS.RETIRED_AT "
				+ " FROM "
					+ " employee_tokens "
				+ " RIGHT JOIN"
					+ " employees ON employee_tokens.employee_id = employees.id "
				+ "RIGHT JOIN"
					+ " employee_details ON employee_details.employee_id = employees.id";
		String searchConditionQuery = String.join(" AND ", searchCondition.keySet());
		if (searchConditionQuery.length() != 0) sql += (" WHERE " + searchConditionQuery);

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			int indexCount = 1;
			for (String key : searchCondition.keySet()) {
				pstmt.setString(indexCount, searchCondition.get(key));
				indexCount++;
			}

			ResultSet rs = pstmt.executeQuery();

			EmployeeToken employeeToken = new EmployeeToken();
			Employee employee = new Employee();
			EmployeeDetail employeeDetail = new EmployeeDetail();

			if (rs.next()) {
				employeeDetail.setName(rs.getString("name"));
				employeeDetail.setSex(rs.getString("sex"));
				employeeDetail.setAge(rs.getInt("age"));
				employeeDetail.setPostalCode(rs.getString("postal_code"));
				employeeDetail.setPrefName(rs.getString("pref_name"));
				employeeDetail.setAddress(rs.getString("address"));
				employeeDetail.setDepartmentId(rs.getInt("department_id"));
				employeeDetail.setRoleId(rs.getInt("role_id"));
				employeeDetail.setJoinedAt(rs.getDate("joined_at"));
				employeeDetail.setRetiredAt(rs.getDate("retired_at"));
				employee.setEmployeeDetail(employeeDetail);
				employeeToken.setEmployee(employee);
			}

			return employeeToken;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean delete(String token) {
		String sql = "DELETE FROM employee_tokens WHERE token = ?";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(0, token);

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("挿入処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
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
