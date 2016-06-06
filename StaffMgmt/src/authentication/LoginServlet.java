package authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import employee.Employee;
import employee.EmployeeMgr;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
		logger.debug("Login servlet was created.");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		logger.debug("An HTTP request was received.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession	objSession = null;
		String 		strUsername = null;
		String 		strPassword = null;
		EmployeeMgr objEmployeeMgr = null;
		Employee	objEmployee = null;

		try
		{
			logger.debug("An HTTP request was received.");
			strUsername = request.getParameter("txtUsername");
			strPassword = request.getParameter("txtPassword");
			if(strUsername.isEmpty())
			{
				throw new Exception("Username cannot be empty.");
			}
			if(strPassword.isEmpty())
			{
				throw new Exception("Password cannot be empty.");
			}
			objEmployeeMgr = new EmployeeMgr();
			objEmployee = objEmployeeMgr.getEmpByUserPass(strUsername, strPassword);
			if(objEmployee == null)
			{
				throw new Exception("Employee with the given information does not exist.");
			}
			
			objSession = request.getSession(true);
			objSession.setAttribute("ProfileInfo", objEmployee);
			
			response.sendRedirect("Profile.jsp");
		}
		catch(Exception ex)
		{
			logger.error("Failed to login: " + ex.getMessage());
			response.sendRedirect("Login.jsp");
		}
	}
}
