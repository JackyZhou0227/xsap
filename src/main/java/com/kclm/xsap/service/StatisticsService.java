package com.kclm.xsap.service;


import com.kclm.xsap.model.vo.MemberCardStatisticsWithTotalDataInfoVo;
import com.kclm.xsap.model.vo.statistics.CardCostVo;
import com.kclm.xsap.model.vo.statistics.ClassCostVo;
import com.kclm.xsap.model.vo.statistics.StatisticsOfCardCostVo;

public interface StatisticsService {
    MemberCardStatisticsWithTotalDataInfoVo getCardInfoStatistics();

    CardCostVo getcardCostVo(StatisticsOfCardCostVo statisticsOfCardCostVo);

    ClassCostVo getclassCostVo(StatisticsOfCardCostVo statisticsOfCardCostVo);

    CardCostVo getCountCostVo(StatisticsOfCardCostVo statisticsOfCardCostVo);

    CardCostVo getCountNum(StatisticsOfCardCostVo statisticsOfCardCostVo);

}
