package employee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logWriter = LogManager.getLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProfile() {
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
		String 		strFirstName;
		String 		strLastName;
		String 		strUsername;
		String 		strPassword;
		HttpSession objSession = null;
		EmployeeMgr	objEmployeeMgr = null;
		Employee	objEmployee = null;

		try 
		{
			strFirstName = request.getParameter("txtFirstName");
			strLastName = request.getParameter("txtLastName");
			strUsername = request.getParameter("txtUsername");
			strPassword = request.getParameter("txtPassword");
			if(strFirstName.isEmpty())
			{
				throw new Exception("First name cannot be empty.");
			}
			if(strLastName.isEmpty())
			{
				throw new Exception("Last name cannot be empty.");
			}
			if(strUsername.isEmpty())
			{
				throw new Exception("Username cannot be empty.");
			}
			if(strPassword.isEmpty())
			{
				throw new Exception("Password cannot be empty.");
			}
			objSession = request.getSession(false);
			if(objSession == null)
			{
				throw new Exception("Session was not found.");
			}
			objEmployee = (Employee) objSession.getAttribute("User");
			if(objEmployee == null)
			{
				throw new Exception("Failed to get user from session.");
			}
			
			objEmployee.setFirstName(strFirstName);
			objEmployee.setLastName(strLastName);
			objEmployee.setUsername(strUsername);
			objEmployee.setPassword(strPassword);
			objEmployeeMgr = new EmployeeMgr();
			objEmployeeMgr.updateEmp(objEmployee);
			objSession.setAttribute("User", objEmployee);
			request.getSession().setAttribute("ProfileInfo", objEmployee);
			request.getRequestDispatcher("Profile.jsp").forward(request, response);
		} 
		catch (Exception e) 
		{
			logWriter.error("Failed to update user profile." + e.getMessage());
			response.sendRedirect("Profile.jsp");
		}
	}
}
