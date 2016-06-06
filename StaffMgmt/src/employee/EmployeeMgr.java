package employee;

import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;

public class EmployeeMgr {
	private static final Logger logWriter = LogManager.getLogger();
	private Configuration objConfigDB = null;
	private SessionFactory objSessionFactoryDB = null;

	public EmployeeMgr() throws Exception
	{
		try
		{
			logWriter.debug("EmployeeMgr is about to be constructed.");
			
			logWriter.info("Configuring Hibernate configuration.");
			objConfigDB = new Configuration();
			objConfigDB.configure("hibernate.cfg.xml");
			logWriter.info("Hibernate configuration was successfully configured.");
			objSessionFactoryDB = objConfigDB.buildSessionFactory();
			logWriter.info("Hibernate session factory was successfully configured.");
			
			logWriter.debug("EmployeeMgr was fully constructed.");
		}
		catch(Exception ex)
		{
			throw new Exception("Failed to construct EmployeeMgr.", ex);
		}
	}

	public void getAllEmployees()
	{
		Employee		objTmpEmp = null;
		List<Employee>	objListEmp;
		Iterator<Employee> iterEmp;
		Session 		objSession = null;
		Criteria 		objCriteria = null;

		try
		{
			objSession = objSessionFactoryDB.openSession();

			objCriteria = objSession.createCriteria(Employee.class);
			objListEmp = objCriteria.list();
			iterEmp = objListEmp.iterator();
			while (iterEmp.hasNext())
			{
				objTmpEmp = iterEmp.next();
				logWriter.info("Employee " + objTmpEmp.getLastName() + " was found.");
			}
		}
		catch(Exception ex)
		{
			logWriter.error(ex.getMessage());
		}
		finally 
		{
			objSession.close();
		}
	}

	public Employee getEmpByID(int id) throws NullPointerException
	{
		Employee 		objEmployee = null;
		Session 		objSession = null;
		Criteria		objCriteria = null;
		List<Employee>	objListEmp;

		try
		{
			logWriter.info("Finding an employee with an ID of: " + id);

			objSessionFactoryDB = objConfigDB.buildSessionFactory();
			objSession = objSessionFactoryDB.openSession();

			objCriteria = objSession.createCriteria(Employee.class);
			objCriteria.add(Restrictions.eq("id", id));
			objListEmp = objCriteria.list();
			if(objListEmp.isEmpty())
			{
				throw new Exception("Employee with the given ID was not found.");
			}
			objEmployee = objListEmp.get(0);

			logWriter.info("Employee with id of " + id + " was found.");
		}
		catch(Exception ex)
		{
			logWriter.error(ex.getMessage());
		}
		finally 
		{
			objSession.close();
		}
		return objEmployee;
	}

	@SuppressWarnings("unchecked")
	public Employee getEmpByUserPass(String strUsername, String strPassword) throws NullPointerException
	{
		Employee 		objEmployee = null;
		Session 		objSession = null;
		Criteria		objCriteria = null;
		List<Employee>	objListEmp;

		try
		{
			logWriter.info("Finding an employee with username of: " + strUsername);

			objSession = objSessionFactoryDB.openSession();
			objCriteria = objSession.createCriteria(Employee.class);
			objCriteria.add(Restrictions.eq("username", strUsername));
			objCriteria.add(Restrictions.eq("password", strPassword));
			objListEmp = objCriteria.list();
			if(objListEmp.isEmpty())
			{
				throw new Exception("Employee with the given username and passowrd was not found.");
			}
			objEmployee = objListEmp.get(0);

			logWriter.info("Employee with username of " + strUsername + " was found.");
		}
		catch(Exception ex)
		{
			logWriter.error(ex.getMessage());
		}
		finally 
		{
			objSession.close();
		}
		return objEmployee;
	}

	public Boolean isUsernameTaken(String strUsername)
	{
		Boolean 	exists = false;
		Session 	objSession = null;
		Criteria	objCriteria = null;
		
		try
		{
			logWriter.info("Checking if username: "+ strUsername + " already exists.");

			objSession = objSessionFactoryDB.openSession();
			objCriteria = objSession.createCriteria(Employee.class);
			objCriteria.add(Restrictions.eq("username", strUsername));
			if(!objCriteria.list().isEmpty())
			{
				exists = true;
				logWriter.error("Username: "+ strUsername + " already exists.");	
			}
		}
		catch(Exception ex)
		{
			logWriter.error(ex.getMessage());
		}
		finally 
		{
			objSession.close();
		}
		return exists;
	}
	
	public void save(Employee emp)
	{
		Session 		objSession = null;
		Transaction		objTransaction = null; 

		try
		{
			logWriter.info("Employee: "+ emp.getLastName() + " is about to be saved.");

			objSession = objSessionFactoryDB.openSession();
			objTransaction = objSession.beginTransaction();
			objSession.persist(emp);
			objTransaction.commit();

			logWriter.info("Employee "+ emp.getLastName() + " was successfully saved.");
		}
		catch(Exception ex)
		{
			logWriter.error(ex.getMessage());
		}
		finally 
		{
			objSession.close();
		}
	}

	public void updateEmp(Employee emp)
	{
		Session 	objSession = null;
		Transaction objTrans = null;
		try 
		{
			logWriter.info("Updating employee: " + emp.getLastName());
			objSession = objSessionFactoryDB.openSession();
			objTrans = objSession.beginTransaction();
			objSession.update(emp);
			objTrans.commit();
			logWriter.info("Employee was successfully updated.");
		} 
		catch (Exception ex) 
		{
			logWriter.error("Failed to update employee: " + emp.getLastName());
		}
		finally
		{
			objSession.close();
		}
	}
}
