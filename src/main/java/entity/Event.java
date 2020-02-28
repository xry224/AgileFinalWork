package entity;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/*
    活动实体
 */
public class Event implements Serializable {
    //活动名称
    private String eventName;
    //活动地点
    private String position;
    //活动日期，精确到分钟
    private Date time;
    //活动描述
    private String description;
    //活动发起人
    private User founder;
    //活动参与者
    private ArrayList<User> member;
    //活动标志ID，该变量唯一
    private String eventID;
    //活动标签
    private ArrayList<String> label;
    //活动的预览图片
    //将其显示在UI前需要将其转换为其他类型
    //此类型可能需要根据数据库实际存储情况做改变
    private Image picture;
    //通知、立即加入与广告相关不属于Event实体
    public Event(String name, String pos, Date timestamp, String des, User found, String id) {
        label = new ArrayList<>();
        member = new ArrayList<>();
        eventName = name;
        position = pos;
        time = timestamp;
        description = des;
        founder = found;
        eventID = id;
    }
    public Event(String name, String pos, Date timestamp, String des, User found, String id, ArrayList<User> mem) {
        label = new ArrayList<>();
        member = mem;
        eventName = name;
        position = pos;
        time = timestamp;
        description = des;
        founder = found;
        eventID = id;
    }

    public Event(String eventName, String position, Date time, String description, User founder,
                 String eventID, ArrayList<User> member, ArrayList<String> label) {
        this.eventName = eventName;
        this.position = position;
        this.time = time;
        this.description = description;
        this.founder = founder;
        this.member = member;
        this.eventID = eventID;
        this.label = label;
    }

    public void addLabel(String content) {
        label.add(content);
    }
    public void deleteLabel(String content) {
        label.remove(content);
    }
    public ArrayList<String> getLabel() {
        return label;
    }

    public String getEventName() {
        return eventName;
    }

    public String getPosition() {
        return position;
    }

    public Date getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public User getFounder() {
        return founder;
    }

    public ArrayList<User> getMember() {
        return member;
    }

    public String getEventID() {
        return eventID;
    }

    public Image getPicture() {
        return picture;
    }
    public void addMember(User u) {
        member.add(u);
    }
    public void deleteMember(User u) {
        member.remove(u);
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
}
