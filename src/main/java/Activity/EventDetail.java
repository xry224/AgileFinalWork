package Activity;

import Bean.Event;
import Bean.User;
import DataBase.DataCenter;
import ServerLogic.EventRelevantImpl;
import ServerLogic.getDataImpl;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.example.agile.R;
import myView.CircleImageView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventDetail extends Activity {
    private static Event event = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        event = (Event)getIntent().getSerializableExtra("eventInfo");
        init();
    }
    @SuppressLint("SetTextI18n")
    private void init() {
        TextView title = findViewById(R.id.eventTitle);
        TextView time = findViewById(R.id.eventTime);
        TextView position = findViewById(R.id.eventPosition);
        TextView description = findViewById(R.id.eventDes);
        TextView founder = findViewById(R.id.founder);
        Button allMember = findViewById(R.id.allMember);
        Button apply = findViewById(R.id.applyEvent);
        final ImageView eventImage = findViewById(R.id.eventImage);
        ArrayList<CircleImageView> userIcons = new ArrayList<>();
        userIcons.add((CircleImageView) findViewById(R.id.userIcon1));
        userIcons.add((CircleImageView) findViewById(R.id.userIcon2));
        userIcons.add((CircleImageView) findViewById(R.id.userIcon3));
        userIcons.add((CircleImageView) findViewById(R.id.userIcon4));
        userIcons.add((CircleImageView) findViewById(R.id.userIcon5));
        //设置event各项属性
        title.setText(event.getEventName());
        Date date = event.getTime();
        @SuppressLint("SimpleDateFormat") Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = format.format(date);
        time.setText(result);
        position.setText("地点: " + event.getPosition());
        description.setText(event.getDescription());
        User user = new getDataImpl().getUser(event.getFounderId());
        founder.setText(user.getUserName());

        eventImage.setImageBitmap(event.getPicture());
        ArrayList<User> member = new getDataImpl().getUser(event.getMemberId());
        setUserIcon(member, userIcons);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRelevantImpl.applyToJoin(DataCenter.loginUser, event);
                Toast toast = Toast.makeText(EventDetail.this, "申请成功！请等待审核", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        allMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //检查用户是否已经为活动的一员
        if (isMember(DataCenter.loginUser, event)){
            apply.setText("已加入");
            apply.setClickable(false);
        }
        else{
            apply.setText("立即加入");
            apply.setClickable(true);
        }
    }
    private boolean isMember(User user, Event event){
        for (Integer integer : event.getMemberId()){
            if (user.getId() == integer){
                return true;
            }
        }
        return false;
    }
    private Bitmap processUserIcon(User user) {
        if (user.getUserIcon() == null){
            return BitmapFactory.decodeResource(getResources(), R.drawable.gym);
        }
        return user.getUserIcon();
    }

    private void setUserIcon(ArrayList<User> member, ArrayList<CircleImageView> userIcons){
        int length = Math.min(member.size(), userIcons.size());
        for (int i = 0; i < length; ++i){
            CircleImageView circleImageView = userIcons.get(i);
            User user = member.get(i);
            circleImageView.setImageBitmap(processUserIcon(user));
        }
    }
}

