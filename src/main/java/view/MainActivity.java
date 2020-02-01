package view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agile.R;
import entity.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class MainActivity extends Activity {
    private ArrayList<Event> totalEventList = new ArrayList<>();
    private ArrayList<User> totalUserList = new ArrayList<>();
    private ArrayList<Merchant> totalMerchantList = new ArrayList<>();
    private boolean firstIn = true;
    /*
    private View mainView = null;
    private View historyView = null;
    private View accountView = null;
    private User currentLogin = null;
    private View findView = null;
    */
    private int lastSelectedBottomNavigator = R.id.homePageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (firstIn)
        {
            initTestData();
            firstIn = false;
        }
        init();
        showEventCardByListView(totalEventList, 5);
    }
    private void init() {
        //底部导航栏
        RadioGroup navigate = findViewById(R.id.navigateGroup);
        navigate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                Intent intent = null;
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
    }

    public void clickLocation(View v) {

    }
    private void showEventCardByListView(ArrayList<Event> events, int targetSize) {
        ListView listView = findViewById(R.id.eventListView);
        //ListView listView = new ListView(this);
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
            //格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(eventTime);

            HashMap<String, Object> map = new HashMap<>();
            map.put("eventTitle", title);
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
        String[] key = new String[] {"eventTitle", "eventLocation", "eventTime", "label1", "label2", "label3", "label4", "label5", "label6"};
        int[] value = new int[] {R.id.eventItemTitle, R.id.eventLocation, R.id.eventDate, R.id.eventLabel1, R.id.eventLabel2,
                                                      R.id.eventLabel3, R.id.eventLabel4, R.id.eventLabel5, R.id.eventLabel6};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.event_list_item, key, value);
        listView.setAdapter(simpleAdapter);
    }

    private void initTestData() {
        //测试用用户数据
        User u1, u2, u3, u4;
        u1 = new User("彼得大弟", "MF1932209", "徐世诚", 100, true, "smail1@nju.edu.cn", "1234561");
        u2 = new User("test", "MF1932208", "徐戎越", 100, true, "smail2@nju.edu.cn", "1234562");
        u3 = new User("臭弟弟", "MF1932212", "许昌舜", 100, true, "smail3@nju.edu.cn", "1234563");
        u4 = new User("牛逼", "MF1932118", "刘少国", 100, true,"smail4@nju.edu.cn", "1234564");
        totalUserList.add(u1);
        totalUserList.add(u2);
        totalUserList.add(u3);
        totalUserList.add(u4);
        //测试用商家数据
        Merchant merchant = new Merchant("南大体育馆", null, "南大鼓楼校区", "测试用场地");
        totalMerchantList.add(merchant);
        //测试用活动数据
        Event event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11;
        ArrayList<User> memberList = new ArrayList<>(totalUserList);
        //测试用标签
        ArrayList<String> label1 = new ArrayList<>();
        ArrayList<String> label2 = new ArrayList<>();
        ArrayList<String> label3 = new ArrayList<>();
        ArrayList<String> label4 = new ArrayList<>();
        ArrayList<String> label5 = new ArrayList<>();
        ArrayList<String> label6 = new ArrayList<>();
        label1.add("户外");
        label1.add("球类运动");

        label2.add("户外");
        label2.add("无器械");

        label3.add("室内");
        label3.add("健身房");
        label3.add("更♂衣♂室");

        label4.add("室内");
        label4.add("你17张牌能秒我");
        label4.add("棋牌");

        label5.add("室内");
        label5.add("棋牌");

        label6.add("室内");
        label6.add("游泳");
        event1 = new Event("游泳", "南大游泳馆", new Date(), "来游泳", u1,"event1", memberList, label6);
        event2 = new Event("足球", "南大体育场", new Date(), "来踢足球", u1,"event2", memberList, label1);
        event3 = new Event("篮球", "南大体育场", new Date(), "来打篮球", u1,"event3", memberList, label1);
        event4 = new Event("国际象棋", "宿舍", new Date(), "来下棋", u2,"event4", memberList, label5);
        event5 = new Event("健身", "南大健身馆", new Date(), "来健♂身", u2,"event5", memberList, label3);
        event6 = new Event("斗地主", "宿舍", new Date(), "来斗地主", u2,"event6", memberList, label4);
        event7 = new Event("跑步", "南大体育场", new Date(), "来跑步", u3,"event7", memberList, label2);
        event8 = new Event("三国杀", "宿舍", new Date(), "来打三国杀", u3,"event8", memberList, label5);
        event9 = new Event("排球", "南大体育场", new Date(), "来玩排球", u3,"event9", memberList, label1);
        event10 = new Event("乒乓球", "南大体育场", new Date(), "来打乒乓球", u4,"event10", memberList, label1);
        event11 = new Event("网球", "南大体育场", new Date(), "来打网球", u4,"event11", memberList, label1);
        totalEventList.add(event1);
        totalEventList.add(event2);
        totalEventList.add(event3);
        totalEventList.add(event4);
        totalEventList.add(event5);
        totalEventList.add(event6);
        totalEventList.add(event7);
        totalEventList.add(event8);
        totalEventList.add(event9);
        totalEventList.add(event10);
        totalEventList.add(event11);
        for (User user : totalUserList)
        {
            for (Event event : totalEventList)
            {
                user.joinEvent(event);
            }
        }
    }
}
