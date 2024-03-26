package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.model.vo.CardTipVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberBindRecordMapper extends BaseMapper<MemberBindRecordEntity> {

    List<Long> getBindCardIdsByMemberId(Long memberId);

    CardTipVo getCardTip(Long bindId, Long scheduleId);

    boolean addCountAndDay(Long memberBindId, int addCount, int addDay);

    boolean deductCount(Long memberBindId, int deductCount);

    List<MemberBindRecordEntity> getUsableBindRecordByCardId(Long memberId);

    MemberCardEntity getCardIdByBindId(Long id);
}
