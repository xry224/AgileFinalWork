package Bean;

import java.io.Serializable;
import java.util.ArrayList;

/*
    对商店的评论类
 */
public class Comment implements Serializable {

    //用于区分评论的Id，可视情况修改为其他属性的组合
    private int commentId;
    //评论者ID
    private int criticId;
    //评论内容
    private String content;
    //评分
    private double rank;
    //对评论的回复
    //暂不考虑对评论的回复
    private ArrayList<String> reply;
    //点赞数
    private int positive;
    //点踩数
    private int negative;
    //商家id
    private int merchantId;

    public Comment() {
    }

    public Comment(int commentId, int criticId, String content, double rank, ArrayList<String> reply, int positive, int negative, int merchantId) {
        this.commentId = commentId;
        this.criticId = criticId;
        this.content = content;
        this.rank = rank;
        this.reply = reply;
        this.positive = positive;
        this.negative = negative;
        this.merchantId = merchantId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCriticId() {
        return criticId;
    }

    public void setCriticId(int criticId) {
        this.criticId = criticId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public ArrayList<String> getReply() {
        return reply;
    }

    public void setReply(ArrayList<String> reply) {
        this.reply = reply;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", criticId=" + criticId +
                ", content='" + content + '\'' +
                ", rank=" + rank +
                ", reply=" + reply +
                ", positive=" + positive +
                ", negative=" + negative +
                ", merchantId=" + merchantId +
                '}';
    }
}
