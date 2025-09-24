<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <h2 class="register-title">
          {{ role === 'student' ? '学员注册' : '教练注册' }}
        </h2>
        <p class="register-subtitle">请填写以下信息完成注册</p>
      </div>

      <div class="role-selector">
        <el-button
            :class="['role-button', role === 'student' ? 'active' : '']"
            @click="role = 'student'">
          学员注册
        </el-button>
        <el-button
            :class="['role-button', role === 'coach' ? 'active' : '']"
            @click="role = 'coach'">
          教练注册
        </el-button>
      </div>

      <el-form
          :model="registerForm"
          :rules="rules"
          ref="registerForm"
          label-width="100px"
          class="register-form"
      >
        <!-- 学生专属字段 -->
        <template v-if="role === 'student'">
          <el-form-item label="学号" prop="studentNo">
            <el-input
                v-model="registerForm.studentNo"
                placeholder="请输入学号"
                size="small"
                style="width: 100%"
            />
          </el-form-item>
        </template>

        <!-- 教练专属字段 -->
        <template v-if="role === 'coach'">
          <el-form-item label="教练编号" prop="coachNo">
            <el-input
                v-model="registerForm.coachNo"
                placeholder="请输入教练编号"
                size="small"
                style="width: 100%"
            />
          </el-form-item>
        </template>

        <!-- 通用字段 -->
        <el-form-item label="用户名" prop="no">
          <el-input
              v-model="registerForm.no"
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

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              size="small"
              style="width: 100%"
              show-password
          />
        </el-form-item>

        <el-form-item label="真实姓名" prop="name">
          <el-input
              v-model="registerForm.name"
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

        <el-form-item label="校区" prop="campusId">
          <el-select
              v-model="registerForm.campusId"
              placeholder="请选择校区"
              size="small"
              style="width: 100%"
          >
            <el-option label="北京校区" value="1" />
            <el-option label="上海校区" value="2" />
            <el-option label="广州校区" value="3" />
            <el-option label="深圳校区" value="4" />
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
          <el-form-item label="班级信息" prop="classGrade">
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

              </div>
              <div v-else class="photo-placeholder">
                <i class="el-icon-camera"></i>
                <div>点击上传照片</div>
              </div>
            </el-upload>
          </el-form-item>

          <el-form-item label="教练等级" prop="level">
            <el-select
                v-model="registerForm.level"
                placeholder="请选择教练等级"
                size="small"
                style="width: 100%"
            >
              <el-option label="初级教练" :value="1" />
              <el-option label="中级教练" :value="2" />
              <el-option label="高级教练" :value="3" />
            </el-select>
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
    // 自定义验证规则：确认密码
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      role: 'student',
      submitting: false,
      photoUrl: "",
      registerForm: {
        // 通用字段
        no: "",
        password: "",
        confirmPassword: "",
        name: "",
        gender: "",
        age: null,
        campusId: "",
        phone: "",
        email: "",

        // 学生字段
        studentNo: "",
        classGrade: "",

        // 教练字段
        coachNo: "",
        level: null,
        achievements: "",
        photo: null
      },
      rules: {
        // 学生字段验证
        studentNo: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        // 教练字段验证
        coachNo: [
          { required: true, message: '请输入教练编号', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        // 通用字段验证
        no: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' }
        ],
        campusId: [
          { required: true, message: '请选择校区', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        classGrade: [
          { required: true, message: '请输入班级信息', trigger: 'blur' }
        ],
        level: [
          { required: true, message: '请选择教练等级', trigger: 'change' }
        ]
      }
    };
  },
  mounted() {
    // 从URL参数获取角色类型
    const urlParams = new URLSearchParams(window.location.search);
    this.role = urlParams.get('role') || 'student';
  },
  methods: {
    handlePhotoChange(file) {
      this.registerForm.photo = file.raw;
      this.photoUrl = URL.createObjectURL(file.raw);
    },

    submitRegister() {
      this.$refs.registerForm.validate((valid) => {
        if (!valid) {
          this.$message.error('请正确填写表单信息');
          return false;
        }

        this.submitting = true;

        if (this.role === 'student') {
          this.registerStudent();
        } else {
          this.registerCoach();
        }
      });
    },

    registerStudent() {
      // 学生注册逻辑
      const studentData = {
        no: this.registerForm.no,
        password: this.registerForm.password,
        name: this.registerForm.name,
        phone: this.registerForm.phone,
        age: this.registerForm.age,
        sex: this.registerForm.gender === 'M' ? 1 : 0,
        campusId: parseInt(this.registerForm.campusId),
        roleId: 3, // 学生角色
        isvalid: 'Y',
        // 学生详细信息
        studentNo: this.registerForm.studentNo,
        classGrade: this.registerForm.classGrade
      };

      this.$axios.post('/user/registerStudent', studentData)
          .then(response => {
            if (response.data.code === 200) {
              this.$message.success('注册成功');
              setTimeout(() => {
                this.$router.push('/login');
              }, 1500);
            } else {
              this.$message.error(response.data.msg || '注册失败');
              this.submitting = false;
            }
          })
          .catch(error => {
            console.error('注册请求错误:', error);
            this.$message.error('注册失败，请稍后重试');
            this.submitting = false;
          });
    },

    registerCoach() {
      // 教练注册逻辑 - 使用FormData处理文件上传
      const formData = new FormData();
      formData.append('no', this.registerForm.no);
      formData.append('password', this.registerForm.password);
      formData.append('name', this.registerForm.name);
      formData.append('phone', this.registerForm.phone);
      formData.append('age', this.registerForm.age);
      formData.append('sex', this.registerForm.gender === 'M' ? 1 : 0);
      formData.append('campusId', parseInt(this.registerForm.campusId));
      formData.append('roleId', 2); // 教练角色
      formData.append('isvalid', 'Y');

      // 教练特定字段
      formData.append('coachNo', this.registerForm.coachNo);
      formData.append('level', this.registerForm.level);
      formData.append('achievements', this.registerForm.achievements);

      if (this.registerForm.photo) {
        formData.append('photo', this.registerForm.photo);
      }

      this.$axios.post('/user/registerCoach', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(response => {
            if (response.data.code === 200) {
              this.$message.success('注册成功，等待管理员审核');
              setTimeout(() => {
                this.$router.push('/login');
              }, 1500);
            } else {
              this.$message.error(response.data.msg || '注册失败');
              this.submitting = false;
            }
          })
          .catch(error => {
            console.error('注册请求错误:', error);
            this.$message.error('注册失败，请稍后重试');
            this.submitting = false;
          });
    },

    goBack() {
      this.$router.push('/login');
    }
  }
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
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
}

.register-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 40px;
  width: 100%;
  max-width: 600px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  overflow-y: auto;
  max-height: 90vh;
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

.error-message {
  color: #f56c6c;
  font-size: 12px;
  line-height: 1;
  padding-top: 4px;
  position: absolute;
  top: 100%;
  left: 0;
}

.role-selector {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.role-button {
  padding: 12px 24px;
  margin: 0 10px;
  border-radius: 8px;
  font-weight: 500;
  transition: all 0.3s;
}

.role-button.active {
  background-color: #409eff;
  color: white;
  border-color: #409eff;
}
</style>