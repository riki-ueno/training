package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.dao.EmployeeDAO;

/**
 * Servlet Filter implementation class VertificationRole
 */
@WebFilter(filterName = "vertification-role", urlPatterns = {
		"/api/employee/create",
		"/api/employee/edit",
		"/api/employee/delete",
		"/api/department/create",
		"/api/department/edit",
		"/api/department/delete",
		"/api/expense/index",
		"/api/expense/accept",
		"/api/expense/reject"
		})
public class VertificationRoleFilter implements Filter {

    /**
     * Default constructor.
     */
    public VertificationRoleFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest) request).getSession(false);

		if (session != null && session.getAttribute("employeeId") != null) {
			String employeeId = (String) session.getAttribute("employeeId");

			if (new EmployeeDAO().get(employeeId).getEmployeeDetail().getRoleId() == 2) {
				chain.doFilter(request, response);
			} else {
				httpResponse.setStatus(403);
			}
		} else {
			httpResponse.setStatus(401);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
