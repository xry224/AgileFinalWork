package Bean;

import java.io.Serializable;

public class Message implements Serializable {
    //活动申请人
    private User applicant;
    //申请参与的活动
    private Event wantJoin;
    Message() {

    }

    public Message(User applicant, Event wantJoin) {
        this.applicant = applicant;
        this.wantJoin = wantJoin;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public Event getWantJoin() {
        return wantJoin;
    }

    public void setWantJoin(Event wantJoin) {
        this.wantJoin = wantJoin;
    }
}
