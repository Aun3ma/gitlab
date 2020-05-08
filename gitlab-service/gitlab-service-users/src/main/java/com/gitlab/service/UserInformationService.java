package com.gitlab.service;

import com.github.pagehelper.PageInfo;
import com.gitlab.users.pojo.UserInformation;

import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:UserInformation业务层接口
 * @Date:2020/04/23
 *****/
public interface UserInformationService {


    String login(String username, String password);

    /***
     * UserInformation多条件分页查询
     * @param userInformation
     * @param page
     * @param size
     * @return
     */
    PageInfo<UserInformation> findPage(UserInformation userInformation, int page, int size);

    /***
     * UserInformation分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<UserInformation> findPage(int page, int size);

    /***
     * UserInformation多条件搜索方法
     * @param userInformation
     * @return
     */
    List<UserInformation> findList(UserInformation userInformation);

    /***
     * 删除UserInformation
     * @param id
     */
    void delete(String id);

    /***
     * 修改UserInformation数据
     * @param userInformation
     */
    void update(UserInformation userInformation);

    /***
     * 新增UserInformation
     * @param userInformation
     */
    void add(UserInformation userInformation);

    /**
     * 根据ID查询UserInformation
     * @param id
     * @return
     */
     UserInformation findById(String id);

    /***
     * 查询所有UserInformation
     * @return
     */
    List<UserInformation> findAll();
}
