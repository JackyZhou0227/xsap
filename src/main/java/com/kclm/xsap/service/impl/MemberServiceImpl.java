package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.MemberMapper;
import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.model.vo.MemberVo;
import com.kclm.xsap.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public List<MemberVo> getMemberVoList() {
        return memberMapper.listAllMemberVo();
    }

    @Override
    public boolean isPhoneExists(String phone) {
        return baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("phone", phone)) > 0;
    }

    @Override
    public List<MemberEntity> searchMembersByNameOrPhone(String keyword) {
        return memberMapper.searchMembersByNameOrPhone(keyword);
    }

    @Override
    public boolean isAllowToDelete(Long memberId) {
        return baseMapper.getReservationByMemberId(memberId).isEmpty();
    }

    @Override
    public List<LocalDateTime> delTimelist() {
        return  baseMapper.delTimeList();
    }
}
