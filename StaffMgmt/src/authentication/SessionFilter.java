package authentication;

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

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter("/SessionFilter")
public class SessionFilter implements Filter {

	/**
	 * Default constructor. 
	 */
	public SessionFilter() {
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

		HttpServletRequest req = null;
		HttpServletResponse res = null;
		HttpSession session = null;

		try 
		{
			req = (HttpServletRequest) request;
			res = (HttpServletResponse) response;

			if(req == null || res == null)
			{
				throw new Exception("Request or response doesn't exist.");
			}

			session = req.getSession(false);
			if(session == null)
			{
				throw new Exception("Session doesn't exist.");
			}
			// otherwise user is logged in
			// pass the request along the filter chain
			chain.doFilter(request, response);			
		} 
		catch (Exception e) 
		{
			request.getRequestDispatcher("Welcome.jsp").forward(request, response);
		}
		finally 
		{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
