package com.czjt.xiaozilangchain4j.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName doctor_scheduling
 */
@TableName(value ="doctor_scheduling")
@Data
public class DoctorScheduling implements Serializable {
    private Long id;

    private String departmentName;

    private String dockerName;

    private Date data;

    private Integer time;

    private Integer availableSlots;

    private Integer maxSlots;

    private static final long serialVersionUID = 1L;
}