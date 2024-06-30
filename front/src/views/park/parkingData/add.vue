<template>
<div>
    <Card>
        <div slot="title">
            <div class="edit-head">
                <a @click="close" class="back-title">
                    <Icon type="ios-arrow-back" />返回
                </a>
                <div class="head-name">添加停车数据</div>
                <span></span>
                <a @click="close" class="window-close">
                    <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
                </a>
            </div>
        </div>
        <Form ref="form" :model="form" :label-width="150" :rules="formValidate" label-position="left">
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="车辆" prop="carId">
                    <Select v-model="form.carId" clearable style="width:100">
                        <Option v-for="(item,index) in vehicleList" :key="index" :value="item.id">{{item.ownerName}} - {{ item.carNumber }}</Option>
                    </Select>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="停车费用" prop="cost">
                    <InputNumber v-model="form.cost" min="0" max="5000000" style="width:100%"></InputNumber>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="停放开始时间" prop="startTime">
                    <DatePicker type="date" clearable placeholder="请选择开始日期..." format="yyyy-MM-dd" @on-change="changeStartTime1" style="width:50%"></DatePicker>
                    <TimePicker type="time" placeholder="请选择开始时间..." format="HH:mm" @on-change="changeStartTime2" style="width: 50%"></TimePicker>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="停放结束时间" prop="endTime">
                    <DatePicker type="date" clearable placeholder="请选择结束日期..." format="yyyy-MM-dd" @on-change="changeEndTime1" style="width:50%"></DatePicker>
                    <TimePicker type="time" placeholder="请选择结束时间..." format="HH:mm" @on-change="changeEndTime2" style="width: 50%"></TimePicker>
                </FormItem>
                </Col>
            </Row>
            <Form-item class="br">
                <Button @click="handleSubmit" :loading="submitLoading" type="primary">提交并保存</Button>
                <Button @click="handleReset">重置</Button>
                <Button type="dashed" @click="close">关闭</Button>
            </Form-item>
        </Form>
    </Card>
</div>
</template>

<script>
import {
    addParkingData,
    getVehicleList
} from "./api.js";
export default {
    name: "add",
    components: {},
    data() {
        return {
            submitLoading: false, // 表单提交状态
            form: { // 添加或编辑表单对象初始化数据
                carId: "",
                carNumber: "",
                owner: "",
                startTime: "",
                endTime: "",
                cost: 0,
                startTime1: "",
                startTime2: "",
                endTime1: "",
                endTime2: ""
            },
            // 表单验证规则
            formValidate: {},
            vehicleList: []
        };
    },
    methods: {
        init() {
            this.getVehicleListFx();
        },
        changeStartTime1(e) {
            this.form.startTime1 = e;
        },
        changeStartTime2(e) {
            this.form.startTime2 = e;
        },
        changeEndTime1(e) {
            this.form.endTime1 = e;
        },
        changeEndTime2(e) {
            this.form.endTime2 = e;
        },
        getVehicleListFx() {
            var that = this;
            that.vehicleList = [];
            getVehicleList().then(res => {
                if (res.success) {
                    that.vehicleList = res.result;
                }
            })
        },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    this.form.startTime = this.form.startTime1 + " " + this.form.startTime2;
                    this.form.endTime = this.form.endTime1 + " " + this.form.endTime2;
                    addParkingData(this.form).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.submited();
                        }
                    });
                }
            });
        },
        close() {
            this.$emit("close", true);
        },
        submited() {
            this.$emit("submited", true);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="less">
// 建议引入通用样式 具体路径自行修改 可删除下面样式代码
// @import "../../../styles/single-common.less";
.edit-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;

    .back-title {
        color: #515a6e;
        display: flex;
        align-items: center;
    }

    .head-name {
        display: inline-block;
        height: 20px;
        line-height: 20px;
        font-size: 16px;
        color: #17233d;
        font-weight: 500;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .window-close {
        z-index: 1;
        font-size: 12px;
        position: absolute;
        right: 0px;
        top: -5px;
        overflow: hidden;
        cursor: pointer;

        .ivu-icon-ios-close {
            color: #999;
            transition: color .2s ease;
        }
    }

    .window-close .ivu-icon-ios-close:hover {
        color: #444;
    }
}
</style>
