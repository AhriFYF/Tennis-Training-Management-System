<!-- Recharge.vue 学生充值组件 -->
<template>
  <div class="recharge-container">
    <h2>账户充值</h2>
    
    <el-card class="balance-card">
      <div class="balance-info">
        <span class="label">当前余额：</span>
        <span class="amount">¥ {{ currentBalance }}</span>
      </div>
    </el-card>

    <el-card class="recharge-card">
      <div slot="header" class="clearfix">
        <span>充值金额</span>
      </div>
      
      <el-form :model="rechargeForm" :rules="rules" ref="rechargeForm" label-width="100px">
        <el-form-item label="充值金额" prop="amount">
          <el-input 
            v-model.number="rechargeForm.amount" 
            placeholder="请输入充值金额"
            clearable
            style="width: 300px;">
            <template slot="prepend">¥</template>
          </el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleRecharge" :loading="loading">立即充值</el-button>
          <el-button @click="resetForm('rechargeForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog title="充值确认" :visible.sync="confirmDialogVisible" width="30%">
      <span>您确定要充值 ¥ {{ rechargeForm.amount }} 吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="confirmDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmRecharge">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Recharge",
  data() {
    return {
      currentBalance: 0,
      loading: false,
      confirmDialogVisible: false,
      rechargeForm: {
        amount: null
      },
      rules: {
        amount: [
          { required: true, message: '请输入充值金额', trigger: 'blur' },
          { type: 'number', min: 0.01, message: '充值金额必须大于0', trigger: 'blur' },
          { type: 'number', message: '请输入有效的金额', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    loadBalance() {
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.currentBalance = user.balance ? parseFloat(user.balance) : 0;
      }
    },
    handleRecharge() {
      this.$refs.rechargeForm.validate((valid) => {
        if (valid) {
          this.confirmDialogVisible = true;
        }
      });
    },
    confirmRecharge() {
      this.confirmDialogVisible = false;
      this.loading = true;
      
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (!user || !user.id) {
        this.$message.error('用户信息异常，请重新登录');
        this.loading = false;
        return;
      }
      
      this.$axios.post(`${this.$httpUrl}/user/recharge`, null, {
        params: {
          userId: user.id,
          amount: this.rechargeForm.amount
        }
      })
      .then(res => res.data)
      .then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg);
          // 更新余额显示
          this.currentBalance = parseFloat(res.data.balance);
          // 更新sessionStorage中的用户信息
          user.balance = res.data.balance;
          sessionStorage.setItem('CurUser', JSON.stringify(user));
          // 重置表单
          this.resetForm('rechargeForm');
        } else {
          this.$message.error(res.msg || '充值失败');
        }
      })
      .catch(() => {
        this.$message.error('充值请求失败');
      })
      .finally(() => {
        this.loading = false;
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  mounted() {
    this.loadBalance();
  }
};
</script>

<style scoped>
.recharge-container {
  padding: 20px;
}

.balance-card {
  margin-bottom: 20px;
}

.balance-info {
  text-align: center;
  padding: 20px 0;
}

.balance-info .label {
  font-size: 18px;
  color: #666;
}

.balance-info .amount {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.recharge-card {
  width: 500px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
</style>