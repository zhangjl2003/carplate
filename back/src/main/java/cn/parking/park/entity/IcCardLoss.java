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
 * @author zjl
 *
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_ic_card_loss")
@TableName("a_ic_card_loss")
@ApiModel(value = "IC卡挂失")
public class IcCardLoss extends ABaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "IC卡ID")
    private String carId;

    @ApiModelProperty(value = "卡号")
    private String carNumber;

    @ApiModelProperty(value = "申请人ID")
    private String applyId;

    @ApiModelProperty(value = "申请人")
    private String applyUser;

    @ApiModelProperty(value = "挂失申请日期")
    private String orderDate;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "新卡号")
    private String newNumber;
}