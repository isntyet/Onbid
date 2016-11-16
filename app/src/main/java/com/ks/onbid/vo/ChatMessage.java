package com.ks.onbid.vo;

/**
 * Created by jo on 2016-11-16.
 */

public class ChatMessage {
    private String userId;
    private String userNm;
    private String userThumnail;
    private String content;
    private String regDate;

    public ChatMessage() {

    }

    public ChatMessage(String userId, String userNm, String userThumnail, String content, String regDate) {
        this.userId = userId;
        this.userNm = userNm;
        this.userThumnail = userThumnail;
        this.content = content;
        this.regDate = regDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserThumnail() {
        return userThumnail;
    }

    public void setUserThumnail(String userThumnail) {
        this.userThumnail = userThumnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
