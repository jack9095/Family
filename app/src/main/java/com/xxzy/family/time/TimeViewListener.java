package com.xxzy.family.time;



public interface TimeViewListener {

    default String[] getTextStyle(int[] times){
         return  new String[]{times[1] + "", ":", times[2] + "", ":", times[3] + ""};
    }

}
