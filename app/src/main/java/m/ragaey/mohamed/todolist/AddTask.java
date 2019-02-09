package m.ragaey.mohamed.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    public static final String Id ="m.ragaey.mohamed.todolist.id";

    public static final String taskk ="m.ragaey.mohamed.todolist.taskk";
    public static final String desc ="m.ragaey.mohamed.todolist.desc";
    public static final String timee = "m.ragaey.mohamed.todolist.timee";
    public static final String datee ="m.ragaey.mohamed.todolist.datee";

    EditText editTexttask;
    EditText editTextdescription;
    static TextView time_txt,date_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTexttask = findViewById(R.id.task_field);
        editTextdescription = findViewById(R.id.desc_field);
        time_txt = findViewById(R.id.time_txt);
        date_txt = findViewById(R.id.date_txt);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(Id))
        {
            setTitle("Edit Task");
            editTexttask.setText(intent.getStringExtra(taskk));
            editTextdescription.setText(intent.getStringExtra(desc));
            time_txt.setText(intent.getStringExtra(timee));
            date_txt.setText(intent.getStringExtra(datee));

        }else {
            setTitle("Add Task");

        }


        time_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

    }


    public void saveTask() {
        String task = editTexttask.getText().toString();
        String description = editTextdescription.getText().toString();
        String time =time_txt.getText().toString();
        String date = date_txt.getText().toString();

        if (task.trim().isEmpty() || description.trim().isEmpty())
        {
            Toast.makeText(this, "Please insert Failed Data", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(taskk,task);
        data.putExtra(desc,description);
        data.putExtra(timee,time);
        data.putExtra(datee,date);

        int id = getIntent().getIntExtra(Id,-1);
        if (id != -1 )
        {
            data.putExtra(Id,id);
        }
        setResult(RESULT_OK ,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_task:
                saveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            int hour = hourOfDay;
            int minutes = minute;

            String timeSet = "";
            if (hour > 12) {
                hour -= 12;
                timeSet = "PM";
            } else if (hour == 0) {
                hour += 12;
                timeSet = "AM";
            } else if (hour == 12){
                timeSet = "PM";
            }else{
                timeSet = "AM";
            }

            String min = "";
            if (minutes < 10)
                min = "0" + minutes ;
            else
                min = String.valueOf(minutes);

            // Append in a StringBuilder
            String aTime = new StringBuilder().append(hour).append(':')
                    .append(min ).append(" ").append(timeSet).toString();

            time_txt.setText(aTime);

        }


    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            int month2 = month+1;
            date_txt.setText(day + "/" + month2 +"/" +year);
        }
    }
}
