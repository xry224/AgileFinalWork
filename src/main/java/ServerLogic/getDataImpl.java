package ServerLogic;

import Bean.*;
import Util.FileGetter;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import DataBase.Invariant;
import DataBase.DataBase;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import com.example.agile.R;

public class getDataImpl {

    private DataBase db = new DataBase();

    private static HashMap<String, Bitmap> picMap = new HashMap<>();

    public static void initPicMap(Context context) {
        picMap.put("gym.jpg", BitmapFactory.decodeResource(context.getResources(), R.drawable.gym));
        picMap.put("yumaoqiu.jpg", BitmapFactory.decodeResource(context.getResources(), R.drawable.yumaoqiu));
        picMap.put("inside.jpg", BitmapFactory.decodeResource(context.getResources(), R.drawable.inside));
    }
    public static Bitmap DrawableToBitmap(Drawable drawable) {
        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();

        drawable.setBounds(0, 0, width, heigh);

        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
    private ArrayList<Bitmap> getBitMaps(String path){
        if (path == null || path.equals("")){
            return null;
        }
        ArrayList<Bitmap> result = new ArrayList<>();
        String[] pathList = path.split(",");
        for (String str : pathList){
            result.add(getBitmap(str));
        }
        return result;
    }
    private Bitmap getBitmap(String path){
        if (path == null || path.equals("")){
            return null;
        }
        String[] result = path.split("/");
        String localFilename = Invariant.rootPath + result[result.length-1];
        Bitmap bitmap = picMap.get(result[result.length-1]);
        if (bitmap != null) {
            //System.out.println(result[result.length-1]);
            return bitmap;
        }
        FileGetter.login();
        File f = FileGetter.copyFile(FileGetter.conn, path, Invariant.rootPath);
        FileGetter.conn.close();
        //System.out.println(f.getAbsolutePath());
        return BitmapFactory.decodeFile(f.getAbsolutePath());
    }
    private ArrayList<Integer> getFounderEvents(int user_id) {
        ArrayList<Integer> eventList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select event_id from event where founderId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                eventList.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return eventList;
    }

    private ArrayList<Integer> getUserComments(int user_id) {
        ArrayList<Integer> comments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select comment_id from comment where criticId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return comments;
    }

    private ArrayList<Integer> getHistoryEvent(int user_id) {
        ArrayList<Integer> historyEvents = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select eventId from user_and_event where userId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                historyEvents.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return historyEvents;
    }
    /**
     * @param id User的ID
     * @return Bean.User
     * @description 根据UserID获取对应的User数据
     */
    public User getUser(int id) {
        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select username,userid,realname,rankscore,hasauthentication,email,phonenumber,userIcon from user where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString(1);
                String userid = rs.getString(2);
                String realname = rs.getString(3);
                int rankscore = rs.getInt(4);
                int hasauthenticatio = rs.getInt(5);
                String email = rs.getString(6);
                String phonenumber = rs.getString(7);
                String userIcon = rs.getString(8);

                user = new User();
                user.setId(id);
                user.setUserName(username);
                user.setUserID(userid);
                user.setRealName(realname);
                user.setRankScore(rankscore);
                user.setHasAuthentication(hasauthenticatio == 1);
                user.setEmail(email);
                user.setPhoneNumber(phonenumber);
                user.setUserIcon(getBitmap(userIcon));
                user.setEventList(getFounderEvents(id));
                user.setComments(getUserComments(id));
                user.setHistoryEvent(getHistoryEvent(id));
                user.setMessageBox(new ArrayList<Integer>());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return user;
    }
    /**
     * @param idList ID的集合
     * @return Bean.User
     * @description 根据UserID获取对应的User数据
     */
    public ArrayList<User> getUser(ArrayList<Integer> idList) {
        ArrayList<User> users = new ArrayList<>();
        for (int id : idList) {
            User user = getUser(id);
            users.add(user);
        }
        return users;
    }

    private ArrayList<Integer> getMerchantComments(int merchantId) {
        ArrayList<Integer> comments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select comment_id from comment where merchantId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, merchantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return comments;
    }

    public Merchant getMerchant(int shopId) {
        Merchant merchant = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select shop_name,imageList,position,mainbusiness,description,titleImage,`rank` from merchant where shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String shopname = rs.getString(1);
                String imageList = rs.getString(2);
                String position = rs.getString(3);
                String mainbusiness = rs.getString(4);
                String description = rs.getString(5);
                String titleImage = rs.getString(6);
                double rank = rs.getDouble(7);

                merchant = new Merchant();
                merchant.setShopId(shopId);
                merchant.setShopName(shopname);
                merchant.setImageList(getBitMaps(imageList));
                merchant.setPosition(position);
                ArrayList<String> mainbussinessList = new ArrayList<>();
                mainbussinessList.add(mainbusiness);
                merchant.setMainBusiness(mainbussinessList);
                merchant.setDescription(description);
                merchant.setTitleImage(getBitmap(titleImage));
                merchant.setRank(rank);
                merchant.setCommentList(getMerchantComments(shopId));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return merchant;
    }

    public ArrayList<Merchant> getMerchant(ArrayList<Integer> shopIdList) {
        ArrayList<Merchant> merchants = new ArrayList<>();
        for (int shopId : shopIdList) {
            Merchant merchant = getMerchant(shopId);
            merchants.add(merchant);
        }
        return merchants;
    }

    private ArrayList<Integer> getMembers(int eventId) {
        ArrayList<Integer> members = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select userId from user_and_event where eventId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                members.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return members;
    }

    public Event getEvent(int eventId) {
        Event event = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select event_name,position,time,description,founderId,label,picture,merchantId from event where event_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String eventName = rs.getString(1);
                String position = rs.getString(2);
                Date time = rs.getDate(3);
                String description = rs.getString(4);
                int founderId = rs.getInt(5);
                String label = rs.getString(6);
                String image = rs.getString(7);
                int merchantId = rs.getInt(8);
                event = new Event();
                if (label != null) {
                    String[] result = label.split(",");
                    ArrayList<String> labels = new ArrayList<>(Arrays.asList(result));
                    event.setLabel(labels);
                }
                event.setEventID(eventId);
                event.setEventName(eventName);
                event.setPosition(position);
                event.setTime(time);
                event.setDescription(description);
                event.setFounderId(founderId);
                event.setPicture(getBitmap(image));
                event.setShopId(merchantId);
                event.setMemberId(getMembers(eventId));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return event;
    }
    /**
     * @description  获取size大小的EventList
     * @param size   size的大小
     * @return java.util.ArrayList<Bean.Event>
     */
    public ArrayList<Event> getEventList(int size) {
        ArrayList<Event> eventList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select event_id,event_name,position,time,description,founderId,label,picture,merchantId from event";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int eventId = rs.getInt(1);
                String eventName = rs.getString(2);
                String position = rs.getString(3);
                Date time = rs.getDate(4);
                String description = rs.getString(5);
                int founderId = rs.getInt(6);
                String label = rs.getString(7);
                String image = rs.getString(8);
                int merchantId = rs.getInt(9);

                Event event = new Event();
                if (label != null) {
                    String[] result = label.split(",");
                    ArrayList<String> labels = new ArrayList<>(Arrays.asList(result));
                    event.setLabel(labels);
                }
                event.setEventID(eventId);
                event.setEventName(eventName);
                event.setPosition(position);
                event.setTime(time);
                event.setDescription(description);
                event.setFounderId(founderId);
                event.setPicture(getBitmap(image));
                event.setShopId(merchantId);
                event.setMemberId(getMembers(eventId));

                eventList.add(event);
                if ((size--) == 0)
                    break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);
        } finally {
            db.closeConnection(stmt, conn);
        }
        return eventList;
    }
    public ArrayList<Event> getEvent(ArrayList<Integer> eventIdList) {
        ArrayList<Event> events = new ArrayList<>();
        for (int eventId : eventIdList) {
            Event event = getEvent(eventId);
            events.add(event);
        }
        return events;
    }

    private Comment getComment(int commentId) {
        Comment comment = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select criticId,content,`rank`,replayId,positive,negative,merchantId from comment where comment_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, commentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt(1);
                String content = rs.getString(2);
                double rank = rs.getDouble(3);
                int replay_id = rs.getInt(4);
                int positive = rs.getInt(5);
                int negative = rs.getInt(6);
                int merchant_id = rs.getInt(7);

                comment = new Comment();
                comment.setCommentId(commentId);
                comment.setCriticId(user_id);
                comment.setContent(content);
                comment.setRank(rank);
                comment.setPositive(positive);
                comment.setNegative(negative);
                comment.setMerchantId(merchant_id);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return comment;
    }

    public ArrayList<Comment> getComment(ArrayList<Integer> commentIdList) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (int commentId : commentIdList) {
            Comment comment = getComment(commentId);
            comments.add(comment);
        }
        return comments;
    }
    public ArrayList<Merchant> getMerchantList(int size) {
        ArrayList<Merchant> merchantList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select shop_id,shop_name,imageList,position,mainbusiness,description,titleImage,`rank` from merchant";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int shopId=rs.getInt(1);
                String shopname = rs.getString(2);
                String imageList = rs.getString(3);
                String position = rs.getString(4);
                String mainbusiness = rs.getString(5);
                String description = rs.getString(6);
                String titleImage = rs.getString(7);
                double rank = rs.getDouble(8);

                Merchant merchant = new Merchant();
                merchant.setShopId(shopId);
                merchant.setShopName(shopname);
                merchant.setImageList(getBitMaps(imageList));
                merchant.setPosition(position);
                ArrayList<String> mainbussinessList = new ArrayList<>();
                mainbussinessList.add(mainbusiness);
                merchant.setMainBusiness(mainbussinessList);
                merchant.setDescription(description);
                merchant.setTitleImage(getBitmap(titleImage));
                merchant.setRank(rank);
                merchant.setCommentList(getMerchantComments(shopId));

                merchantList.add(merchant);
                if ((size--) == 0)
                    break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return merchantList;
    }
    public Message getMessage(int messageId) {
        Message message = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select applicant,wantJoin from message where id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, messageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int applicant = rs.getInt(1);
                int wantJoin = rs.getInt(2);

                message = new Message();
                message.setMessageId(messageId);
                message.setApplicantId(applicant);
                message.setWantJoinId(wantJoin);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return message;
    }

    public ArrayList<Message> getMessage(ArrayList<Integer> messageIdList) {
        ArrayList<Message> messages = new ArrayList<>();
        for (int commentId : messageIdList) {
            Message message = getMessage(commentId);
            messages.add(message);
        }
        return messages;
    }
}
