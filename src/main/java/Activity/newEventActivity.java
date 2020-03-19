package Activity;

import Bean.Event;
import DataBase.DataCenter;
import ServerLogic.EventRelevantImpl;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import com.example.agile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class newEventActivity extends Activity {
    private String month;
    private String day;
    private ArrayList<String> labels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);
        init();
    }
    private String getSysYear() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }

    private void init() {
        TextView textView = findViewById(R.id.year);
        textView.setText(getSysYear());
        //获取月与日信息
        final Spinner months = findViewById(R.id.month);
        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = months.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                month = "1";
            }
        });
        final Spinner days = findViewById(R.id.day);
        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = days.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                day = "1";
            }
        });
        //添加标签
        final Button addLabel = findViewById(R.id.addLabel);
        addLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.newEventLabel);
                labels.add(editText.getText().toString());
                if (labels.size()>=3){
                    addLabel.setClickable(false);
                }
            }
        });
        //获取活动的其他信息
        Button newEvent = findViewById(R.id.releaseEvent);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = findViewById(R.id.newEventTitle);
                EditText position = findViewById(R.id.newEventLocation);
                EditText time = findViewById(R.id.newEventTime);
                EditText credit = findViewById(R.id.creditLimit);
                EditText description = findViewById(R.id.newEventDes);

                Event event = new Event();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM");
                String dates = getSysYear() + "-" + month + "-" + day + " "+ time.getText().toString();
                Date date = new Date();
                try {
                    date = format.parse(dates);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                event.setTime(date);
                event.setDescription(description.getText().toString());
                event.setEventName(title.getText().toString());
                event.setFounderId(DataCenter.loginUser.getId());
                event.setLabel(labels);
                event.setPosition(position.getText().toString());
                event.setEventID(EventRelevantImpl.getEventCount() + 1);
                EventRelevantImpl.releaseEvent(DataCenter.loginUser, event);
            }
        });
    }
}
