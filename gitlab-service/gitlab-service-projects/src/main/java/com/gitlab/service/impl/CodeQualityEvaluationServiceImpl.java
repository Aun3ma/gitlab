package com.gitlab.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.dao.CodeQualityEvaluationMapper;
import com.gitlab.projects.pojo.CodeQualityEvaluation;
import com.gitlab.projects.pojo.ProjectInformation;
import com.gitlab.service.CodeQualityEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:CodeQualityEvaluation业务层接口实现类
 * @Date:2020/04/23
 *****/
@Service
public class CodeQualityEvaluationServiceImpl implements CodeQualityEvaluationService {

    @Autowired
    private CodeQualityEvaluationMapper codeQualityEvaluationMapper;


    /**
     * CodeQualityEvaluation条件+分页查询
     * @param codeQualityEvaluation 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<CodeQualityEvaluation> findPage(CodeQualityEvaluation codeQualityEvaluation, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(codeQualityEvaluation);
        //执行搜索
        return new PageInfo<CodeQualityEvaluation>(codeQualityEvaluationMapper.selectByExample(example));
    }

    /**
     * CodeQualityEvaluation分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<CodeQualityEvaluation> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<CodeQualityEvaluation>(codeQualityEvaluationMapper.selectAll());
    }

    /**
     * CodeQualityEvaluation条件查询
     * @param codeQualityEvaluation
     * @return
     */
    @Override
    public List<CodeQualityEvaluation> findList(CodeQualityEvaluation codeQualityEvaluation) {
        //构建查询条件
        Example example = createExample(codeQualityEvaluation);
        //根据构建的条件查询数据
        return codeQualityEvaluationMapper.selectByExample(example);
    }


    /***
     * 查询所有CodeQualityEvaluation
     * @return
     */
    @Override
    public List<CodeQualityEvaluation> findByUserID(String user_id){
        CodeQualityEvaluation codeQualityEvaluation = new CodeQualityEvaluation();
        codeQualityEvaluation.setUserId(user_id);
        return findList(codeQualityEvaluation);

    }

    /**
     * CodeQualityEvaluation构建查询对象
     * @param codeQualityEvaluation
     * @return
     */
    public Example createExample(CodeQualityEvaluation codeQualityEvaluation) {
        Example example = new Example(CodeQualityEvaluation.class);
        Example.Criteria criteria = example.createCriteria();
        if(codeQualityEvaluation != null) {
            criteria.andEqualTo("userId",codeQualityEvaluation.getUserId());
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        codeQualityEvaluationMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改CodeQualityEvaluation
     * @param codeQualityEvaluation
     */
    @Override
    public void update(CodeQualityEvaluation codeQualityEvaluation) {
        codeQualityEvaluationMapper.updateByPrimaryKey(codeQualityEvaluation);
    }

    /**
     * 增加CodeQualityEvaluation
     * @param codeQualityEvaluation
     */
    @Override
    public void add(CodeQualityEvaluation codeQualityEvaluation) {
        codeQualityEvaluationMapper.insert(codeQualityEvaluation);
    }

    /**
     * 根据ID查询CodeQualityEvaluation
     * @param id
     * @return
     */
    @Override
    public CodeQualityEvaluation findById(String id) {
        return  codeQualityEvaluationMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询CodeQualityEvaluation全部数据
     * @return
     */
    @Override
    public List<CodeQualityEvaluation> findAll() {
        return codeQualityEvaluationMapper.selectAll();
    }

    /***
     * 修改CodeQualityEvaluation的state
     */
    @Override
    public boolean updateById(String task_id , int state){
        CodeQualityEvaluation  codeQualityEvaluation = codeQualityEvaluationMapper.selectByPrimaryKey(task_id);
        if(codeQualityEvaluation != null){
            codeQualityEvaluation.setTaskState(state);
            update(codeQualityEvaluation);
            return true;
        }
        return false;
    }
}
