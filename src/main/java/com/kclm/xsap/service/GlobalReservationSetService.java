package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.consts.ReservationAction;
import com.kclm.xsap.model.entity.GlobalReservationSetEntity;

public interface GlobalReservationSetService extends IService<GlobalReservationSetEntity> {
    boolean isNotInPrescriptiveTime(Long id, ReservationAction action);
}
