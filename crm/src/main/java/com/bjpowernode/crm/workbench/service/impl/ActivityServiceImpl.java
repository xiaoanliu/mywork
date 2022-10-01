package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

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

}

















