package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.model.vo.MemberVo;

import java.util.List;

public interface MemberService extends IService<MemberEntity> {

    List<MemberVo> getMemberVoList();

    boolean isPhoneExists(String phone);

    List<MemberEntity> searchMembersByNameOrPhone(String keyword);
}
