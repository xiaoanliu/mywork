package com.bjpowernode.crm.clue.service.impl;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.bjpowernode.crm.clue.dao.ClueDao;
import com.bjpowernode.crm.clue.domain.Clue;
import com.bjpowernode.crm.clue.service.ClueService;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;


import java.util.List;
import java.util.Map;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.clue.service.impl.ClueServiceImpl
 * @Date: 2022年10月18日 21:53
 * @Description:
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    @Override
    public PaginationVO<Clue> search(Map<String, Object> map) {

        int total=clueDao.getTotalByCondition(map);
        System.out.println("total========="+total);
        List<Clue> dataList=clueDao.getClueListByCondition(map);



        PaginationVO<Clue> vo=new PaginationVO<Clue>();
        vo.setDataList(dataList);


        vo.setTotal(total);
        return vo;
    }
}
