package com.gitlab.projects.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
/**
 * @Author:shenjunjie
 * @Description:FileInformation构建
 * @Date:2020/04/23
 */

@ApiModel(description = "FileInformation",value = "FileInformation")
@Table(name="tb_file_information")
public class FileInformation implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @Column(name = "file_id")
	private String fileId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "task_id")
	private String taskId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "file_name")
	private String fileName;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "file_type")
	private String fileType;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "file_coding")
	private String fileCoding;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "file_path")
	private String filePath;//



	//get方法
	public String getFileId() {
		return fileId;
	}

	//set方法
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	//get方法
	public String getTaskId() {
		return taskId;
	}

	//set方法
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	//get方法
	public String getFileName() {
		return fileName;
	}

	//set方法
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	//get方法
	public String getFileType() {
		return fileType;
	}

	//set方法
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	//get方法
	public String getFileCoding() {
		return fileCoding;
	}

	//set方法
	public void setFileCoding(String fileCoding) {
		this.fileCoding = fileCoding;
	}
	//get方法
	public String getFilePath() {
		return filePath;
	}

	//set方法
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


}
