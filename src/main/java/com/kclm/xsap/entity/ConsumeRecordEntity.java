package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 消费记录
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_consume_record")
@Accessors(chain = true)
public class ConsumeRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 操作类型
	 */
	private String operateType;
	/**
	 * 卡次变化
	 */
	private Integer cardCountChange;
	/**
	 * 有效天数变化
	 */
	private Integer cardDayChange;
	/**
	 * 花费的金额
	 */
	private BigDecimal moneyCost;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 *
	 */
	private String note;
	/**
	 * 会员绑定id
	 */
	private Long memberBindId;

	/**
	 * 操记录id
	 */
	private Long logId;

	/**
	 * 消费的对应课程【排课】的id
	 */
	private Long scheduleId;
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
	 *  封装会员绑定实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private MemberBindRecordEntity memberBindRecordEntity;

}
