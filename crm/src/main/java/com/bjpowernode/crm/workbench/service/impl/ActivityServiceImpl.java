package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.service.ActivityService;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl
 * @Date: 2022年09月27日 23:13
 * @Description:
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao =SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


}
