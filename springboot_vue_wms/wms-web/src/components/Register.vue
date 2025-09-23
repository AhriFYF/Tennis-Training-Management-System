<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <h2 class="register-title">
          {{ role === 'student' ? '学员注册' : '教练注册' }}
        </h2>
        <p class="register-subtitle">请填写以下信息完成注册</p>
      </div>

      <!-- 添加v-if确保数据已加载 -->
      <el-form
          v-if="role"
          :model="registerForm"
          ref="registerForm"
          label-width="100px"
          class="register-form"
      >
        <!-- 通用字段 -->
        <el-form-item label="用户名" prop="username">
          <el-input
              v-model="registerForm.username"
              placeholder="请输入用户名"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
              size="small"
              style="width: 100%"
              show-password
          />
        </el-form-item>

        <el-form-item label="真实姓名" prop="realName">
          <el-input
              v-model="registerForm.realName"
              placeholder="请输入真实姓名"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-select
              v-model="registerForm.gender"
              placeholder="请选择性别"
              size="small"
              style="width: 100%"
          >
            <el-option label="男" value="M" />
            <el-option label="女" value="F" />
          </el-select>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input-number
              v-model="registerForm.age"
              :min="1"
              :max="120"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="校区" prop="campus">
          <el-select
              v-model="registerForm.campus"
              placeholder="请选择校区"
              size="small"
              style="width: 100%"
          >
            <el-option label="北京校区" value="北京校区" />
            <el-option label="上海校区" value="上海校区" />
            <el-option label="广州校区" value="广州校区" />
            <el-option label="深圳校区" value="深圳校区" />
          </el-select>
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input
              v-model="registerForm.phone"
              placeholder="请输入联系电话"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input
              v-model="registerForm.email"
              placeholder="请输入邮箱（可选）"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <!-- 学生专属字段 -->
        <template v-if="role === 'student'">
          <el-form-item label="班级信息">
            <el-input
                v-model="registerForm.classGrade"
                placeholder="请输入班级信息（例如：2023级1班）"
                size="small"
                style="width: 100%"
            />
          </el-form-item>
        </template>

        <!-- 教练专属字段 -->
        <template v-if="role === 'coach'">
          <el-form-item label="教练照片">
            <el-upload
                class="photo-uploader"
                action="#"
                :auto-upload="false"
                :show-file-list="false"
                :on-change="handlePhotoChange"
                accept="image/*"
            >
              <div v-if="photoUrl" class="photo-preview">
                <img :src="photoUrl" alt="教练照片预览" />
              </div>
              <div v-else class="photo-placeholder">
                <i class="el-icon-camera"></i>
                <div>点击上传照片</div>
              </div>
            </el-upload>
          </el-form-item>

          <el-form-item label="比赛成绩">
            <el-input
                v-model="registerForm.achievements"
                type="textarea"
                placeholder="请简要描述您的比赛成绩或经历"
                :rows="4"
                size="small"
                style="width: 100%"
            />
          </el-form-item>
        </template>

        <!-- 提交按钮 -->
        <el-form-item>
          <div style="display: flex; justify-content: center; gap: 10px;">
            <el-button
                type="primary"
                @click="submitRegister"
                :loading="submitting"
                size="small"
                style="width: 48%;"
            >
              提交注册
            </el-button>
            <el-button @click="goBack" size="small" style="width: 48%;">
              返回登录
            </el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      role: null, // 初始化为null
      submitting: false,
      photoUrl: "",
      registerForm: {
        username: "",
        password: "",
        realName: "",
        gender: "",
        age: null,
        campus: "",
        phone: "",
        email: "",
        classGrade: "",
        photo: null,
        achievements: ""
      }
    };
  },
  mounted() {
    // 确保正确获取路由参数
    this.role = this.$route.query.role || 'student';
    console.log('Register component mounted, role:', this.role);
  },
  methods: {
    handlePhotoChange(file) {
      this.registerForm.photo = file.raw;
      this.photoUrl = URL.createObjectURL(file.raw);
    },
    submitRegister() {
      this.submitting = true;
      console.log('Submitting register form:', this.registerForm);

      // 模拟API请求
      setTimeout(() => {
        this.submitting = false;
        this.$message.success('注册请求已发送（模拟）');
        console.log('注册数据:', {
          role: this.role,
          ...this.registerForm
        });
      }, 1500);
    },
    goBack() {
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
/* 添加临时背景色确保可见 */
.register-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 40px;
  width: 100%;
  max-width: 500px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.register-header {
  text-align: center;
  margin-bottom: 30px;
}

.register-title {
  margin: 0 0 10px 0;
  font-size: 24px;
  color: #303133;
  font-weight: 600;
}

.register-subtitle {
  margin: 0;
  color: #909399;
  font-size: 14px;
}

.register-form {
  width: 100%;
}

.register-form .el-form-item {
  margin-bottom: 22px;
}

.photo-uploader {
  width: 100%;
  border: 2px dashed #dcdfe6;
  border-radius: 8px;
  cursor: pointer;
  text-align: center;
  transition: border-color 0.3s;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.photo-uploader:hover {
  border-color: #409eff;
}

.photo-preview img {
  max-width: 100%;
  max-height: 100%;
  border-radius: 6px;
}

.photo-placeholder {
  color: #909399;
  font-size: 14px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.photo-placeholder i {
  font-size: 24px;
  margin-bottom: 8px;
}

/* 调试用样式 */
.debug-border {
  border: 1px solid red !important;
}
</style>