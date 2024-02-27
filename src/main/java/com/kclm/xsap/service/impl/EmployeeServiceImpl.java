package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kclm.xsap.dao.EmployeeDao;
import com.kclm.xsap.entity.EmployeeEntity;
import com.kclm.xsap.service.EmployeeService;


@Slf4j
@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, EmployeeEntity> implements EmployeeService {

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
        EmployeeEntity selectOneForLogin = this.baseMapper.selectOne(new QueryWrapper<EmployeeEntity>().eq("name", username).eq("role_password", password));
        log.debug("selectOneForLogin{}",selectOneForLogin);
        return selectOneForLogin;
    }

    @Override
    public String getTeacherNameById(Long teacherId) {

        EmployeeEntity entity = baseMapper.selectTeacherNameById(teacherId);
        String teacherName = entity.getName();
        if (entity.getIsDeleted() == 1) {
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
