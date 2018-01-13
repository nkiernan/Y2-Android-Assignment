import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

//Nathan Kiernan 15088410
public class Controller
{
	public static void main(String[] args) throws SQLException, IOException
	{
		HttpServer server = HttpServer.create(new InetSocketAddress(8005), 0); //Create a HTTP Server on port 8005

		//HTTP Handler to display all database employees as a JSON array:
		server.createContext("/", new HttpHandler() 
		{
			@Override
			public void handle(HttpExchange he) throws IOException
			{				
				EmployeeDAO dao = new EmployeeDAO();
				ArrayList<Employee> allEmployees = dao.showAllRecords(); //Connect to database get ArrayList of all Employee records
				Gson gson = new Gson();
				StringBuilder response = new StringBuilder();

				response.append(gson.toJson(allEmployees)); //Store allEmployees in GSON object and convert to JSON array, append to StringBuilder
				writeResponse(he, response.toString()); //Output string format of JSON array to page
			}			
		});

		//HTTP Handler to allow input of new Employee details:
		server.createContext("/add_emp", new HttpHandler() 
		{
			public void handle(HttpExchange he) throws IOException
			{
				he.sendResponseHeaders(200, 0);
				BufferedWriter out = new BufferedWriter(new	OutputStreamWriter(he.getResponseBody())); //Create output stream to write to page
				//Display HTML form for data input:
				out.write("<html><head></head><body><form method=\"POST\" action=\"/process_emp\">");
				out.write("<table border=\"1\">");
				out.write("<tr><td>Name:</td><td><input name=\"name\"></td></tr>");
				out.write("<tr><td>Gender:</td><td><input name=\"gender\"></td></tr>");
				out.write("<tr><td>DOB:</td><td><input name=\"dob\"></td></tr>");
				out.write("<tr><td>Address:</td><td><input name=\"address\"></td></tr>");
				out.write("<tr><td>Postcode:</td><td><input name=\"postcode\"></td></tr>");
				out.write("<tr><td>NIN:</td><td><input name=\"nin\"></td></tr>");
				out.write("<tr><td>Salary:</td><td><input name=\"salary\"></td></tr>");
				out.write("<tr><td>Job Title:</td><td><input name=\"jobtitle\"></td></tr>");
				out.write("<tr><td>Start Date:</td><td><input name=\"startdate\"></td></tr>");
				out.write("<tr><td>Email:</td><td><input name=\"email\"></td></tr>");
				out.write("<tr><td>Submit:</td><td><input type=\"submit\" value=\"Add to Database\"></td></tr>");
				out.write("</table></form></body></html>");
				out.close(); //Close output stream
			}		
		});

		//HTTP Handler to store new Employee in database:
		server.createContext("/process_emp", new HttpHandler()
		{
			@Override
			public void handle(HttpExchange he) throws IOException
			{		
				HashMap<String, String> post = new HashMap<String, String>();
				BufferedReader in = new	BufferedReader(new InputStreamReader(he.getRequestBody())); //Read request body
				String line = "";
				String request = "";
				while ((line = in.readLine()) != null)
				{
					request = request + line;
				}
				
				String[] pairs = request.split("&"); //Individual key=value pairs delimited by ampersands
				for (int i = 0; i < pairs.length; i++)
				{
					//Each key=value pair separated by equals, both halves require URL decoding:
					String pair = pairs[i];
					post.put(URLDecoder.decode(pair.split("=")[0], "UTF-8"), URLDecoder.decode(pair.split("=")[1], "UTF-8"));
				}
				
				EmployeeDAO dao = new EmployeeDAO(); //Create DAO
				//Store each form field value in individual strings:
				String name = post.get("name");
				String gender = post.get("gender");
				String dob = post.get("dob");
				String address = post.get("address");
				String postcode = post.get("postcode");
				String nin = post.get("nin");
				String salary = post.get("salary");
				String jobTitle = post.get("jobtitle");
				String startDate = post.get("startdate");			
				String email = post.get("email");

				Employee emp = new Employee(); //Create temporary Employee object
				//Set temporary Employee details to strings generated from HTML form:
				emp.setName(name);
				emp.setGender(gender);
				emp.setDob(dob);
				emp.setAddress(address);
				emp.setPostcode(postcode);
				emp.setNin(nin);
				emp.setSalary(salary);
				emp.setJobTitle(jobTitle);
				emp.setStartDate(startDate);
				emp.setEmail(email);

				Boolean update;
				try
				{
					update = dao.insertEmployee(emp); //Add new Employee to database using temporary Employee's details
					BufferedWriter out = new BufferedWriter(new	OutputStreamWriter(he.getResponseBody())); //Create output stream to write to page

					out.write("<html><head></head><body>");
					if (update)
					{
						out.write(emp.getName() + " added to database.<br>"); //Confirm insertion of Employee if successful
					}
					else
					{
						out.write("An error occured.<br>"); //Display error message if not
					}
					out.write("<a href=\"/\"><button>Back</button></a>"); //Display button leading back to JSON Array page
					out.write("<a href=\"/add_emp\"><button>Add Another</button></a></body></html>"); //Display button going back to add Employee page

					he.sendResponseHeaders(200, 0); //Send HTTP 200 response (OK) head
					out.close(); //Close output stream
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}		
			}		
		});

		server.setExecutor(null); //Default implementation of threading
		server.start(); //Start server
		System.out.println("The server is up and running on port 8005"); //Display message to console confirming that server is running
	}

	public static void writeResponse(HttpExchange he, String response) throws IOException //A method used to output string to page
	{
		he.sendResponseHeaders(200, response.length()); //Send HTTP status code 200
		OutputStream os = he.getResponseBody(); //Get output stream
		os.write(response.getBytes()); //Write response bytes to output stream
		os.close(); //Close output stream
	}
}