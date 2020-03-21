package Activity;

import DataBase.DataCenter;
import ServerLogic.MerchantRelevantImpl;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import com.example.agile.R;
import myView.MyViewBinder;
import myView.RatingBarAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import Bean.Merchant;

public class findNearby extends Activity {
    private boolean isPause;
    private ArrayList<Merchant> merchantList = new ArrayList<>();
    @Override
    protected void onResume() {
        super.onResume();
        if (isPause){ //判断是否暂停
            isPause = false;
            initMerchantList(merchantList, 6);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        isPause = true; //记录页面已经被暂停
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_near);
        InitListener();
        merchantList = DataCenter.merchantList;
        initMerchantList(DataCenter.merchantList, 6);
        initListListener();
    }
    private void initListListener() {
        ListView listView = findViewById(R.id.findNearListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataCenter.selectedMerchant = DataCenter.merchantList.get(position);
                DataCenter.selectedMerchantPosition = position;
                Intent intent = new Intent(findNearby.this, MerchantDetail.class);
                startActivity(intent);
            }
        });
        ImageButton message = findViewById(R.id.messageInFind);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findNearby.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        ImageButton searchMer = findViewById(R.id.searchFindButton);
        searchMer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText content = findViewById(R.id.findSearch);
                String searchContent = content.getText().toString();
                ArrayList<Merchant> filteredList = MerchantRelevantImpl.searchMerchant(searchContent, 10);
                initMerchantList(filteredList, filteredList.size());
            }
        });
    }

    private ArrayList<Merchant> FilterMerchant(ArrayList<Merchant> list, String filter){
        ArrayList<Merchant> merchants = new ArrayList<>();
        for (Merchant merchant : list){
            if (merchant.getShopName().contains(filter)){
                merchants.add(merchant);
            }
        }
        return merchants;
    }


    private void initMerchantList(ArrayList<Merchant> shopList, int targetSize) {
        ListView listView = findViewById(R.id.findNearListView);
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        int length = Math.min(targetSize, shopList.size());
        double[] ranks = new double[length];
        for (int i = 0; i < length; ++i) {
            Merchant merchant = shopList.get(i);
            double rank = merchant.getRank();
            String name = merchant.getShopName();
            String position = "地点: " + merchant.getPosition();
            String des = merchant.getDescription();
            ArrayList<Bitmap> bitmaps = merchant.getImageList();
            //图片相关的处理
            HashMap<String, Object> hashMap = new HashMap<>();
            //hashMap.put("rank", rank);
            processImage(bitmaps, hashMap);
            ranks[i] = rank == -1 ? 0 : rank;
            hashMap.put("shopName", name);
            hashMap.put("Position", position);
            hashMap.put("description", des);
            itemList.add(hashMap);
        }
        String[] key = new String[]{"shopName", "Position", "description", "shopImage1", "shopImage2", "shopImage3"};
        int[] value = new int[]{R.id.ShopTitle, R.id.shopPosition, R.id.shopDes, R.id.shopImage1, R.id.shopImage2, R.id.shopImage3};
        RatingBarAdapter adapter = new  RatingBarAdapter(this, itemList, R.layout.shop_list_item, key, value, ranks);
        adapter.setViewBinder(new MyViewBinder());
        listView.setAdapter(adapter);
    }

    private void processImage(ArrayList<Bitmap> bitmaps, HashMap<String, Object> hashMap){
        int length = Math.min(bitmaps.size(), 3);
        String id = "shopImage";
        for (int i = 1; i <= length; ++i){
            String curID = id + i;
            hashMap.put(curID, bitmaps.get(i - 1));
        }
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
