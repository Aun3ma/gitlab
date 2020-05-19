package com.gitlab.projects.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
/**
 * @Author:shenjunjie
 * @Description:ModuleInformation构建
 * @Date:2020/04/23
 */

@ApiModel(description = "ModuleInformation",value = "ModuleInformation")
@Table(name="tb_module_information")
public class ModuleInformation implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @Column(name = "module_id")
	private String moduleId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "file_id")
	private String fileId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "module_name")
	private String moduleName;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "pmd_url")
	private String pmdUrl;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "sonarqube_url")
	private String sonarqubeUrl;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "ml_predicted_result")
	private String mlPredictedResult;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "spare_item")
	private String spareItem;//



	//get方法
	public String getModuleId() {
		return moduleId;
	}

	//set方法
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	//get方法
	public String getFileId() {
		return fileId;
	}

	//set方法
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	//get方法
	public String getModuleName() {
		return moduleName;
	}

	//set方法
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	//get方法
	public String getPmdUrl() {
		return pmdUrl;
	}

	//set方法
	public void setPmdUrl(String pmdUrl) {
		this.pmdUrl = pmdUrl;
	}
	//get方法
	public String getSonarqubeUrl() {
		return sonarqubeUrl;
	}

	//set方法
	public void setSonarqubeUrl(String sonarqubeUrl) {
		this.sonarqubeUrl = sonarqubeUrl;
	}
	//get方法
	public String getMlPredictedResult() {
		return mlPredictedResult;
	}

	//set方法
	public void setMlPredictedResult(String mlPredictedResult) {
		this.mlPredictedResult = mlPredictedResult;
	}
	//get方法
	public String getSpareItem() {
		return spareItem;
	}

	//set方法
	public void setSpareItem(String spareItem) {
		this.spareItem = spareItem;
	}


}
