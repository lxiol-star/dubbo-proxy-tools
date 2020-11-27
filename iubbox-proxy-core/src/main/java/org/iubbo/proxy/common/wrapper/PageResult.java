package org.iubbo.proxy.common.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author idea
 * @date 2020/3/1
 * @version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private int page;
    private int pageSize;
    private List<T> resultList;

    /**
     * 未分页前List总数目
     */
    private int totalCount;

    /**
     * 分页总数
     */
    private int totalPage;


    public static <T> PageResult buildPageResult(int page, int pageSize, List<T> data, int totalCount) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPage(page);
        pageResult.setPageSize(pageSize);
        pageResult.setResultList(data);
        pageResult.setTotalCount(totalCount);
        int totalPage = (int) Math.ceil((double) totalCount / (double) pageSize);
        pageResult.setTotalPage(totalPage);
        return pageResult;
    }

}
