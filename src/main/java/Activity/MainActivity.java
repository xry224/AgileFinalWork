package Activity;

import Bean.Event;
import DataBase.DataCenter;
import ServerLogic.EventRelevantImpl;
import android.annotation.SuppressLint;
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
import java.util.*;

public class MainActivity extends Activity {
    private ArrayList<Event> shownList = null;
    private String[] filter = new String[]{"足球","篮球","国际象棋","健身","扑克","跑步","三国杀","游泳","气排球","乒乓球","网球"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        showEventCardByListView(DataCenter.currentMainEventList, DataCenter.showEventListSize);
        shownList = DataCenter.currentMainEventList;
    }
    private void init() {
        //底部导航栏
        RadioGroup navigate = findViewById(R.id.navigateGroup);
        navigate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent;
                //直接使用setContentView(int viewID)会使所有view失效
                switch (checkedId){
                    case R.id.eventListButton:
                    {
                        intent = new Intent(MainActivity.this, historyList.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.newEventButton:
                    {
                        intent = new Intent(MainActivity.this, newEventActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.findNearByButton:
                    {
                        intent = new Intent(MainActivity.this, findNearby.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.myAccountButton:
                    {
                        intent = new Intent(MainActivity.this, myAccount.class);
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
        });
        //消息按钮
        ImageButton message = findViewById(R.id.messageButton);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        //活动列表
        ListView listView = findViewById(R.id.eventListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selected = shownList.get(position);
                Intent intent = new Intent(MainActivity.this, EventDetail.class);
                intent.putExtra("eventInfo", selected);
                startActivity(intent);
            }
        });
        //搜索按钮
        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText content = findViewById(R.id.mainSearch);
                String searchContent = content.getText().toString();
                shownList = EventRelevantImpl.searchEvent(searchContent, 10);
                if (shownList == null){
                    shownList = new ArrayList<>();
                }
                showEventCardByListView(shownList, 5);
            }
        });
        //筛选按钮
        /*
        Button football = findViewById(R.id.footballButton);
        Button basketball = findViewById(R.id.basketballButton);
        Button chess = findViewById(R.id.chessButton);
        Button exercise = findViewById(R.id.exerciseButton);
        Button poker = findViewById(R.id.pokerButton);
        Button run = findViewById(R.id.runButton);
        Button treeKingdom = findViewById(R.id.sanguoButton);
        Button swim = findViewById(R.id.swimButton);
        Button volley = findViewById(R.id.volleyButton);
        Button tableTennis = findViewById(R.id.tabletennisButton);
        Button tennis = findViewById(R.id.tennisButton);*/

    }

    private ArrayList<Event> searchQualifiedEvent(ArrayList<Event> totalList, String searchContent) {
        ArrayList<Event> res = new ArrayList<>();
        for (Event event : totalList){
            if (event.getEventName().contains(searchContent) || event.inLabel(searchContent)){
                res.add(event);
            }
        }
        return res;
    }

    public void clickFilter(View view) {
        int id = view.getId();
        switch (id){
            case R.id.footballButton:
            {
                shownList = EventRelevantImpl.searchEventByLabel(filter[0], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.basketballButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[1]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[1], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.chessButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[2]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[2], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.exerciseButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[3]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[3], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.pokerButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[4]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[4], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.runButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[5]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[5], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.sanguoButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[6]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[6], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.swimButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[7]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[7], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.volleyButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[8]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[8], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.tabletennisButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[9]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[9], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            case R.id.tennisButton:
            {
                //shownList = searchQualifiedEvent(DataCenter.currentMainEventList, filter[10]);
                shownList = EventRelevantImpl.searchEventByLabel(filter[10], 10);
                showEventCardByListView(shownList, 8);
                break;
            }
            default:
            {
                shownList = DataCenter.currentMainEventList;
                showEventCardByListView(shownList, 8);
                break;
            }
        }

    }
    private void showEventCardByListView(ArrayList<Event> events, int targetSize) {
        ListView listView = findViewById(R.id.eventListView);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        int length = Math.min(events.size(), targetSize);
        for (int i = 0; i < length; ++i)
        {
            //获取需要显示的各项数据
            //活动图片暂且硬编码在item中
            Event event = events.get(i);
            Date eventTime = event.getTime();
            String title = event.getEventName();
            String location = event.getPosition();
            ArrayList<String> labels = event.getLabel();
            Bitmap bitmap = event.getPicture();
            //格式化日期
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(eventTime);
            //信息放入map
            HashMap<String, Object> map = new HashMap<>();
            map.put("eventTitle", title);
            map.put("image", bitmap);
            map.put("eventLocation", "地点：" + location);
            map.put("eventTime", "时间：" + time);
            //处理label
            for (int j = 1; j <= 6; ++j)
            {
                String key = "label" + j;
                String value = j <= labels.size() ? labels.get(j - 1) : "";
                map.put(key, value);
            }
            itemList.add(map);
        }
        String[] key = new String[] {"image", "eventTitle", "eventLocation", "eventTime", "label1", "label2", "label3", "label4", "label5", "label6"};
        int[] value = new int[] {R.id.eventItemImage, R.id.eventItemTitle, R.id.eventLocation, R.id.eventDate, R.id.eventLabel1, R.id.eventLabel2,
                                                      R.id.eventLabel3, R.id.eventLabel4, R.id.eventLabel5, R.id.eventLabel6};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.event_list_item, key, value);
        simpleAdapter.setViewBinder(new MyViewBinder());
        listView.setAdapter(simpleAdapter);
    }
}
