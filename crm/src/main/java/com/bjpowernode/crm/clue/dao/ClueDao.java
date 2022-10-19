package com.bjpowernode.crm.clue.dao;

import com.bjpowernode.crm.clue.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ClueDao {
//    List<Clue> search(Map<String, Object> map);
    List<Clue> getClueListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

}
