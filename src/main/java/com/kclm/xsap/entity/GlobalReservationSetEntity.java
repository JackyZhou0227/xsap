package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 全局预约设置表	此类用来描述了全局预约条件的设置
 * 全局预约设置在数据库的记录永远只有第一条，后续的更改操作，都是对第一条的更新
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_global_reservation_set")
@Accessors(chain = true)
public class GlobalReservationSetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;

	/**
	 * 预约开始时间:可提前预约的天数
	 */
	private Integer startDay;

	/**
	 * 预约截止时间：模式2：提前预约截止小时数，离上课前
	 */
	private Integer endHour;
	/**
	 * 预约截止时间：模式3：提前预约截止天数，上课前
	 */
	private Integer endDay;
	/**
	 * 预约截止时间：模式3：提前预约截止时间(24小时内)，上课前
	 */
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;

	/**
	 * 预约取消时间：模式2：提前预约取消的距离小时数
	 */
	private Integer cancelHour;
	/**
	 * 预约取消时间：模式3：提前预约取消的距离天数
	 */
	private Integer cancelDay;
	/**
	 * 预约取消时间：模式3：提前预约取消的时间限制（24小时内）
	 */
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime cancelTime;

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
	private Integer version= 1;

	//以下是修改增加的三个属性
	/**
	 * 预约开始时间模式
	 */
	private Integer appointmentStartMode;
	/**
	 * 预约截止时间模式
	 */
	private Integer appointmentDeadlineMode;
	/**
	 * 预约取消时间模式
	 */
	private Integer appointmentCancelMode;

}
