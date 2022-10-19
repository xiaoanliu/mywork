package com.bjpowernode.crm.clue.web.controller;

import com.bjpowernode.crm.clue.domain.Clue;
import com.bjpowernode.crm.clue.service.ClueService;
import com.bjpowernode.crm.clue.service.impl.ClueServiceImpl;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.vo.PaginationVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.clue.web.controller.ClueController
 * @Date: 2022年10月19日 19:07
 * @Description:
 */
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        if ("/workbench/clue/search.do".equals(path)){
            search(req,resp);
        }
    }
    private void search(HttpServletRequest request,HttpServletResponse response){
        System.out.println("进入search方法");
        System.out.println("company================"+request.getParameter("company"));
        String fullname=request.getParameter("fullname");
        String company=request.getParameter("company");
        String phone=request.getParameter("phone");
        String mphone=request.getParameter("mphone");
        String source=request.getParameter("source");
        String owner=request.getParameter("owner");
        String state=request.getParameter("state");
        String pagestr=request.getParameter("pageNo");
        int pageNo=Integer.valueOf(pagestr);
        String pageSizestr=request.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizestr);
        int skipCount=(pageNo-1)*pageSize;

        //将查询条件放入map集合中
        Map<String,Object> map=new HashMap<String,Object>();
        //Map<String,Object> map=new HashMap<String,Object>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("mphone",mphone);
        map.put("source",source);
        map.put("owner",owner);
        map.put("state",state);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        PaginationVO<Clue> vo=cs.search(map);
        System.out.println("vo============"+vo);
        PrintJson.printJsonObj(response,vo);
        System.out.println("search结束");


    }
}
