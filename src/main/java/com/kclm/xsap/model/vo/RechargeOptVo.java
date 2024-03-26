package com.kclm.xsap.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class RechargeOptVo {

    private String operator;

    private Long memberId;

    @NotNull(message = "请输入绑卡id")
    private Long memberBindId;

    @NotNull(message = "请输入充值次数")
    private int addCount;

    @NotNull(message = "请输入延长有效期")
    private int addDay;

    @NotNull(message = "请输入实付金额")
    @Min(value = 0 ,message = "至少充值1元")
    private BigDecimal receivedMoney;

    @NotNull(message = "请选择支付方式")
    private String payMode;

    private String note;
}
