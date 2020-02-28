package view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.agile.R;
import entity.Comment;
import entity.Merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantDetail extends Activity {
    private Merchant merchant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail);
        merchant = (Merchant)getIntent().getSerializableExtra("shopInfo");
        Init();
    }
    private void Init() {
        String shopName = merchant.getShopName();
        String description = merchant.getDescription();
        String position = "地点: " + merchant.getPosition();
        ArrayList<String> mainBusiness = merchant.getMainBusiness();
        ArrayList<Comment> comments = merchant.getCommentList();
        String business = "主营业务: ";
        for (int i = 0; i < mainBusiness.size(); ++i) {
            business += mainBusiness.get(i);
            if (i != mainBusiness.size()-1) {
                business += "、";
            }
        }

        TextView title = findViewById(R.id.shopTitle);
        TextView des = findViewById(R.id.shopDescription);
        TextView location = findViewById(R.id.shopLoc);
        TextView bus = findViewById(R.id.mainBusiness);

        des.setText(description);
        title.setText(shopName);
        location.setText(position);
        bus.setText(business);

        showComment(comments, 5);
    }

    private void showComment(ArrayList<Comment> commentList, int Size) {
        int length = Math.min(Size, commentList.size());
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        ListView listView = findViewById(R.id.commentList);
        for (int i = 0; i < length; ++i) {
            Comment comment = commentList.get(i);
            String critic = comment.getCritic().getUserName();
            String content = comment.getContent();
            int positive = comment.getPositive();
            int negative = comment.getNegative();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("commenter", critic);
            hashMap.put("content", content);
            hashMap.put("support",positive);
            hashMap.put("oppose",negative);
            itemList.add(hashMap);
        }
        String[] key = new String[]{"commenter", "content", "support", "oppose"};
        int[] value = new int[]{R.id.critic, R.id.commentContent, R.id.positiveRank, R.id.negativeRank};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,itemList, R.layout.comment_list_item,key, value);
        listView.setAdapter(simpleAdapter);
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
    public Merchant getMerchant() {
        return merchant;
    }
}
