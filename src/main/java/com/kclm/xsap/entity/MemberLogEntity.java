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
 * 操作记录
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_member_log")
@Accessors(chain = true)
public class MemberLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 操作类型
	 */
	private String type;
	/**
	 * 影响的金额
	 */
	private BigDecimal involveMoney;
	/**
	 * 操作员名称
	 */
	private String operator;
	/**
	 * 会员绑定id
	 */
	private Long memberBindId;
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;
	/**
	 * 最后一次修改时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime lastModifyTime;
	/**
	 * 版本
	 */
	private Integer version = 1;

	/**
	 * 单次操作的卡次变化
	 */
	private Integer cardCountChange;
	/**
	 * 单次操作的天数变化
	 */
	private Integer cardDayChange;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 单次操作时卡的状态，默认是1，表示已激活
	 */
	private Integer cardActiveStatus;

	/**
	 *  封装会员绑定实体数据
	 */
	@TableField(exist = false)
	@ToString.Exclude
	private MemberBindRecordEntity memberBindRecordEntity;

}
