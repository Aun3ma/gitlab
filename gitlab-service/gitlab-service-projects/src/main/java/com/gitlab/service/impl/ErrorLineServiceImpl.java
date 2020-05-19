package com.gitlab.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.dao.ErrorLineMapper;
import com.gitlab.projects.pojo.ErrorLine;
import com.gitlab.service.ErrorLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:fuyunkai
 * @Description:ErrorLine业务层接口实现类
 * @Date:2020/05/19
 *****/
@Service
public class ErrorLineServiceImpl implements ErrorLineService {

    @Autowired
    private ErrorLineMapper errorLineMapper;


    /**
     * ErrorLine条件+分页查询
     * @param errorLine 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ErrorLine> findPage(ErrorLine errorLine, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(errorLine);
        //执行搜索
        return new PageInfo<ErrorLine>(errorLineMapper.selectByExample(example));
    }

    /**
     * ErrorLine分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<ErrorLine> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<ErrorLine>(errorLineMapper.selectAll());
    }

    /**
     * ErrorLine条件查询
     * @param errorLine
     * @return
     */
    @Override
    public List<ErrorLine> findList(ErrorLine errorLine) {
        //构建查询条件
        Example example = createExample(errorLine);
        //根据构建的条件查询数据
        return errorLineMapper.selectByExample(example);
    }


    /**
     * ErrorLine构建查询对象
     * @param errorLine
     * @return
     */
    public Example createExample(ErrorLine errorLine) {
        Example example = new Example(ErrorLine.class);
        Example.Criteria criteria = example.createCriteria();
        if(errorLine != null) {
            // write it yourself
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id) {
        errorLineMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改ErrorLine
     * @param errorLine
     */
    @Override
    public void update(ErrorLine errorLine) {
        errorLineMapper.updateByPrimaryKey(errorLine);
    }

    /**
     * 增加ErrorLine
     * @param errorLine
     */
    @Override
    public void add(ErrorLine errorLine) {
        errorLineMapper.insert(errorLine);
    }

    /**
     * 根据ID查询ErrorLine
     * @param id
     * @return
     */
    @Override
    public ErrorLine findById(Integer id) {
        return  errorLineMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询ErrorLine全部数据
     * @return
     */
    @Override
    public List<ErrorLine> findAll() {
        return errorLineMapper.selectAll();
    }
}
