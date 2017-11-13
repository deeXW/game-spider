package com.zdf.dto.base;

/**
 * Created by zhongdifeng on 2017/9/5.
 */
public class AbstractPageParamDto {
    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 分页数
     */
    private Integer pageSize;

    public Integer getPageNo() {
        if (null == pageNo || pageNo <= 0) {
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize == null ? 1 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
