package com.xxzy.family.time;



public class ToolDate {
    /**
     * 根据秒数获取日时分秒
     *
     * @param second 秒数
     * @return 返回大小为4的int数组 int[0]-天 int[1]-时 int[2]-分 int[3]-秒
     */
   public static int[] getDayHourMinSecond(long second) {
        int numDay = 60 * 60 * 24;
        // 日
        int timeDay = (int) (second / numDay);
//        int[] srcArr = getHourMinSecond(second % numDay); // 限制 时分秒 是一天内的，也就是不超过24小时
        int[] srcArr = getHourMinSecond(second); // 不限制
        int[] timeArray = new int[4];
        timeArray[0] = timeDay;
        System.arraycopy(srcArr, 0, timeArray, 1, 3);
        return timeArray;
    }
    /**
     * 根据秒数获取时分秒
     *
     * @param second 秒数
     * @return 返回大小为3的int数组 int[0]-时 int[1]-分 int[2]-秒
     */
    public static int[] getHourMinSecond(long second) {
        int numHour = 60 * 60;
        long timeMin = second % numHour;
        return new int[]{
                // 时
                (int) (second / numHour)
                // 分
                , (int) (timeMin / 60)
                // 秒
                , (int) (timeMin % 60)};
    }

}
