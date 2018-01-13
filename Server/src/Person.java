/*
 * Nathan Kiernan 15088410
 * Person object used to represent database employees when storing new entries
 * or display existing entries. The superclass of the Employee class.
 */
public class Person
{
	private String name;
    private String gender;
    private String dob;
    private String address;
    private String postcode;

    public Person(){} //Constructor for Person

    //Various getter and setter methods used to view or change a Person's details:
    public String getName()
    {
        return name;
    }
       
    public void setName(String name)
    {
        this.name = name;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getDob()
    {
        return dob;
    }

    public void setDob(String dob)
    {
        this.dob = dob;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
}