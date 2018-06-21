package com.example.administrator.testcustomview.radar;

/**
 * Created by Administrator on 2018/6/14.
 */

public class LeanRecordBean {

    String day;
    String xinqi;
    int value;
    Boolean videoFlag =false;
    Boolean questionFlag =false;
    Boolean liveFlag=false;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getXinqi() {
        return xinqi;
    }

    public void setXinqi(String xinqi) {
        this.xinqi = xinqi;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Boolean getVideoFlag() {
        return videoFlag;
    }

    public void setVideoFlag(Boolean videoFlag) {
        this.videoFlag = videoFlag;
    }

    public Boolean getQuestionFlag() {
        return questionFlag;
    }

    public void setQuestionFlag(Boolean questionFlag) {
        this.questionFlag = questionFlag;
    }

    public Boolean getLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(Boolean liveFlag) {
        this.liveFlag = liveFlag;
    }
}


