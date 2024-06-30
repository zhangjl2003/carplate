<template>
    <div>
      <Card>
        <div slot="title">
          <div class="edit-head">
            <a @click="close" class="back-title">
              <Icon type="ios-arrow-back" />返回
            </a>
            <div class="head-name">编辑</div>
            <span></span>
            <a @click="close" class="window-close">
              <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
            </a>
          </div>
        </div>
        <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left" >        <FormItem label="入场时间" prop="entryTime"  >
            <Input v-model="form.entryTime" clearable maxlength="240" show-word-limit placeholder="请输入入场时间..." style="width:570px"/>
          </FormItem>
          <FormItem label="离场时间" prop="departureTime"  >
            <Input v-model="form.departureTime" clearable maxlength="240" show-word-limit placeholder="请输入离场时间..." style="width:570px"/>
          </FormItem>
          <FormItem label="费用" prop="fee"  >
            <Input v-model="form.fee" clearable maxlength="240" show-word-limit placeholder="请输入费用..." style="width:570px"/>
          </FormItem>
          <FormItem label="收费标准id" prop="feeId"  >
            <Input v-model="form.feeId" clearable maxlength="240" show-word-limit placeholder="请输入收费标准id..." style="width:570px"/>
          </FormItem>
          <FormItem label="车主编号" prop="userId"  >
            <Input v-model="form.userId" clearable maxlength="240" show-word-limit placeholder="请输入车主编号..." style="width:570px"/>
          </FormItem>
          <FormItem label="车牌号" prop="carNumber"  >
            <Input v-model="form.carNumber" clearable maxlength="240" show-word-limit placeholder="请输入车牌号..." style="width:570px"/>
          </FormItem>
          <Form-item class="br">
            <Button
              @click="handleSubmit"
              :loading="submitLoading"
              type="primary"
            >提交并保存</Button>
            <Button @click="handleReset">重置</Button>
            <Button type="dashed" @click="close">关闭</Button>
          </Form-item>
        </Form>
      </Card>
    </div>
  </template>
  
  <script>
  import { editParkingInfo } from "./api.js";
  export default {
    name: "edit",
    components: {
      },
    props: {
      data: Object
    },
    data() {
      return {
        submitLoading: false,
        form: {
        entryTime: "",
        departureTime: "",
        fee: "",
        feeId: "",
        userId: "",
        carNumber: "",
      },
      formValidate: {
      }
      };
    },
    methods: {
      init() {
        this.handleReset();
        this.form = this.data;
      },
      handleReset() {
        this.$refs.form.resetFields();
      },
      handleSubmit() {
        this.$refs.form.validate(valid => {
          if (valid) {
            editParkingInfo(this.form).then(res => {
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