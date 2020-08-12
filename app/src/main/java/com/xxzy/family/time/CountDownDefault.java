package com.xxzy.family.time;


public class CountDownDefault implements TimeViewListener{

//    private String[] timeStyle;
//
//    public String[] getTimeStyle() {
//        return timeStyle;
//    }
//
//    public void setTimeStyle(String[] timeStyle) {
//        this.timeStyle = timeStyle;
//    }

    @Override
    public String[] getTextStyle(int[] times) {
//
//        if(timeStyle!=null){
//            return timeStyle;
//        }
//        return  new String[]{times[0] + "", "天", times[1] + "", "小时",times[2] + "", "分", times[3] + "", "秒"};
        return  new String[]{times[1] + "", ":", times[2] + "", ":", times[3] + ""};
    }
}
