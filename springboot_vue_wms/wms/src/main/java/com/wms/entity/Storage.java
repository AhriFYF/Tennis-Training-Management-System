package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Storage对象", description="")
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "仓库名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", remark='" + remark + '\'' +
                '}';
    }
}
