package com.kclm.xsap.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员表
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_member")
@Accessors(chain = true)
public class MemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    @NotBlank(message = "用户名不能为空")
    private String name;
    /**
     *
     */
    @NotBlank(message = "性别不能为空")
    private String sex;
    /**
     *
     */
    @NotBlank(message = "用户手机号码不能为空")
    private String phone;
    /**
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "用户出生日期不需要在今天之间")
    private LocalDate birthday;
    /**
     * 备注信息
     */
    private String note;
    /**
     * 用户头像路径
     */
    private String avatarUrl;
    /**
     * 用户的逻辑删除，1有效，0无效
     */
    private Integer isDeleted;
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

}
