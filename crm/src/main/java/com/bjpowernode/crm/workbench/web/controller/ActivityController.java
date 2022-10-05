package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

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
        //getServletPath():获取能够与“url-pattern”中匹配的路径，注意是完全匹配的部分，*的部分不包括。
        String path = request.getServletPath();
        System.out.println("path================="+path);
        //if 判断中equals前面的路径开头有/=====
//        System.out.println(path);
        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request, response);

        } else if ("/workbench/activity/save.do".equals(path)) {
            save(request,response);
        } else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)) {
            delete(request,response);
        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)) {
            getUserListAndActivity(request,response);
        }else if ("/workbench/activity/update.do".equals(path)) {
            update(request,response);
        }

    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入修改操作");

        String id=request.getParameter("id");
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");
        //创建时间为当前系统时间
        String editTime= DateTimeUtil.getSysTime();
        //创建人为当前用户（从session中获取）
        String editBy=((User)request.getSession().getAttribute("user")).getName();

        Activity a=new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.update(a);

        PrintJson.printJsonFlag(response,flag);


    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("查询加修改");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map=as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);


    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除信息列表操作");
        String ids[]=request.getParameterValues("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.delete(ids);
        PrintJson.printJsonFlag(response,flag);


    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询信息列表的操作");
        String name=request.getParameter("name");
        String owner=request.getParameter("owner");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String pageNoStr=request.getParameter("pageNo");
        int pageNo=Integer.valueOf(pageNoStr);
        String pageSizeStr=request.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizeStr);
        //分页查询
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //前端需要的信息。。
        PaginationVO<Activity> vo=as.pageList(map);
        System.out.println("vo============="+vo);
        PrintJson.printJsonObj(response,vo);
        System.out.println("vo============="+vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的添加操作");
        String id= UUIDUtil.getUUID();
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");
        //创建时间为当前系统时间
        String createTime= DateTimeUtil.getSysTime();
        //创建人为当前用户（从session中获取）
        String createBy=((User)request.getSession().getAttribute("user")).getName();

        Activity a=new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.save(a);

        PrintJson.printJsonFlag(response,flag);
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
