<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <h2 class="register-title">
          {{ role === 'student' ? '学员注册' : '教练注册' }}
        </h2>
        <p class="register-subtitle">请填写以下信息完成注册</p>
      </div>

      <el-form
          :model="registerForm"
          ref="registerForm"
          label-width="100px"
          class="register-form"
      >
        <!-- 通用字段 -->
        <el-form-item label="用户名">
          <el-input
              v-model="registerForm.username"
              placeholder="请输入用户名"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="密码">
          <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
              size="small"
              style="width: 100%"
              show-password
          />
        </el-form-item>

        <el-form-item label="真实姓名">
          <el-input
              v-model="registerForm.realName"
              placeholder="请输入真实姓名"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="性别">
          <el-select
              v-model="registerForm.gender"
              placeholder="请选择性别"
              size="small"
              style="width: 100%"
          >
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>

        <el-form-item label="年龄">
          <el-input-number
              v-model="registerForm.age"
              :min="1"
              :max="120"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="校区">
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

        <el-form-item label="电话">
          <el-input
              v-model="registerForm.phone"
              placeholder="请输入联系电话"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input
              v-model="registerForm.email"
              placeholder="请输入邮箱（可选）"
              size="small"
              style="width: 100%"
          />
        </el-form-item>

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
import axios from "axios";

export default {
  data() {
    return {
      role: "student", // 'student' | 'coach'
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
        photo: null,
        achievements: "",
      },
    };
  },
  mounted() {
    this.role = this.$route.query.role || "student";
  },
  methods: {
    handlePhotoChange(file) {
      this.registerForm.photo = file.raw;
      this.photoUrl = URL.createObjectURL(file.raw);
    },
    submitRegister() {
      this.submitting = true;

      const formData = new FormData();
      formData.append("role", this.role);
      formData.append("username", this.registerForm.username || "");
      formData.append("password", this.registerForm.password || "");
      formData.append("realName", this.registerForm.realName || "");
      formData.append("gender", this.registerForm.gender || "");
      formData.append("age", this.registerForm.age || "");
      formData.append("campus", this.registerForm.campus || "");
      formData.append("phone", this.registerForm.phone || "");
      formData.append("email", this.registerForm.email || "");

      if (this.registerForm.photo) {
        formData.append("photo", this.registerForm.photo);
      }
      if (this.registerForm.achievements) {
        formData.append("achievements", this.registerForm.achievements);
      }

      const url =
          this.role === "student"
              ? this.$httpUrl + "/user/registerStudent"
              : this.$httpUrl + "/user/registerCoach";

      axios
          .post(url, formData)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success(
                  this.role === "student"
                      ? "学员注册成功！"
                      : "教练注册提交成功，请等待审核！"
              );
              this.goBack();
            } else {
              this.$message.error(res.data.message || "注册失败，请重试");
            }
          })
          .catch(() => {
            this.$message.error("网络错误，注册失败");
          })
          .finally(() => {
            this.submitting = false;
          });
    },
    goBack() {
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
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
  max-width: 450px;
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

.register-form label {
  font-weight: 500;
  color: #606266;
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
  width: 100%;
  height: 100%;
  object-fit: cover;
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

.el-button {
  border-radius: 6px;
}
</style>