package com.gitlab.projects.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
/**
 * @Author:fuyunkai
 * @Description:ErrorLine构建
 * @Date:2020/05/19
 */

@ApiModel(description = "ErrorLine",value = "ErrorLine")
@Table(name="tb_error_line")
public class ErrorLine implements Serializable{

	@ApiModelProperty(value = "自增主键",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "error_id")
	private Integer errorId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "task_id")
	private String taskId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "file_id")
	private String fileId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "error_line")
	private Integer errorLine;//

	@ApiModelProperty(value = "",required = false)
	@Column(name = "ml_predicted_result")
	private String mlPredictedResult;//

	//get方法
	public Integer getErrorId() {
		return errorId;
	}

	//set方法
	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
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
	public String getFileId() {
		return fileId;
	}

	//set方法
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	//get方法
	public Integer getErrorLine() {
		return errorLine;
	}

	//set方法
	public void setErrorLine(Integer errorLine) {
		this.errorLine = errorLine;
	}

	//get方法
	public String getMlPredictedResult() {
		return mlPredictedResult;
	}

	//set方法
	public void setMlPredictedResult(String mlPredictedResult) {
		this.mlPredictedResult = mlPredictedResult;
	}

}
