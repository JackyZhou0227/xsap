package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 中间表：会员绑定记录
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */
@Data
@TableName("t_member_bind_record")
@Accessors(chain = true)
public class MemberBindRecordEntity implements Serializable {
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
	 * 会员卡id
	 */
	@NotNull(message = "请选择会员卡！")
	private Long cardId;
	/**
	 * 可使用次数
	 */
	@NotNull(message = "请输入要充值的次数")
	private Integer validCount;
	/**
	 * 有效期，按天算
	 */
	@NotNull(message = "请填入有效期")
	private Integer validDay;
	/**
	 * 实收金额
	 */
	@NotNull(message = "请输入实收金额")
	private BigDecimal receivedMoney;
	/**
	 * 支付方式
	 */
	@NotBlank(message = "请选择支付方式")
	private String payMode;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 激活状态，1激活，0非激活
	 */
	private Integer activeStatus;
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
