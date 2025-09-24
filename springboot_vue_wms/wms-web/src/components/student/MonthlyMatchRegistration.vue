<template>
  <div>
    <h2>月赛报名</h2>
    <el-alert
        title="每月第四个星期天举行月赛，报名费30元/人"
        type="info"
        show-icon
        style="margin-bottom: 20px;">
    </el-alert>

    <el-card class="registration-card">
      <div slot="header" class="clearfix">
        <span>报名信息</span>
      </div>
      <el-form ref="registrationForm" :model="registrationForm" :rules="rules" label-width="120px">
        <el-form-item label="选择组别" prop="groupType">
          <el-select v-model="registrationForm.groupType" placeholder="请选择组别" style="width: 100%;">
            <el-option label="甲组" value="甲"></el-option>
            <el-option label="乙组" value="乙"></el-option>
            <el-option label="丙组" value="丙"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报名费" prop="registrationFee">
          <el-input v-model="registrationForm.registrationFee" disabled></el-input>
        </el-form-item>
        <el-form-item label="当前余额" prop="balance">
          <el-input v-model="userBalance" disabled>
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitRegistration">提交报名</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="my-registrations-card" style="margin-top: 20px;">
      <div slot="header" class="clearfix">
        <span>我的报名记录</span>
      </div>
      <el-table :data="myRegistrations" style="width: 100%">
        <el-table-column prop="groupType" label="组别" width="100"></el-table-column>
        <el-table-column prop="registrationFee" label="报名费" width="100"></el-table-column>
        <el-table-column prop="paymentStatus" label="支付状态" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.paymentStatus === '已支付' ? 'success' : 'danger'">
              {{ scope.row.paymentStatus }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="registrationDate" label="报名时间" width="180"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
                v-if="scope.row.paymentStatus === '未支付'"
                size="mini"
                type="success"
                @click="payRegistrationFee(scope.row.id)">
              支付报名费
            </el-button>
            <el-button
                v-else
                size="mini"
                type="info"
                disabled>
              已支付
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "MonthlyMatchRegistration",
  data() {
    return {
      userBalance: 0,
      registrationForm: {
        groupType: '',
        registrationFee: 30.00
      },
      myRegistrations: [],
      rules: {
        groupType: [
          {required: true, message: '请选择组别', trigger: 'change'}
        ]
      }
    };
  },
  methods: {
    resetForm() {
      this.$refs.registrationForm.resetFields();
    },
    submitRegistration() {
      this.$refs.registrationForm.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl + '/matchRegistration/register', this.registrationForm)
              .then(res => res.data)
              .then(res => {
                if (res.code === 200) {
                  this.$message({
                    message: res.msg,
                    type: 'success'
                  });
                  this.loadMyRegistrations();
                  this.resetForm();
                } else {
                  this.$message({
                    message: res.msg,
                    type: 'error'
                  });
                }
              });
        } else {
          console.log('表单验证失败!!');
          return false;
        }
      });
    },
    payRegistrationFee(id) {
      this.$axios.post(this.$httpUrl + '/matchRegistration/pay/' + id)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.$message({
                message: res.msg,
                type: 'success'
              });
              this.loadMyRegistrations();
              this.loadUserBalance();
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          });
    },
    loadMyRegistrations() {
      this.$axios.get(this.$httpUrl + '/matchRegistration/myRegistrations')
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.myRegistrations = res.data;
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          });
    },
    loadUserBalance() {
      // 从session中获取用户信息
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.$axios.get(this.$httpUrl + '/user/findById?id=' + user.id)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200 && res.data) {
                this.userBalance = res.data.balance || 0;
              }
            });
      }
    }
  },
  mounted() {
    this.loadMyRegistrations();
    this.loadUserBalance();
  }
};
</script>

<style scoped>
.registration-card, .my-registrations-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>