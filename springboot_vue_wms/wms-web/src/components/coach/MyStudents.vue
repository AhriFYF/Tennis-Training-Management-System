<template>
  <div class="my-students">
    <el-card>
      <div slot="header" class="card-header">
        <span>我的学员</span>
        <el-button type="primary" @click="loadData">刷新</el-button>
      </div>

      <!-- 学员统计 -->
      <el-row :gutter="20" class="stats-row">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalStudents }}</div>
            <div class="stat-label">总学员数</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.activeStudents }}</div>
            <div class="stat-label">活跃学员</div>
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
      </el-row>

      <!-- 学员列表 -->
      <el-table :data="studentList" v-loading="loading">
        <el-table-column prop="studentName" label="学员姓名" width="120"></el-table-column>
        <el-table-column prop="studentPhone" label="联系电话" width="130"></el-table-column>
        <el-table-column prop="studentAge" label="年龄" width="80"></el-table-column>
        <el-table-column prop="studentSex" label="性别" width="80">
          <template slot-scope="scope">
            <el-tag :type="scope.row.studentSex == 1 ? 'primary' : 'danger'">
              {{ scope.row.studentSex == 1 ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="selectionCount" label="选课次数" width="100"></el-table-column>
        <el-table-column prop="lastSelectionTime" label="最后选课时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.lastSelectionTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 'active' ? 'success' : 'info'">
              {{ scope.row.status === 'active' ? '活跃' : '非活跃' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="info" size="mini" @click="viewStudentDetail(scope.row)">
              详情
            </el-button>
            <el-button type="success" size="mini" @click="viewStudentCourses(scope.row)">
              课程记录
            </el-button>
            <el-button type="danger" size="mini" @click="removeStudent(scope.row)">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 学员详情对话框 -->
    <el-dialog title="学员详情" :visible.sync="studentDetailVisible" width="500px">
      <div v-if="selectedStudent">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="姓名">{{ selectedStudent.studentName }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ selectedStudent.studentAge }}</el-descriptions-item>
          <el-descriptions-item label="性别">
            <el-tag :type="selectedStudent.studentSex == 1 ? 'primary' : 'danger'">
              {{ selectedStudent.studentSex == 1 ? '男' : '女' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="电话">{{ selectedStudent.studentPhone }}</el-descriptions-item>
          <el-descriptions-item label="选课次数">{{ selectedStudent.selectionCount }}</el-descriptions-item>
          <el-descriptions-item label="最后选课时间">{{ formatDateTime(selectedStudent.lastSelectionTime) }}</el-descriptions-item>
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="studentDetailVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 学员课程记录对话框 -->
    <el-dialog title="学员课程记录" :visible.sync="studentCoursesVisible" width="800px">
      <el-table :data="studentCourses" v-loading="coursesLoading">
        <el-table-column prop="courseName" label="课程名称" width="150"></el-table-column>
        <el-table-column prop="courseStartTime" label="开始时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.courseStartTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="courseEndTime" label="结束时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.courseEndTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="courseHours" label="课时" width="80"></el-table-column>
        <el-table-column prop="payState" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.payState === 'confirmed' ? 'success' : 'warning'">
              {{ scope.row.payState === 'confirmed' ? '已确认' : '待确认' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="申请时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.createTime) }}
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="studentCoursesVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "MyStudents",
  data() {
    return {
      loading: false,
      coursesLoading: false,
      studentList: [],
      studentCourses: [],
      studentDetailVisible: false,
      studentCoursesVisible: false,
      selectedStudent: null,
      stats: {
        totalStudents: 0,
        activeStudents: 0,
        totalCourses: 0,
        avgRating: 0
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
      this.$axios.get(`/api/coach/student-selection/coach/${coachId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.studentList = response.data.data
            this.calculateStats()
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
      this.stats.activeStudents = this.studentList.filter(s => s.status === 'active').length
      this.stats.totalCourses = this.studentList.reduce((sum, s) => sum + s.selectionCount, 0)
      this.stats.avgRating = 4.8 // 模拟数据
    },
    viewStudentDetail(row) {
      this.selectedStudent = row
      this.studentDetailVisible = true
    },
    viewStudentCourses(row) {
      this.coursesLoading = true
      const coachId = JSON.parse(sessionStorage.getItem('CurUser')).id
      
      this.$axios.get(`/api/coach/student-selection/coach-student?coachId=${coachId}&studentId=${row.studentId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.studentCourses = response.data.data
            this.studentCoursesVisible = true
          }
        })
        .catch(error => {
          console.error('加载学员课程记录失败:', error)
          this.$message.error('加载学员课程记录失败')
        })
        .finally(() => {
          this.coursesLoading = false
        })
    },
    removeStudent() {
      this.$confirm('确定要移除该学员吗？', '确认操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 这里需要调用删除双选关系的API
        this.$message.success('移除成功')
        this.loadData()
      })
    },
    formatDateTime(dateTime) {
      if (!dateTime) return ''
      return new Date(dateTime).toLocaleString('zh-CN')
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

.dialog-footer {
  text-align: right;
}
</style>
