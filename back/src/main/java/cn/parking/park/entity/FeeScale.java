package cn.parking.park.entity;

import cn.parking.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 收费标准 实体类
 * @author zjl
 *
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_fee_scale")
@TableName("a_fee_scale")
@ApiModel(value = "收费标准")
public class FeeScale extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费标准类型")
    private String feeType;

    @ApiModelProperty(value = "免费时间")
    private String freeTime;

    @ApiModelProperty(value = "起步费用")
    private String startPrice;

    @ApiModelProperty(value = "超时费用")
    private String beyondPrice;

}