package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import com.example.agile.R;
import entity.Merchant;

import java.util.ArrayList;
import java.util.HashMap;

public class findNearby extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_near);
        InitListener();
        initMerchantList(MainActivity.getTotalMerchantList(), 6);
    }
    private void initMerchantList(ArrayList<Merchant> shopList, int targetSize) {
        ListView listView = findViewById(R.id.findNearListView);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        int length = Math.min(targetSize, shopList.size());
        for (int i = 0; i < length; ++i) {
            Merchant merchant = shopList.get(i);
            int rank = merchant.getRank();
            String name = merchant.getShopName();
            String position = "地点: " + merchant.getPosition();
            String des = merchant.getDescription();
            //图片相关的处理
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("rank", rank);
            hashMap.put("shopName", name);
            hashMap.put("Position", position);
            hashMap.put("description", des);
            itemList.add(hashMap);
        }
        String[] key = new String[]{"shopName", "Position", "description"};
        int[] value = new int[]{R.id.ShopTitle, R.id.shopPosition, R.id.shopDes};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.shop_list_item, key, value);
        listView.setAdapter(simpleAdapter);
    }
    private void InitListener() {
        //底部导航栏
        RadioGroup navigate = findViewById(R.id.navigateGroup);
        navigate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AlertDialog.Builder builder = new AlertDialog.Builder(findNearby.this);
                Intent intent = null;
                //直接使用setContentView(int viewID)会使所有view失效
                switch (checkedId){
                    case R.id.homePageButton:
                    {
                        intent = new Intent(findNearby.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.newEventButton:
                    {
                        intent = new Intent(findNearby.this, newEventActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.eventListButton:
                    {
                        intent = new Intent(findNearby.this, historyList.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.myAccountButton:
                    {
                        intent = new Intent(findNearby.this, myAccount.class);
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
