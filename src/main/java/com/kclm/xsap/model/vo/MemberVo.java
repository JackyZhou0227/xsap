package com.kclm.xsap.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.model.entity.MemberEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

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
    private String cardHold;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;
    private String note;
//    private String[] clubCard;
//    private String remark;

    public MemberVo(){
    }

    public MemberVo(MemberEntity memberEntity){
        this.id = memberEntity.getId();
        this.memberName = String.format("%s(%s)", memberEntity.getName(), memberEntity.getPhone());;
        this.gender = memberEntity.getSex();
        this.note = memberEntity.getNote();
        this.joiningDate = LocalDate.from(memberEntity.getCreateTime());
    }

    @Override
    public String toString() {
        return "MemberVo{" +
                "id=" + id +
                ", memberName='" + memberName + '\'' +
                ", gender='" + gender + '\'' +
                ", cardHold=" + cardHold +
                ", joiningDate=" + joiningDate +
                ", note='" + note + '\'' +
                '}';
    }
}
