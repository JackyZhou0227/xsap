package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.entity.MemberCardEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-20 9:56
 */
@Data
@Accessors(chain = true)
public class CardInfoVo {

    /**
     * 绑定id
     */
    private Long bindId;
    /**
     * 会员卡名
     */
    private String name;
    /**
     * 卡类型
     */
    private String type;
    /**
     * 可用次数
     */
    private Integer totalCount;
    /**
     * 有效期
     */
    private Integer validDay;

    /**
     * 到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime dueTime;
    /**
     * 激活状态
     */
    private Integer activeStatus;

    /**
     *
     */
    private LocalDateTime createTime;
    /**
     *
     */
    private LocalDateTime lastModifyTime;
}