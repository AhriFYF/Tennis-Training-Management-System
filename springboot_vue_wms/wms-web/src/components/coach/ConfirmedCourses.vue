<template>
  <div class="confirmed-courses">
    <el-card>
      <div slot="header" class="card-header">
        <span>确认课程查看</span>
        <el-button type="primary" @click="loadData">刷新</el-button>
      </div>

      <!-- 课程统计 -->
      <el-row :gutter="20" class="stats-row">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalCourses }}</div>
            <div class="stat-label">总课程数</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalHours }}</div>
            <div class="stat-label">总课时</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.totalStudents }}</div>
            <div class="stat-label">参与学员</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-number">{{ stats.avgRating }}</div>
            <div class="stat-label">平均评分</div>
          </div>
        </el-col>
      </el-row>

      <!-- 课程列表 -->
      <el-table :data="courseList" v-loading="loading">
        <el-table-column prop="courseName" label="课程名称" width="150"></el-table-column>
        <el-table-column prop="courseTypes" label="课程类型" width="120"></el-table-column>
        <el-table-column prop="studentName" label="学员姓名" width="120"></el-table-column>
        <el-table-column prop="parentsPhoneNumber" label="联系电话" width="130"></el-table-column>
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
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag type="success">已确认</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="requestTime" label="申请时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.requestTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="info" size="mini" @click="viewCourseDetail(scope.row)">
              详情
            </el-button>
            <el-button type="success" size="mini" @click="startCourse(scope.row)">
              开始课程
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 课程详情对话框 -->
    <el-dialog title="课程详情" :visible.sync="courseDetailVisible" width="600px">
      <div v-if="selectedCourse">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="课程名称">{{ selectedCourse.courseName }}</el-descriptions-item>
          <el-descriptions-item label="课程类型">{{ selectedCourse.courseTypes }}</el-descriptions-item>
          <el-descriptions-item label="学员姓名">{{ selectedCourse.studentName }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ selectedCourse.parentsPhoneNumber }}</el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ formatDateTime(selectedCourse.courseStartTime) }}</el-descriptions-item>
          <el-descriptions-item label="结束时间">{{ formatDateTime(selectedCourse.courseEndTime) }}</el-descriptions-item>
          <el-descriptions-item label="课时">{{ selectedCourse.courseHours }}</el-descriptions-item>
          <el-descriptions-item label="申请时间">{{ formatDateTime(selectedCourse.requestTime) }}</el-descriptions-item>
          <el-descriptions-item label="状态" :span="2">
            <el-tag type="success">已确认</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="courseDetailVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 开始课程对话框 -->
    <el-dialog title="开始课程" :visible.sync="startCourseVisible" width="500px">
      <div v-if="selectedCourse">
        <el-form :model="courseForm" label-width="100px">
          <el-form-item label="课程名称">
            <el-input v-model="courseForm.courseName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="学员姓名">
            <el-input v-model="courseForm.studentName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="课程时间">
            <el-input v-model="courseForm.courseTime" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="课程内容">
            <el-input type="textarea" v-model="courseForm.content" placeholder="请输入本次课程内容"></el-input>
          </el-form-item>
          <el-form-item label="学员表现">
            <el-input type="textarea" v-model="courseForm.performance" placeholder="请输入学员表现评价"></el-input>
          </el-form-item>
          <el-form-item label="课后作业">
            <el-input type="textarea" v-model="courseForm.homework" placeholder="请输入课后作业"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="startCourseVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmStartCourse">确认开始</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ConfirmedCourses",
  data() {
    return {
      loading: false,
      courseList: [],
      courseDetailVisible: false,
      startCourseVisible: false,
      selectedCourse: null,
      courseForm: {
        courseName: '',
        studentName: '',
        courseTime: '',
        content: '',
        performance: '',
        homework: ''
      },
      stats: {
        totalCourses: 0,
        totalHours: 0,
        totalStudents: 0,
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
      
      this.$axios.get(`/api/coach/course/confirmed/${coachId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.courseList = response.data.data
            this.calculateStats()
          }
        })
        .catch(error => {
          console.error('加载确认课程失败:', error)
          this.$message.error('加载确认课程失败')
        })
        .finally(() => {
          this.loading = false
        })
    },
    calculateStats() {
      this.stats.totalCourses = this.courseList.length
      this.stats.totalHours = this.courseList.reduce((sum, course) => sum + (course.courseHours || 0), 0)
      this.stats.totalStudents = new Set(this.courseList.map(course => course.studentUsers)).size
      this.stats.avgRating = 4.8 // 模拟数据
    },
    viewCourseDetail(row) {
      this.selectedCourse = row
      this.courseDetailVisible = true
    },
    startCourse(row) {
      this.selectedCourse = row
      this.courseForm = {
        courseName: row.courseName,
        studentName: row.studentName,
        courseTime: `${this.formatDateTime(row.courseStartTime)} - ${this.formatDateTime(row.courseEndTime)}`,
        content: '',
        performance: '',
        homework: ''
      }
      this.startCourseVisible = true
    },
    confirmStartCourse() {
      this.$message.success('课程已开始，请认真教学！')
      this.startCourseVisible = false
      // 这里可以调用API记录课程开始状态
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
.confirmed-courses {
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
