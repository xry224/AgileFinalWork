package Activity;

import Bean.Message;
import DataBase.DataCenter;
import ServerLogic.getDataImpl;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.agile.R;
import myView.MessageAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends Activity {
    private ArrayList<Message> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        messages = new getDataImpl().getMessage(DataCenter.loginUser.getMessageBox());
        //TestMessageList();
        showMessageList(messages);
    }
    private void TestMessageList() {
        ListView listView = findViewById(R.id.messageList);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        //use later
        for (int i = 0; i < 6; ++i) {
            String from = "许昌舜 申请加入";
            String content = "申请活动: 普雷攻坚战";
            String rank = "用户信用等级: 100";

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("applicant", from);
            hashMap.put("message", content);
            hashMap.put("credit", rank);
            itemList.add(hashMap);
        }
        String[] key = new String[]{"applicant", "message", "credit"};
        int[] value = new int[]{R.id.messgaeFrom, R.id.messageContent, R.id.userCredit};
        MessageAdapter adapter = new MessageAdapter(this, itemList, R.layout.message_list_item, key, value, itemList);
        listView.setAdapter(adapter);

    }
    public static void addItem(ArrayList<Message> messageArrayList, ArrayList<HashMap<String, Object>> itemList){
        itemList.clear();
        for (Message message : messageArrayList) {
            String from = new getDataImpl().getUser(message.getApplicantId()).getRealName() + " 申请加入";
            String content = "申请活动: " + new getDataImpl().getEvent(message.getWantJoinId()).getEventName();
            String rank = "用户信用等级: " + new getDataImpl().getUser(message.getApplicantId()).getRankScore();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("applicant", from);
            hashMap.put("message", content);
            hashMap.put("credit", rank);
            itemList.add(hashMap);
        }
    }
    private void showMessageList(ArrayList<Message> messageList) {
        messages = new getDataImpl().getMessage(DataCenter.loginUser.getMessageBox());
        ListView listView = findViewById(R.id.messageList);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        addItem(messageList, itemList);
        String[] key = new String[]{"applicant", "message", "credit"};
        int[] value = new int[]{R.id.messgaeFrom, R.id.messageContent, R.id.userCredit};
        MessageAdapter adapter = new MessageAdapter(this, itemList, R.layout.message_list_item, key, value, itemList);
        listView.setAdapter(adapter);

    }
}
