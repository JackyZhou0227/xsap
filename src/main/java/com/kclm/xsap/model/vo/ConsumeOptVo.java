package com.kclm.xsap.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ConsumeOptVo {

    @NotNull(message = "请选择一门课程！")
    private Long scheduleId;


    private String operator;

    private Long memberId;

    private Long cardBindId;


    @NotNull(message = "请填写扣除次数！")
    private Integer cardCountChange;


    @NotNull(message = "请填写消费金额！")
    private BigDecimal amountOfConsumption;

    private String note;
}
