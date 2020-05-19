package com.gitlab.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.FileInformation;
import entity.Method;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:FileInformation业务层接口
 * @Date:2020/04/23
 *****/
public interface FileInformationService {

    /***
     * FileInformation多条件分页查询
     * @param fileInformation
     * @param page
     * @param size
     * @return
     */
    PageInfo<FileInformation> findPage(FileInformation fileInformation, int page, int size);

    /***
     * FileInformation分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<FileInformation> findPage(int page, int size);

    /***
     * FileInformation多条件搜索方法
     * @param fileInformation
     * @return
     */
    List<FileInformation> findList(FileInformation fileInformation);

    /***
     * 删除FileInformation
     * @param id
     */
    void delete(String id);

    /***
     * 修改FileInformation数据
     * @param fileInformation
     */
    void update(FileInformation fileInformation);

    /***
     * 新增FileInformation
     * @param fileInformation
     */
    void add(FileInformation fileInformation);

    /**
     * 根据ID查询FileInformation
     * @param id
     * @return
     */
     FileInformation findById(String id);

    /***
     * 查询所有FileInformation
     * @return
     */
    List<FileInformation> findAll();

    /***
     * 根据projectID查询
     */
    List<FileInformation> findByProjectID(String projectID);

    /***
     * 下载代码文件
     */
    String downloadFile(String fileID) throws Exception;

    /***
     * 删除代码文件
     */
    boolean deleteFile(String fileID) throws IOException;

    /***
     * 代码缺陷检查方法
     */
    List<Method> checkFile(String fileID , String userID , String filename) throws Exception;

    /***
     * 运行python方法
     */
    JSONObject runPython();


}
