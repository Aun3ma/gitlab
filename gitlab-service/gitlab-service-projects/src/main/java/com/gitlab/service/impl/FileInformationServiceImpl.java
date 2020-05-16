package com.gitlab.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.dao.FileInformationMapper;
import com.gitlab.projects.pojo.FileInformation;
import com.gitlab.service.FileInformationService;
import com.gitlab.tools.HttpDeleteWithBody;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:FileInformation业务层接口实现类
 * @Date:2020/04/23
 *****/
@Service
public class FileInformationServiceImpl implements FileInformationService {

    @Autowired
    private FileInformationMapper fileInformationMapper;


    /**
     * FileInformation条件+分页查询
     * @param fileInformation 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<FileInformation> findPage(FileInformation fileInformation, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(fileInformation);
        //执行搜索
        return new PageInfo<FileInformation>(fileInformationMapper.selectByExample(example));
    }

    /**
     * FileInformation分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<FileInformation> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<FileInformation>(fileInformationMapper.selectAll());
    }

    /**
     * FileInformation条件查询
     * @param fileInformation
     * @return
     */
    @Override
    public List<FileInformation> findList(FileInformation fileInformation) {
        //构建查询条件
        Example example = createExample(fileInformation);
        //根据构建的条件查询数据
        return fileInformationMapper.selectByExample(example);
    }


    /**
     * FileInformation构建查询对象
     * @param fileInformation
     * @return
     */
    public Example createExample(FileInformation fileInformation) {
        Example example = new Example(FileInformation.class);
        Example.Criteria criteria = example.createCriteria();
        if(fileInformation != null) {
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
        fileInformationMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改FileInformation
     * @param fileInformation
     */
    @Override
    public void update(FileInformation fileInformation) {
        fileInformationMapper.updateByPrimaryKey(fileInformation);
    }

    /**
     * 增加FileInformation
     * @param fileInformation
     */
    @Override
    public void add(FileInformation fileInformation) {
        fileInformationMapper.insert(fileInformation);
    }

    /**
     * 根据ID查询FileInformation
     * @param id
     * @return
     */
    @Override
    public FileInformation findById(String id) {
        return  fileInformationMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询FileInformation全部数据
     * @return
     */
    @Override
    public List<FileInformation> findAll() {
        return fileInformationMapper.selectAll();
    }

    /***
     * 根据projectID查询
     * @param projectID
     * @return
     */
    @Override
    public List<FileInformation> findByProjectID(String projectID) {
        FileInformation fileInformation = new FileInformation();
        fileInformation.setTaskId(projectID);

        return findList(fileInformation);
    }

    /***
     * 下载代码文件
     */
    @Override
    public boolean downloadFile(String fileID) {


        return true;
    }

    /***
     * 删除代码文件
     */
    @Override
    public boolean deleteFile(String fileID) throws IOException {
        FileInformation fileInformation = findById(fileID);
        String projectID = fileInformation.getTaskId();
        String filePath = fileInformation.getFilePath();

        String privateToken = "76hSmH3ihw9f_29SadRS";
        String url = "http://111.231.248.99:81/api/v4/projects/" + projectID + "/repository/files/" + filePath;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();

        httpDeleteWithBody.setConfig(requestConfig);

        net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject();
        jsonObject.appendField("branch", "master");
        jsonObject.appendField("commit_message", "New Code File");

        String json = jsonObject.toJSONString();

        System.out.println(json);

        StringEntity requestEntity = new StringEntity(json,"utf-8");
        requestEntity.setContentType("application/json");

        httpDeleteWithBody.addHeader("PRIVATE-TOKEN", privateToken);
        httpDeleteWithBody.setEntity(requestEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpDeleteWithBody);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);
        System.out.println(message);

        delete(fileID);

        return true;
    }
}
