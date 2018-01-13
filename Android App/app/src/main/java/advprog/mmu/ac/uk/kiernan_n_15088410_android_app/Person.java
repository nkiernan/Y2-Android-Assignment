package advprog.mmu.ac.uk.kiernan_n_15088410_android_app;

import java.io.Serializable;

/*
 * Nathan Kiernan 15088410
 * Person object used to represent database employees when display existing entries.
 * The superclass of the Employee class. Uses the serializable interface (inherited by Employee)
 * in order to convert it into bytes for communication across a stream.
 */
public class Person implements Serializable
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