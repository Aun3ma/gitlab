package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.ErrorLine;
import com.gitlab.service.ErrorLineService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author:fuyunkai
 * @Description:ErrorLineController构建
 * @Date:2020/05/19
 */
@Api(value = "ErrorLineController")
@RestController
@RequestMapping("/projects/errorLine")
@CrossOrigin
public class ErrorLineController {

    @Autowired
    private ErrorLineService errorLineService;

    /***
     * ErrorLine分页条件搜索实现
     * @param errorLine
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "ErrorLine条件分页查询",notes = "分页条件查询ErrorLine方法详情",tags = {"ErrorLineController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "ErrorLine对象",value = "传入JSON数据",required = false) ErrorLine errorLine, @PathVariable  int page, @PathVariable  int size) {
        //调用ErrorLineService实现分页条件查询ErrorLine
        PageInfo<ErrorLine> pageInfo = errorLineService.findPage(errorLine, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * ErrorLine分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "ErrorLine分页查询",notes = "分页查询ErrorLine方法详情",tags = {"ErrorLineController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size) {
        //调用ErrorLineService实现分页查询ErrorLine
        PageInfo<ErrorLine> pageInfo = errorLineService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索errorLine数据
     * @param errorLine
     * @return
     */
    @ApiOperation(value = "ErrorLine条件查询",notes = "条件查询ErrorLine方法详情",tags = {"ErrorLineController"})
    @PostMapping(value = "/search" )
    public Result<List<ErrorLine>> findList(@RequestBody(required = false) @ApiParam(name = "ErrorLine对象",value = "传入JSON数据",required = false) ErrorLine errorLine) {
        //调用ErrorLineService实现条件查询ErrorLine
        List<ErrorLine> list = errorLineService.findList(errorLine);
        return new Result<List<ErrorLine>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除errorLine数据
     * @param id
     * @return
     */
    @ApiOperation(value = "ErrorLine根据ID删除",notes = "根据ID删除ErrorLine方法详情",tags = {"ErrorLineController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id) {
        //调用ErrorLineService实现根据主键删除
        errorLineService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改ErrorLine数据
     * @param errorLine
     * @param id
     * @return
     */
    @ApiOperation(value = "ErrorLine根据ID修改",notes = "根据ID修改ErrorLine方法详情",tags = {"ErrorLineController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "ErrorLine对象",value = "传入JSON数据",required = false) ErrorLine errorLine,@PathVariable Integer id) {
        //设置主键值
        errorLine.setErrorId(id);
        //调用ErrorLineService实现修改ErrorLine
        errorLineService.update(errorLine);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增ErrorLine数据
     * @param errorLine
     * @return
     */
    @ApiOperation(value = "ErrorLine添加",notes = "添加ErrorLine方法详情",tags = {"ErrorLineController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "ErrorLine对象",value = "传入JSON数据",required = true) ErrorLine errorLine) {
        //调用ErrorLineService实现添加ErrorLine
        errorLineService.add(errorLine);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询ErrorLine数据
     * @param id
     * @return
     */
    @ApiOperation(value = "ErrorLine根据ID查询",notes = "根据ID查询ErrorLine方法详情",tags = {"ErrorLineController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Result<ErrorLine> findById(@PathVariable Integer id) {
        //调用ErrorLineService实现根据主键查询ErrorLine
        ErrorLine errorLine = errorLineService.findById(id);
        return new Result<ErrorLine>(true,StatusCode.OK,"查询成功",errorLine);
    }

    /***
     * 查询ErrorLine全部数据
     * @return
     */
    @ApiOperation(value = "查询所有ErrorLine",notes = "查询所ErrorLine有方法详情",tags = {"ErrorLineController"})
    @GetMapping
    public Result<List<ErrorLine>> findAll() {
        //调用ErrorLineService实现查询所有ErrorLine
        List<ErrorLine> list = errorLineService.findAll();
        return new Result<List<ErrorLine>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
