package com.ks.onbid.vo;

/**
 * Created by jo on 2016-11-15.
 */

public class Comment {
    //댓글 고유 키
    private String key;

    //상위 게시물(물건 목록)의 공매번호 + 공고번호
    private String category;

    //물건관리번호
    private String cltrMnmtNo;

    //카카오 닉네임
    private String userName;

    //카카오 썸네일
    private String userThumnail;

    //카카오 유저번호
    private String userId;

    //내용
    private String content;

    //등록일
    private String regDate;

    public Comment() {

    }

    public Comment(String category, String cltrMnmtNo, String userName, String userThumnail, String userId, String content, String regDate) {
        this.category = category;
        this.cltrMnmtNo = cltrMnmtNo;
        this.userName = userName;
        this.userThumnail = userThumnail;
        this.userId = userId;
        this.content = content;
        this.regDate = regDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCltrMnmtNo() {
        return cltrMnmtNo;
    }

    public void setCltrMnmtNo(String cltrMnmtNo) {
        this.cltrMnmtNo = cltrMnmtNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserThumnail() {
        return userThumnail;
    }

    public void setUserThumnail(String userThumnail) {
        this.userThumnail = userThumnail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
