package Activity;

import Bean.Event;
import DataBase.DataCenter;
import ServerLogic.EventRelevantImpl;
import ServerLogic.getDataImpl;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
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
    private ArrayList<String> labels = new ArrayList<>();
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

    private void showLables(){
        ArrayList<TextView> textViewList = new ArrayList<>();
        TextView label1 = findViewById(R.id.newEventLabel1);
        TextView label2 = findViewById(R.id.newEventLabel2);
        TextView label3 = findViewById(R.id.newEventLabel3);
        textViewList.add(label1);
        textViewList.add(label2);
        textViewList.add(label3);
        int length = Math.min(textViewList.size(), labels.size());
        for (int i = 0; i < length; ++i){
            String content = labels.get(i);
            textViewList.get(i).setText(content);
        }
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
                editText.setText("");
                if (labels.size()>=3){
                    addLabel.setClickable(false);
                }
                showLables();
            }
        });
        //获取活动的其他信息
        Button newEvent = findViewById(R.id.releaseEvent);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = findViewById(R.id.newEventTitle);
                EditText position = findViewById(R.id.newEventLocation);
                EditText Hour = findViewById(R.id.newEventHour);
                EditText minute = findViewById(R.id.newEventMinute);
                EditText credit = findViewById(R.id.creditLimit);
                EditText description = findViewById(R.id.newEventDes);

                Event event = new Event();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM");
                String dates = getSysYear() + "-" + month + "-" + day + " "+ Hour.getText().toString() + ":" + minute.getText().toString();
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
                event.setEventID(EventRelevantImpl.getNextEventID());
                EventRelevantImpl.releaseEvent(DataCenter.loginUser, event);
                labels.clear();
                DataCenter.currentMainEventList = new getDataImpl().getEventList(DataCenter.expectedEventListSize);
                Toast toast = Toast.makeText(newEventActivity.this, "发布成功！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                final Intent intent = new Intent(newEventActivity.this, MainActivity.class);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                },1000);
            }
        });
    }
}
