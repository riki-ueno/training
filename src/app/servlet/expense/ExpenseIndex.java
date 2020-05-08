package app.servlet.expense;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.ExpenseDAO;
import app.model.Expense;

/**
 * Servlet implementation class ExpenseIndex
 */
@WebServlet("/api/expense/index")
public class ExpenseIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenseIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> searchCondition = new HashMap<String, String>() {
			{
				put("status = ?", "0");
			}
		};
		List<Expense> expenseList = new ExpenseDAO().all(searchCondition);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(expenseList));
	}
}
