package com.bjpowernode.crm.vo;

import java.util.List;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.vo.PaginationVO
 * @Date: 2022年10月01日 14:37
 * @Description:
 */
public class PaginationVO<T> {
    private  int total;
    private List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


}
