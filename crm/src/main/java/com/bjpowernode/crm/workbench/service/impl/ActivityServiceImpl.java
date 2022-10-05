package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl
 * @Date: 2022年09月27日 23:13
 * @Description:
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao =SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao =SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao =SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity a) {
        System.out.println("进入serviceimpl的save方法");
        boolean flag=true;
        int count=activityDao.save(a);
        if (count!=1){
            flag=false;
        }


        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        //取得total
        int total=activityDao.getTotalByCondition(map);

        //取得datalist
        List<Activity> dataList=activityDao.getActivityListByCondition(map);


        //封装到vo中
        PaginationVO<Activity> vo=new PaginationVO<Activity>();
        vo.setDataList(dataList);
        vo.setTotal(total);


        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag=true;

        //查询需要删除的数量
        int count1=activityRemarkDao.getCountByAids(ids);

        //删除备注就，返回受影响的条数（实际删除数量）
        int count2=activityRemarkDao.deleteByAids(ids);
        if(count1!=count2){
            flag=false;
        }

        //删除活动信息
        int count3=activityDao.delete(ids);
        if (count3!=ids.length){
            flag=false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        //取list
        List<User> ulist=userDao.getUserList();

        //取对象
        Activity a=activityDao.getById(id);

        //放入map集合
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("ulist",ulist);
        map.put("a",a);


        return map;
    }

    @Override
    public boolean update(Activity a) {
        System.out.println("进入serviceimpl的save方法");
        boolean flag=true;
        int count=activityDao.updete(a);
        if (count!=1){
            flag=false;
        }


        return flag;
    }

}

















