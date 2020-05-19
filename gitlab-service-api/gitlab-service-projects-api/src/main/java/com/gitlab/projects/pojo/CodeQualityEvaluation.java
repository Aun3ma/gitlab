package com.gitlab.projects.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/**
 * @Author:shenjunjie
 * @Description:CodeQualityEvaluation构建
 * @Date:2020/04/23
 */

@ApiModel(description = "CodeQualityEvaluation",value = "CodeQualityEvaluation")
@Table(name="tb_code_quality_evaluation")
public class CodeQualityEvaluation implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @Column(name = "task_id")
	private String taskId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "proj_id")
	private String projId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "porj_version")
	private String porjVersion;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "proj_branch")
	private String projBranch;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "user_id")
	private String userId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "task_state")
	private Integer taskState;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "start_time")
	private Date startTime;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "end_time")
	private Date endTime;//



	//get方法
	public String getTaskId() {
		return taskId;
	}

	//set方法
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	//get方法
	public String getProjId() {
		return projId;
	}

	//set方法
	public void setProjId(String projId) {
		this.projId = projId;
	}
	//get方法
	public String getPorjVersion() {
		return porjVersion;
	}

	//set方法
	public void setPorjVersion(String porjVersion) {
		this.porjVersion = porjVersion;
	}
	//get方法
	public String getProjBranch() {
		return projBranch;
	}

	//set方法
	public void setProjBranch(String projBranch) {
		this.projBranch = projBranch;
	}
	//get方法
	public String getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//get方法
	public Integer getTaskState() {
		return taskState;
	}

	//set方法
	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}
	//get方法
	public Date getStartTime() {
		return startTime;
	}

	//set方法
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	//get方法
	public Date getEndTime() {
		return endTime;
	}

	//set方法
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


}
