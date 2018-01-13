package advprog.mmu.ac.uk.kiernan_n_15088410_android_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

/*
 * Nathan Kiernan 15088410
 * Displays details of Employee selected on MainActivity screen
 */
public class DetailsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) //What app does when this activity runs
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); //Set screen view to activity_details layout

        //Get details of Employee selected from the MainActivity screen:
        Bundle extras = getIntent().getExtras();
        Employee e = (Employee) extras.get("employee");
        String[] employeeDetails = new String[10]; //Create string array to store selected Employee details

        //If temporary Employee object is not null, store details in employeeDetails:
        assert e != null;
        employeeDetails[0] = "Name: " + e.getName();
        employeeDetails[1] = "Gender: " + e.getGender();
        employeeDetails[2] = "DOB: " + e.getDob();
        employeeDetails[3] = "Address: " + e.getAddress();
        employeeDetails[4] = "Postcode: " + e.getPostcode();
        employeeDetails[5] = "NIN: " + e.getNin();
        employeeDetails[6] = "Salary: " + e.getSalary();
        employeeDetails[7] = "Job Title: " + e.getJobTitle();
        employeeDetails[8] = "Start Date: " + e.getStartDate();
        employeeDetails[9] = "Email: " + e.getEmail();

        ListView employeeInformation = (ListView) findViewById(R.id.employeeInformation); //Get reference to onscreen ListView

        //Adapt employeeDetails into Array and display onscreen using referenced ListView:
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeDetails);
        employeeInformation.setAdapter(arrayAdapter);
    }
}