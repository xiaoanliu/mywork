package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.workbench.service.ActivityServiceImpl
 * @Date: 2022年09月27日 23:13
 * @Description:
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao =SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


}
