package com.kclm.xsap.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.model.entity.CourseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
public class MemberCardDTO {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    @NotBlank(message = "会员卡名不能为空")
    private String name;
    /**
     *
     */
    @NotNull(message = "卡价格不能为空")
    private BigDecimal price;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 备注信息
     */
    private String note;
    /**
     * 会员卡类型
     */
    @NotBlank(message = "卡类型不能为空")
    private String type;
    /**
     * 默认可用次数
     */
    @NotNull(message = "请输入可用次数")
    private Integer totalCount;
    /**
     * 默认可用天数
     */
    @NotNull(message = "请输入可用天数")
    private Integer totalDay;
    /**
     * 激活状态，0激活，1非激活
     */
    private Integer status;
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

    @TableField(exist = false)
    private LocalDateTime dueTime;

    /**
     * 关联的课程id
     */
    @ToString.Exclude
    private List<Long> courseListStr;
}
