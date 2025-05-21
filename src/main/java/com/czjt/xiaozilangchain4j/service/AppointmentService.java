package com.czjt.xiaozilangchain4j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.czjt.xiaozilangchain4j.pojo.Appointment;

public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);
}
