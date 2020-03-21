package Activity;

import Bean.Comment;
import Bean.Merchant;
import Bean.User;
import DataBase.DataCenter;
import ServerLogic.MerchantRelevantImpl;
import ServerLogic.getDataImpl;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.agile.R;
import myView.CommentAdapter;
import myView.ListViewForScroll;

import java.util.ArrayList;
import java.util.HashMap;

public class MerchantDetail extends Activity {
    private Merchant merchant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail);
        merchant = DataCenter.selectedMerchant;
        Init();
    }
    private void Init() {
        String shopName = merchant.getShopName();
        String description = merchant.getDescription();
        String position = "地点: " + merchant.getPosition();
        ArrayList<String> mainBusiness = merchant.getMainBusiness();
        ArrayList<Comment> comments = new getDataImpl().getComment(merchant.getCommentList());
        Bitmap shopicon = merchant.getTitleImage();
        ArrayList<Bitmap> shopImageList = merchant.getImageList();
        StringBuilder business = new StringBuilder("主营业务: ");
        for (int i = 0; i < mainBusiness.size(); ++i) {
            business.append(mainBusiness.get(i));
            if (i != mainBusiness.size()-1) {
                business.append("、");
            }
        }

        ImageView imageView = findViewById(R.id.shopIcon);
        TextView title = findViewById(R.id.shopTitle);
        TextView des = findViewById(R.id.shopDescription);
        TextView location = findViewById(R.id.shopLoc);
        TextView bus = findViewById(R.id.mainBusiness);
        ImageView desImage1 = findViewById(R.id.shopDesImage1);
        ImageView desImage2 = findViewById(R.id.shopDesImage2);

        setShopDesImage(shopImageList, desImage1, desImage2);
        imageView.setImageBitmap(shopicon);
        des.setText(description);
        title.setText(shopName);
        location.setText(position);
        bus.setText(business.toString());

        showComment(comments, comments.size());
        final Comment curComment;
        Button newComment = findViewById(R.id.newComment);
        if ((curComment = hasComment(comments, DataCenter.loginUser))!= null){
            newComment.setText("编辑评价");
            newComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditComment(curComment);
                }
            });
        }
        else{
            newComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newCommentDialog();
                }
            });
        }
    }

    private void EditComment(final Comment comment){
        AlertDialog.Builder builder = new AlertDialog.Builder(MerchantDetail.this);
        final AlertDialog alertDialog = builder.create();
        final View dialogView = View.inflate(MerchantDetail.this, R.layout.new_comment, null);
        alertDialog.setView(dialogView);
        alertDialog.show();

        final RatingBar ratingBar = dialogView.findViewById(R.id.criticRank);
        final EditText content = dialogView.findViewById(R.id.newCommentContent);
        ratingBar.setRating((float) comment.getRank());
        content.setText(comment.getContent());

        Button publish = dialogView.findViewById(R.id.publishComment);
        final Button cancel = dialogView.findViewById(R.id.cancelComment);
        publish.setText("修改");
        cancel.setText("取消");
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double rank = ratingBar.getRating();
                String commentContent = content.getText().toString();
                comment.setRank(rank);
                comment.setContent(commentContent);
                MerchantRelevantImpl.updateComment(comment);

                //更新merchant对象中的commentList
                merchant = new getDataImpl().getMerchant(merchant.getShopId());
                DataCenter.merchantList.set(DataCenter.selectedMerchantPosition, merchant);
                DataCenter.selectedMerchant = merchant;
                Toast toast = Toast.makeText(MerchantDetail.this, "修改成功！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Integer> commentId = new getDataImpl().getMerchantComments(merchant.getShopId());
                        ArrayList<Comment> comments = new getDataImpl().getComment(commentId);
                        showComment(comments, comments.size());
                        alertDialog.dismiss();
                    }
                }, 1500);//1.5秒后执行Runnable中的run方法
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private Comment hasComment(ArrayList<Comment> comments, User user){
        for (Comment comment : comments){
            if (comment.getCriticId() == user.getId()){
                return comment;
            }
        }
        return null;
    }

    private void setShopDesImage(ArrayList<Bitmap> list, ImageView imageView1, ImageView imageView2){
        if (list.size()<2){
            imageView1.setImageBitmap(list.get(0));
            imageView2.setImageBitmap(list.get(0));
        }
        else{
            imageView1.setImageBitmap(list.get(0));
            imageView2.setImageBitmap(list.get(1));
        }
    }

    private void newCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerchantDetail.this);
        final AlertDialog alertDialog = builder.create();
        final View dialogView = View.inflate(MerchantDetail.this, R.layout.new_comment, null);
        alertDialog.setView(dialogView);
        alertDialog.show();

        Button publish = dialogView.findViewById(R.id.publishComment);
        final Button cancel = dialogView.findViewById(R.id.cancelComment);
        publish.setText("发表");
        cancel.setText("取消");
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: about new comment
                RatingBar ratingBar = dialogView.findViewById(R.id.criticRank);
                EditText content = dialogView.findViewById(R.id.newCommentContent);
                double rank = ratingBar.getRating();
                String comment = content.getText().toString();
                Comment newComment = new Comment();

                newComment.setContent(comment);
                newComment.setCriticId(DataCenter.loginUser.getId());
                newComment.setMerchantId(merchant.getShopId());
                newComment.setNegative(0);
                newComment.setPositive(0);
                newComment.setRank(rank);
                MerchantRelevantImpl.evaluateMerchant(newComment, merchant);
                //更新merchant对象中的commentList
                merchant = new getDataImpl().getMerchant(merchant.getShopId());
                DataCenter.merchantList.set(DataCenter.selectedMerchantPosition, merchant);
                DataCenter.selectedMerchant = merchant;
                Toast toast = Toast.makeText(MerchantDetail.this, "评论成功！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Integer> commentId = new getDataImpl().getMerchantComments(merchant.getShopId());
                        ArrayList<Comment> comments = new getDataImpl().getComment(commentId);
                        showComment(comments, comments.size());
                        alertDialog.dismiss();
                        refresh();
                    }
                }, 1500);//1.5秒后执行Runnable中的run方法
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    private void refresh(){
        finish();
        Intent intent = new Intent(MerchantDetail.this, MerchantDetail.class);
        startActivity(intent);
    }

    private void showComment(final ArrayList<Comment> commentList, int Size) {
        int length = Math.min(Size, commentList.size());
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
        ListViewForScroll listView = findViewById(R.id.commentList);
        for (int i = 0; i < length; ++i) {
            Comment comment = commentList.get(i);
            String critic = new getDataImpl().getUser(comment.getCriticId()).getUserName();
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
        CommentAdapter adapter = new CommentAdapter(this,itemList, R.layout.comment_list_item,key, value, commentList);
        listView.setAdapter(adapter);
    }

}
