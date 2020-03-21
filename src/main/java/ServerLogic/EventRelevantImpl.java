package ServerLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import Bean.Event;
import Bean.Message;
import Bean.User;
import DataBase.DataBase;
import DataBase.DataCenter;

public class EventRelevantImpl {

    private static DataBase db = new DataBase();
    /**
     * @param size 返回列表的大小，-1表示不限制大小
     * @return java.util.ArrayList<Bean.Event>
     * @description 获取所有的已发布的活动。通过size参数限制返回的活动数量
     * 返回Event对象的的集合
     */
    public static ArrayList<Event> getTotalEventList(int size) {
        ArrayList<Event> eventList = new ArrayList<Event>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select event_id from event";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                int eventId = rs.getInt(1);
                Event event = new getDataImpl().getEvent(eventId);
                eventList.add(event);
                count++;
                if (count == size)
                    break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return eventList;
    }
    /**
     * @param condition 搜索条件，可能是活动的名称或活动的标签
     * @param size      结果集合大小，-1表示不限制大小
     * @return java.util.ArrayList<Bean.Event>
     * @description 根据搜索条件搜索活动
     */
    public static ArrayList<Event> searchEvent(String condition, int size) {
        ArrayList<Event> result = new ArrayList<>(searchEventByLabel(condition, size));
        result.addAll(searchEventByName(condition, size));
        return result;
    }

    /**
     * @param condition 搜索条件：活动的标签
     * @param size      结果集合大小，-1表示不限制大小
     * @return java.util.ArrayList<Bean.Event>
     * @description 根据活动的标签搜索活动，返回Event对象的的集合
     */
    public static ArrayList<Event> searchEventByLabel(String condition, int size) {
        ArrayList<Event> eventList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select event_id from event where label like \"%\"?\"%\"";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, condition);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                int eventId = rs.getInt(1);
                Event event = new getDataImpl().getEvent(eventId);
                eventList.add(event);
                count++;
                if (count == size)
                    break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return eventList;
    }
    /**
     * @param condition 搜索条件：活动的名称
     * @param size      结果集合大小，-1表示不限制大小
     * @return java.util.ArrayList<Bean.Event>
     * @description 根据活动的名称搜索活动，返回Event对象的的集合
     */
    public static ArrayList<Event> searchEventByName(String condition, int size) {
        ArrayList<Event> eventList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select event_id from event where event_name=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, condition);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                int eventId = rs.getInt(1);
                Event event = new getDataImpl().getEvent(eventId);
                eventList.add(event);
                count++;
                if (count == size)
                    break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return eventList;
    }
    /**
     * @param applicant 申请人
     * @param applyFor  申请加入的活动
     * @return boolean
     * @description 用户申请加入一项活动。申请操作成功返回true，否则返回false
     * 具体操作为新建一个消息发送给活动发起人，根据审核结果决定该用户是否能加入该活动
     */
    public static boolean applyToJoin(User applicant, Event applyFor) {
        sendMessage(new getDataImpl().getUser(applyFor.getFounderId()), new Message(applicant.getId(), applyFor.getEventID()));
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "insert into message(`applicant`,`wantJoin`) values (?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, applicant.getId());
            stmt.setInt(2, applyFor.getEventID());
            stmt.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return true;
    }
    /**
     * @param receiver 消息接收人
     * @param message  待接收消息
     * @return boolean
     * @description 向一指定用户发送消息，发送成功返回true，否则返回false
     */
    public static boolean sendMessage(User receiver, Message message) {
        ArrayList<Integer> messages = receiver.getMessageBox();
        messages.add(message.getMessageId());
        receiver.setMessageBox(messages);
        return true;
    }

    /**
     * @param message     消息
     * @param user        消息接收者
     * @param acceptOrNor 为true则同意加入，否则拒绝为加入
     * @return boolean
     * @description 处理申请消息，根据@acceptOrNot参数决定接收申请与否。
     * 无论结果如何，处理结束后将消息移除接收人的消息列表。
     * 如果结果为接收，将申请者加入申请的活动中。处理成功返回true，否则返回false
     */
    public static boolean handleMessage(Message message, User user, boolean acceptOrNor) {
        int wantJoinId = message.getWantJoinId();
        int applicantId = message.getApplicantId();
        boolean insertUserEvent = true;
        if (acceptOrNor) {
            //申请者的历史记录+1
            //无用代码？？
            User applicant = new getDataImpl().getUser(applicantId);
            ArrayList<Integer> historyEvent = applicant.getHistoryEvent();
            historyEvent.add(wantJoinId);
            applicant.setHistoryEvent(historyEvent);
            //Event的成员+1
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                //添加Event与User的关系
                conn = db.getConnection();
                String sql = "insert into user_and_event(userId,eventId) values(?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, applicantId);
                stmt.setInt(2, wantJoinId);
                int row = stmt.executeUpdate();
                if (row <= 0)
                    insertUserEvent = false;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace(System.out);;
            } finally {
                db.closeConnection(stmt, conn);
            }
            DataCenter.currentMainEventList = new getDataImpl().getEventList(DataCenter.expectedEventListSize);
        }
        //移出消息
        boolean messageRemovedOrNot = false;
        ArrayList<Integer> messages = user.getMessageBox();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) == message.getMessageId()) {
                user.getMessageBox().remove(i);
                messageRemovedOrNot = true;
                break;
            }
        }
        //移除消息
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "delete from message where applicant=? and wantJoin=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, applicantId);
            stmt.setInt(2, wantJoinId);
            stmt.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }

        return insertUserEvent && messageRemovedOrNot;
    }
    public static int getEventCount() {
        int size = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select count(*) from Event";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return size;
    }

    /**
     * @param creator 活动创始人
     * @param event   创建的活动
     * @return boolean
     * @description 用户创建新的活动。创建成功返回true，否则返回false
     */
    public static boolean releaseEvent(User creator, Event event) {
        boolean userSucc = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "insert into user_and_event(userId,eventId) values(?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, creator.getId());
            stmt.setInt(2, event.getEventID());
            int row = stmt.executeUpdate();
            if (row > 0)
                userSucc = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        boolean eventSucc = false;
        event.setFounderId(creator.getId());
        stmt = null;
        try {
            conn = db.getConnection();
            String sql = "insert into event(event_name,position,time,description,founderId,label,merchantId,event_id) values (?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, event.getEventName());
            stmt.setString(2, event.getPosition());
            stmt.setTime(3, (Time) event.getTime());
            stmt.setString(4, event.getDescription());
            stmt.setInt(5, creator.getId());
            stmt.setString(6, event.getLabel().toString());
            stmt.setInt(7, event.getShopId());
            stmt.setInt(8, event.getEventID());
            int row = stmt.executeUpdate();
            if (row > 0)
                eventSucc = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return userSucc && eventSucc;
    }
}
