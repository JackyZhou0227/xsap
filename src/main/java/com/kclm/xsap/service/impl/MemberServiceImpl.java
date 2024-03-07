package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.mapper.MemberMapper;
import com.kclm.xsap.model.vo.MemberVo;
import com.kclm.xsap.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public List<MemberVo> getMemberVoList(){
        List<MemberVo> memberVoList = new ArrayList<>();
        List<MemberEntity> memberEntities = this.list();
        for (MemberEntity memberEntity : memberEntities) {
            memberVoList.add(new MemberVo(memberEntity));
        }
        return memberVoList;
    }
}
