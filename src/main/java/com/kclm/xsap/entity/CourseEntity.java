package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 课程表
 * 
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_course")
@Accessors(chain = true)
public class CourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	@NotBlank(message = "课程名不能为空")
	private String name;
	/**
	 * 课程时长
	 */
	@NotNull(message = "课程时长不能为空")
	private Long duration;
	/**
	 * 课堂容纳人数
	 */
	@TableField(value = "`contains`")
	@NotNull(message = "请填写课堂容纳人数")
	private Integer contains;
	/**
	 * 卡片颜色
	 */
	@NotBlank(message = "请选择课程颜色")
	private String color;
	/**
	 * 课程介绍
	 */
	private String introduce;
	/**
	 * 每节课程需花费的次数
	 */
	@NotNull(message = "请填写每节课消耗次数")
	private Integer timesCost;
	/**
	 * 限制性别
	 */
	private String limitSex;
	/**
	 * 限制年龄
	 */
	private Integer limitAge;
	/**
	 * 限制预约次数
	 */
	private Integer limitCounts;
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


	/**
	 * 关联的会员卡, 不是数据库的列
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private List<MemberCardEntity> cardList;

}
