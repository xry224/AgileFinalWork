package Bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
/*
用户实体
 */
public class User implements Serializable {
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
    //创建的活动的列表
    private ArrayList<Event> eventList;
    //评论列表
    private ArrayList<Comment> comments;
    //参与的所有活动的历史记录，此列表内容包括eventList，或许可以去掉eventList，使用时根据Event中的founder属性进行筛选
    private ArrayList<Event> historyEvent;
    //需要通知的活动
    //通知功能暂时不考虑
    private ArrayList<Event> needNotification;
    //用户邮箱
    private String email;
    //用户信息列表
    private ArrayList<Message> messageBox;
    //用户头像
    private Bitmap userIcon;

    public User(String userName, String userID, String realName, int rankScore, boolean hasAuthentication, String email) {
        eventList = new ArrayList<>();
        historyEvent = new ArrayList<>();
        needNotification = new ArrayList<>();
        comments = new ArrayList<>();
        this.userName = userName;
        this.userID = userID;
        this.realName = realName;
        this.rankScore = rankScore;
        this.hasAuthentication = hasAuthentication;
        this.email = email;
    }

    public User(String userName, String userID, String realName, int rankScore, boolean hasAuthentication, ArrayList<Event> eventList,
                ArrayList<Comment> comments, ArrayList<Event> historyEvent, ArrayList<Event> needNotification, String email,
                ArrayList<Message> messageBox, Bitmap userIcon) {
        this.userName = userName;
        this.userID = userID;
        this.realName = realName;
        this.rankScore = rankScore;
        this.hasAuthentication = hasAuthentication;
        this.eventList = eventList;
        this.comments = comments;
        this.historyEvent = historyEvent;
        this.needNotification = needNotification;
        this.email = email;
        this.messageBox = messageBox;
        this.userIcon = userIcon;
    }

    public User(String userName, String userID, String realName, int rankScore, boolean hasAuthentication, ArrayList<Event> eventList,
                ArrayList<Event> historyEvent, ArrayList<Event> needNotification, ArrayList<Comment> comments, String email) {

        this.userName = userName;
        this.userID = userID;
        this.realName = realName;
        this.rankScore = rankScore;
        this.hasAuthentication = hasAuthentication;
        this.eventList = eventList;
        this.historyEvent = historyEvent;
        this.comments = comments;
        this.needNotification = needNotification;
        this.email = email;
    }
    private User()
    {
        eventList = new ArrayList<>();
        historyEvent = new ArrayList<>();
        needNotification = new ArrayList<>();
        comments = new ArrayList<>();
        this.userName = "testName";
        this.userID = "testID";
        this.realName = "realName";
        this.rankScore = 100;
        this.hasAuthentication = true;
        this.email = "test@teat.com";
    }
    //该方法可能被移植到DataBase相关类中
    public static User getUser(String userName, String passwd) {
        //some action in database
        return new User();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addComment (Comment comment) {
        comments.add(comment);
    }
    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }

    public void joinEvent(Event event) {
        eventList.add(event);
        event.addMember(this);
    }
    public void quitEvent(Event event) {
        eventList.remove(event);
        event.deleteMember(this);
    }
    public boolean addNotification(Event event) {
        int index = eventList.indexOf(event);
        if (index == -1) {
            return false;
        }
        needNotification.add(event);
        return true;
    }
    public boolean closeNotification(Event event) {
        int index = eventList.indexOf(event);
        if (index == -1) {
            return false;
        }
        needNotification.remove(event);
        return true;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public ArrayList<Event> getHistoryEvent() {
        return historyEvent;
    }

    public ArrayList<Event> getNeedNotification() {
        return needNotification;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }

    public void setHasAuthentication(boolean hasAuthentication) {
        this.hasAuthentication = hasAuthentication;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getRealName() {
        return realName;
    }

    public int getRankScore() {
        return rankScore;
    }

    public boolean isHasAuthentication() {
        return hasAuthentication;
    }

    public ArrayList<Message> getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(ArrayList<Message> messageBox) {
        this.messageBox = messageBox;
    }

    public Bitmap getUserIcon() {
        return userIcon;
    }
}
