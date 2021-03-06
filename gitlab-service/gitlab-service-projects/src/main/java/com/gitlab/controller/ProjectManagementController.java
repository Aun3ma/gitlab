package com.gitlab.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.*;
import com.gitlab.service.*;
import com.gitlab.tools.GetUUID;
import entity.IdWorker;
import entity.Method;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.gitlab.tools.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Api(value = "ProjectManagementController")
@RestController
@RequestMapping("/projects/projectInformation")
@CrossOrigin
@EnableAsync
public class ProjectManagementController {

    @Autowired
    private ProjectManagementService projectManagementService;
    @Autowired
    private ProjectInformationService projectInformationService;
    @Autowired
    private FileInformationService fileInformationService;
    @Autowired
    private CodeQualityEvaluationService codeQualityEvaluationService;
    @Autowired
    private ErrorLineService errorLineService;
    @Autowired
    private ModuleInformationService moduleInformationService;


    private String fileID;

    /***
     * 上传文件
     */
    @ApiOperation(value = "上传文件", notes = "将代码文件上传到gitlab仓库", tags = {"ProjectManagementController"})
    @PostMapping(value = "/upload/{projectID}")
    public Result uploadFile(@PathVariable String projectID, @RequestParam MultipartFile uploadFile) throws IOException {

        //String extendName = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
        String name = uploadFile.getOriginalFilename();
        File file;
        InputStream inputStream = uploadFile.getInputStream();
        file = new File(name);
        FileUtils.inputStreamToFile(inputStream, file);

        FileInformation fileInformation = projectManagementService.uploadFile(projectID, file);
        if(fileInformation == null){
            File del = new File(file.toURI());
            del.delete();
            return new Result(true, StatusCode.ERROR, "上传失败");
        }
        fileID = GetUUID.getUUID();
        fileInformation.setFileId(fileID);
        fileInformation.setCreateTime(getFormatDate());
        fileInformationService.add(fileInformation);

        File del = new File(file.toURI());
        del.delete();

        return new Result(true, StatusCode.OK, "上传成功");
    }

