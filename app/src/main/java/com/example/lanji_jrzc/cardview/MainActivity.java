package com.example.lanji_jrzc.cardview;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.TextView.OnEditorActionListener;

// Import SharedPreferences and its Editor class
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * This is the MainActivity for the to-do app.
 *
 * @author Lan Jing Li
 * @version May 29, 2018
 */

public class MainActivity extends Activity {

    // Declare reference variables for widgets
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ImageButton addButton;
    private ImageView todoListImage;
    private TextView noTodoLabel;
    private EditText taskEditText;
    private DatePicker date;
    private Button okButton;
    private Button cancelButton;
    private AlertDialog dialog;

    // Other instance variables
    private int color;
    private String taskString;
    private String dateString = "5/29/2018";
    private String thumbnailLetter;
    private List<Task> taskList;

    // Declare reference for a SharedPreferences object
    private SharedPreferences savedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize widget reference variables
        recyclerView = findViewById(R.id.recyclerView);
        todoListImage =  findViewById(R.id.todoListImage);
        noTodoLabel = findViewById(R.id.noTodoLabel);
        addButton = findViewById(R.id.addButton);

        // Initialize a new ArrayList for each task
        taskList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, taskList);

        //set layout manager, animator and adapter for recyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // Set a click listener for add item button
        OnClickListener buttonEventListener = new AddButtonListener();
        addButton.setOnClickListener(buttonEventListener);

        // get SharedPreferences object
        savedPrefs = getSharedPreferences( "TaskListPrefs", MODE_PRIVATE );
    }

    // This is a method that handles the add button events
    class AddButtonListener implements OnClickListener {
        @Override
        public void onClick(View view) {

            // set initial images to be invisible
            todoListImage.setVisibility(View.GONE);
            noTodoLabel.setVisibility(View.GONE);

            // initialize dialog
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

            // initialize widget reference variables for the dialog
            taskEditText = mView.findViewById(R.id.taskEditText);
            date = mView.findViewById(R.id.date);
            Calendar today = Calendar.getInstance();
            okButton = mView.findViewById(R.id.okButton);
            cancelButton = mView.findViewById(R.id.cancelButton);

            // set event handlers to widgets in dialog
            OnClickListener buttonEventListener = new ButtonListener();
            DatePicker.OnDateChangedListener datePickerListener = new DatePickerListener();
            okButton.setOnClickListener(buttonEventListener);
            cancelButton.setOnClickListener(buttonEventListener);
            taskEditText.setOnEditorActionListener(new TaskListener());
            date.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH),
                    datePickerListener);

            //build the dialog and shows it
            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.show();
        }
    }

    // This is a method used to handle the DatePicker widget events
    class DatePickerListener implements DatePicker.OnDateChangedListener {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateString = (monthOfYear + "/" + dayOfMonth + "/" + year);
        }
    }

    // This is a method used to handle the add and cancel button events
    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            // if OK button is pressed, add the user customized task and closes dialog
            if (v.getId() == R.id.okButton) {
                color = getRandomColor();
                Task t = new Task(taskString, dateString, thumbnailLetter, color);
                taskString = ".";
                thumbnailLetter = ".";
                taskList.add(t);
                dialog.cancel();
            }
            // if CANCEL button is pressed, close the dialog
            else if (v.getId() == R.id.cancelButton) {
                dialog.cancel();
            }
        }
    }

    // This is a method used to handle the edit text field events
    class TaskListener implements OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            taskString = v.getText().toString();
            thumbnailLetter = Character.toString(Character.toUpperCase(taskString.charAt(0)));
            return false;
        }
    }

    // This is a method that generates a random color code and returns it
    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    // onPause() method
    @Override
    public void onPause() {
        // Save important instance variables
        Editor prefsEditor = savedPrefs.edit();
        prefsEditor.putString( "taskString", taskString );
        prefsEditor.putString( "thumbnailLetter", thumbnailLetter);
        prefsEditor.commit();

        // Calling the parent onPause()
        super.onPause();
    }

    // onResume() method
    @Override
    public void onResume() {
        super.onResume();

        // Load the instance variables back (or default values)
        taskString = savedPrefs.getString("taskString", "");
        thumbnailLetter = savedPrefs.getString("thumbnailLetter", "");
    }
}
