package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Department;

public class DepartmentDAO extends DAOBase {
	public DepartmentDAO() {
		super();
	}

	public List<Department> all() {
		String sql = "SELECT id, name FROM departments order by id";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			ResultSet rs = pstmt.executeQuery();
			List<Department> departmentList = new ArrayList<>();

			while (rs.next()) {
				Department department = new Department();
				department.setId(rs.getInt("id"));
				department.setName(rs.getString("name"));
				departmentList.add(department);
			}

			return departmentList;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public Department get(int id) {
		String sql = "SELECT id, name FROM departments WHERE id = ?";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			Department department = new Department();

			while (rs.next()) {

				department.setId(rs.getInt("id"));
				department.setName(rs.getString("name"));
			}

			return department;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean create(Department department) {
		String sql = "INSERT INTO departments(name) VALUES(?)";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, department.getName());

			int resultCount = pstmt.executeUpdate();

			if (resultCount != 1) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("挿入処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean update(Department department) {
		String sql = "UPDATE departments SET name = ? WHERE id = ?";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, department.getName());
			pstmt.setInt(2, department.getId());

			int resultCount = pstmt.executeUpdate();

			if (resultCount != 1) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("更新処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM departments WHERE id = ?";
		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setInt(1, id);

			int resultCount = pstmt.executeUpdate();

			if (resultCount != 1) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new RuntimeException(String.format("更新処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
