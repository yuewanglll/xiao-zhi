package com.czjt.xiaozilangchain4j.tools;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.czjt.xiaozilangchain4j.mapper.DoctorSchedulingMapper;
import com.czjt.xiaozilangchain4j.pojo.Appointment;
import com.czjt.xiaozilangchain4j.pojo.DoctorScheduling;
import com.czjt.xiaozilangchain4j.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AppointmentTools {
    @Autowired
    private AppointmentService appointmentService;


    @Autowired
    private DoctorSchedulingMapper doctorSchedulingMapper;



    // Appointment参数是由LangChain4j框架根据用户输入自动解析并传入的
    @Tool(name = "预约挂号", value = "根据参数，先执行工具方法queryDepartment查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约。。如果用户没有提供具体的医生姓名，请从\n" +
            "向量存储中找到一位医生。")
    public String bookAppointment(Appointment appointment) {
        //查找数据库中是否包含对应的预约记录
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB == null) {
            appointment.setId(null);//防止大模型幻觉设置了id
            if (appointmentService.save(appointment)) {
                return "预约成功，并返回预约详情";
            } else {
                return "预约失败";
            }
        }
        return "您在相同的科室和时间已有预约";
    }

    @Tool(name = "取消预约挂号", value = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回取 消预约成功，否则返回取消预约失败")
    public String cancelAppointment(Appointment appointment) {
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB != null) {
            //删除预约记录
            if (appointmentService.removeById(appointmentDB.getId())) {
                return "取消预约成功";
            } else {
                return "取消预约失败";
            }
        }
        //取消失败
        return "您没有预约记录，请核对预约科室和时间";
    }


    @Tool(name = "查询是否有号源", value = "根据科室名称，日期，时间和医生查询是否有号源，并返回给用户")
    public boolean queryDepartment(
            @P(value = "科室名称") String DepartmentName,
            @P(value = "日期") String date,
            @P(value = "时间，可选值：上午、下午") String timeStr,
            @P(value = "医生名称", required = false) String doctorName
    ) {
        log.info("开始查询号源信息 - 科室：{}, 日期：{}, 时间段：{}, 医生：{}",
                DepartmentName, date, timeStr, doctorName);

        // 将时间段转换为数字格式（1: 上午，2: 下午，3: 全天或其他）
        Integer time = convertTimeToCode(timeStr);
        if (time == null) {
            log.warn("时间段参数错误：{}", timeStr);
            return false;
        }

        LambdaQueryWrapper<DoctorScheduling> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DoctorScheduling::getDepartmentName, DepartmentName)
                .eq(DoctorScheduling::getData, date)
                .eq(DoctorScheduling::getTime, time);

        if (doctorName != null && !doctorName.isEmpty()) {
            // 指定医生的情况：精确匹配医生姓名
            queryWrapper.eq(DoctorScheduling::getDockerName, doctorName);

            DoctorScheduling scheduling = doctorSchedulingMapper.selectOne(queryWrapper);
            if (scheduling == null) {
                log.info("医生 {} 在指定条件下无排班记录", doctorName);
                return false;
            }

            // 判断是否还有剩余号源
            return scheduling.getAvailableSlots() < scheduling.getMaxSlots();
        } else {
            // 未指定医生：判断是否存在任意医生有可用号源
            List<DoctorScheduling> availableList = doctorSchedulingMapper.selectList(queryWrapper);
            boolean hasAvailable = availableList != null && !availableList.isEmpty();

            if (!hasAvailable) {
                log.info("未找到符合条件的排班信息");
            }

            return hasAvailable;
        }
    }

    /**
     * 将时间段字符串转换为对应编码（上午=1，下午=2）
     */
    private Integer convertTimeToCode(String timeStr) {
        if (timeStr == null) return null;

        switch (timeStr.trim()) {
            case "上午": return 1;
            case "下午": return 2;
            default: return null; // 支持扩展全天等
        }
    }
}