    public String getFormatDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }
    /***
     * 删除仓库实现
     */
    @ApiOperation(value = "删除用户仓库", notes = "从ProjectInformation中删除若干条数据", tags = {"ProjectManagementController"})
    @DeleteMapping(value = "/deleteRepo/{projectID}")
    public Result deleteRepo(@PathVariable String projectID) throws IOException {

        projectManagementService.deleteRepo(projectID);
        projectInformationService.delete(projectID);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 新建仓库实现
     */
    @ApiOperation(value = "新建用户仓库", notes = "向ProjectInformation插入一条新数据", tags = {"ProjectManagementController"})
    @PostMapping(value = "/createRepo/{userID}/{projectName}/{description}/{visibility}")
    public Result createRepo(@PathVariable String userID, @PathVariable String projectName,
                             @PathVariable String description, @PathVariable String visibility) throws IOException, JSONException {
        ProjectInformation projectInformation = projectManagementService.createRepo(userID, projectName, description, visibility);
        if(projectInformation == null){
            return new Result(true, StatusCode.ERROR, "新建仓库失败");
        }
        projectInformationService.add(projectInformation);
        return new Result(true, StatusCode.OK, "新建成功");
    }

    /***
     * 修改仓库信息
     */
    @ApiOperation(value = "修改仓库信息", notes = "修改ProjectInformation中的一条数据", tags = {"ProjectManagementController"})
    @PutMapping(value = "/changeRepo/{userID}/{projectID}/{newProjectName}/{newDescription}/{newVisibility}")
    public Result changeRepoInfo(@PathVariable String userID, @PathVariable String projectID,
                                 @PathVariable String newProjectName, @PathVariable String newDescription, @PathVariable String newVisibility) throws IOException {
        ProjectInformation projectInformation =
                projectManagementService.changeRepoInfo(userID, projectID, newProjectName, newDescription, newVisibility);
        if(projectInformation == null){
            return new Result(true, StatusCode.ERROR, "修改失败");
        }
        projectInformationService.update(projectInformation);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 删除代码文件
     */
    @ApiOperation(value = "删除代码文件", notes = "删除FileInformation中的一条数据", tags = {"ProjectManagementController"})
    @DeleteMapping(value = "/deleteFile/{fileID}")
    public Result deleteFile(@PathVariable String fileID) throws IOException {

        if (!fileInformationService.deleteFile(fileID)){
            return new Result(true, StatusCode.OK, "删除失败");
        }

        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 获取文件的代码
     */
    @ApiOperation(value = "获取文件的代码", notes = "获取文件的代码", tags = {"ProjectManagementController"})
    @GetMapping(value = "/getCode/{fileID}")
    public Result getCode(@PathVariable String fileID) throws Exception {
        String code = fileInformationService.getCode(fileID);
        if(code == null){
            return new Result(true, StatusCode.ERROR, "下载文件失败");
        }
        return new Result(true, StatusCode.OK, "获取成功", code);
    }

    /***
     * 修改代码文件
     */
    @ApiOperation(value = "修改代码文件", notes = "修改代码文件", tags = {"ProjectManagementController"})
    @PostMapping(value = "/modifyCode")
    public Result modifyCode(@RequestParam String fileID, @RequestParam String newContent) throws Exception {
        if(!fileInformationService.modifyFile(fileID, newContent)){
            return new Result(true, StatusCode.ERROR, "修改失败");
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 下载代码文件（测试用）
     */
    @ApiOperation(value = "下载代码文件（测试用）", notes = "下载代码文件", tags = {"ProjectManagementController"})
    @GetMapping(value = "/downloadFile/{fileID}")
    public Result downloadFile(@PathVariable String fileID) throws Exception {
        fileInformationService.downloadFile(fileID);

        return new Result(true, StatusCode.OK, "下载成功");
    }

    /***
     * 获取静态测试报告（测试用）
     */
    @ApiOperation(value = "获取静态测试报告（测试用）", notes = "获取静态测试报告", tags = {"ProjectManagementController"})
    @GetMapping(value = "/getReport")
    public Result getReport() throws Exception {
        return new Result(true, StatusCode.OK, "获取成功", projectManagementService.getReport());
    }


    /***
     * 测试代码 6d1d356a26c144f3912301f9074a9d6a  1257931343392669696
     */
    @ApiOperation(value = "测试代码文件", notes = "测试代码文件", tags = {"ProjectManagementController"})
    @GetMapping(value = "/checkFile1/{fileID}/{userID}")
    public Result checkFile1(@PathVariable String fileID , @PathVariable String userID) throws Exception {
        System.out.println("测试开始：");
        //下载文件到本地
        String filename = fileInformationService.downloadFile(fileID);
        if(filename == null){
            return new Result(true, StatusCode.ERROR, "下载文件失败");
        }
        System.out.println("下载文件"+filename);

        //讲代码转为方法文件
        List<Method> methods = fileInformationService.checkFile(fileID , userID , filename);
        if(methods == null){
            File del = new File(filename);
            del.delete();
            return new Result(true, StatusCode.ERROR, "转换失败");
        }

        //运行方法获取预测结果
        JSONObject jsonObject = fileInformationService.runPython();
        if(jsonObject == null){
            File del = new File(filename);
            del.delete();
            File del1 = new File("/root/project/my_sdp/my_pred_dir");
            File[] files = del1.listFiles();
            for (File f : files) {
                f.delete();
            }
            return new Result(true, StatusCode.ERROR, "运行预测失败");
        }

        //建立任务
        CodeQualityEvaluation codeQualityEvaluation = new CodeQualityEvaluation();
        IdWorker idWorker = new IdWorker(0,0);
        long task_id = idWorker.nextId();
        Date date = new Date();
        System.out.println(task_id);
        codeQualityEvaluation.setTaskId(Long.toString(task_id));
        codeQualityEvaluation.setPorjVersion("1.0");
        codeQualityEvaluation.setProjBranch("master");
        codeQualityEvaluation.setStartTime(date);
        codeQualityEvaluation.setProjId(fileID);
        codeQualityEvaluation.setUserId(userID);
        codeQualityEvaluation.setTaskState(2);
        codeQualityEvaluationService.add(codeQualityEvaluation);

        //把方法添加到方法数据库中
        for(int i=0 ; i<methods.size() ; i++){
            ModuleInformation moduleInformation = new ModuleInformation();
            Long module_id = idWorker.nextId();
            moduleInformation.setModuleId(Long.toString(module_id));
            moduleInformation.setFileId(Long.toString(task_id));
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
                errorLine.setTaskId(Long.toString(task_id));
                errorLine.setFileId(fileID);
                int line_num = jsonArray.getJSONArray(i).getInteger(j) + begin_line -1;
                errorLine.setErrorLine(line_num);
                errorLine.setMlPredictedResult(jsonObject.getJSONArray("lstm_pred").get(i).toString());
                errorLineService.add(errorLine);
            }
        }

        File del = new File(filename);
        del.delete();
        File del1 = new File("/root/project/my_sdp/my_pred_dir");
        File[] files = del1.listFiles();
        for (File f : files) {
            f.delete();
        }
        return new Result(true, StatusCode.OK, "测试成功" , Long.toString(task_id));
    }

    /***
     * 测试代码 6d1d356a26c144f3912301f9074a9d6a  1257931343392669696
     */
    @ApiOperation(value = "测试代码文件", notes = "测试代码文件", tags = {"ProjectManagementController"})
    @GetMapping(value = "/checkFile/{fileID}/{userID}")
    public Result checkFile(@PathVariable String fileID , @PathVariable String userID) throws Exception {
        System.out.println("测试开始：");
        //下载文件到本地
        String filename = fileInformationService.downloadFile(fileID);
        if(filename == null){
            return new Result(true, StatusCode.ERROR, "提交文件失败");
        }
        System.out.println("下载文件"+filename);

        //建立任务
        CodeQualityEvaluation codeQualityEvaluation = new CodeQualityEvaluation();
        IdWorker idWorker = new IdWorker(0,0);
        long task_id = idWorker.nextId();
        Date date = new Date();
        System.out.println(task_id);
        codeQualityEvaluation.setTaskId(Long.toString(task_id));
        codeQualityEvaluation.setPorjVersion("1.0");
        codeQualityEvaluation.setProjBranch("master");
        codeQualityEvaluation.setStartTime(date);
        codeQualityEvaluation.setProjId(fileID);
        codeQualityEvaluation.setUserId(userID);
        codeQualityEvaluation.setTaskState(0);
        codeQualityEvaluationService.add(codeQualityEvaluation);

        fileInformationService.getAll(fileID,userID,filename,Long.toString(task_id));

        return new Result(true, StatusCode.OK, "开始测试" , Long.toString(task_id));
    }



}

