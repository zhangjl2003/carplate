package cn.parking.data.entity;

import cn.parking.basics.baseClass.ABaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zjl
 *
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_department")
@TableName("a_department")
@ApiModel(value = "部门")
public class Department extends ABaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门标题")
    private String title;

    @ApiModelProperty(value = "状态")
    private Integer status = 0;

    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "父节点标识")
    private Boolean isParent = false;

    @ApiModelProperty(value = "父部门ID")
    private String parentId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "领导人")
    private List<String> mainHeader;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "副领导人")
    private List<String> viceHeader;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "父部门名称")
    private String parentTitle;
}