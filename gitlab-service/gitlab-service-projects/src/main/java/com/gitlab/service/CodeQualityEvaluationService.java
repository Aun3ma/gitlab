package com.gitlab.service;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.CodeQualityEvaluation;

import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:CodeQualityEvaluation业务层接口
 * @Date:2020/04/23
 *****/
public interface CodeQualityEvaluationService {

    /***
     * CodeQualityEvaluation多条件分页查询
     * @param codeQualityEvaluation
     * @param page
     * @param size
     * @return
     */
    PageInfo<CodeQualityEvaluation> findPage(CodeQualityEvaluation codeQualityEvaluation, int page, int size);

    /***
     * CodeQualityEvaluation分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<CodeQualityEvaluation> findPage(int page, int size);

    /***
     * CodeQualityEvaluation多条件搜索方法
     * @param codeQualityEvaluation
     * @return
     */
    List<CodeQualityEvaluation> findList(CodeQualityEvaluation codeQualityEvaluation);

    /***
     * 删除CodeQualityEvaluation
     * @param id
     */
    void delete(String id);

    /***
     * 修改CodeQualityEvaluation数据
     * @param codeQualityEvaluation
     */
    void update(CodeQualityEvaluation codeQualityEvaluation);

    /***
     * 新增CodeQualityEvaluation
     * @param codeQualityEvaluation
     */
    void add(CodeQualityEvaluation codeQualityEvaluation);

    /**
     * 根据ID查询CodeQualityEvaluation
     * @param id
     * @return
     */
     CodeQualityEvaluation findById(String id);

    /***
     * 查询所有CodeQualityEvaluation
     * @return
     */
    List<CodeQualityEvaluation> findAll();
}
