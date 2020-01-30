package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agile.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myAccount extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        InitListener();
        //初始化设置列表
        ListView listView = findViewById(R.id.setList);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        InitSetListItem(itemList);
        //绑定adapter
        String[] key = new String[] {"itemImage", "itemText"};
        int[] value = new int[] {R.id.setListItemImage, R.id.setListItemText};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.set_list_item, key, value);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(myAccount.this, "行id: "+ id+", position: " + position, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
    /*
     * @Description 初始化设置列表项
     */
    private void InitSetListItem(@NotNull ArrayList<HashMap<String, Object>> itemList) {
        //添加列表项
        //此函数视情况可能需要重写
        String[] listText = new String[]{"实名认证", "设置", "关于我们", "退出登录"};
        int[] listImage = new int[]{R.drawable.accountrealname,  R.drawable.set, R.drawable.about, R.drawable.quit_login};
        for (int i = 0; i < listText.length; ++i)
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("itemImage", listImage[i]);
            map.put("itemText", listText[i]);
            itemList.add(map);
        }
    }
    private void InitListener() {
        //底部导航栏
        final RadioGroup navigate = findViewById(R.id.navigateGroup);
        navigate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myAccount.this);
                Intent intent = null;
                //直接使用setContentView(int viewID)会使所有view失效
                switch (checkedId){
                    case R.id.homePageButton:
                    {
                        intent = new Intent(myAccount.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.newEventButton:
                    {
                        System.out.println("new Event Pressed!");
                        builder.setMessage("not yet");
                        builder.create().show();
                        break;
                    }
                    case R.id.eventListButton:
                    {
                        intent = new Intent(myAccount.this, historyList.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.findNearByButton:
                    {
                        intent = new Intent(myAccount.this, findNearby.class);
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
