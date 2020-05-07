package com.gitlab.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.dao.ProjectInformationMapper;
import com.gitlab.projects.pojo.ProjectInformation;
import com.gitlab.service.ProjectInformationService;
import com.gitlab.service.ProjectManagementService;
import com.netflix.ribbon.proxy.Utils;
import com.netflix.ribbon.proxy.annotation.Http;
import entity.Result;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import org.gitlab.api.models.GitlabUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.*;

@Service
public class ProjectManagementServiceImpl implements ProjectManagementService {

    /***
     * 上传文件
     */
    @Override
    public boolean uploadFile(String projectID, File uploadFile) throws IOException {

//        GitlabAPI gitlabAPI = GitlabAPI.connect("http://111.231.248.99:81", "76hSmH3ihw9f_29SadRS");
//
//        GitlabProject gitlabProject = new GitlabProject();
//        gitlabProject.setId(Integer.parseInt(projectID));
//
//        System.out.println(gitlabAPI.uploadFile(gitlabProject, uploadFile).getUrl());

        String privateToken = "76hSmH3ihw9f_29SadRS";
        String url = "http://111.231.248.99:81/api/v4/projects/5/uploads";

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("file", "xxx.java"));


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();

        httpPost.setConfig(requestConfig);

        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        httpPost.addHeader("Content-Type", "multipart/form-data; boundary=-----------------------***");

        httpPost.addHeader("PRIVATE-TOKEN", privateToken);
        httpPost.setEntity(encodedFormEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);

        System.out.println(message);

        return true;
    }

    /***
     * 删除仓库
     */
    @Override
    public boolean deleteRepo(String projectID) throws IOException {
        String privateToken = "76hSmH3ihw9f_29SadRS";
        String url = "http://111.231.248.99:81/api/v4/projects/" + projectID;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("PRIVATE-TOKEN", privateToken);

        CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);

        System.out.println(EntityUtils.toString(httpResponse.getEntity()));

        return true;
    }

    /***
     * 新建仓库
     *
     */
    @Override
    public ProjectInformation createRepo(String userID, String projectName, String description) throws IOException, JSONException {
        String privateToken = "76hSmH3ihw9f_29SadRS";
        String url = "http://111.231.248.99:81/api/v4/projects";

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("private_token", privateToken));
        formparams.add(new BasicNameValuePair("name", projectName));
        formparams.add(new BasicNameValuePair("path", projectName));
        formparams.add(new BasicNameValuePair("description", description));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        httpPost.setHeader("PRIVATE-TOKEN", privateToken);
        httpPost.setEntity(encodedFormEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);

        JSONObject jsonObject = JSONObject.parseObject(message);

        String projectID = jsonObject.getString("id");
        Timestamp createTime = jsonObject.getTimestamp("created_at");

        ProjectInformation projectInformation = new ProjectInformation();
        projectInformation.setProjId(projectID);
        projectInformation.setCreateTime(createTime);
        projectInformation.setOwnerUserId(userID);
        projectInformation.setProjName(projectName);

        return projectInformation;
    }


//    /***
////     * 获取Private_Token
////     */
//    private String getPrivateToken() throws IOException {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://111.231.248.99:81/api/v4/session");
//
//        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//        formparams.add(new BasicNameValuePair("login", "root"));
//        formparams.add(new BasicNameValuePair("password", "abcd1234"));
//
////        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
////
////        httpPost.setEntity(encodedFormEntity);
//
//        System.out.println(httpPost);
////
//        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//        HttpEntity entity = httpResponse.getEntity();
//
//        String privateToken = EntityUtils.toString(entity);
//        System.out.println(privateToken);
//
//        return privateToken;
//    }
}
