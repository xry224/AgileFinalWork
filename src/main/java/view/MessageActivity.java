package view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.agile.R;
import entity.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        TestMessageList();

        //showMessageList(messages);
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
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.message_list_item, key, value);
        listView.setAdapter(simpleAdapter);
    }
    private void showMessageList(ArrayList<Message> messageList) {
        ListView listView = findViewById(R.id.messageList);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        //use later
        for (int i = 0; i < messageList.size(); ++i) {

        }
        String[] key = new String[]{};
        int[] value = new int[]{};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.comment_list_item, key, value);
        listView.setAdapter(simpleAdapter);
    }
}
