package com.kclm.xsap.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.entity.EmployeeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TeacherDTO {
    private Long id;
    @NotBlank
    private String name;

    @NotBlank
    private String phone;
    private String sex;
    @Past(message = "至少在今天之前")
    @DateTimeFormat(pattern="yyyy-MM-dd")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getRoleEmail() {
        return roleEmail;
    }

    public void setRoleEmail(String roleEmail) {
        this.roleEmail = roleEmail;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(LocalDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
