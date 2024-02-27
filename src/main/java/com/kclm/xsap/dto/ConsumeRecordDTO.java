package com.kclm.xsap.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author harima
 * @since JDK11.0
 * @CreateDate 2020年9月17日 下午4:21:42 
 * @description 此类用来描述了消费记录
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ConsumeRecordDTO{

	private Long consumeId;

	private Long memberBindId;

	/**
	 * 会员卡名
	 */
	private String cardName;
	
	/**
	 * 操作时间
	 */
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime operateTime;
	
	/**
	 * 操作类型
	 */
	private String operateType;
	
	/**
	 * 卡次变化
	 */
	private Integer cardCountChange;
	
	/**
	 * 剩下次数
	 */
	private Integer timesRemainder;
	
	/**
	 * 花费的金额
	 */
	private BigDecimal moneyCost;
	
	/**
	 * 操作员
	 */
	private String operator;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 激活状态。1，已激活；0，未激活
	 */
	private Integer status;
	
}
