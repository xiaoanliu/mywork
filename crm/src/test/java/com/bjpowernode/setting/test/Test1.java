package com.bjpowernode.setting.test;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.setting.test.Test1
 * @Date: 2022年09月21日 23:22
 * @Description:
 */
public class Test1 {

    public static void main(String[] args) {

//        String expiertime="2022-08-12 10:10:10";
//
//        String currenttime= DateTimeUtil.getSysTime();
//
//        int count=expiertime.compareTo(currenttime);

//          String lockstate="0";
//          if ("0".equals(lockstate)){
//              System.out.println("账号锁定");
//          }
//            String pwd ="123";
//             pwd =MD5Util.getMD5(pwd);
//          System.out.println(pwd);
            String ip="192.168.1.1";
            String allowIps="192.168.1.1,192.168.1.2";
            if (allowIps.contains(ip)){
                System.out.println("有效ip");

            }else{
                System.out.println("无效ip");
            };








    }
}
