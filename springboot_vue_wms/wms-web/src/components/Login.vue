<template>
  <div class="loginBody">
    <header-component />
    <div class="loginDiv">
      <div class="login-content">
        <h1 class="login-title">用户登录</h1>
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">
          <el-form-item label="账号" prop="no">
            <el-input
                v-model="loginForm.no"
                placeholder="请输入用户名"
                size="small"
                style="width: 250px"
                autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                size="small"
                style="width: 250px"
                show-password
                autocomplete="off"
                @keyup.enter.native="confirm"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="confirm" :loading="confirm_disabled">登录</el-button>
            <el-button type="default" @click="studentRegister">学生注册</el-button>
            <el-button type="default" @click="coachRegister">教练注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderComponent from './HeaderComponent.vue';
export default {
  name: "Login",
  components: {
    HeaderComponent
  },
  data() {
    return {
      confirm_disabled: false,
      loginForm: {
        no: "",
        password: "",
      },
      rules: {
        no: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  methods: {
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$axios
              .post(this.$httpUrl + "/user/login", this.loginForm)
              .then((res) => res.data)
              .then((res) => {
                if (res.code == 200) {
                  sessionStorage.setItem("CurUser", JSON.stringify(res.data.user));
                  // 保存登录响应，用于密钥验证
                  sessionStorage.setItem("loginResponse", JSON.stringify(res));
                  localStorage.setItem("user", JSON.stringify(res.data));
                  const menu = res.data.menu;
                  this.$store.commit("setMenu", menu); // 假定您有 Vuex store

                  const user = res.data.user;
                  const roleId = user.roleId;

                  // 检查是否需要密钥处理
                  if (res.needKeyGeneration) {
                    this.$router.replace("/SuperAdmin");
                  } else if (res.needKeyVerification) {
                    this.$router.replace("/SuperAdmin");
                  } else if (roleId === 0) {
                    // 超级管理员已验证密钥
                    sessionStorage.setItem("superAdminKeyVerified", "true");
                    this.$router.replace("/SuperAdmin");
                  } else if (roleId === 1) {
                    this.$router.replace("/admin");
                  } else if (roleId === 2) {
                    this.$router.replace("/coach-dashboard");
                  } else if (roleId === 3) {
                    this.$router.replace("/student");
                  }
                } else {
                  this.confirm_disabled = false;
                  this.$message.error(res.message || "用户名或密码错误");
                }
              })
              .catch(() => {
                this.confirm_disabled = false;
                this.$message.error("登录请求失败");
              });
        } else {
          this.confirm_disabled = false;
          this.$message.error("请检查输入内容");
        }
      });
    },
    studentRegister() {
      this.$router.push("/register?role=student");
    },
    coachRegister() {
      this.$router.push("/register?role=coach");
    },
  }
};
</script>

<style scoped>
.loginBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #e0f2e0;
}
.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  height: 250px;
  background: #f7f7f7;
  border-radius: 8px;
  padding: 20px;
}
.login-title {
  text-align: center;
  margin-bottom: 20px;
}
.login-content {
  width: 100%;
}
</style>