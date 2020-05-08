package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.model.Expense;

public class ExpenseDAO extends DAOBase {
	public ExpenseDAO() {
		super();
	}

	public List<Expense> all(HashMap<String, String> searchCondition) {
		String sql = "SELECT id, claimed_at, claimer_id, title, payment_destination, amount, updated_at, updater_id, status, reason FROM expenses ";
		String searchConditionQuery = String.join(" AND ", searchCondition.keySet());
		if (searchConditionQuery.length() != 0) sql += (" WHERE " + searchConditionQuery);
		sql += " ORDER BY claimed_at, id";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			int indexCount = 1;
			for (String key : searchCondition.keySet()) {
				pstmt.setString(indexCount, searchCondition.get(key));
				indexCount++;
			}

			ResultSet rs = pstmt.executeQuery();
			List<Expense> expenseList = new ArrayList<>();

			while (rs.next()) {
				Expense expense = new Expense();
				expense.setId(rs.getInt("id"));
				expense.setClaimerId(rs.getString("claimer_id"));
				expense.setClaimedAt(rs.getDate("claimed_at"));
				expense.setTitle(rs.getString("title"));
				expense.setPaymentDestination(rs.getString("payment_destination"));
				expense.setAmount(rs.getInt("amount"));
				expense.setStatus(rs.getInt("status"));
				expense.setReason(rs.getString("reason"));
				expense.setUpdaterId(rs.getString("updater_id"));
				expense.setUpdatedAt(rs.getDate("updated_at"));
				expenseList.add(expense);
			}

			return expenseList;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public Expense get(String expenseId) {
		String sql = "SELECT * FROM expenses WHERE id = ?";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, expenseId);

			ResultSet rs = pstmt.executeQuery();

			Expense expense = new Expense();

			if (rs.next()) {
				expense.setId(rs.getInt("id"));
				expense.setClaimerId(rs.getString("claimer_id"));
				expense.setClaimedAt(rs.getDate("claimed_at"));
				expense.setTitle(rs.getString("title"));
				expense.setPaymentDestination(rs.getString("payment_destination"));
				expense.setAmount(rs.getInt("amount"));
				expense.setStatus(rs.getInt("status"));
				expense.setReason(rs.getString("reason"));
				expense.setUpdaterId(rs.getString("updater_id"));
				expense.setUpdatedAt(rs.getDate("updated_at"));
			}

			return expense;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean create(Expense expense) {
		String sql = "INSERT INTO expenses(claimer_id, title, payment_destination, amount) VALUES(?, ?, ?, ?)";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, expense.getClaimerId());
			pstmt.setString(2, expense.getTitle());
			pstmt.setString(3, expense.getPaymentDestination());
			pstmt.setInt(4, expense.getAmount());

			int rsCount = pstmt.executeUpdate();

			if (rsCount != 1) {
				return false;
			}

			return true;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean update(Expense expense) {
		String sql = "UPDATE expenses SET title = ?, payment_destination = ?, amount = ?, updater_id = ?, updated_at = CURRENT_DATE WHERE id = ? AND status = 0";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setString(1, expense.getTitle());
			pstmt.setString(2, expense.getPaymentDestination());
			pstmt.setInt(3, expense.getAmount());
			pstmt.setString(4, expense.getUpdaterId());
			pstmt.setInt(5, expense.getId());

			int rsCount = pstmt.executeUpdate();

			if (rsCount != 1) {
				return false;
			}

			return true;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}

	public boolean updateStatus(Expense expense) {
		String sql = "UPDATE expenses SET status = ?, updater_id = ?, reason = ?, updated_at = CURRENT_DATE WHERE id = ? AND status = 0";

		try (
				PreparedStatement pstmt = createPreparedStatement(sql);
		) {
			pstmt.setInt(1, expense.getStatus());
			pstmt.setString(2, expense.getUpdaterId());
			pstmt.setString(3, expense.getReason());
			pstmt.setInt(4, expense.getId());

			int rsCount = pstmt.executeUpdate();

			if (rsCount != 1) {
				return false;
			}

			return true;
		} catch (SQLException e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()));
		}
	}
}
