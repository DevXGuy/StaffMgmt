package registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import employee.Employee;
import employee.EmployeeMgr;


/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logWrite = LogManager.getLogger();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String strFirstName = null;
		String strLastName = null;
		String strUsername = null;
		String strPasswword = null;
		Employee objEmployee = null;
		EmployeeMgr objEmpMgr = null;
			
		try 
		{
			strFirstName = request.getParameter("txtFirstName").toString();
			strLastName = request.getParameter("txtLastName").toString();
			strUsername = request.getParameter("txtUsername").toString();
			strPasswword = request.getParameter("txtPassword").toString();
			
			objEmployee = new Employee();
			objEmpMgr = new EmployeeMgr();
			if(objEmpMgr.isUsernameTaken(strUsername))
			{
				throw new Exception("This username is already taken.");
			}
			objEmployee.setFirstName(strFirstName);
			objEmployee.setLastName(strLastName);
			objEmployee.setUsername(strUsername);
			objEmployee.setPassword(strPasswword);
			
			objEmpMgr.save(objEmployee);
			response.sendRedirect("Login.jsp");
			logWrite.info("User: " + strLastName + " was successfully registered.");
		}
		catch(Exception ex)
		{
			logWrite.error("Failed to register user: " + strUsername + " " + ex.getMessage());
			response.sendRedirect("Welcome.jsp");
		}
		finally 
		{
			// TODO: handle finally clause
		}
	}
}
