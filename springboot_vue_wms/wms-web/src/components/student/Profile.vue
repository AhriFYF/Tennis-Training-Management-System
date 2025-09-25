<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <div slot="header" class="clearfix">
        <span>个人信息</span>
        <el-button
            v-if="!isEditing"
            style="float: right; padding: 3px 0"
            type="text"
            @click="isEditing = true">
          编辑
        </el-button>
        <div v-else style="float: right;">
          <el-button size="mini" @click="cancelEdit">取消</el-button>
          <el-button size="mini" type="primary" @click="saveProfile">保存</el-button>
        </div>
      </div>

      <el-form :model="studentForm" label-width="100px" :disabled="!isEditing">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学号">
              <el-input v-model="studentForm.studentId" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="studentForm.name"  disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别">
              <el-select v-model="studentForm.gender" placeholder="请选择性别">
                <el-option label="男" value="M"></el-option>
                <el-option label="女" value="F"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄">
              <el-input-number v-model="studentForm.age" :min="1" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="手机号">
          <el-input v-model="studentForm.phone"></el-input>
        </el-form-item>

        <el-form-item label="班级">
          <el-input v-model="studentForm.classGrade"></el-input>
        </el-form-item>

        <el-form-item label="校区">
          <el-select v-model="studentForm.campusId" placeholder="请选择校区">
            <el-option
                v-for="campus in campusList"
                :key="campus.id"
                :label="campus.name"
                :value="campus.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="训练时长">
          <el-input v-model="studentForm.trainingHours" disabled>
            <template slot="append">小时</template>
          </el-input>
        </el-form-item>

        <el-form-item label="账户余额">
          <el-input v-model="studentForm.balance" disabled>
            <template slot="append">元</template>
          </el-input>
        </el-form-item>

        <el-form-item label="照片">
          <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <!-- 添加条件判断 -->
            <img v-if="studentForm.photoUrl" :src="studentForm.photoUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 修改密码对话框 -->
    <el-dialog title="修改密码" :visible.sync="passwordDialogVisible" width="30%">
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordForm" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input type="password" v-model="passwordForm.oldPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input type="password" v-model="passwordForm.newPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="passwordForm.confirmPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="passwordDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="changePassword">确 定</el-button>
      </span>
    </el-dialog>

    <el-button type="primary" @click="passwordDialogVisible = true" style="margin-top: 20px;">
      修改密码
    </el-button>
  </div>
</template>

<script>
export default {
  name: 'StudentProfile',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      isEditing: false,
      studentForm: {
        studentId: '',
        no: '',
        name: '',
        gender: '',
        age: null,
        phone: '',
        classGrade: '',
        campusId: null,
        trainingHours: 0,
        balance: 0,
        photoUrl: '',
        userId: null // 添加userId字段
      },
      originalForm: {},
      campusList: [],
      passwordDialogVisible: false,
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '密码长度至少6个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    // 加载学生信息
    loadStudentProfile() {
      this.$axios.get('/student/profile')
          .then(response => {
            const res = response.data;
            if (res.code === 200) {
              // 确保所有字段都被正确赋值，包括userId


              this.studentForm = {
                ...this.studentForm,
                ...res.data
              };
              this.originalForm = { ...res.data };
              this.$forceUpdate();
            } else {
              this.$message.error(res.msg || '加载个人信息失败');
            }
          })
          .catch(error => {
            this.$message.error('网络错误，请稍后重试');
            console.error(error);
          });
    },

    // 加载校区列表
    loadCampusList() {
      this.$axios.get('/campus/list')
          .then(response => {
            const res = response.data;
            if (res.code === 200) {
              this.campusList = res.data;
            } else {
              this.$message.error('加载校区列表失败');
              console.error('加载校区列表失败:', res.msg);
            }
          })
          .catch(error => {
            console.error('加载校区列表网络错误:', error);
            this.$message.error('网络错误，加载校区列表失败');
          });
    },

    // 保存个人信息
    saveProfile() {
      // 确保传递所有必要字段给后端
      const updateData = {
        studentId: this.studentForm.studentId,
        name: this.studentForm.name,
        gender: this.studentForm.gender,
        age: this.studentForm.age,
        phone: this.studentForm.phone,
        classGrade: this.studentForm.classGrade,
        campusId: this.studentForm.campusId,
        photoUrl: this.studentForm.photoUrl,
        // 这些字段虽然在前端是只读的，但确保后端有完整数据
        no: this.studentForm.no,
        trainingHours: this.studentForm.trainingHours,
        balance: this.studentForm.balance,
        userId: this.studentForm.userId
      };

      this.$axios.put('/student/profile', updateData)
          .then(response => {
            const res = response.data;
            if (res.code === 200) {
              this.$message.success('个人信息更新成功');
              this.isEditing = false;
              // 重新加载完整的学生信息
              this.loadStudentProfile();
            } else {
              this.$message.error(res.msg || '更新失败');
            }
          })
          .catch(error => {
            this.$message.error('网络错误，请稍后重试');
            console.error(error);
          });
    },

    // 取消编辑
    cancelEdit() {
      this.studentForm = JSON.parse(JSON.stringify(this.originalForm));
      this.isEditing = false;
    },

    // 修改密码
    changePassword() {
      this.$refs.passwordForm.validate(valid => {
        if (valid) {
          this.$axios.post('/student/changePassword', {
            oldPassword: this.passwordForm.oldPassword,
            newPassword: this.passwordForm.newPassword
          })
              .then(response => {
                const res = response.data;
                if (res.code === 200) {
                  this.$message.success('密码修改成功');
                  this.passwordDialogVisible = false;
                  this.passwordForm = {
                    oldPassword: '',
                    newPassword: '',
                    confirmPassword: ''
                  };
                } else {
                  this.$message.error(res.msg || '密码修改失败');
                }
              })
              .catch(error => {
                this.$message.error('网络错误，请稍后重试');
                console.error(error);
              });
        } else {
          return false;
        }
      });
    },

    // 头像上传成功
    handleAvatarSuccess(res) {
      if (res.code === 200) {
        this.studentForm.photoUrl = res.data.url;
        this.$message.success('头像上传成功');
      } else {
        this.$message.error('头像上传失败');
      }
    },

    // 上传前校验
    beforeAvatarUpload(file) {
      const isJPGOrPNG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPGOrPNG) {
        this.$message.error('头像只能是 JPG 或 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('头像大小不能超过 2MB!');
      }
      return isJPGOrPNG && isLt2M;
    }
  },
  mounted() {
    this.loadStudentProfile();
    this.loadCampusList();
  }
};
</script>

<style scoped>
.profile-container {
  padding: 20px;
}

.profile-card {
  max-width: 800px;
  margin: 0 auto;
}

.avatar-uploader >>> .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 120px;
  height: 120px;
  display: block;
}

.avatar-uploader >>> .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>