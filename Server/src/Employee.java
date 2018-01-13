/*
 * Nathan Kiernan 15088410
 * Employee object used to represent database employees when storing new entries
 * or display existing entries. A subclass of the Person class.
 */
public class Employee extends Person
{
	private String nin;
    private String salary;
    private String jobTitle;
    private String startDate;
    private String email;

    public Employee() //Constructor for Employee
    {
        super();
    }

    
    //Various getter and setter methods used to view or change an Employee's details:    
    public String getNin()
    {
        return nin;
    }

    public void setNin(String nin)
    {
        this.nin = nin;
    }

    public String getSalary()
    {
        return salary;
    }

    public void setSalary(String salary)
    {
        this.salary = salary;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}