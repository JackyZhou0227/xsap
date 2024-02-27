package com.kclm.xsap.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.kclm.xsap.entity.TMember;
import com.kclm.xsap.entity.MemberEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author harima
 * @since JDK11.0
 * @CreateDate 2020年9月17日 下午4:06:37
 * @description 此类用来描述了上课的记录信息
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassRecordDTO{

	/****
	 * 添加各自对应的id
	 * add by yejf
	 */
	private Long classRecordId;

	private Long courseId;

	private Long scheduleId;

	private Long memberId;

	private Long cardId;

	/**
	 *  会员信息
	 */
	private MemberEntity member;

	/**
	 * 课程名
	 */
	private String courseName;

	/**
	 * 上课准确时间
	 */
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime classTime;

	 /**
	  *	上课日期（拼接用）
	  */
	private LocalDate startDate;

	/**
	 *	上课时间（拼接用）
	 */
	private LocalTime startTime;
	/**
	 * 授课老师
	 */
	private String teacherName;

	/**
	 * 会员卡名
	 */
	private String cardName;

	/**
	 * 上课人数
	 */
	private Integer classNumbers;

	/**
	 * 消耗卡次/节
	 */
	private Integer timesCost;

	/**
	 * 	涉及金额
	 */
	private BigDecimal involveMoney;

	/**
	 * 价格（计算单价用）
	 */
	private BigDecimal price;
	/**
	 * 	次数（计算单价用）
	 */
	private BigDecimal count;

	/**
	 * 上课备注
	 */
	private String classNote;

	/**
	 * 教师评语
	 */
	private String comment;

	/**
	 * 上课状态检定。1，已上课；0，未上课
	 */
	private Integer checkStatus;

	/**
	 * 	操作记录
	 */
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime operateTime;

}
