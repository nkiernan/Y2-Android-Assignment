import java.sql.*;
import java.util.ArrayList;

/*
 * Nathan Kiernan 15088410
 * An Employee Database Access Object used to connect to a given database.
 * It has methods to view all Employees stored in the database and to store
 * new instances of an Employee into it.
 */
public class EmployeeDAO
{
	public EmployeeDAO(){} //Empty constructor for Database Access Object

    public Connection getDBConnection() //Method used to generate a connection to the database
    {
        Connection c = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
        	//Create a connection to empdb database in res folder:
            String dbURL = "jdbc:sqlite:res/empdb.sqlite";
            c = DriverManager.getConnection(dbURL);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return c; //Return the database connection
    }

    public ArrayList<Employee> showAllRecords() //A method to get the information for all employees in the database
    {
        ArrayList<Employee> allEmployees = new ArrayList<Employee>();
        ResultSet rs = null;
        
        try
        {
            rs = getDBConnection().createStatement().executeQuery("SELECT * FROM employees"); //Fill rs with all records in the employees table
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (rs != null) //If results are generated:
        {
            try
            {
                while (rs.next()) //While query results are still populated:
                {
                	//Create a temporary Employee object and store the details using it's setter methods:
                    Employee emp = new Employee();
                    emp.setName(rs.getString("Name"));
                    emp.setGender(rs.getString("Gender"));
                    emp.setDob(rs.getString("DOB"));
                    emp.setAddress(rs.getString("Address"));
                    emp.setPostcode(rs.getString("Postcode"));
                    emp.setNin(rs.getString("Nin"));
                    emp.setSalary(rs.getString("Salary"));
                    emp.setJobTitle(rs.getString("JobTitle"));
                    emp.setStartDate(rs.getString("StartDate"));
                    emp.setEmail(rs.getString("Email"));
    
                    allEmployees.add(emp); //Add temporary Employee object to ArrayList of Employees
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return allEmployees; //Return an ArrayList of all database employees
    }

    public boolean insertEmployee(Employee emp) throws SQLException //Method to add new Employee to database, for use with server's /add_emp page
    {
    	//Prepare insert statement:
    	String query = "INSERT INTO employees (Name, Gender, DOB, Address, Postcode, NIN, Salary, JobTitle, StartDate, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement s = getDBConnection().prepareStatement(query);
        
        //Use Employee's getter methods to add field values into query string:
        s.setString(1, emp.getName());
        s.setString(2, emp.getGender());
        s.setString(3, emp.getDob());
        s.setString(4, emp.getAddress());
        s.setString(5, emp.getPostcode());
        s.setString(6, emp.getNin());
        s.setString(7, emp.getSalary());
        s.setString(8, emp.getJobTitle());
        s.setString(9, emp.getStartDate());
        s.setString(10, emp.getEmail());

        //Insert new employee and generate boolean based on success:
        int updated = s.executeUpdate();
        if (updated > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}