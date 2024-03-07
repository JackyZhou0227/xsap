package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.GlobalReservationSetMapper;
import com.kclm.xsap.model.entity.GlobalReservationSetEntity;
import com.kclm.xsap.service.GlobalReservationSetService;
import org.springframework.stereotype.Service;

@Service
public class GlobalReservationSetServiceImpl extends ServiceImpl<GlobalReservationSetMapper, GlobalReservationSetEntity>
        implements GlobalReservationSetService {

}
