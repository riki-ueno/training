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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.EmployeeTokenDAO;
import app.dao.ExpenseDAO;
import app.model.EmployeeToken;
import app.model.Expense;

/**
 * Servlet implementation class ExpenseIndex
 */
@WebServlet("/api/expense/own")
public class OwnExpenseIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnExpenseIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		HashMap<String, String> tokenSearchCondition = new HashMap<String, String>() {
			{
				put("token = ?", (String) session.getAttribute("token"));
			}
		};
		EmployeeToken employeeToken = new EmployeeTokenDAO().get_by(tokenSearchCondition);
		HashMap<String, String> expenseSearchCondition = new HashMap<String, String>() {
			{
				put("claimer_id = ?", employeeToken.getEmployeeId());
			}
		};
		List<Expense> expenseList = new ExpenseDAO().all(expenseSearchCondition);

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(expenseList));
	}

}
