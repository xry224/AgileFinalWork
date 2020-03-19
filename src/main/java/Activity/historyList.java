package Activity;

import Bean.Event;
import DataBase.DataCenter;
import ServerLogic.EventRelevantImpl;
import ServerLogic.getDataImpl;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import com.example.agile.R;
import myView.MyViewBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class historyList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        InitListener();
        ArrayList<Event> allEvent = new getDataImpl().getEvent(DataCenter.loginUser.getHistoryEvent()) ;
        initList(allEvent, allEvent.size());
    }
    private void initList(ArrayList<Event> historyList, int targetSize) {
        ListView listView = findViewById(R.id.HistoryListView);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        int length = Math.min(targetSize, historyList.size());
        for (int i = 0; i < length; ++i) {
            Event event = historyList.get(i);
            String description = event.getDescription();
            Date time = event.getTime();
            String position = "地点: " + event.getPosition();
            String title = event.getEventName();
            Bitmap image = event.getPicture();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
            String eventTime = "时间: " + sdf.format(time);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("Icon", image);
            hashMap.put("title", title);
            hashMap.put("description",description);
            hashMap.put("position", position);
            hashMap.put("time", eventTime);
            itemList.add(hashMap);
        }
        String[] key = new String[]{"Icon", "title", "description", "position", "time"};
        int[] value = new int[]{R.id.historyEventImage, R.id.historyTitle, R.id.historyDes, R.id.historyPos, R.id.historyTime};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,itemList, R.layout.history_list_item, key, value);
        simpleAdapter.setViewBinder(new MyViewBinder());
        listView.setAdapter(simpleAdapter);
        //simpleAdapter.notifyDataSetChanged();
    }
    private void InitListener() {
        //底部导航栏
        RadioGroup navigate = findViewById(R.id.navigateGroup);
        navigate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent;
                //直接使用setContentView(int viewID)会使所有view失效
                switch (checkedId){
                    case R.id.homePageButton:
                    {
                        intent = new Intent(historyList.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.newEventButton:
                    {
                        intent = new Intent(historyList.this, newEventActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.findNearByButton:
                    {
                        intent = new Intent(historyList.this, findNearby.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.myAccountButton:
                    {
                        intent = new Intent(historyList.this, myAccount.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                } //end of switch
                //startActivity(intent);
            }
        }); //end of RadioGroup checked changed listener
        // some other view....
        ImageButton search = findViewById(R.id.searchHistoryButton);
        ImageButton message = findViewById(R.id.messageInHistory);
        Button allEvent = findViewById(R.id.allEvent);
        Button myHoldEvent = findViewById(R.id.myHoldEvent);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText content = findViewById(R.id.historySearch);
                String searchContent = content.getText().toString();
                ArrayList<Event> filteredList = EventRelevantImpl.searchEvent(searchContent, 10);
                if (filteredList == null){
                   filteredList = new ArrayList<>();
                }
                initList(filteredList, filteredList.size());
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(historyList.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        allEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Event> allEvent = new getDataImpl().getEvent(DataCenter.loginUser.getHistoryEvent());
                initList(allEvent, allEvent.size());
            }
        });
        myHoldEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Event> allEvent = new getDataImpl().getEvent(DataCenter.loginUser.getEventList());
                initList(allEvent, allEvent.size());
            }
        });
    }// end of initListener
    private ArrayList<Event> FilterEvent(ArrayList<Event> totalList, String filter) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : totalList){
            if (event.getEventName().contains(filter)){
                events.add(event);
            }
        }
        return events;
    }

}
