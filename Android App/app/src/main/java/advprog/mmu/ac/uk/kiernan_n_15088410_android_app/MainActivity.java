package advprog.mmu.ac.uk.kiernan_n_15088410_android_app;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import org.json.*;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * Nathan Kiernan 15088410
 * Displays all Employees from JSON Array on HTTP Server
 */
public class MainActivity extends AppCompatActivity
{
    String[] employeeIndex; //Create string array to store Employee names
    ArrayList<Employee> allEmployees = new ArrayList<>(); //ArrayList to store Employee objects

    @Override
    protected void onCreate(Bundle savedInstanceState) //What app does when this activity runs
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Set screen view to activity_main layout

        //Create instance of getEmployees AsyncTask and run it:
        GetEmployees getEmployees = new GetEmployees();
        getEmployees.execute();

        //Display Toast informing user that they can see more details for Employee of interest:
        Toast.makeText(MainActivity.this, "Select an employee for more details", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() //What app will do when user navigates back to this activity:
    {
        super.onResume();
        Toast.makeText(MainActivity.this, "Select an employee for more details", Toast.LENGTH_SHORT).show(); //Redisplay Toast from onCreate() method
    }

    public String convertStreamToString(InputStream is) //Generate string from server response
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private class GetEmployees extends AsyncTask<Void, Void, ArrayList<Employee>>
    {
        @Override
        protected ArrayList<Employee> doInBackground(Void... v)
        {
            HttpURLConnection urlConnection;
            InputStream in = null;
            try
            {
                //Connect to HTTP Server and store content in an input stream:
                URL url = new URL("http://10.0.2.2:8005/");
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            String response = convertStreamToString(in); //Convert server response to a string
            System.out.println("Server response = " + response); //Display server response as console message

            try
            {
                JSONArray jsonArray = new JSONArray(response); //Parse JSON Array from server
                employeeIndex = new String[jsonArray.length()]; //Set size of employee index equal to JSON Array

                //Take all JSON array item details and store in individual strings:
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    String name = jsonArray.getJSONObject(i).get("name").toString();
                    String gender = jsonArray.getJSONObject(i).get("gender").toString();
                    String dob = jsonArray.getJSONObject(i).get("dob").toString();
                    String address = jsonArray.getJSONObject(i).get("address").toString();
                    String postcode = jsonArray.getJSONObject(i).get("postcode").toString();
                    String nin = jsonArray.getJSONObject(i).get("nin").toString();
                    String salary = jsonArray.getJSONObject(i).get("salary").toString();
                    String jobTitle = jsonArray.getJSONObject(i).get("jobTitle").toString();
                    String startDate = jsonArray.getJSONObject(i).get("startDate").toString();
                    String email = jsonArray.getJSONObject(i).get("email").toString();

                    employeeIndex[i] = name + "\n" + email; //Store Employee's name and email

                    Employee e = new Employee(); //Create temporary Employee object
                    //Set temporary Employee details to strings generated from JSON Array:
                    e.setName(name);
                    e.setGender(gender);
                    e.setDob(dob);
                    e.setAddress(address);
                    e.setPostcode(postcode);
                    e.setNin(nin);
                    e.setSalary(salary);
                    e.setJobTitle(jobTitle);
                    e.setStartDate(startDate);
                    e.setEmail(email);
                    allEmployees.add(e); //Add temporary Employee object to allEmployees
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return allEmployees;
        }

        @Override
        protected void onPostExecute(ArrayList<Employee> employeeArrayList)
        {
            super.onPostExecute(allEmployees);

            ListView employeeList = (ListView) findViewById(R.id.employeeList); //Get reference to onscreen ListView

            //Adapt employeeIndex into Array and display onscreen using referenced ListView:
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, employeeIndex);
            employeeList.setAdapter(arrayAdapter);

            //What happens when user clicks on an employee in index:
            employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    //Display Toast confirming which Employee has been selected:
                    Toast.makeText(MainActivity.this, "Selected: " + allEmployees.get(i).getName(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class); //Create new instance of DetailsActivity
                    intent.putExtra("employee", allEmployees.get(i)); //Pass selected Employee to DetailsActivity screen
                    startActivity(intent); //Start DetailsActivity
                }
            });
        }
    }
}