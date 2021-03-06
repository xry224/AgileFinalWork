package Bean;

import java.io.Serializable;
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
    //点赞数
    private int positive;
    //点踩数
    private int negative;
    //商家id
    private int merchantId;

    public Comment() {
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
                ", positive=" + positive +
                ", negative=" + negative +
                ", merchantId=" + merchantId +
                '}';
    }
}
