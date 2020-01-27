package entity;

import java.util.ArrayList;
/*
    对商店的评论类
 */
public class Comment {
    //评论者
    private User critic;
    //评论内容
    private String content;
    //评分
    private int rank;
    //暂不考虑对评论的回复
    private ArrayList<String> replay;
    //点赞数
    private int positive;
    //点踩数
    private int negative;

    public Comment(String content, int rank, int positive, int negative, User user) {
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
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
}
