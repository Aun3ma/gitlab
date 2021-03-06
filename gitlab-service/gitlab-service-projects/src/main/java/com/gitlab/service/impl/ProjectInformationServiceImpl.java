package com.gitlab.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.dao.ProjectInformationMapper;
import com.gitlab.projects.pojo.ProjectInformation;
import com.gitlab.service.ProjectInformationService;
import com.netflix.ribbon.proxy.annotation.Http;
import entity.Result;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:ProjectInformation业务层接口实现类
 * @Date:2020/04/23
 *****/
@Service
public class ProjectInformationServiceImpl implements ProjectInformationService {

    @Autowired
    private ProjectInformationMapper projectInformationMapper;

    /**
     * ProjectInformation条件+分页查询
     * @param projectInformation 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ProjectInformation> findPage(ProjectInformation projectInformation, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(projectInformation);
        //执行搜索
        return new PageInfo<ProjectInformation>(projectInformationMapper.selectByExample(example));
    }

    /**
     * ProjectInformation分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<ProjectInformation> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<ProjectInformation>(projectInformationMapper.selectAll());
    }

    /**
     * ProjectInformation条件查询
     * @param projectInformation
     * @return
     */
    @Override
    public List<ProjectInformation> findList(ProjectInformation projectInformation) {
        //构建查询条件
        Example example = createExample(projectInformation);
        //根据构建的条件查询数据
        return projectInformationMapper.selectByExample(example);
    }


    /**
     * ProjectInformation构建查询对象
     * @param projectInformation
     * @return
     */
    public Example createExample(ProjectInformation projectInformation) {
        Example example = new Example(ProjectInformation.class);
        Example.Criteria criteria = example.createCriteria();
        if(projectInformation != null) {
            criteria.andEqualTo("ownerUserId", projectInformation.getOwnerUserId());
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        projectInformationMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改ProjectInformation
     * @param projectInformation
     */
    @Override
    public void update(ProjectInformation projectInformation) {
        projectInformationMapper.updateByPrimaryKey(projectInformation);
    }

    /**
     * 增加ProjectInformation
     * @param projectInformation
     */
    @Override
    public void add(ProjectInformation projectInformation) {
        projectInformationMapper.insert(projectInformation);
    }

    /**
     * 根据ID查询ProjectInformation
     * @param id
     * @return
     */
    @Override
    public ProjectInformation findById(String id) {
        return  projectInformationMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询ProjectInformation全部数据
     * @return
     */
    @Override
    public List<ProjectInformation> findAll() {
        return projectInformationMapper.selectAll();
    }

    /***
     *获取一个用户的所有项目仓库
     */
    @Override
    public List<ProjectInformation> findByUserID(String userID){
        ProjectInformation projectInformation = new ProjectInformation();
        projectInformation.setOwnerUserId(userID);
        return findList(projectInformation);
    }
}
