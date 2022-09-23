package guestbook.vo;

import java.util.Date;

public class ReplyVO {

    public int getReplySeq() {
        return replySeq;
    }

    public void setReplySeq(int replySeq) {
        this.replySeq = replySeq;
    }

    private int replySeq;
    private String content;
    private Date reg_date;
    private int seq;

    public ReplyVO(){}
    public ReplyVO(int replySeq, String content, Date reg_date, int seq) {
        this.replySeq = replySeq;
        this.content = content;
        this.reg_date = reg_date;
        this.seq = seq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
