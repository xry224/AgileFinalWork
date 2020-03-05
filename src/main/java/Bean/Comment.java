package Bean;

import java.io.Serializable;
import java.util.ArrayList;
/*
    对商店的评论类
 */
public class Comment implements Serializable {
    //用于区分评论的Id，可视情况修改为其他属性的组合
    private String commentId;
    //评论者
    private User critic;
    //评论内容
    private String content;
    //评分
    private double rank;
    //对评论的回复
    //暂不考虑对评论的回复
    private ArrayList<String> replay;
    //点赞数
    private int positive;
    //点踩数
    private int negative;

    public Comment(String content, double rank, int positive, int negative, User user) {
        this.content = content;
        this.rank = rank;
        this.positive = positive;
        this.negative = negative;
        critic = user;
    }

    public User getCritic() {
        return critic;
    }

    public Comment(String content, int rank, User user) {
        this.content = content;
        this.rank = rank;
        critic = user;
        positive = negative = 0;
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

    public String getCommentId() {
        return commentId;
    }
}
