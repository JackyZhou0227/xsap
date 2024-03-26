package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.EmployeeMapper;
import com.kclm.xsap.mapper.ReservationRecordMapper;
import com.kclm.xsap.model.dto.TeacherDTO;
import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.model.vo.register.RegisterVo;
import com.kclm.xsap.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private ReservationRecordMapper reservationRecordMapper;

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
        log.debug("selectOneForLogin{}", selectOneForLogin);
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

    @Override
    public boolean isPhoneExists(String phone) {
        return baseMapper.selectCount(new QueryWrapper<EmployeeEntity>().eq("phone", phone)) > 0;
    }

    @Override
    public boolean isEmailExists(String email) {
        return baseMapper.selectCount(new QueryWrapper<EmployeeEntity>().eq("role_email", email)) > 0;
    }

    /**
     * 注册员工信息。
     *
     * @param registerVo 注册视图对象，包含用户名和密码。
     * @return 返回注册是否成功，如果插入员工信息到数据库成功，则返回true，否则返回false。
     */
    @Override
    public boolean isrRegisterSuccess(RegisterVo registerVo) {
        // 创建一个新的员工实体并设置其属性
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setRoleName(registerVo.getUserName()); // 设置用户名
        employeeEntity.setRolePassword(registerVo.getPassword()); // 设置密码
        employeeEntity.setCreateTime(LocalDateTime.now()); // 设置创建时间
        employeeEntity.setLastModifyTime(LocalDateTime.now()); // 设置最后修改时间
        // 尝试将员工实体插入数据库，并根据操作结果判断注册是否成功
        return employeeMapper.insert(employeeEntity) > 0;
    }

    /**
     * 根据关键字获取教师信息列表。
     *
     * @param keyword 关键字，用于搜索教师信息。
     * @return 返回教师信息的列表，列表中每个元素都是一个TeacherDTO对象。
     */
    @Override
    public List<TeacherDTO> getTeacherByKeyword(String keyword) {
        // 通过关键字查询员工实体列表，这里的员工实体包括了所有的教师信息
        List<EmployeeEntity> employeeEntities = employeeMapper.getTeacherByKeyword(keyword);
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        // 遍历员工实体列表，将每个实体转换为TeacherDTO对象，并添加到教师信息列表中
        for (EmployeeEntity employeeEntity : employeeEntities) {
            teacherDTOList.add(new TeacherDTO(employeeEntity));
        }
        return teacherDTOList;
    }

    @Override
    public EmployeeEntity getEmployeeByRoleName(String roleName) {
        return baseMapper.selectOne(new QueryWrapper<EmployeeEntity>().eq("role_name", roleName));
    }

    @Override
    public String getPwdById(Long id) {
        return employeeMapper.getPwdById(id);
    }

    @Override
    public boolean isAllowToDelete(Long id) {
        return reservationRecordMapper.getReservationByTeacherId(id).isEmpty();
    }

}
