<template>
  <div class="coach-profile">
    <el-card>
      <div slot="header" class="card-header">
        <span>个人信息管理</span>
        <el-button type="primary" @click="editMode = !editMode">
          {{ editMode ? '取消编辑' : '编辑信息' }}
        </el-button>
      </div>

      <el-form :model="profileForm" :rules="rules" ref="profileForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="教练ID" prop="coachId">
              <el-input v-model="profileForm.coachId" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="profileForm.name" :disabled="!editMode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number
                  v-model="profileForm.age"
                  :disabled="!editMode"
                  :min="18"
                  :max="65"
                  controls-position="right"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="profileForm.gender" :disabled="!editMode">
                <el-radio label="M">男</el-radio>
                <el-radio label="F">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
              <el-input v-model="profileForm.phone" :disabled="!editMode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户ID" prop="userId">
              <el-input v-model="profileForm.userId" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="校区" prop="campusId">
              <el-select v-model="profileForm.campusId" :disabled="!editMode" placeholder="请选择校区">
                <el-option label="主校区" value="1"></el-option>
                <el-option label="东校区" value="2"></el-option>
                <el-option label="西校区" value="3"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="教练等级" prop="level">
              <el-select v-model="profileForm.level" :disabled="!editMode" placeholder="请选择等级">
                <el-option label="初级" :value="1"></el-option>
                <el-option label="中级" :value="2"></el-option>
                <el-option label="高级" :value="3"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="课时费" prop="hourlyRate">
              <el-input v-model="hourlyRateText" :disabled="true">
                <template slot="append">元/小时</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核状态" prop="auditStatus">
              <el-input v-model="auditStatusText" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="比赛成绩" prop="achievements">
          <el-input
              type="textarea"
              v-model="profileForm.achievements"
              :disabled="!editMode"
              :rows="4"
              placeholder="请输入您的比赛成绩和荣誉"
              maxlength="1000"
              show-word-limit
          ></el-input>
        </el-form-item>

        <el-form-item label="照片" prop="photoUrl">
          <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              :disabled="!editMode"
          >
            <img v-if="profileForm.photoUrl" :src="profileForm.photoUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="upload-tip" v-if="editMode">点击上传教练照片</div>
        </el-form-item>

        <el-form-item v-if="editMode">
          <el-button type="primary" @click="saveProfile" :loading="saving">保存修改</el-button>
          <el-button @click="cancelEdit">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 统计信息 -->
    <el-card class="stats-card">
      <div slot="header">
        <span>教练统计信息</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalStudents }}</div>
            <div class="stat-label">总学员数</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalCourses }}</div>
            <div class="stat-label">总课程数</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.avgRating }}</div>
            <div class="stat-label">平均评分</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalHours }}</div>
            <div class="stat-label">总教学时长</div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CoachProfile",
  data() {
    return {
      editMode: false,
      saving: false,
      originalData: {},
      profileForm: {
        coachId: null,
        name: '',
        gender: 'M',
        age: null,
        phone: '',
        userId: null,
        campusId: null,
        achievements: '',
        photoUrl: '',
        level: 1,
        auditStatus: 0,
        hourlyRate: 80
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 10, message: '姓名长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        achievements: [
          { max: 1000, message: '比赛成绩描述不能超过1000个字符', trigger: 'blur' }
        ],
        campusId: [
          { required: true, message: '请选择校区', trigger: 'change' }
        ]
      },
      stats: {
        totalStudents: 0,
        totalCourses: 0,
        avgRating: 0,
        totalHours: 0
      }
    }
  },
  computed: {
    hourlyRateText() {
      // 根据等级自动计算课时费
      switch(this.profileForm.level) {
        case 1: return '80';
        case 2: return '150';
        case 3: return '200';
        default: return '80';
      }
    },
    auditStatusText() {
      const statusMap = {
        0: '待审核',
        1: '审核通过',
        2: '审核拒绝'
      };
      return statusMap[this.profileForm.auditStatus] || '未知状态';
    }
  },
  methods: {
    async init() {
      try {
        const user = JSON.parse(sessionStorage.getItem('CurUser'));
        if (!user) {
          this.$message.error('用户信息不存在，请重新登录');
          this.$router.push('/login');
          return;
        }

        // 根据userId获取教练信息
        const response = await this.$http.get(`/api/coach/profile/user/${user.id}`);
        if (response.data.code === 200) {
          const coachData = response.data.data;
          this.profileForm = {
            ...coachData,
            achievements: coachData.achievements || '',
            photoUrl: coachData.photoUrl || '',
            level: coachData.level || 1,
            auditStatus: coachData.auditStatus || 0
          };
          this.originalData = { ...this.profileForm };
        } else {
          throw new Error(response.data.msg || '获取教练信息失败');
        }

        this.loadStats();
      } catch (error) {
        console.error('初始化失败:', error);
        this.$message.error('加载教练信息失败');
        // 使用sessionStorage中的用户ID创建基础教练信息
        const user = JSON.parse(sessionStorage.getItem('CurUser') || '{}');
        this.profileForm.userId = user.id;
      }
    },

    async loadStats() {
      try {
        const user = JSON.parse(sessionStorage.getItem('CurUser'));
        const response = await this.$http.get(`/api/coach/stats/${user.id}`);
        if (response.data.code === 200) {
          this.stats = response.data.data;
        } else {
          throw new Error(response.data.msg || '获取统计信息失败');
        }
      } catch (error) {
        console.error('加载统计信息失败:', error);
        this.stats = {
          totalStudents: 0,
          totalCourses: 0,
          avgRating: 0,
          totalHours: 0
        };
      }
    },

    async saveProfile() {
      try {
        const valid = await this.$refs.profileForm.validate();
        if (!valid) return;

        this.saving = true;

        // 设置自动计算的课时费
        this.profileForm.hourlyRate = parseInt(this.hourlyRateText);

        // 调用后端API保存教练信息
        const response = await this.$http.put('/api/coach/profile', this.profileForm);

        if (response.data.code === 200) {
          this.$message.success('教练信息保存成功');

          // 更新原始数据
          this.originalData = { ...this.profileForm };
          this.editMode = false;

          // 触发全局事件通知其他组件
          this.$bus.$emit('coachInfoUpdated', this.profileForm);
        } else {
          throw new Error(response.data.msg || '保存失败');
        }
      } catch (error) {
        console.error('保存教练信息失败:', error);
        this.$message.error(error.message || '保存失败，请稍后重试');
      } finally {
        this.saving = false;
      }
    },

    cancelEdit() {
      this.editMode = false;
      this.profileForm = { ...this.originalData };
      this.$refs.profileForm.clearValidate();
    },

    handleAvatarSuccess(response, file) {
      this.profileForm.photoUrl = URL.createObjectURL(file.raw);
      // 实际项目中应该上传到服务器并返回URL
      // this.profileForm.photoUrl = response.data.url;
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return (isJPG || isPNG) && isLt2M;
    }
  },
  created() {
    this.init();
  }
}
</script>

<style scoped>
.coach-profile {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stats-card {
  margin-top: 20px;
}

.stat-item {
  text-align: center;
  padding: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 8px;
  transition: transform 0.3s;
}

.stat-item:hover {
  transform: translateY(-2px);
}

.stat-number {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.avatar-uploader:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.upload-tip {
  font-size: 12px;
  color: #606266;
  margin-top: 10px;
}

@media (max-width: 768px) {
  .el-col {
    margin-bottom: 15px;
  }

  .stat-item {
    padding: 15px;
  }

  .stat-number {
    font-size: 24px;
  }

  .avatar-uploader {
    width: 120px;
    height: 120px;
  }

  .avatar-uploader-icon {
    width: 120px;
    height: 120px;
    line-height: 120px;
  }

  .avatar {
    width: 120px;
    height: 120px;
  }
}
</style>