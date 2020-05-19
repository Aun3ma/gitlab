package com.gitlab.service;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.ModuleInformation;

import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:ModuleInformation业务层接口
 * @Date:2020/04/23
 *****/
public interface ModuleInformationService {

    /***
     * ModuleInformation多条件分页查询
     * @param moduleInformation
     * @param page
     * @param size
     * @return
     */
    PageInfo<ModuleInformation> findPage(ModuleInformation moduleInformation, int page, int size);

    /***
     * ModuleInformation分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<ModuleInformation> findPage(int page, int size);

    /***
     * ModuleInformation多条件搜索方法
     * @param moduleInformation
     * @return
     */
    List<ModuleInformation> findList(ModuleInformation moduleInformation);

    /***
     * 删除ModuleInformation
     * @param id
     */
    void delete(String id);

    /***
     * 修改ModuleInformation数据
     * @param moduleInformation
     */
    void update(ModuleInformation moduleInformation);

    /***
     * 新增ModuleInformation
     * @param moduleInformation
     */
    void add(ModuleInformation moduleInformation);

    /**
     * 根据ID查询ModuleInformation
     * @param id
     * @return
     */
     ModuleInformation findById(String id);

    /***
     * 查询所有ModuleInformation
     * @return
     */
    List<ModuleInformation> findAll();
}
