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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 充值记录
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_recharge_record")
@Accessors(chain = true)
public class RechargeRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 充值可用次数
	 */
	@NotNull(message = "请输入充值次数")
	private Integer addCount;
	/**
	 * 延长有效天数
	 */
	@NotNull(message = "请输入演唱天数")
	private Integer addDay;
	/**
	 * 实收金额
	 */
	@NotNull(message = "请输入实收金额")
	private BigDecimal receivedMoney;
	/**
	 * 支付方式
	 */
	@NotBlank(message = "请确认支付方式")
	private String payMode;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 备注信息
	 */
	private String note;
	/**
	 * 会员绑定id
	 */
	private Long memberBindId;
	/**创建时间
	 *
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

	private Long logId;


}
