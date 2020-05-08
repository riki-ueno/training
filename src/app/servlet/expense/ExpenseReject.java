package app.servlet.expense;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.ExpenseDAO;
import app.model.Expense;

/**
 * Servlet implementation class ExpenseReject
 */
@WebServlet("/apiexpense/reject")
public class ExpenseReject extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenseReject() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Expense expense = new Expense();
		expense.setId(Integer.parseInt(request.getParameter("expenseId")));
		expense.setStatus(2);
		expense.setReason(request.getParameter("reason"));
		expense.setUpdaterId((String) request.getSession(false).getAttribute("employeeId"));

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(new ExpenseDAO().updateStatus(expense)));
	}

}
