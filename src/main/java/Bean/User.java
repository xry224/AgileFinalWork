package Bean;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.ArrayList;

/*
用户实体
 */
public class User implements Serializable {

    //数据库中用户编号
    private int id;
    //用户名
    private String userName;
    //用户ID，唯一标志
    //该标志或许可由邮箱替代
    private String userID;
    //真实姓名
    private String realName;
    //信用评级
    private int rankScore;
    //是否实名认证
    private boolean hasAuthentication;
    //用户邮箱
    private String email;
    //用户电话
    private String phoneNumber;
    //用户头像
    private Bitmap userIcon;
    //创建的活动的ID的列表
    private ArrayList<Integer> eventList;
    //评论的ID列表
    private ArrayList<Integer> comments;
    //参与的所有活动的历史记录，存储ID，此列表内容包括eventList，或许可以去掉eventList，使用时根据Event中的founder属性进行筛选
    private ArrayList<Integer> historyEvent;
    //用户信息列表
    private ArrayList<Integer> messageBox;

    public User() {
        this.userIcon = null;
        eventList = new ArrayList<>();
        comments = new ArrayList<>();
        historyEvent = new ArrayList<>();
        messageBox = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getRankScore() {
        return rankScore;
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }

    public boolean isHasAuthentication() {
        return hasAuthentication;
    }

    public void setHasAuthentication(boolean hasAuthentication) {
        this.hasAuthentication = hasAuthentication;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bitmap getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Bitmap userIcon) {
        this.userIcon = userIcon;
    }

    public ArrayList<Integer> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Integer> eventList) {
        this.eventList = eventList;
    }

    public ArrayList<Integer> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Integer> comments) {
        this.comments = comments;
    }

    public ArrayList<Integer> getHistoryEvent() {
        return historyEvent;
    }

    public void setHistoryEvent(ArrayList<Integer> historyEvent) {
        this.historyEvent = historyEvent;
    }

    public ArrayList<Integer> getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(ArrayList<Integer> messageBox) {
        this.messageBox = messageBox;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userID='" + userID + '\'' +
                ", realName='" + realName + '\'' +
                ", rankScore=" + rankScore +
                ", hasAuthentication=" + hasAuthentication +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userIcon=" + userIcon +
                ", eventList=" + eventList +
                ", comments=" + comments +
                ", historyEvent=" + historyEvent +
                ", messageBox=" + messageBox +
                '}';
    }
}
