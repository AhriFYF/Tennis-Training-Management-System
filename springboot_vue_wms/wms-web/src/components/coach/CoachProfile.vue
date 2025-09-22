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
            <el-form-item label="账号" prop="no">
              <el-input v-model="profileForm.no" :disabled="true"></el-input>
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
              <el-input-number v-model="profileForm.age" :disabled="!editMode" :min="18" :max="65"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="profileForm.sex" :disabled="!editMode">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
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
            <el-form-item label="角色" prop="roleId">
              <el-input v-model="roleText" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="个人简介" prop="introduction">
          <el-input 
            type="textarea" 
            v-model="profileForm.introduction" 
            :disabled="!editMode"
            :rows="4"
            placeholder="请输入您的个人简介"
          ></el-input>
        </el-form-item>

        <el-form-item label="专业领域" prop="specialties">
          <el-input 
            v-model="profileForm.specialties" 
            :disabled="!editMode"
            placeholder="请输入您的专业领域，用逗号分隔"
          ></el-input>
        </el-form-item>

        <el-form-item label="教学经验" prop="experience">
          <el-input-number 
            v-model="profileForm.experience" 
            :disabled="!editMode" 
            :min="0" 
            :max="50"
            placeholder="请输入教学经验年限"
          ></el-input-number>
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
      profileForm: {
        id: null,
        no: '',
        name: '',
        age: null,
        sex: 1,
        phone: '',
        roleId: 2,
        introduction: '',
        specialties: '',
        experience: 0
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
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
    roleText() {
      return '教练'
    }
  },
  methods: {
    init() {
      const user = JSON.parse(sessionStorage.getItem('CurUser'))
      this.profileForm = {
        ...user,
        introduction: user.introduction || '',
        specialties: user.specialties || '',
        experience: user.experience || 0
      }
      this.loadStats()
    },
    loadStats() {
      // 模拟统计数据
      this.stats = {
        totalStudents: 15,
        totalCourses: 28,
        avgRating: 4.8,
        totalHours: 156
      }
    },
    saveProfile() {
      this.$refs.profileForm.validate((valid) => {
        if (valid) {
          this.saving = true
          // 这里调用API保存个人信息
          setTimeout(() => {
            this.saving = false
            this.editMode = false
            this.$message.success('个人信息保存成功')
            // 更新sessionStorage中的用户信息
            sessionStorage.setItem('CurUser', JSON.stringify(this.profileForm))
          }, 1000)
        } else {
          this.$message.error('请检查输入信息')
        }
      })
    },
    cancelEdit() {
      this.editMode = false
      this.init() // 重新加载原始数据
    }
  },
  created() {
    this.init()
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
</style>
