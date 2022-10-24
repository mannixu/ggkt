package com.ggkts.ggkt.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggkts.ggkt.model.vod.Teacher;
import com.ggkts.ggkt.result.Result;
import com.ggkts.ggkt.vo.vod.TeacherQueryVo;
import com.ggkts.ggkt.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author mannixu
 * @since 2022-10-23
 */
@RestController
@RequestMapping("/vod/teacher")
@Api("讲师相关接口")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/findAll")
    @ApiOperation("查询所有讲师findAll")
    public Result<List<Teacher>> findAll(){
        List<Teacher> list = teacherService.list();
        return Result.ok(list);
    }

    @DeleteMapping("/deleteByid/{id}")
    @ApiOperation("根据id删除讲师")
    public Result<String> deleteById(@ApiParam(value = "被删除讲师id",required = true,defaultValue = "1") @PathVariable Long id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }


    }

    @PostMapping("/findQueryPage/{current}/{limit}")
    @ApiOperation("根据分页条件分页查询讲师对象")
    public Result<Page<Teacher>> page(@ApiParam("当前页") @PathVariable Long current,
                              @ApiParam("每页记录数") @PathVariable Long limit,
                              @ApiParam(value = "分页条件") @RequestBody TeacherQueryVo teacherQueryVo)
    {
        Page<Teacher> teacherPage = new Page<>(current,limit);
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (teacherLambdaQueryWrapper==null){
            teacherPage = teacherService.page(teacherPage);

            return Result.ok(teacherPage);
        }
        teacherLambdaQueryWrapper.like(teacherQueryVo.getName()!=null,Teacher::getName,teacherQueryVo.getName());
        teacherLambdaQueryWrapper.gt(teacherQueryVo.getJoinDateBegin()!=null,Teacher::getJoinDate,teacherQueryVo.getJoinDateBegin());
        teacherLambdaQueryWrapper.lt(teacherQueryVo.getJoinDateEnd()!=null,Teacher::getJoinDate,teacherQueryVo.getJoinDateEnd());
        teacherLambdaQueryWrapper.eq(teacherQueryVo.getLevel()!=null,Teacher::getLevel,teacherQueryVo.getLevel());
        teacherPage = teacherService.page(teacherPage, teacherLambdaQueryWrapper);
        return Result.ok(teacherPage);


    }

    //4 添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //5 修改-根据id查询
    @ApiOperation("根据id查询")
    @GetMapping("getTeacher/{id}")
    public Result getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    //6 修改-最终实现
    // {...}
    @ApiOperation("修改最终实现")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //7 批量删除讲师
    // json数组 [1,2,3]  //(json集合格式用list集合接收
    @ApiOperation("批量删除讲师")
    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = teacherService.removeByIds(idList);
        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

}

