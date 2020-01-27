package View;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agile.R;
import entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private ArrayList<Event> totalEventList = new ArrayList<>();
    private ArrayList<User> totalUserList = new ArrayList<>();
    private ArrayList<Merchant> totalMerchantList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTestData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showEventCard(totalEventList, 5);
        //Button bind
        ImageButton searchButton = findViewById(R.id.searchButton);
        ImageButton messageButton = findViewById(R.id.messageButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        messageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void clickLocation(View v) {

    }

    private void setFrameContent(FrameLayout frameContent, Event event) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //framelayout中的图片
        layoutParams.gravity = Gravity.START;
        layoutParams.width = 450;
        layoutParams.setMarginStart(30);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.gym);
        frameContent.addView(imageView);

        //framelayout中的标题
        //整个右半部分的线性布局
        FrameLayout.LayoutParams rightlayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightlayoutParams.leftMargin = 520;
        LinearLayout rightLinerLayout = new LinearLayout(this);
        rightLinerLayout.setLayoutParams(rightlayoutParams);
        //title本身的layoutparam
        LinearLayout.LayoutParams llayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //llayoutParams.gravity = Gravity.LEFT;
        rightLinerLayout.setOrientation(LinearLayout.VERTICAL);
        //text内容设置
        TextView title = new TextView(this);
        title.setText(event.getEventName());
        title.setTextColor(Color.rgb(0, 0, 0));
        title.setTextSize(25);
        llayoutParams.setMargins(0,20,0,0);
        title.setLayoutParams(llayoutParams);
        //将title添加到rightlinerLayout中
        rightLinerLayout.addView(title);

        //framelayout中的标签
        ArrayList<String> labels = event.getLabel();
        int length = Math.min(labels.size(), 3);
        LinearLayout labelLiner = new LinearLayout(this);
        //显示label的线性布局的layoutparam
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //文本本身的layoutparam
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelLiner.setLayoutParams(labelParams);
        for (int i=0; i<length;++i) {
            String content = labels.get(i);
            TextView label = new TextView(this);
            label.setText(content);
            label.setTextSize(15);
            //label.setBackgroundColor(Color.rgb(255,255,255));
            label.setBackground(getDrawable(R.drawable.shape));
            textParams.setMargins(10,20,0,0);
            label.setLayoutParams(textParams);
            labelLiner.addView(label);
        }
        rightLinerLayout.addView(labelLiner);

        //framelayout中的地点
        TextView pos = new TextView(this);
        pos.setText("地点：" + event.getPosition());
        pos.setTextColor(Color.rgb(0, 0, 0));
        pos.setTextSize(15);
        pos.setLayoutParams(llayoutParams);
        //将title添加到rightlinerLayout中
        rightLinerLayout.addView(pos);

        //framelayout中的时间
        TextView dateTime = new TextView(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(event.getTime());
        dateTime.setText("活动时间：" + time);
        dateTime.setTextColor(Color.rgb(0, 0, 0));
        dateTime.setTextSize(12);
        dateTime.setLayoutParams(llayoutParams);
        //将title添加到rightlinerLayout中
        rightLinerLayout.addView(dateTime);

        frameContent.addView(rightLinerLayout);
    }
    //显示活动卡片
    public void showEventCard(ArrayList<Event> events, int targetSize) {
        LinearLayout layoutRoot = findViewById(R.id.showEvent);
        int length = Math.min(events.size(), targetSize);
        //至多只显示targetSize个活动
        for (int i = 0; i < length; ++i)
        {
            int marginTop = 15 * (i + 1);
            //设置framelayout的布局
            FrameLayout frameLayout = new FrameLayout(layoutRoot.getContext());
            frameLayout.setBackgroundColor(Color.rgb(232,232,232));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 400);
            layoutParams.setMargins(0, marginTop,0,0);
            frameLayout.setLayoutParams(layoutParams);
            //绘制framelayout中的内容
            setFrameContent(frameLayout, events.get(i));
            layoutRoot.addView(frameLayout);
        }
    }

    private void initTestData() {
        //测试用用户数据
        User u1, u2, u3, u4;
        u1 = new User("彼得大弟", "MF1932209", "徐世诚", 100, true);
        u2 = new User("test", "MF1932208", "徐戎越", 100, true);
        u3 = new User("臭弟弟", "MF1932212", "许昌舜", 100, true);
        u4 = new User("牛逼", "MF1932118", "刘少国", 100, true);
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
    }
}
