package com.kclm.xsap.dao;

import com.kclm.xsap.entity.EmployeeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工表
 * 
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
@Mapper
public interface EmployeeDao extends BaseMapper<EmployeeEntity> {

    /*这应该是一个通用方法，应该查完整信息，但这里只是查老师名字，---以后少用mybatis plus*/
    @Select("select `name`, `is_deleted` from t_employee where `id` = #{teacherId}")
    EmployeeEntity selectTeacherNameById(@Param("teacherId") Long teacherId);

    /*
    根据idList批量查询老师信息
     */
    List<EmployeeEntity> selectTeacherNameListByIds(@Param("teacherIdList") List<Long> teacherIdList);
}
