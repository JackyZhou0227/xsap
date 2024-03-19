package com.kclm.xsap.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ReserveInfoVo {
    private String courseName;
    private LocalDateTime reserveTime;
    private String cardName;
    private int reserveNumbers;
    private int timesCost;
    private LocalDateTime operateTime;
    private String operator;
    private String reserveNote;
    private int reserveStatus;
}
