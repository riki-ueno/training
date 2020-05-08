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

import app.dao.EmployeeTokenDAO;
import app.model.EmployeeToken;

/**
 * Servlet Filter implementation class VerificationToken
 */
@WebFilter(filterName="vertification-token", urlPatterns = {"/api/employee/*", "/api/department/*", "/api/expense/*", "/api/auth/*", "/api/authentication/logout", "/api/authentication/role"})
public class VerificationTokenFilter implements Filter {

    /**
     * Default constructor.
     */
    public VerificationTokenFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest) request).getSession(false);

		if (session != null && session.getAttribute("employeeId") != null && session.getAttribute("token") != null) {
			EmployeeToken employeeToken = new EmployeeToken();
			employeeToken.setEmployeeId((String) session.getAttribute("employeeId"));
			employeeToken.setToken((String) session.getAttribute("token"));

			if (new EmployeeTokenDAO().exists(employeeToken)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.setStatus(401);
			}
		} else {
			httpResponse.setStatus(401);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
