package com.kclm.xsap.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kclm.xsap.mapper.EmployeeMapper;
import com.kclm.xsap.entity.EmployeeEntity;
import com.kclm.xsap.service.EmployeeService;

import javax.annotation.Resource;


@Slf4j
@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
    /*
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EmployeeEntity> page = this.page(
                new Query<EmployeeEntity>().getPage(params),
                new QueryWrapper<EmployeeEntity>()
        );

        return new PageUtils(page);
    }
    */
    @Override
    public EmployeeEntity isExistEmp(String username, String password) {
        EmployeeEntity selectOneForLogin = this.baseMapper.selectOne(new QueryWrapper<EmployeeEntity>().eq("role_name", username).eq("role_password", password));
        log.debug("selectOneForLogin{}",selectOneForLogin);
        return selectOneForLogin;
    }

    @Override
    public boolean isUsernameExists(String username) {
        QueryWrapper<EmployeeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name", username);
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public String getTeacherNameById(Long teacherId) {

        EmployeeEntity entity = baseMapper.selectTeacherNameById(teacherId);
        String teacherName = entity.getName();
        if (entity.getIsDeleted() == 1){
            teacherName = teacherName + "(已退出)";
        }
        return teacherName;
    }

    @Override
    public List<String> getTeacherNameListByIds(List<Long> teacherIdList) {
        List<EmployeeEntity> teacherList = baseMapper.selectTeacherNameListByIds(teacherIdList);
        //获取老师name-list
        return teacherList.stream().map(teacher -> {
            if (teacher.getIsDeleted() == 1) {
                return teacher.getName() + "(已退出)";
            }
            return teacher.getName();
        }).collect(Collectors.toList());
    }


}
