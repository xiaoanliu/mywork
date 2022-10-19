package com.bjpowernode.crm.clue.service;

import com.bjpowernode.crm.clue.domain.Clue;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.HashMap;
import java.util.Map;

public interface ClueService {
    PaginationVO<Clue> search(Map<String,Object> map);
}
