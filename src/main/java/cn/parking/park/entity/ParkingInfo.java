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
public class ParkingInfo extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "停车信息标题")
    private String title;

}