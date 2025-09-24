<template>
  <div class="my-students">
    <el-card>
      <div slot="header" class="card-header">
        <span>我的学员</span>
        <el-button type="primary" @click="loadData">刷新</el-button>
      </div>

      <!-- 学员统计 -->
      <el-row :gutter="20" class="stats-row">
        <el-col :span="8">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalStudents }}</div>
            <div class="stat-label">总学员数</div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stat-item">
            <div class="stat-number">{{ stats.maleStudents }}</div>
            <div class="stat-label">男学员</div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stat-item">
            <div class="stat-number">{{ stats.femaleStudents }}</div>
            <div class="stat-label">女学员</div>
          </div>
        </el-col>
      </el-row>

      <!-- 学员列表 -->
      <el-table :data="studentList" v-loading="loading">
        <el-table-column prop="name" label="学员姓名" width="120"></el-table-column>
        <el-table-column prop="phone" label="联系电话" width="130"></el-table-column>
        <el-table-column prop="age" label="年龄" width="80"></el-table-column>
        <el-table-column prop="gender" label="性别" width="80">
          <template slot-scope="scope">
            <el-tag :type="scope.row.gender === 'M' ? 'primary' : 'danger'">
              {{ scope.row.gender === 'M' ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="classGrade" label="班级信息" width="150"></el-table-column>
        <el-table-column prop="trainingHours" label="训练时长(小时)" width="120"></el-table-column>
        <el-table-column prop="paymentStatus" label="缴费状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.paymentStatus === 1 ? 'success' : 'warning'">
              {{ scope.row.paymentStatus === 1 ? '已缴费' : '未缴费' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "MyStudents",
  data() {
    return {
      loading: false,
      studentList: [],
      stats: {
        totalStudents: 0,
        maleStudents: 0,
        femaleStudents: 0
      }
    }
  },
  methods: {
    init() {
      this.loadData()
    },
    loadData() {
      this.loading = true
      const coachId = JSON.parse(sessionStorage.getItem('CurUser')).id

      // 加载学员列表
      this.$axios.get(`/api/coach/students/${coachId}`)
          .then(response => {
            if (response.data.code === 200) {
              this.studentList = response.data.data || []
              this.calculateStats()
            } else {
              this.$message.error(response.data.msg || '加载学员列表失败')
            }
          })
          .catch(error => {
            console.error('加载学员列表失败:', error)
            this.$message.error('加载学员列表失败')
          })
          .finally(() => {
            this.loading = false
          })
    },
    calculateStats() {
      this.stats.totalStudents = this.studentList.length
      this.stats.maleStudents = this.studentList.filter(s => s.gender === 'M').length
      this.stats.femaleStudents = this.studentList.filter(s => s.gender === 'F').length
    }
  },
  created() {
    this.init()
  }
}
</script>

<style scoped>
.my-students {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stats-row {
  margin-bottom: 20px;
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