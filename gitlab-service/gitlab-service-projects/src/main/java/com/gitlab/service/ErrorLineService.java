package com.gitlab.service;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.ErrorLine;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
/****
 * @Author:fuyunkai
 * @Description:ErrorLine业务层接口
 * @Date:2020/05/19
 *****/
public interface ErrorLineService {

    /***
     * ErrorLine多条件分页查询
     * @param errorLine
     * @param page
     * @param size
     * @return
     */
    PageInfo<ErrorLine> findPage(ErrorLine errorLine, int page, int size);

    /***
     * ErrorLine分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<ErrorLine> findPage(int page, int size);

    /***
     * ErrorLine多条件搜索方法
     * @param errorLine
     * @return
     */
    List<ErrorLine> findList(ErrorLine errorLine);

    /***
     * 删除ErrorLine
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改ErrorLine数据
     * @param errorLine
     */
    void update(ErrorLine errorLine);

    /***
     * 新增ErrorLine
     * @param errorLine
     */
    void add(ErrorLine errorLine);

    /**
     * 根据ID查询ErrorLine
     * @param id
     * @return
     */
     ErrorLine findById(Integer id);

    /***
     * 查询所有ErrorLine
     * @return
     */
    List<ErrorLine> findAll();

    /***
     * ErrorLine根据是否为有缺陷查询
     * @param task_id
     * @return
     */
    List<ErrorLine> findErrorLineByPre(String task_id);

    /***
     * ErrorLine根据是否为无缺陷查询
     * @param task_id
     * @return
     */
    List<ErrorLine> findErrorLineByNoPre(String task_id);
}
