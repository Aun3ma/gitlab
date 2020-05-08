package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.ProjectInformation;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

@Api(value = "ProjectManagementController")
@RestController
@RequestMapping("/projectManagement")
@CrossOrigin
public class ProjectManagementController {

    @Autowired
    private ProjectManagementService projectManagementService;
    @Autowired
    private ProjectInformationService projectInformationService;

    /***
     * 上传文件
     */
    @ApiOperation(value = "上传文件", notes = "将代码文件上传到gitlab仓库", tags = {"ProjectManagementController"})
    @PostMapping(value = "/{projectID}/upload")
    public Result uploadFile(@PathVariable String projectID, @RequestParam MultipartFile uploadFile) throws IOException {

        String extendName = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());

        String name = "xxx." + extendName;
        File file;
        InputStream inputStream = uploadFile.getInputStream();
        file = new File(name);
        FileUtils.inputStreamToFile(inputStream, file);

//        String fileName = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
//
//        File file = new File("xxx." + fileName);
//        if (!file.exists()){
//            file.createNewFile();
//        }
//        uploadFile.transferTo(file);

        projectManagementService.uploadFile(projectID, file);

        File del = new File(file.toURI());
        del.delete();

        return new Result(true, StatusCode.OK, "上传成功");
    }

    /***
     * 删除仓库实现
     */
    @ApiOperation(value = "删除用户仓库", notes = "从ProjectInformation中删除若干条数据", tags = {"ProjectInformationController"})
    @DeleteMapping(value = "/{projectID}")
    public Result deleteRepo(@PathVariable String projectID) throws IOException {

        projectManagementService.deleteRepo(projectID);
        projectInformationService.delete(projectID);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 新建仓库实现
     */
    @ApiOperation(value = "新建用户仓库", notes = "向ProjectInformation插入一条新数据", tags = {"ProjectInformationController"})
    @PostMapping(value = "/{userID}/{projectName}/{description}")
    public Result createRepo(@PathVariable String userID, @PathVariable String projectName,
                             @PathVariable String description) throws IOException, JSONException {

        ProjectInformation projectInformation = projectManagementService.createRepo(userID, projectName, description);
        projectInformationService.add(projectInformation);
        return new Result(true, StatusCode.OK, "新建成功");
    }
}

