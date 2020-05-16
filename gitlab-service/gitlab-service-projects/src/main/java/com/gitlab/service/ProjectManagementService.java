package com.gitlab.service;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.FileInformation;
import com.gitlab.projects.pojo.ProjectInformation;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProjectManagementService {

    /***
     * 上传文件
     */
    FileInformation uploadFile(String projectID, File uploadFile) throws IOException;

    /***
     * 删除仓库
     */
    boolean deleteRepo(String projectID) throws IOException;

    /***
     * 新建仓库
     */
    ProjectInformation createRepo(String userID, String projectName, String description) throws IOException, JSONException;

    /***
     * 编辑仓库信息
     */
    ProjectInformation changeRepoInfo(String userID, String projectID, String newProjectName, String newDescription) throws IOException;
}