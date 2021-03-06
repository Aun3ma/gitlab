package com.gitlab.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.dao.CodeQualityEvaluationMapper;
import com.gitlab.dao.FileInformationMapper;
import com.gitlab.projects.dto.CodeReport;
import com.gitlab.projects.pojo.CodeQualityEvaluation;
import com.gitlab.projects.pojo.ErrorLine;
import com.gitlab.projects.pojo.FileInformation;
import com.gitlab.projects.pojo.ModuleInformation;
import com.gitlab.service.*;
import com.gitlab.tools.DecodeBase64;
import com.gitlab.tools.HttpDeleteWithBody;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import entity.IdWorker;
import entity.LogicPositivizer;
import entity.Method;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.jni.FileInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/****
 * @Author:shenjunjie
 * @Description:FileInformation业务层接口实现类
 * @Date:2020/04/23
 *****/
@Service
public class FileInformationServiceImpl implements FileInformationService {

    @Autowired
    private FileInformationMapper fileInformationMapper;

    @Autowired
    private CodeQualityEvaluationService codeQualityEvaluationService;

    @Autowired
    private ModuleInformationService moduleInformationService;

    @Autowired
    private ErrorLineService errorLineService;

    @Autowired
    private ProjectManagementService projectManagementService;

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
            criteria.andEqualTo("taskId", fileInformation.getTaskId());
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
     * 下载代码文件 0de3cd52b3f44a758336b262a39e6f29
     */
    @Override
    public String downloadFile(String fileID) throws Exception {
        FileInformation fileInformation = findById(fileID);

        String privateToken = "2-NTBRTswUhGm-4dzmWh";
        String url = "http://106.55.247.218/api/v4/projects/" +
                fileInformation.getTaskId() + "/repository/files/" + fileInformation.getFilePath();

        URI uri = new URIBuilder(url).setParameter("ref", "master").build();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        httpGet.addHeader("PRIVATE-TOKEN", privateToken);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);

        JSONObject response = JSONObject.parseObject(message);
        String Base64Code = response.getString("content");
        String fileName = response.getString("file_name");

        System.out.println(fileName);

        DecodeBase64.decoderBase64File(Base64Code, fileName);

        // 静态测试项目路径
        String staticAnalysisFilePath =
                "E:\\projects\\SonarQubeTestProgram\\src\\test." + fileName.split("\\.")[1];
        DecodeBase64.decoderBase64File(Base64Code, staticAnalysisFilePath);

//        File del = new File(fileName);
//        del.delete();

