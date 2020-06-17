package com.gitlab.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitlab.projects.dto.CodeReport;
import com.gitlab.projects.pojo.FileInformation;
import com.gitlab.projects.pojo.ProjectInformation;
import com.gitlab.service.ProjectManagementService;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManagementServiceImpl implements ProjectManagementService {

    /***
     * 上传文件
     */
    @Override
    public FileInformation uploadFile(String projectID, File uploadFile) throws IOException {
        String privateToken = "2-NTBRTswUhGm-4dzmWh";
        String url = "http://106.55.48.209/api/v4/projects/" + projectID + "/repository/files/" + uploadFile.getName();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

//        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
//                .setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
//
//        httpPost.setConfig(requestConfig);

        String fileContent = FileUtils.readFileToString(uploadFile, "utf-8");

        net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject();
        jsonObject.appendField("branch", "master");
        jsonObject.appendField("content", fileContent);
        jsonObject.appendField("commit_message", "New Code File");

        String json = jsonObject.toJSONString();

        System.out.println(json);

        StringEntity requestEntity = new StringEntity(json,"utf-8");
        requestEntity.setContentType("application/json");

        httpPost.addHeader("PRIVATE-TOKEN", privateToken);
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);
        System.out.println(message);

        JSONObject response = JSONObject.parseObject(message);
        String filePath = response.getString("file_path");
        if(filePath == null){
            return null;
        }

        FileInformation fileInformation = new FileInformation();
        fileInformation.setFileName(uploadFile.getName());
        fileInformation.setTaskId(projectID);
        fileInformation.setFilePath(filePath);

        return fileInformation;
    }

    /***
     * 删除仓库
     */
    @Override
    public boolean deleteRepo(String projectID) throws IOException {
        String privateToken = "2-NTBRTswUhGm-4dzmWh";
        String url = "http://106.55.48.209/api/v4/projects/" + projectID;

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
    public ProjectInformation createRepo(
            String userID, String projectName, String description, String visibility) throws IOException, JSONException {
        String privateToken = "2-NTBRTswUhGm-4dzmWh";
        String url = "http://106.55.48.209/api/v4/projects";

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("private_token", privateToken));
        formparams.add(new BasicNameValuePair("name", projectName));
        formparams.add(new BasicNameValuePair("path", projectName));
        formparams.add(new BasicNameValuePair("description", description));
        formparams.add(new BasicNameValuePair("visibility", visibility));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        httpPost.setHeader("PRIVATE-TOKEN", privateToken);
        httpPost.setEntity(encodedFormEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);
        System.out.println(message);

        JSONObject jsonObject = JSONObject.parseObject(message);

        String projectID = jsonObject.getString("id");
        Timestamp createTime = jsonObject.getTimestamp("created_at");
        if( projectID == null){
            return null;
        }
        ProjectInformation projectInformation = new ProjectInformation();
        projectInformation.setProjId(projectID);
        projectInformation.setCreateTime(createTime);
        projectInformation.setOwnerUserId(userID);
        projectInformation.setProjName(projectName);
        projectInformation.setUpdateTime(createTime);
        projectInformation.setVisibility(visibility);
        projectInformation.setProjectDescription(description);

        return projectInformation;
    }

    /***
     * 编辑仓库信息
     */
    @Override
    public ProjectInformation changeRepoInfo(
            String userID, String projectID, String newProjectName, String newDescription, String visibility) throws IOException {
        String privateToken = "2-NTBRTswUhGm-4dzmWh";
        String url = "http://106.55.48.209/api/v4/projects/" + projectID;

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("private_token", privateToken));
        formparams.add(new BasicNameValuePair("name", newProjectName));
        formparams.add(new BasicNameValuePair("description", newDescription));
        formparams.add(new BasicNameValuePair("visibility", visibility));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);

        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        httpPut.setHeader("PRIVATE-TOKEN", privateToken);
        httpPut.setEntity(encodedFormEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);
        JSONObject jsonObject = JSONObject.parseObject(message);

        Timestamp createTime = jsonObject.getTimestamp("created_at");
        Timestamp updateTime = jsonObject.getTimestamp("last_activity_at");
        if(updateTime == null){
            return null;
        }

        ProjectInformation projectInformation = new ProjectInformation();
        projectInformation.setProjId(projectID);
        projectInformation.setCreateTime(createTime);
        projectInformation.setOwnerUserId(userID);
        projectInformation.setProjName(newProjectName);
        projectInformation.setUpdateTime(updateTime);
        projectInformation.setVisibility(visibility);

        return projectInformation;
    }

    /***
     * 获取静态检测报告
     */
    @Override
    public CodeReport getReport() throws IOException {

        try {
            Process p = Runtime.getRuntime().exec(
                    "cmd /c start /w /MIN C:\\Users\\19134\\Desktop\\SonarQubeTestProgram\\cmd.bat");
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "http://8.129.183.196:9000/api/measures/component?" +
                "component=sonarScannerTest&metricKeys=bugs,vulnerabilities,duplicated_lines_density,code_smells";
        String privateToken = "Basic YWRtaW46YWRtaW4=";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Authorization", privateToken);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();

        String message = EntityUtils.toString(entity);
        System.out.println(message);

        JSONObject jsonObject = JSONObject.parseObject(message);

        JSONArray codeReport = jsonObject.getJSONObject("component").getJSONArray("measures");

        int bugs = 0;
        int vulnerabilities = 0;
        int code_smells = 0;
        float duplicated_lines_density = 0;
        for (int i = 0; i < 4; i++) {
            String tag = codeReport.getJSONObject(i).getString("metric");
            switch (tag){
                case "bugs":
                    bugs = codeReport.getJSONObject(i).getInteger("value");
                case "vulnerabilities":
                    vulnerabilities = codeReport.getJSONObject(i).getInteger("value");
                case "code_smells":
                    code_smells = codeReport.getJSONObject(i).getInteger("value");
                case "duplicated_lines_density":
                    duplicated_lines_density = codeReport.getJSONObject(i).getFloat("value");
            }
        }

        CodeReport myCodeReport = new CodeReport();
        myCodeReport.setBugs(bugs);
        myCodeReport.setCode_smells(code_smells);
        myCodeReport.setDuplicated_lines_density(duplicated_lines_density);
        myCodeReport.setVulnerabilities(vulnerabilities);

        return myCodeReport;
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

    //        final String newLine = "\r\n";
//        final String boundaryPrefix = "--";
//        String BOUNDARY = "========7d4a6d158c9";
//        URL url = new URL("http://111.231.248.99:81/api/v4/projects/" + projectID + "/repository/files");
//        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        connection.setDoInput(true);
//        connection.setUseCaches(false);
//        connection.setRequestProperty("PRIVATE-TOKEN", "76hSmH3ihw9f_29SadRS");
//        connection.setRequestProperty("connection", "Keep-Alive");
//        connection.setRequestProperty("Charset", "UTF-8");
//        connection.setRequestProperty("Content-Type", "application/json; boundary=" + BOUNDARY);
//
//        OutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(boundaryPrefix );
//        stringBuilder.append(BOUNDARY);
//        stringBuilder.append(newLine);
//        stringBuilder.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + uploadFile.getName()
//                + "\"" + newLine);
//        stringBuilder.append("Content-Type: application/octet-stream");
//
//        stringBuilder.append(newLine);
//        stringBuilder.append(newLine);
//
//        outputStream.write(stringBuilder.toString().getBytes());
//
//        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(uploadFile));
//        byte[] bufferOut = new byte[1024];
//        int bytes = 0;
//        while ((bytes = dataInputStream.read(bufferOut)) != -1) {
//            outputStream.write(bufferOut, 0, bytes);
//        }
//        outputStream.write(newLine.getBytes());
//        dataInputStream.close();
//
//        byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
//                .getBytes();
//        outputStream.write(end_data);
//        outputStream.flush();
//        outputStream.close();
//
//        System.out.println(connection.getResponseMessage());
//        System.out.println(connection.getResponseCode());
//        connection.disconnect();


    //        GitlabAPI gitlabAPI = GitlabAPI.connect("http://111.231.248.99:81", "76hSmH3ihw9f_29SadRS");
//
//        GitlabProject gitlabProject = new GitlabProject();
//        gitlabProject.setId(Integer.parseInt(projectID));
//
//        System.out.println(gitlabAPI.uploadFile(gitlabProject, uploadFile).getUrl());
}
