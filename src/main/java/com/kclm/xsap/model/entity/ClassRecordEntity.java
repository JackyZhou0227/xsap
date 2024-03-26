package com.kclm.xsap.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级记录？
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
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
     * 封装会员实体数据
     */
    @TableField(exist = false)
    @ToString.Exclude
    private MemberEntity memberEntity;


    /**
     * 封装排课计划实体数据
     */
    @TableField(exist = false)
    @ToString.Exclude
    private ScheduleRecordEntity scheduleRecordEntity;

    public ClassRecordEntity() {
    }

    public ClassRecordEntity(ReservationRecordEntity reservationRecordEntity) {

        this.createTime = LocalDateTime.now();
        this.memberId = reservationRecordEntity.getMemberId();
        this.cardName = reservationRecordEntity.getCardName();
        this.scheduleId = reservationRecordEntity.getScheduleId();
        this.note = reservationRecordEntity.getNote();
        this.comment = reservationRecordEntity.getComment();
        this.checkStatus = 0;
        this.reserveCheck = reservationRecordEntity.getStatus();
        this.bindCardId = reservationRecordEntity.getCardId();

    }
}
