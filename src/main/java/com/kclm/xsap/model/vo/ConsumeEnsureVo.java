package com.kclm.xsap.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ConsumeEnsureVo {

    private Long classId;

    private Long memberId;

    private Long cardBindId;

    private Long scheduleId;

    private String operator;

    @NotNull(message = "请填写扣除次数！")
    private Integer cardCountChange;

    @NotNull(message = "请填写消费金额！")
    private BigDecimal amountOfConsumption;

    @NotNull(message = "请填写扣除有效期！")
    private Integer cardDayChange;

    private String note;
}
