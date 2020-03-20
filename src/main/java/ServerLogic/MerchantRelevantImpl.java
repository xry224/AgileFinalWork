package ServerLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.Comment;
import Bean.Merchant;
import DataBase.DataBase;

public class MerchantRelevantImpl {

    private static DataBase db = new DataBase();
    /**
     * @param condition 搜索条件，可以为商家的名称或地点
     * @param size      结果集合的大小，-1表示对大小不做限制
     * @return java.util.ArrayList<Bean.Merchant>
     * @description 根据搜索条件，返回符合条件的商家的集合
     */
    public static ArrayList<Merchant> searchMerchant(String condition, int size) {
        ArrayList<Merchant> merchantList = new ArrayList<Merchant>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select shop_id from merchant where shop_name like ? or position like ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + condition + "%");
            stmt.setString(2, "%" + condition + "%");
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                int shop_id = rs.getInt(1);
                Merchant merchant = new getDataImpl().getMerchant(shop_id);
                merchantList.add(merchant);
                count++;
                if (size == count)
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

    /**
     * @param comment  评论
     * @param merchant 被评价的商家
     * @return boolean
     * @description 对一指定商家进行评价，操作成功返回true，否则返回false
     */
    public static boolean evaluateMerchant(Comment comment, Merchant merchant) {
        ArrayList<Integer> comments = merchant.getCommentList();
        comments.add(comment.getCommentId());
        merchant.setCommentList(comments);
        int row = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "insert into comment(criticId,content,`rank`,positive,negative,merchantId) values(?,?,?,?,?) ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, comment.getCriticId());
            stmt.setString(2, comment.getContent());
            stmt.setInt(3, (int) comment.getRank());
            stmt.setInt(4, comment.getPositive());
            stmt.setInt(5, comment.getNegative());
            stmt.setInt(6, merchant.getShopId());
            row = stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return row > 0;
    }

    /**
     * @param comment     待更新评论
     * @param changeValue 值为正表示评论被赞同，否则表示评论被反对
     * @return boolean
     * @description 用户对评论点赞或点踩，更新评论数据。操作成功返回true，
     * 否则返回false
     */
    public static boolean updateComment(Comment comment, int changeValue) {
        int row = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql;
            if (changeValue > 0) {
                sql = "update comment set positive=? where comment_id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, comment.getPositive() + changeValue);
                stmt.setInt(2, comment.getCommentId());
            } else {
                sql = "update comment set negative=? where comment_id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, comment.getNegative() - changeValue);
                stmt.setInt(2, comment.getCommentId());
            }
            row = stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return row > 0;
    }
}
