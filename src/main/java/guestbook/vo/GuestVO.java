package guestbook.vo;

import java.util.ArrayList;
import java.util.Date;

public class GuestVO {

    private int seq;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    private String title;
    private String content;
    private int readCount;
    private Date date;
    private String userId;

    private ArrayList<ReplyVO> replyList = new ArrayList<ReplyVO>();

    public GuestVO(){}
    public GuestVO(int seq, String userId, String title, String content, Date date, int readCount) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.readCount = readCount;
        this.date = date;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
