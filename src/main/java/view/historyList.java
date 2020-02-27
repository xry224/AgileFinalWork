package view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import com.example.agile.R;
import entity.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class historyList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        InitListener();
        initList(MainActivity.getEventList(), 6);
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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
            String eventTime = "时间: " + sdf.format(time);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("title", title);
            hashMap.put("description",description);
            hashMap.put("position", position);
            hashMap.put("time", eventTime);
            itemList.add(hashMap);
        }
        String[] key = new String[]{"title", "description", "position", "time"};
        int[] value = new int[]{R.id.historyTitle, R.id.historyDes, R.id.historyPos, R.id.historyTime};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,itemList, R.layout.history_list_item, key, value);
        listView.setAdapter(simpleAdapter);
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
    }// end of initListener
}
