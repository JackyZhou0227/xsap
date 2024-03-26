package com.kclm.xsap.consts;


import lombok.Getter;

@Getter
public enum KeyNameOfCache {
    //课程表页面list的缓存信息的key    CACHE_SCHEDULE_INFO
    CACHE_SCHEDULE_INFO("SCHEDULE_INFO"),
    //统计页面会员卡统计数据的缓存信息的key  MEMBER_CARD_INFO
    CACHE_OF_MEMBER_CARD_INFO("MEMBER_CARD_INFO"),
    CACHE_OF_CLASS_COST_INFO("课消记录当前总记录数"),

    TEMP1("一个临时测试常量"),
    TEMP2("一个临时测试常量"),
    TEMP3("一个临时测试常量");

    private final String msg;

    KeyNameOfCache(String msg) {
        this.msg = msg;
    }

}

