package entity;

import java.util.ArrayList;

public class User {
    private String userName;
    private String userID;
    private String realName;
    private int rankScore;
    private boolean hasAuthentication;
    private ArrayList<Event> eventList;
    private ArrayList<Comment> comments;
    //历史活动记录暂不考虑
    private ArrayList<Event> historyEvent;
    private ArrayList<Event> needNotification;
    public User(String userName, String userID, String realName, int rankScore, boolean hasAuthentication) {
        eventList = new ArrayList<>();
        historyEvent = new ArrayList<>();
        needNotification = new ArrayList<>();
        comments = new ArrayList<>();
        this.userName = userName;
        this.userID = userID;
        this.realName = realName;
        this.rankScore = rankScore;
        this.hasAuthentication = hasAuthentication;
    }

    public User(String userName, String userID, String realName, int rankScore, boolean hasAuthentication, ArrayList<Event> eventList,
                ArrayList<Event> historyEvent, ArrayList<Event> needNotification, ArrayList<Comment> comments) {

        this.userName = userName;
        this.userID = userID;
        this.realName = realName;
        this.rankScore = rankScore;
        this.hasAuthentication = hasAuthentication;
        this.eventList = eventList;
        this.historyEvent = historyEvent;
        this.comments = comments;
        this.needNotification = needNotification;
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
}
