package Activity;

import Bean.User;
import DataBase.DataCenter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.example.agile.R;

import java.util.ArrayList;
import java.util.HashMap;

public class myAccount extends Activity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        InitListener();
        //初始化设置列表
        ListView listView = findViewById(R.id.setList);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        InitSetListItem(itemList);
        ImageView userIcon = findViewById(R.id.userImage);
        TextView uName = findViewById(R.id.userNameText);
        TextView realName = findViewById(R.id.realName);
        TextView credit = findViewById(R.id.useScore);
        userIcon.setImageBitmap(processUserIcon(DataCenter.loginUser));
        uName.setText(DataCenter.loginUser.getUserName());
        realName.setText("实名: " + DataCenter.loginUser.getRealName());
        credit.setText("信用积分: " + DataCenter.loginUser.getRankScore());
        setRankDes(DataCenter.loginUser.getRankScore());
        //绑定adapter
        String[] key = new String[] {"itemImage", "itemText"};
        int[] value = new int[] {R.id.setListItemImage, R.id.setListItemText};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.set_list_item, key, value);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myAccount.this);
                switch (position){
                    case 0:{
                        builder.setTitle("实名认证情况");
                        String content = DataCenter.loginUser.isHasAuthentication() ? "已实名"  : "未实名";
                        builder.setMessage(content);
                        break;
                    }
                    case 3:{
                        builder.setTitle("关于我们");
                        String content = "敏捷开发大作业\n" +
                                         "MF1932208 徐戎越\n" + "MF1932209 徐世诚\n" +
                                         "MF1932118 刘少国\n" + "MF1932212 许昌舜";
                        builder.setMessage(content);
                        break;
                    }
                    case 2:
                    {
                        DataCenter.loginUser = null;
                        Intent intent = new Intent(myAccount.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                builder.show();
            }
        });
    }
    private void setRankDes(int rank) {
        TextView rankDes = findViewById(R.id.rankDes);
        if (rank > 85){
            rankDes.setText(R.string.ExcellentCredit);
        }
        else if (rank > 75){
            rankDes.setText(R.string.GoodCredit);
        }
        else if (rank > 60){
            rankDes.setText(R.string.NormalCredit);
        }
        else if (rank > 50){
            rankDes.setText(R.string.notGoodCredit);
        }
        else{
            rankDes.setText(R.string.BadCredit);
        }
    }

    /*
     * @Description 初始化设置列表项
     */
    private void InitSetListItem(ArrayList<HashMap<String, Object>> itemList) {
        //添加列表项
        //此函数视情况可能需要重写
        String[] listText = new String[]{"实名认证", "设置", "退出登录", "关于我们"};
        int[] listImage = new int[]{R.drawable.accountrealname,  R.drawable.set, R.drawable.quit, R.drawable.about};
        for (int i = 0; i < listText.length; ++i)
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("itemImage", listImage[i]);
            map.put("itemText", listText[i]);
            itemList.add(map);
        }
    }
    private Bitmap processUserIcon(User user) {
        if (user.getUserIcon() == null){
            return BitmapFactory.decodeResource(getResources(), R.drawable.gym);
        }
        return user.getUserIcon();
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
                        intent = new Intent(myAccount.this, newEventActivity.class);
                        startActivity(intent);
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
