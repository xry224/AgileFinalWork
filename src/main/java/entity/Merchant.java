package entity;

import android.media.Image;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Merchant {
    private String shopName;
    //类型可能需要更改，同Event中的image
    private Image titleImage;
    private ArrayList<Image> imageList;
    private String position;
    private ArrayList<String> mainBusiness;
    private String description;
    private ArrayList<Comment> commentList;

    public Merchant(String shopName, Image titleImage, String position, String description) {
        commentList = new ArrayList<>();
        mainBusiness = new ArrayList<>();
        imageList = new ArrayList<>();
        this.shopName = shopName;
        this.titleImage = titleImage;
        this.position = position;
        this.description = description;
    }

    public Merchant(String shopName, Image titleImage, ArrayList<Image> imageList, String position,
                    ArrayList<String> mainBusiness, String description, ArrayList<Comment> commentList) {
        this.shopName = shopName;
        this.titleImage = titleImage;
        this.imageList = imageList;
        this.position = position;
        this.mainBusiness = mainBusiness;
        this.description = description;
        this.commentList = commentList;
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
