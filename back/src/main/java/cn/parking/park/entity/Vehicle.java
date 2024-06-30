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

/**
 * @author zjl
 *  
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_vehicle")
@TableName("a_vehicle")
@ApiModel(value = "车辆")
public class Vehicle extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "车牌号")
    private String carNumber;

    @ApiModelProperty(value = "行驶证")
    private String drivingLicense;

    @ApiModelProperty(value = "车辆类型")
    private String carType;

    @ApiModelProperty(value = "车主ID")
    private String ownerId;

    @ApiModelProperty(value = "车主姓名")
    private String ownerName;
}