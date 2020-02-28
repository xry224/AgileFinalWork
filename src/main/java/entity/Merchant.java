package entity;

import android.media.Image;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;
/*
商家
 */
public class Merchant implements Serializable {
    //商家名称
    private String shopName;
    //类型可能需要更改，同Event中的image
    private Image titleImage;
    //店内预览图
    private ArrayList<Image> imageList;
    //上架位置
    private String position;
    //主营业务
    private ArrayList<String> mainBusiness;
    //介绍
    private String description;
    //商店评论
    private ArrayList<Comment> commentList;
    //商店评分,-1代表新店
    private int rank;

    public Merchant(String shopName, Image titleImage, String position, String description) {
        commentList = new ArrayList<>();
        mainBusiness = new ArrayList<>();
        imageList = new ArrayList<>();
        this.shopName = shopName;
        this.titleImage = titleImage;
        this.position = position;
        this.description = description;
        rank = -1;
    }

    public Merchant(String shopName, Image titleImage, ArrayList<Image> imageList, String position, int rank,
                    ArrayList<String> mainBusiness, String description, ArrayList<Comment> commentList) {
        this.shopName = shopName;
        this.titleImage = titleImage;
        this.imageList = imageList;
        this.position = position;
        this.mainBusiness = mainBusiness;
        this.description = description;
        this.commentList = commentList;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    //此方法参数视情况修改
    public void addImage(Image image) {
        imageList.add(image);
    }
    public void removeImage(Image image) {
        imageList.remove(image);
    }

    public void addBusiness(String des) {
        mainBusiness.add(des);
    }
    public void removeBusiness(String des) {
        mainBusiness.remove(des);
    }
    public void addComment(Comment comment) {
        commentList.add(comment);
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setTitleImage(Image titleImage) {
        this.titleImage = titleImage;
    }

    public void setImageList(ArrayList<Image> imageList) {
        this.imageList = imageList;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setMainBusiness(ArrayList<String> mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShopName() {
        return shopName;
    }

    public Image getTitleImage() {
        return titleImage;
    }

    public ArrayList<Image> getImageList() {
        return imageList;
    }

    public String getPosition() {
        return position;
    }

    public ArrayList<String> getMainBusiness() {
        return mainBusiness;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }
}
