package com.kclm.xsap.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.consts.OperateType;
import com.kclm.xsap.model.vo.ConsumeOptVo;
import com.kclm.xsap.model.vo.RechargeOptVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 消费记录
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
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
     * 封装会员绑定实体数据
     */
    @TableField(exist = false)
    @ToString.Exclude
    private MemberBindRecordEntity memberBindRecordEntity;

    public ConsumeRecordEntity() {
    }

    public ConsumeRecordEntity(RechargeOptVo rechargeOptVo) {
        this.createTime = LocalDateTime.now();
        this.setOperateType(OperateType.RECHARGE_OPERATION.getMsg());
        this.operator = rechargeOptVo.getOperator();
        this.memberBindId = rechargeOptVo.getMemberBindId();
        this.cardCountChange = rechargeOptVo.getAddCount();
        this.cardDayChange = rechargeOptVo.getAddDay();
        this.moneyCost = rechargeOptVo.getReceivedMoney();
        this.note = rechargeOptVo.getNote();

    }

    public ConsumeRecordEntity(ConsumeOptVo consumeOptVo) {
        this.createTime = LocalDateTime.now();
        this.operateType = OperateType.CLASS_DEDUCTION_OPERATION.getMsg();
        this.operator = consumeOptVo.getOperator();
        this.memberBindId = consumeOptVo.getCardBindId();
        this.cardCountChange = consumeOptVo.getCardCountChange();
        this.moneyCost = consumeOptVo.getAmountOfConsumption();
        this.note = consumeOptVo.getNote();
        this.scheduleId = consumeOptVo.getScheduleId();
    }

    public ConsumeRecordEntity(MemberBindRecordEntity memberBindRecordEntity) {
        this.createTime = LocalDateTime.now();
        this.setOperateType(OperateType.BINDING_CARD_OPERATION.getMsg());
        this.memberBindId = memberBindRecordEntity.getId();
        this.moneyCost = memberBindRecordEntity.getReceivedMoney();
        this.cardCountChange = memberBindRecordEntity.getValidCount();
        this.cardDayChange = memberBindRecordEntity.getValidDay();
        this.note = memberBindRecordEntity.getNote();
    }

    public ConsumeRecordEntity(ClassRecordEntity classRecordEntity) {
        this.createTime = LocalDateTime.now();
        this.operateType = OperateType.CLASS_DEDUCTION_OPERATION.getMsg();
        this.memberBindId = classRecordEntity.getBindCardId();
        this.scheduleId = classRecordEntity.getScheduleId();
    }
}
