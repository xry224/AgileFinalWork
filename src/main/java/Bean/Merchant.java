package Bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/*
商家
 */
public class Merchant implements Serializable {

    //商家的唯一标志ID
    private int shopId;
    //商家名称
    private String shopName;
    //店内预览图
    private ArrayList<Bitmap> imageList;
    //上架位置
    private String position;
    //主营业务
    private ArrayList<String> mainBusiness;
    //介绍
    private String description;
    //类型可能需要更改，同Event中的image
    private Bitmap titleImage;
    //商店评分,-1代表新店
    private double rank;
    //商店评论ID列表
    private ArrayList<Integer> commentList;

    public Merchant() {
        titleImage = null;
        mainBusiness = new ArrayList<>();
        commentList = new ArrayList<>();
        imageList = new ArrayList<>();
    }

    public Merchant(int shopId, String shopName, ArrayList<Bitmap> imageList, String position, ArrayList<String> mainBusiness, String description, Bitmap titleImage, double rank, ArrayList<Integer> commentList) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.imageList = imageList;
        this.position = position;
        this.mainBusiness = mainBusiness;
        this.description = description;
        this.titleImage = titleImage;
        this.rank = rank;
        this.commentList = commentList;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ArrayList<Bitmap> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Bitmap> imageList) {
        this.imageList = imageList;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ArrayList<String> getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(ArrayList<String> mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(Bitmap titleImage) {
        this.titleImage = titleImage;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public ArrayList<Integer> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Integer> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", imageList=" + imageList +
                ", position='" + position + '\'' +
                ", mainBusiness=" + mainBusiness +
                ", description='" + description + '\'' +
                ", titleImage=" + titleImage +
                ", rank=" + rank +
                ", commentList=" + commentList +
                '}';
    }
}
