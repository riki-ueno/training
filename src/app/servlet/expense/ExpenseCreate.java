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
 * Servlet implementation class ExpenseCreate
 */
@WebServlet("/api/expense/create")
public class ExpenseCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenseCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Expense expense = Expense.build(request);
		expense.setClaimerId((String) request.getSession(false).getAttribute("employeeId"));


		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(new ExpenseDAO().create(expense)));
	}

}
