package com.gitlab.service;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.ProjectInformation;

import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:ProjectInformation业务层接口
 * @Date:2020/04/23
 *****/
public interface ProjectInformationService {

    /***
     * ProjectInformation多条件分页查询
     * @param projectInformation
     * @param page
     * @param size
     * @return
     */
    PageInfo<ProjectInformation> findPage(ProjectInformation projectInformation, int page, int size);

    /***
     * ProjectInformation分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<ProjectInformation> findPage(int page, int size);

    /***
     * ProjectInformation多条件搜索方法
     * @param projectInformation
     * @return
     */
    List<ProjectInformation> findList(ProjectInformation projectInformation);

    /***
     * 删除ProjectInformation
     * @param id
     */
    void delete(String id);

    /***
     * 修改ProjectInformation数据
     * @param projectInformation
     */
    void update(ProjectInformation projectInformation);

    /***
     * 新增ProjectInformation
     * @param projectInformation
     */
    void add(ProjectInformation projectInformation);

    /**
     * 根据ID查询ProjectInformation
     * @param id
     * @return
     */
     ProjectInformation findById(String id);

    /***
     * 查询所有ProjectInformation
     * @return
     */
    List<ProjectInformation> findAll();
}
