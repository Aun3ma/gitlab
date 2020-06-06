package com.gitlab.projects.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
/**
 * @Author:shenjunjie
 * @Description:ProjectInformation构建
 * @Date:2020/04/23
 */

@ApiModel(description = "ProjectInformation",value = "ProjectInformation")
@Table(name="tb_project_information")
public class ProjectInformation implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @Column(name = "proj_id")
	private String projId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "proj_name")
	private String projName;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "owner_user_id")
	private String ownerUserId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "visibility")
	private String visibility;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "gitlab_repo_handle")
	private String gitlabRepoHandle;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "create_time")
	private Date createTime;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "update_time")
	private Date updateTime;//

	@ApiModelProperty(value = "",required = false)
	@Column(name = "description")
	private String description;//


	//get方法
	public String getProjId() {
		return projId;
	}

	//set方法
	public void setProjId(String projId) {
		this.projId = projId;
	}
	//get方法
	public String getProjName() {
		return projName;
	}

	//set方法
	public void setProjName(String projName) {
		this.projName = projName;
	}
	//get方法
	public String getOwnerUserId() {
		return ownerUserId;
	}

	//set方法
	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	//get方法
	public String getVisibility() {
		return visibility;
	}

	//set方法
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	//get方法
	public String getGitlabRepoHandle() {
		return gitlabRepoHandle;
	}

	//set方法
	public void setGitlabRepoHandle(String gitlabRepoHandle) {
		this.gitlabRepoHandle = gitlabRepoHandle;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法
	public Date getUpdateTime() {
		return updateTime;
	}

	//set方法
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	//get方法
	public String getProjectDescription() {
		return description;
	}

	//set方法
	public void setProjectDescription(String description) {
		this.description = description;
	}

}
