package com.kclm.xsap.service;

import com.kclm.xsap.consts.KeyNameOfCache;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("mapCache")
public class MapCacheService {

    //存放会员卡统计数据的信息
    HashMap<KeyNameOfCache, Object> cacheMemberInfoMap = new HashMap<>();

    /**
     * 统计页面-会员卡统计缓存
     *
     * @return
     */
//    @Bean(name = "getMemberCardInfoMap")
    public HashMap<KeyNameOfCache, Object> getCacheInfo() {
        //存放会员卡统计信息的map
        return cacheMemberInfoMap;
    }

}