package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Variable Declaration
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton, deleteButton, confirmButton;
    EditText enterCityText;
    LinearLayout promptLayout;
    private Boolean visible = false; // Used to check if the user prompt bar is visible or not
    private Boolean deleteMode = false; // Used to check if we want to delete a city or not
    private Integer cityPosition = -1; // Used for making sure we delete the proper city or no city
    private String addedCity; // Used in adding cities to the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sets the main application to display the content according to the activity_main format

        cityList = findViewById(R.id.city_list); // Grabbing id of the city list
        promptLayout = findViewById(R.id.enter_city_layout); // Grabbing id the linear layout containing the prompt and confirm button
        enterCityText = findViewById(R.id.enter_city_prompt); // Grabbing id of the Enter City Name prompt
        deleteButton = findViewById(R.id.button_delete); // Grabbing the id of the Delete City button
        addButton = findViewById(R.id.button_add); // Grabbing the id of the Add City button
        confirmButton = findViewById(R.id.button_confirm); // Grabbing id of the confirm button

        // Array of default cities
        String[] cities = {"Edmonton", "Vancouver", "Calgary", "Edson", "Red Deer", "Toronto", "Airdrie", "Nunavut", "St. Paul"};

        // Setting up for displaying city list on the app
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // Display the individual cities according to the content.xml file's text format
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Setting up what happens anytime the Add City button is clicked
        addButton = findViewById(R.id.button_add); // Grabbing the id of the Add City button
        addButton.setOnClickListener(view -> {
            // Conditional logic to check if the user input prompt is visible already or not
            if (!visible) {
                promptLayout.setVisibility(LinearLayout.VISIBLE);
                visible = true;
            }
            else {
                promptLayout.setVisibility(LinearLayout.GONE);
                visible = false;
            }
        });

        // Removing Cities Method #1: Click on city first, then Delete City button
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            // Conditional logic for if clicking on a city while in or not in delete mode
            if (deleteMode) {
                deleteMode = false;
                deleteButton.setBackgroundColor(0x009698);
                cityPosition = -1;
            }
            else {
                deleteMode = true;
                cityPosition = position;
                deleteButton.setBackgroundColor(0xFFFF0000);
                deleteButton.setOnClickListener(v -> {
                    // Final check to make sure we are deleting the proper city, if not do nothing
                    if (cityPosition != -1) {
                        dataList.remove(position);
                        cityList.setAdapter(cityAdapter);

                        deleteMode = false;
                        cityPosition = -1;
                        deleteButton.setBackgroundColor(0x009698);
                    }
                });
            }
        });

        // Setting up what happens anytime the Confirm button is clicked
        confirmButton.setOnClickListener(view -> {
            addedCity = String.valueOf(enterCityText.getText()); // Stores the text that's currently within the user prompt

            // Checking if something was typed within and doesn't start with a blank space
            if (!addedCity.isBlank() && !addedCity.startsWith(" ")) {
                dataList.add(addedCity);
                cityList.setAdapter(cityAdapter);
            }

            // Resetting the user prompt for next use
            enterCityText.setText("");
            promptLayout.setVisibility(LinearLayout.GONE);
            visible = false;
        });
    }
}