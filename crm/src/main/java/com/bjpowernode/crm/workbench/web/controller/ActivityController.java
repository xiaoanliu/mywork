package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.settings.web.controller.ActivityController
 * @Date: 2022年09月21日 21:20
 * @Description:
 */
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动控制器");

        //拿到web.xml文件中的<url-pattern>/settings/user/login.do</url-pattern>中的内容
        String path = request.getServletPath();
        //if 判断中equals前面的路径开头有/=====
//        System.out.println(path);
        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request, response);

        } else if ("/workbench/activity/xxx.do".equals(path)) {


        }

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList= us.getUserList();
        //list集合转换成json数组
        PrintJson.printJsonObj(response,uList);


    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("登录验证");
        //接受页面传递的参数
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        System.out.println("接受页面传递的参数");
        //接受ip地址
        String ip = request.getRemoteAddr();
        System.out.println("========ip+" + ip);

        //统一使用代理形态代替impl
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());


        try {

            User user = us.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            PrintJson.printJsonFlag(response, true);


        } catch (Exception e) {
            e.printStackTrace();

            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            map.put("msg", msg);
            System.out.println("------------------" + map.get("msg"));
            System.out.println("------------------" + map.get("success"));
            PrintJson.printJsonObj(response, map);

        }

    }
}
