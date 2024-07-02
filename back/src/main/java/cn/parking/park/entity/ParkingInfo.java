package cn.parking.park.entity;

import cn.parking.basics.baseClass.ABaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 停车信息 实体类
 * @author zjl
 *
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_parking_info")
@TableName("a_parking_info")
@ApiModel(value = "停车信息")
public class ParkingInfo extends ABaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "入场时间")
    private String entryTime;

    @ApiModelProperty(value = "离场时间")
    private String departureTime;

    @ApiModelProperty(value = "费用")
    private String fee;

    @ApiModelProperty(value = "收费标准id")
    private String feeId;

    @ApiModelProperty(value = "车主编号")
    private String userId;

    @ApiModelProperty(value = "车牌号")
    private String carNumber;
}