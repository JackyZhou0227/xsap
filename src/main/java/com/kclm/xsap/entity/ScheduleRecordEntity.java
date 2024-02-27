package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * 中间表：排课计划表
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_schedule_record")
@Accessors(chain = true)
public class ScheduleRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 课程号
	 */
	@NotNull(message = "请选择课程")
	private Long courseId;
	/**
	 * 课程时长
	 */
	@TableField(exist = false)
	private Long courseDuration;
	/**
	 * 教师号
	 */
	@NotNull(message = "请选择上课老师")
	private Long teacherId;
	/**
	 * 此项排课的预约人数
	 */
	private Integer orderNums;
	/**
	 * 上课日期
	 */
	@NotNull(message = "请确定上课日期")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	/**
	 * 上课时间
	 */
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime classTime;
	/**
	 * 限制性别
	 */
	private String limitSex;
	/**
	 * 限制年龄
	 */
	private Integer limitAge;
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime lastModifyTime;
	/**
	 * 版本
	 */
	private Integer version=1;

	/**
	 *  封装课程实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private CourseEntity courseEntity;


	/**
	 *  封装教师实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private EmployeeEntity employeeEntity;

}
