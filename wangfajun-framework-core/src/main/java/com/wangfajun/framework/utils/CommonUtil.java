package com.wangfajun.framework.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CommonUtil
 * @Description: 公共工具类
 * @Author: miaolei
 * @Date: miaolei 14:14
 */
public class CommonUtil {


    /**
     *
     * <b>将一个大的list分成几个长度length小的list</b>
     *
     * @param list
     * @param length
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int length) {
        List<List<T>> rList = new ArrayList<List<T>>();
        if(list!=null && list.size()>0) {
            int num = list.size()/length + 1;
            int endIndex = length>list.size() ? list.size():length;
            int startIndex = 0;
            for(int i=0; i<num; i++) {
                startIndex = i*length;
                if(startIndex >= list.size()) {
                    break;
                }
                List<T> subList = list.subList(startIndex, endIndex);
                rList.add(subList);
                endIndex = endIndex + length;
                if(endIndex > list.size()) {
                    endIndex = list.size();
                }
            }
        }
        return rList;
    }

}
