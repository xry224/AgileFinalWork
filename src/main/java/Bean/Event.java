package Bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
    活动实体
 */
public class Event implements Serializable {

    //活动标志ID，该变量唯一
    private int eventID;
    //活动名称
    private String eventName;
    //活动地点
    private String position;
    //活动日期，精确到分钟
    private Date time;
    //活动描述
    private String description;
    //活动发起人ID
    private int founderId;
    //活动标签
    private ArrayList<String> label;
    //活动的预览图片
    //将其显示在UI前需要将其转换为其他类型
    //此类型可能需要根据数据库实际存储情况做改变
    private Bitmap picture;
    //商家ID
    private int shopId;
    //活动参与者ID
    private ArrayList<Integer> memberId;
    //通知、立即加入与广告相关不属于Event实体

    public Event() {
        picture = null;
        label = new ArrayList<>();
        memberId = new ArrayList<>();
    }

    public Event(int eventID, String eventName, String position, Date time, String description, int founderId, ArrayList<String> label, Bitmap picture, int shopId, ArrayList<Integer> memberId) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.position = position;
        this.time = time;
        this.description = description;
        this.founderId = founderId;
        this.label = label;
        this.picture = picture;
        this.shopId = shopId;
        this.memberId = memberId;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFounderId() {
        return founderId;
    }

    public void setFounderId(int founderId) {
        this.founderId = founderId;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {
        this.label = label;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public ArrayList<Integer> getMemberId() {
        return memberId;
    }

    public String getLabelString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < label.size(); ++i){
            result.append(label.get(i));
            if (i != label.size() -1){
                result.append(',');
            }
        }
        return result.toString();
    }

    public void setMemberId(ArrayList<Integer> memberId) {
        this.memberId = memberId;
    }
    public boolean inLabel(String content) {
        for (String str : label){
            if (content.equals(str)){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", eventName='" + eventName + '\'' +
                ", position='" + position + '\'' +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", founderId=" + founderId +
                ", label=" + label +
                ", picture=" + picture +
                ", shopId=" + shopId +
                ", memberId=" + memberId +
                '}';
    }
}
