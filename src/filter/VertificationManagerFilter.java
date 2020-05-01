package filter;

import java.io.IOException;
import java.util.HashMap;

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

/**
 * Servlet Filter implementation class VertificationRole
 */
@WebFilter("/VertificationRole")
public class VertificationManagerFilter implements Filter {

    /**
     * Default constructor.
     */
    public VertificationManagerFilter() {
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
        HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpSession session = ((HttpServletRequest)request).getSession(false);

		HashMap<String, String> searchCondition = new HashMap<String, String>(){
			{
				put("token = ?", (String) session.getAttribute("token"));
			}
		};

		if (new EmployeeTokenDAO().get_by(searchCondition).getEmployee().getEmployeeDetail().getRoleId() != 2) {
			chain.doFilter(request, response);
		} else {
			httpResponse.setStatus(403);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
