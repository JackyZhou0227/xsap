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
 * 预约记录
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_reservation_record")
@Accessors(chain = true)
public class ReservationRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 预约状态，1有效，0无效
	 */
	private Integer status;
	/**
	 * 单次操作预约人数
	 */
	private Integer reserveNums;
	/**
	 * 取消次数统计
	 */
	private Integer cancelTimes;
	/**
	 * 教师评语
	 */
	private String comment;
	/**
	 * 备注信息
	 */
	private String note;
	/**
	 * 上课备注
	 */
	private String classNote;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * 会员kaid
	 */
//	@TableField(exist = false)
	private Long cardId;
	/**
	 * 会员指定的会员卡来预约
	 */
	private String cardName;
	/**
	 * 排课记录id
	 */
	private Long scheduleId;
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;
	/**
	 * 最后修改时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime lastModifyTime;
	/**
	 * 版本
	 */
	private Integer version = 1;


	/**
	 *  封装排课计划实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private ScheduleRecordEntity scheduleRecordEntity;

	/**
	 *  封装会员实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private MemberEntity memberEntity;

	/**
	 * 封装预约课程实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private CourseEntity courseEntity;



}
