package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.dto.TeacherDTO;
import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.model.vo.TeacherClassRecordVo;
import com.kclm.xsap.model.vo.register.RegisterVo;

import java.util.List;

/**
 * 员工表
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
public interface EmployeeService extends IService<EmployeeEntity> {

    //PageUtils queryPage(Map<String, Object> params);

    EmployeeEntity isExistEmp(String username, String password);

    boolean isUsernameExists(String username);

    /**
     * 根据老师Id查询老师名字【包括已经删除的老师】
     * @param teacherId
     * @return
     */
    String getTeacherNameById(Long teacherId);

    /**
     * g根据批量id批量查询老师信息
     * @param teacherIdList
     * @return
     */
    List<String> getTeacherNameListByIds(List<Long> teacherIdList);

    boolean isPhoneExists(String phone);

    boolean isEmailExists(String email);

    boolean isrRegisterSuccess(RegisterVo registerVo);

    List<TeacherDTO> getTeacherByKeyword(String keyword);
}

