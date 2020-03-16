package Bean;

import java.io.Serializable;

public class Message implements Serializable {

    //message ID
    private int messageId;
    //活动申请人的ID
    private int applicantId;
    //申请参与的活动的ID
    private int wantJoinId;

    public Message() {
    }

    public Message(int applicantId, int wantJoinId) {
        this.messageId = messageId;
        this.applicantId = applicantId;
        this.wantJoinId = wantJoinId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getWantJoinId() {
        return wantJoinId;
    }

    public void setWantJoinId(int wantJoinId) {
        this.wantJoinId = wantJoinId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", applicantId=" + applicantId +
                ", wantJoinId=" + wantJoinId +
                '}';
    }
}
