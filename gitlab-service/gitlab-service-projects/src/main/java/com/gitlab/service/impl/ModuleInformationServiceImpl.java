package com.gitlab.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.dao.ModuleInformationMapper;
import com.gitlab.projects.pojo.ModuleInformation;
import com.gitlab.service.ModuleInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:ModuleInformation业务层接口实现类
 * @Date:2020/04/23
 *****/
@Service
public class ModuleInformationServiceImpl implements ModuleInformationService {

    @Autowired
    private ModuleInformationMapper moduleInformationMapper;


    /**
     * ModuleInformation条件+分页查询
     * @param moduleInformation 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ModuleInformation> findPage(ModuleInformation moduleInformation, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(moduleInformation);
        //执行搜索
        return new PageInfo<ModuleInformation>(moduleInformationMapper.selectByExample(example));
    }

    /**
     * ModuleInformation分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<ModuleInformation> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<ModuleInformation>(moduleInformationMapper.selectAll());
    }

    /**
     * ModuleInformation条件查询
     * @param moduleInformation
     * @return
     */
    @Override
    public List<ModuleInformation> findList(ModuleInformation moduleInformation) {
        //构建查询条件
        Example example = createExample(moduleInformation);
        //根据构建的条件查询数据
        return moduleInformationMapper.selectByExample(example);
    }


    /**
     * ModuleInformation构建查询对象
     * @param moduleInformation
     * @return
     */
    public Example createExample(ModuleInformation moduleInformation) {
        Example example = new Example(ModuleInformation.class);
        Example.Criteria criteria = example.createCriteria();
        if(moduleInformation != null) {
            // write it yourself
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        moduleInformationMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改ModuleInformation
     * @param moduleInformation
     */
    @Override
    public void update(ModuleInformation moduleInformation) {
        moduleInformationMapper.updateByPrimaryKey(moduleInformation);
    }

    /**
     * 增加ModuleInformation
     * @param moduleInformation
     */
    @Override
    public void add(ModuleInformation moduleInformation) {
        moduleInformationMapper.insert(moduleInformation);
    }

    /**
     * 根据ID查询ModuleInformation
     * @param id
     * @return
     */
    @Override
    public ModuleInformation findById(String id) {
        return  moduleInformationMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询ModuleInformation全部数据
     * @return
     */
    @Override
    public List<ModuleInformation> findAll() {
        return moduleInformationMapper.selectAll();
    }
}
