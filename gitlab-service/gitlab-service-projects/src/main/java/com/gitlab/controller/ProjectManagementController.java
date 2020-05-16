package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.FileInformation;
import com.gitlab.projects.pojo.ProjectInformation;
import com.gitlab.service.FileInformationService;
import com.gitlab.service.ProjectInformationService;
import com.gitlab.service.ProjectManagementService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Api(value = "ProjectManagementController")
@RestController
@RequestMapping("/projects/projectInformation")
@CrossOrigin
public class ProjectManagementController {

    @Autowired
    private ProjectManagementService projectManagementService;
    @Autowired
    private ProjectInformationService projectInformationService;
    @Autowired
    private FileInformationService fileInformationService;

    private int fileID = 1;

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
        fileInformation.setFileId(Integer.toString(fileID));
        fileID++;
        fileInformationService.add(fileInformation);

        File del = new File(file.toURI());
        del.delete();

        return new Result(true, StatusCode.OK, "上传成功");
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
    @PostMapping(value = "/createRepo/{userID}/{projectName}/{description}")
    public Result createRepo(@PathVariable String userID, @PathVariable String projectName,
                             @PathVariable String description) throws IOException, JSONException {

        ProjectInformation projectInformation = projectManagementService.createRepo(userID, projectName, description);
        projectInformationService.add(projectInformation);
        return new Result(true, StatusCode.OK, "新建成功");
    }

    /***
     * 修改仓库信息
     */
    @ApiOperation(value = "修改仓库信息", notes = "修改ProjectInformation中的一条数据", tags = {"ProjectManagementController"})
    @PutMapping(value = "/changeRepo/{userID}/{projectID}/{newProjectName}/{newDescription}")
    public Result changeRepoInfo(@PathVariable String userID, @PathVariable String projectID,
                                  @PathVariable String newProjectName, @PathVariable String newDescription) throws IOException {
        ProjectInformation projectInformation =
                projectManagementService.changeRepoInfo(userID, projectID, newProjectName, newDescription);
        projectInformationService.update(projectInformation);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 删除代码文件
     */
    @ApiOperation(value = "删除代码文件", notes = "删除FileInformation中的一条数据", tags = {"ProjectManagementController"})
    @DeleteMapping(value = "/deleteFile/{fileID}")
    public Result deleteFile(@PathVariable String fileID) throws IOException {
        fileInformationService.deleteFile(fileID);

        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 下载代码文件（测试用）
     */
    @ApiOperation(value = "下载代码文件", notes = "下载代码文件", tags = {"ProjectManagementController"})
    @GetMapping(value = "/downloadFile/{fileID}")
    public Result downloadFile(@PathVariable String fileID) throws Exception {
        fileInformationService.downloadFile(fileID);


        return new Result(true, StatusCode.OK, "下载成功");
    }
}

