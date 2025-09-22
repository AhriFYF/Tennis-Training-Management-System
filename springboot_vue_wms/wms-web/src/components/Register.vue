<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h2>{{ isRegister ? '用户注册' : '用户登录' }}</h2>
      </div>

      <el-form
          :model="formData"
          :rules="rules"
          ref="formRef"
          class="login-form"
          label-width="100px"
      >
        <!-- 登录表单 -->
        <template v-if="!isRegister">
          <el-form-item label="账号" prop="no">
            <el-input v-model="formData.no" placeholder="请输入账号"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
                v-model="formData.password"
                type="password"
                show-password
                placeholder="请输入密码"
                @keyup.enter.native="handleSubmit"
            ></el-input>
          </el-form-item>
        </template>

        <!-- 注册表单 -->
        <template v-else>
          <el-form-item label="用户类型" prop="roleId">
            <el-radio-group v-model="formData.roleId">
              <el-radio :label="3">学员</el-radio>
              <el-radio :label="2">教练</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="账号" prop="no">
            <el-input v-model="formData.no" placeholder="请输入账号"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
                v-model="formData.password"
                type="password"
                show-password
                placeholder="请输入密码"
            ></el-input>
          </el-form-item>

          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
                v-model="formData.confirmPassword"
                type="password"
                show-password
                placeholder="请再次输入密码"
            ></el-input>
          </el-form-item>

          <el-form-item label="真实姓名" prop="name">
            <el-input v-model="formData.name" placeholder="请输入真实姓名"></el-input>
          </el-form-item>

          <el-form-item label="手机号" prop="phone">
            <el-input v-model="formData.phone" placeholder="请输入手机号"></el-input>
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="formData.email" placeholder="请输入邮箱（选填）"></el-input>
          </el-form-item>

          <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="formData.gender">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="校区" prop="campusId">
            <el-select v-model="formData.campusId" placeholder="请选择校区">
              <el-option
                  v-for="campus in campusList"
                  :key="campus.id"
                  :label="campus.name"
                  :value="campus.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </template>

        <el-form-item class="form-actions">
          <el-button
              type="primary"
              @click="handleSubmit"
              :loading="loading"
              class="submit-btn"
          >
            {{ isRegister ? '注册' : '登录' }}
          </el-button>

          <el-button
              type="text"
              @click="toggleFormMode"
              class="toggle-btn"
          >
            {{ isRegister ? '已有账号？去登录' : '没有账号？去注册' }}
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data() {
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.formData.password) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    };

    return {
      isRegister: false,
      loading: false,
      campusList: [],
      formData: {
        roleId: 3,
        no: '',
        password: '',
        confirmPassword: '',
        name: '',
        phone: '',
        email: '',
        gender: 1,
        campusId: null,
        isValid: 'Y',
        auditStatus: 0
      },
      rules: {
        roleId: [
          { required: true, message: '请选择用户类型', trigger: 'change' }
        ],
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validateConfirmPassword, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ],
        campusId: [
          { required: true, message: '请选择校区', trigger: 'change' }
        ]
      }
    };
  },
  created() {
    this.fetchCampusList();
  },
  methods: {
    fetchCampusList() {
      this.$axios.get('/api/campus/list').then(res => {
        this.campusList = res.data || [];
      }).catch(() => {
        this.$message.error('获取校区列表失败');
      });
    },

    toggleFormMode() {
      this.isRegister = !this.isRegister;
      this.$refs.formRef.resetFields();
      this.formData = {
        roleId: 3,
        no: '',
        password: '',
        confirmPassword: '',
        name: '',
        phone: '',
        email: '',
        gender: 1,
        campusId: null,
        isValid: 'Y',
        auditStatus: 0
      };
    },

    handleSubmit() {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.isRegister) {
            this.handleRegister();
          } else {
            this.handleLogin();
          }
        }
      });
    },

    handleLogin() {
      const { no, password } = this.formData;

      this.$axios.post('/user/login', { no, password })
          .then(res => {
            if (res.code === 200) {
              this.$store.commit('SET_USER', res.data.user);
              this.$store.commit('SET_TOKEN', res.data.token);

              switch(res.data.user.roleId) {
                case 0:
                  this.$router.push('/SuperAdmin');
                  break;
                case 1:
                  this.$router.push('/admin');
                  break;
                case 2:
                  this.$router.push('/coach');
                  break;
                case 3:
                  this.$router.push('/student');
                  break;
                default:
                  this.$router.push('/');//----------------------------修改
              }
            } else {
              this.$message.error(res.message || '登录失败');
            }
          })
          .finally(() => {
            this.loading = false;
          });
    },

    handleRegister() {
      const registerData = { ...this.formData };
      delete registerData.confirmPassword;

      if (registerData.roleId === 3) {
        registerData.auditStatus = 1;
      }

      this.$axios.post('/api/user/register', registerData)
          .then(res => {
            if (res.code === 200) {
              this.$message.success('注册成功');
              if (registerData.roleId === 3) {
                this.handleLogin();
              } else {
                this.$message.info('教练注册申请已提交，请等待管理员审核');
                this.toggleFormMode();
              }
            } else {
              this.$message.error(res.message || '注册失败');
            }
          })
          .finally(() => {
            this.loading = false;
          });
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100vh;
  background: url('~@/assets/login-bg.jpg') no-repeat center center;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-box {
  width: 500px;
  padding: 30px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  color: #333;
  font-size: 24px;
}

.login-form {
  margin-top: 20px;
}

.form-actions {
  margin-top: 30px;
  text-align: center;
}

.submit-btn {
  width: 100%;
  margin-bottom: 15px;
}

.toggle-btn {
  width: 100%;
}
</style>