        return fileName;
    }

    /***
     * 获取代码文件信息
     */
    @Override
    public String getCode(String fileID) throws Exception{
        String filename = downloadFile(fileID);
        if(filename == null){
            return null;
        }
        File file = new File(filename);
        String str="";
        try {
            FileInputStream in=new FileInputStream(file);
            int size=in.available();
            byte[] buffer=new byte[size];
            in.read(buffer);
            in.close();
            str=new String(buffer,"GB2312");
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
        return str;
    }


    /***
     * 删除代码文件
     */
    @Override
    public boolean deleteFile(String fileID) throws IOException {
        FileInformation fileInformation = findById(fileID);
        String projectID = fileInformation.getTaskId();
        String filePath = fileInformation.getFilePath();

        String privateToken = "2-NTBRTswUhGm-4dzmWh";
        String url = "http://106.55.247.218/api/v4/projects/" + projectID + "/repository/files/" + filePath;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);

        net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject();
        jsonObject.appendField("branch", "master");
        jsonObject.appendField("commit_message", "Delete Code File");

        String json = jsonObject.toJSONString();

        System.out.println(json);

        StringEntity requestEntity = new StringEntity(json,"utf-8");
        requestEntity.setContentType("application/json");

        httpDeleteWithBody.addHeader("PRIVATE-TOKEN", privateToken);
        httpDeleteWithBody.setEntity(requestEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpDeleteWithBody);
        HttpEntity entity = httpResponse.getEntity();

        if (entity != null){
            return false;
        }
//        String message = EntityUtils.toString(entity);
//        System.out.println(message);

        delete(fileID);

        return true;
    }

    /***
     * 修改代码文件
     */
    @Override
    public boolean modifyFile(String fileID, String newContent) throws IOException {
        FileInformation fileInformation = findById(fileID);
        String projectID = fileInformation.getTaskId();
        String filePath = fileInformation.getFilePath();

        String privateToken = "2-NTBRTswUhGm-4dzmWh";
        String url = "http://106.55.247.218/api/v4/projects/" + projectID + "/repository/files/" + filePath;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);

        net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject();
        jsonObject.appendField("branch", "master");
        jsonObject.appendField("commit_message", "Modify Code File");
        jsonObject.appendField("content", newContent);

        String json = jsonObject.toJSONString();

        System.out.println(json);

        StringEntity requestEntity = new StringEntity(json,"utf-8");
        requestEntity.setContentType("application/json");

        httpPut.addHeader("PRIVATE-TOKEN", privateToken);
        httpPut.setEntity(requestEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);
        JSONObject response = JSONObject.parseObject(message);
        String resp = response.getString("file_path");
        System.out.println(message);

        return resp != null;
    }

    /***
     * 代码缺陷检查方法
     */
    @Override
    public List<Method> checkFile(String fileID , String userID , String filename) throws Exception{

        File file = new File(filename);
        String str="";
        try {
            FileInputStream in=new FileInputStream(file);
            int size=in.available();
            byte[] buffer=new byte[size];
            in.read(buffer);
            in.close();
            str=new String(buffer,"GB2312");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, Object> res = LogicPositivizer.parser(str);
        if(res == null){
            return null;
        }
        List<Method> methods = (List<Method>) res.get("methods");
        for(int i=0;i < methods.size();i++){
            try{
                File MethodFile = new File("E:\\projects\\my_sdp\\my_pred_dir\\"+i+".java");
//                File MethodFile = new File("/root/project/my_sdp/my_pred_dir/"+i+".java");
                if(!file.exists()){
                    MethodFile.createNewFile();
                }

//                FileWriter fileWriter = new FileWriter("/root/project/my_sdp/my_pred_dir/"+i+".java");
                FileWriter fileWriter = new FileWriter("E:\\projects\\my_sdp\\my_pred_dir\\"+i+".java");

                fileWriter.write(LogicPositivizer.getMethodBody(methods.get(i),str));

                fileWriter.close();

            }catch(IOException e){

                e.printStackTrace();
                return null;
            }

        }
        return methods;

    }

    @Override
    public JSONObject runPython(){
        System.out.println("写入文件结束，开始运行python");
        List<String> CodeJson = new ArrayList<>();
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python E:\\projects\\my_sdp\\defect_prediction.py");
//            proc = Runtime.getRuntime().exec("python /root/project/my_sdp/defect_prediction.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                CodeJson.add(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("python运行结束，取得测试码"+CodeJson.get(CodeJson.size()-1));

        JSONObject jsonObject = JSONObject.parseObject(CodeJson.get(CodeJson.size()-1));
        return jsonObject;
    }

    @Async
    @Override
    public void getAll(String fileID ,String userID ,String filename , String task_id){
        List<Method> methods = null;
        try {
            CodeReport codeReport = projectManagementService.getReport();
            if(codeReport == null){
                codeQualityEvaluationService.updateById(task_id , 3);
            }else{
                CodeQualityEvaluation codeQualityEvaluation = codeQualityEvaluationService.findById(task_id);
                codeQualityEvaluation.setBugs(Integer.toString(codeReport.getBugs()));
                codeQualityEvaluation.setVulnerabilities(Integer.toString(codeReport.getVulnerabilities()));
                codeQualityEvaluation.setDuplicated_lines_density(Float.toString(codeReport.getDuplicated_lines_density()));
                codeQualityEvaluation.setCode_smells(Integer.toString(codeReport.getCode_smells()));
                codeQualityEvaluationService.update(codeQualityEvaluation);
                codeQualityEvaluationService.updateById(task_id , 1);
                System.out.println("?");
                methods = checkFile(fileID , userID , filename);
                if(methods == null){
                    File del = new File(filename);
                    del.delete();
                    codeQualityEvaluationService.updateById(task_id , 11);
                }else{
                    //运行方法获取预测结果
                    JSONObject jsonObject = runPython();
                    if(jsonObject == null){
                        File del = new File(filename);
                        del.delete();
//                    File del1 = new File("/root/project/my_sdp/my_pred_dir");
                        File del1 = new File("E:\\projects\\my_sdp\\my_pred_dir");
                        File[] files = del1.listFiles();
                        for (File f : files) {
                            f.delete();
                        }
                        codeQualityEvaluationService.updateById(task_id , 12);
                    }else{
                        //把方法添加到方法数据库中
                        for(int i=0 ; i<methods.size() ; i++){
                            ModuleInformation moduleInformation = new ModuleInformation();
                            IdWorker idWorker = new IdWorker(0,0);
                            Long module_id = idWorker.nextId();
                            moduleInformation.setModuleId(Long.toString(module_id));
                            moduleInformation.setFileId(task_id);
                            moduleInformation.setModuleName(methods.get(i).getMethodName());
                            moduleInformation.setPmdUrl("unknown");
                            moduleInformation.setMlPredictedResult(jsonObject.getJSONArray("lstm_pred").get(i).toString());
                            moduleInformationService.add(moduleInformation);
                        }

                        //把需要注意的行添加到数据库中
                        JSONArray jsonArray = jsonObject.getJSONArray("topk_list");
                        for(int i=0 ; i<methods.size() ; i++){
                            int begin_line = methods.get(i).getBeginLine();
                            for(int j=0 ; j<jsonArray.getJSONArray(i).size();j++){
                                if(jsonArray.getJSONArray(i).getInteger(j).equals(0)){
                                    continue;
                                }
                                ErrorLine errorLine = new ErrorLine();
                                errorLine.setTaskId(task_id);
                                errorLine.setFileId(fileID);
                                int line_num = jsonArray.getJSONArray(i).getInteger(j) + begin_line -1;
                                errorLine.setErrorLine(line_num);
                                errorLine.setMlPredictedResult(jsonObject.getJSONArray("lstm_pred").get(i).toString());
                                errorLineService.add(errorLine);
                            }
                        }

                        File del = new File(filename);
                        del.delete();
//                    File del1 = new File("/root/project/my_sdp/my_pred_dir");
                        File del1 = new File("E:\\projects\\my_sdp\\my_pred_dir");
                        File[] files = del1.listFiles();
                        for (File f : files) {
                            f.delete();
                        }
                        codeQualityEvaluationService.updateById(task_id , 4);
                        System.out.println("4");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            File del = new File(filename);
            if(del.exists()){
                del.delete();
            }
//                    File del1 = new File("/root/project/my_sdp/my_pred_dir");
            File del1 = new File("E:\\projects\\my_sdp\\my_pred_dir");
            if(del1.exists()){
                File[] files = del1.listFiles();
                for (File f : files) {
                    f.delete();
                }
            }
            codeQualityEvaluationService.updateById(task_id , 13);

        }
    }


}

