package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.CodeQualityEvaluation;
import com.gitlab.service.CodeQualityEvaluationService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author:shenjunjie
 * @Description:CodeQualityEvaluationController构建
 * @Date:2020/04/23
 */
@Api(value = "CodeQualityEvaluationController")
@RestController
@RequestMapping("/projects/codeQualityEvaluation")
@CrossOrigin
public class CodeQualityEvaluationController {

    @Autowired
    private CodeQualityEvaluationService codeQualityEvaluationService;

    /***
     * CodeQualityEvaluation分页条件搜索实现
     * @param codeQualityEvaluation
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "CodeQualityEvaluation条件分页查询",notes = "分页条件查询CodeQualityEvaluation方法详情",tags = {"CodeQualityEvaluationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "CodeQualityEvaluation对象",value = "传入JSON数据",required = false) CodeQualityEvaluation codeQualityEvaluation, @PathVariable  int page, @PathVariable  int size) {
        //调用CodeQualityEvaluationService实现分页条件查询CodeQualityEvaluation
        PageInfo<CodeQualityEvaluation> pageInfo = codeQualityEvaluationService.findPage(codeQualityEvaluation, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * CodeQualityEvaluation分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "CodeQualityEvaluation分页查询",notes = "分页查询CodeQualityEvaluation方法详情",tags = {"CodeQualityEvaluationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size) {
        //调用CodeQualityEvaluationService实现分页查询CodeQualityEvaluation
        PageInfo<CodeQualityEvaluation> pageInfo = codeQualityEvaluationService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索codeQualityEvaluation数据
     * @param codeQualityEvaluation
     * @return
     */
    @ApiOperation(value = "CodeQualityEvaluation条件查询",notes = "条件查询CodeQualityEvaluation方法详情",tags = {"CodeQualityEvaluationController"})
    @PostMapping(value = "/search" )
    public Result<List<CodeQualityEvaluation>> findList(@RequestBody(required = false) @ApiParam(name = "CodeQualityEvaluation对象",value = "传入JSON数据",required = false) CodeQualityEvaluation codeQualityEvaluation) {
        //调用CodeQualityEvaluationService实现条件查询CodeQualityEvaluation
        List<CodeQualityEvaluation> list = codeQualityEvaluationService.findList(codeQualityEvaluation);
        return new Result<List<CodeQualityEvaluation>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除codeQualityEvaluation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "CodeQualityEvaluation根据ID删除",notes = "根据ID删除CodeQualityEvaluation方法详情",tags = {"CodeQualityEvaluationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id) {
        //调用CodeQualityEvaluationService实现根据主键删除
        codeQualityEvaluationService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改CodeQualityEvaluation数据
     * @param codeQualityEvaluation
     * @param id
     * @return
     */
    @ApiOperation(value = "CodeQualityEvaluation根据ID修改",notes = "根据ID修改CodeQualityEvaluation方法详情",tags = {"CodeQualityEvaluationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "CodeQualityEvaluation对象",value = "传入JSON数据",required = false) CodeQualityEvaluation codeQualityEvaluation,@PathVariable String id) {
        //设置主键值
        codeQualityEvaluation.setTaskId(id);
        //调用CodeQualityEvaluationService实现修改CodeQualityEvaluation
        codeQualityEvaluationService.update(codeQualityEvaluation);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增CodeQualityEvaluation数据
     * @param codeQualityEvaluation
     * @return
     */
    @ApiOperation(value = "CodeQualityEvaluation添加",notes = "添加CodeQualityEvaluation方法详情",tags = {"CodeQualityEvaluationController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "CodeQualityEvaluation对象",value = "传入JSON数据",required = true) CodeQualityEvaluation codeQualityEvaluation) {
        //调用CodeQualityEvaluationService实现添加CodeQualityEvaluation
        codeQualityEvaluationService.add(codeQualityEvaluation);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询CodeQualityEvaluation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "CodeQualityEvaluation根据ID查询",notes = "根据ID查询CodeQualityEvaluation方法详情",tags = {"CodeQualityEvaluationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public Result<CodeQualityEvaluation> findById(@PathVariable String id) {
        //调用CodeQualityEvaluationService实现根据主键查询CodeQualityEvaluation
        CodeQualityEvaluation codeQualityEvaluation = codeQualityEvaluationService.findById(id);
        return new Result<CodeQualityEvaluation>(true,StatusCode.OK,"查询成功",codeQualityEvaluation);
    }

    /***
     * 查询CodeQualityEvaluation全部数据
     * @return
     */
    @ApiOperation(value = "查询所有CodeQualityEvaluation",notes = "查询所CodeQualityEvaluation有方法详情",tags = {"CodeQualityEvaluationController"})
    @GetMapping
    public Result<List<CodeQualityEvaluation>> findAll() {
        //调用CodeQualityEvaluationService实现查询所有CodeQualityEvaluation
        List<CodeQualityEvaluation> list = codeQualityEvaluationService.findAll();
        return new Result<List<CodeQualityEvaluation>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
