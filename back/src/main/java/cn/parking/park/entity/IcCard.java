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
import java.math.BigDecimal;

/**
 * @author zjl
 *  
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_ic_card")
@TableName("a_ic_card")
@ApiModel(value = "IC卡")
public class IcCard extends ABaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "IC卡号")
    private String carNumber;

    @ApiModelProperty(value = "开卡日期")
    private String date;

    @ApiModelProperty(value = "所属人ID")
    private String userId;

    @ApiModelProperty(value = "所属人")
    private String userName;

    @ApiModelProperty(value = "开卡经办人")
    private String workUser;

    @ApiModelProperty(value = "服务电话")
    private String mobile;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;
}