<template>
    <div class="loginBody">
        <div class="loginDiv">
            <div class="login-content">
                <h1 class="login-title">用户登录</h1>
                <el-form :model="loginForm" label-width="100px"
                         :rules="rules" ref="loginForm">
                    <el-form-item label="账号" prop="no">
                        <el-input style="width: 200px" type="text" v-model="loginForm.no"
                                  autocomplete="off" size="small"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input style="width: 200px" type="password" v-model="loginForm.password"
                                  show-password autocomplete="off" size="small" @keyup.enter.native="confirm"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="confirm" :disabled="confirm_disabled">确 定</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>


<script>
    export default {
        name: "Login",
        data(){
            return{
                confirm_disabled:false,
                loginForm:{
                    no:'',
                    password:''
                },
                rules:{
                    no: [
                        { required: true, message: '请输入账号', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输密码', trigger: 'blur' }
                    ],
                }
            }
        },
        methods:{

// 前端代码
          confirm() {
            this.confirm_disabled = true;
            this.$refs.loginForm.validate((valid) => {
              if (valid) {
                this.$axios.post(this.$httpUrl + '/user/login', this.loginForm).then(res => res.data).then(res => {
                  if (res.code == 200) {
                    sessionStorage.setItem("CurUser", JSON.stringify(res.data.user));
                    this.$store.commit("setMenu", res.data.menu);

                    // 获取用户的 roleId
                    const user = res.data.user;
                    const roleId = user.roleId; // 这里可以直接获取，因为后端已经返回了

                    // 根据 roleId 进行跳转
                    if (roleId === 0) {
                      this.$router.replace('/Index');
                    } else if (roleId === 1) {
                      this.$router.replace('/admin');
                    } else if (roleId === 2) {
                      this.$router.replace('/coach');
                    } else if (roleId === 3) {
                      this.$router.replace('/student');
                    }

                  } else {
                    this.confirm_disabled = false;
                    alert('校验失败，用户名或密码错误！');
                    return false;
                  }
                });
              } else {
                this.confirm_disabled = false;
                console.log('校验失败');
                return false;
              }
            });
          }
        }
    }
</script>

<style scoped>
    .loginBody {
        position: absolute;
        width: 100%;
        height: 100%;
        background-color: #B3C0D1;
    }
    .loginDiv {
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -200px;
        margin-left: -250px;
        width: 450px;
        height: 330px;
        background: #fff;
        border-radius: 5%;

    }
    .login-title {
        margin: 20px 0;
        text-align: center;
    }
    .login-content {
        width: 400px;
        height: 250px;
        position: absolute;
        top: 25px;
        left: 25px;
    }
</style>