package cn.parking.park.entity;

import cn.parking.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * @author zjl
 *  
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_parking_data")
@TableName("a_parking_data")
@ApiModel(value = "停车记录")
public class ParkingData extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "车辆ID")
    private String carId;

    @ApiModelProperty(value = "车牌号")
    private String carNumber;

    @ApiModelProperty(value = "车主ID")
    private String ownerId;

    @ApiModelProperty(value = "车主姓名")
    private String owner;

    @ApiModelProperty(value = "停放开始时间")
    private String startTime;

    @ApiModelProperty(value = "停放结束时间")
    private String endTime;

    @ApiModelProperty(value = "停车费用")
    private BigDecimal cost;
}