package com.kclm.xsap.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.model.entity.EmployeeEntity;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class TeacherDTO {
    private Long id;
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private String sex;

    @Past(message = "至少在今天之前")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String introduce;

    private String avatarUrl;

    private String note;

    private Integer roleType;

    @NotBlank
    @Email(message = "请输入正确的邮箱格式")
    private String roleEmail;

    private Integer isDeleted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastModifyTime;

    private Integer version = 1;

    public TeacherDTO() {
    }

    public TeacherDTO(EmployeeEntity employeeEntity) {
        this.id = employeeEntity.getId();
        this.name = employeeEntity.getName();
        this.phone = employeeEntity.getPhone();
        this.sex = employeeEntity.getSex();
        this.birthday = employeeEntity.getBirthday();
        this.introduce = employeeEntity.getIntroduce();
        this.avatarUrl = employeeEntity.getAvatarUrl();
        this.note = employeeEntity.getNote();
        this.roleType = employeeEntity.getRoleType();
        this.roleEmail = employeeEntity.getRoleEmail();
        this.createTime = employeeEntity.getCreateTime();
        this.lastModifyTime = employeeEntity.getLastModifyTime();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public void setRoleEmail(String roleEmail) {
        this.roleEmail = roleEmail;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setLastModifyTime(LocalDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
