package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-06 13:05
 */

@Data
@Accessors(chain = true)
public class MemberVo {
    private Long id;
    private String memberName;
    private String gender;
    private String[] cardHold;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;
    private String note;
//    private String[] clubCard;
//    private String remark;
}
