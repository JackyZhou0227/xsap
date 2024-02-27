package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

/**
 * 员工表
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_employee")
@Accessors(chain = true)
public class EmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *
	 */
	@NotBlank
	private String name;
	/**
	 * 用来登录？？？？？
	 */
	@NotBlank
	private String phone;
	/**
	 *
	 */
	private String sex;
	/**
	 *
	 */
	@Past(message = "至少在今天之前")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday;
	/**
	 * 介绍
	 */
	private String introduce;
	/**
	 * 头像文件路径
	 */
	private String avatarUrl;
	/**
	 *
	 */
	private String note;
	/**
	 * 操作角色名，暂不使用
	 */
	private String roleName;
	/**
	 * 操作角色密码
	 */
	private String rolePassword;
	/**
	 * 操作角色类型，1，超级管理员；0，普通管理员
	 */
	private Integer roleType;
	/**
	 * 操作角色邮箱
	 */
	@NotBlank
	@Email(message = "请输入正确的邮箱格式")
	private String roleEmail;
	/**
	 * 逻辑删除，1有效，0无效
	 */
	private Integer isDeleted;
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
	private Integer version = 1;

}
