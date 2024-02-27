package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.entity.MemberEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-15 17:57
 */

@Data
@Accessors(chain = true)
public class ClassRecordVo {
    //id
    private Long classRecordId;

    //会员卡名
    private String cardName;
    //消费次数/一节课/一个人
    private Integer timesCost;
    //参加人数
    private Integer reserveNums;
    //消费金额
    private BigDecimal involveMoney;
    //操作时间
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime operateTime;

    //会员卡实体绑定id
    private Long cardId;
    //状态
    /**
     * 用户确认上课与否。1，已上课；0，未上课
     */
    private Integer checkStatus;

    private Long memberId;
    private String memberName;
    private String memberPhone;
    private String memberSex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate memberBirthday;

//    private MemberEntity member;

    /**
     * 创建时间
     */
    private LocalDateTime createTimes;

    //上课开始时间
    private LocalDate startDate;
    private LocalTime classTime;
}
