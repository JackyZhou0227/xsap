package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
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
 * 会员卡表
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_member_card")
@Accessors(chain = true)
public class MemberCardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *
	 */
	@NotBlank(message = "会员卡名不能为空")
	private String name;
	/**
	 *
	 */
	@NotNull(message = "卡价格不能为空")
	private BigDecimal price;
	/**
	 * 描述信息
	 */
	private String description;
	/**
	 * 备注信息
	 */
	private String note;
	/**
	 * 会员卡类型
	 */
	@NotBlank(message = "卡类型不能为空")
	private String type;
	/**
	 * 默认可用次数
	 */
	@NotNull(message = "请输入可用次数")
	private Integer totalCount;
	/**
	 * 默认可用天数
	 */
	@NotNull(message = "请输入可用天数")
	private Integer totalDay;
	/**
	 * 激活状态，0激活，1非激活
	 */
	private Integer status;
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
	 * 关联的课程
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private List<CourseEntity> courseList;


	@TableField(exist = false)
	private LocalDateTime dueTime;



}
