package cn.parking.data.entity;

import cn.parking.basics.baseClass.ABaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author zjl
 *  
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_role_permission")
@TableName("a_role_permission")
@ApiModel(value = "角色权限")
public class RolePermission extends ABaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    private String permissionId;

    @ApiModelProperty(value = "角色ID")
    private String roleId;
}