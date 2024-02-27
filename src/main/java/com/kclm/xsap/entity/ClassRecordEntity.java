package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 班级记录？
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_class_record")
@Accessors(chain = true)
public class ClassRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * 会员卡名
	 */
	private String cardName;
	/**
	 * 排课记录id
	 */
	private Long scheduleId;
	/**
	 *
	 */
	private String note;
	/**
	 * 教师评语
	 */
	private String comment;
	/**
	 * 用户确认上课与否。1，已上课；0，未上课
	 */
	private Integer checkStatus;
	/**
	 * 是否已预约，默认0
	 */
	private Integer reserveCheck;
	/**
	 * 会员绑定id
	 */
	private Long bindCardId;
	/**
	 *
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;
	/**
	 *
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime lastModifyTime;
	/**
	 *
	 */
	private Integer version = 1;

	/**
	 *  封装会员实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private MemberEntity memberEntity;


	/**
	 *  封装排课计划实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private ScheduleRecordEntity scheduleRecordEntity;

}
