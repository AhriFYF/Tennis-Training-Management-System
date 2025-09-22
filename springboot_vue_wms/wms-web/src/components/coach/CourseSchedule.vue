<template>
  <div class="course-schedule">
    <el-card>
      <div slot="header" class="card-header">
        <span>课程时间管理</span>
        <el-button type="primary" @click="showAddDialog = true">上传课程时间</el-button>
      </div>

      <!-- 课程时间列表 -->
      <el-table :data="courseList" v-loading="loading">
        <el-table-column prop="courseName" label="课程名称" width="150"></el-table-column>
        <el-table-column prop="courseTypes" label="课程类型" width="120"></el-table-column>
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
            <el-tag v-if="scope.row.status === '0'" type="info">无人预约</el-tag>
            <el-tag v-else-if="scope.row.status === '1'" type="warning">有人预约</el-tag>
            <el-tag v-else-if="scope.row.status === '2'" type="success">已确认</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="deleteCourse(scope.row)" 
                       v-if="scope.row.status === '0'">删除</el-button>
            <el-button type="info" size="mini" @click="viewCourseDetail(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加课程时间对话框 -->
    <el-dialog title="上传课程时间" :visible.sync="showAddDialog" width="600px">
      <el-form :model="courseForm" :rules="courseRules" ref="courseForm" label-width="100px">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="courseForm.courseName" placeholder="请输入课程名称"></el-input>
        </el-form-item>
        <el-form-item label="课程类型" prop="courseTypes">
          <el-select v-model="courseForm.courseTypes" placeholder="请选择课程类型">
            <el-option label="基础训练" value="基础训练"></el-option>
            <el-option label="进阶训练" value="进阶训练"></el-option>
            <el-option label="比赛训练" value="比赛训练"></el-option>
            <el-option label="体能训练" value="体能训练"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="courseStartTime">
          <el-date-picker
            v-model="courseForm.courseStartTime"
            type="datetime"
            placeholder="选择开始时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="courseEndTime">
          <el-date-picker
            v-model="courseForm.courseEndTime"
            type="datetime"
            placeholder="选择结束时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="课时" prop="courseHours">
          <el-input-number v-model="courseForm.courseHours" :min="0.5" :max="8" :step="0.5"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="addCourse" :loading="saving">确定</el-button>
      </div>
    </el-dialog>

    <!-- 课程详情对话框 -->
    <el-dialog title="课程详情" :visible.sync="showDetailDialog" width="500px">
      <div v-if="selectedCourse">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="课程名称">{{ selectedCourse.courseName }}</el-descriptions-item>
          <el-descriptions-item label="课程类型">{{ selectedCourse.courseTypes }}</el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ formatDateTime(selectedCourse.courseStartTime) }}</el-descriptions-item>
          <el-descriptions-item label="结束时间">{{ formatDateTime(selectedCourse.courseEndTime) }}</el-descriptions-item>
          <el-descriptions-item label="课时">{{ selectedCourse.courseHours }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag v-if="selectedCourse.status === '0'" type="info">无人预约</el-tag>
            <el-tag v-else-if="selectedCourse.status === '1'" type="warning">有人预约</el-tag>
            <el-tag v-else-if="selectedCourse.status === '2'" type="success">已确认</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDetailDialog = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "CourseSchedule",
  data() {
    return {
      loading: false,
      saving: false,
      courseList: [],
      showAddDialog: false,
      showDetailDialog: false,
      selectedCourse: null,
      courseForm: {
        courseName: '',
        courseTypes: '',
        courseStartTime: '',
        courseEndTime: '',
        courseHours: 1
      },
      courseRules: {
        courseName: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ],
        courseTypes: [
          { required: true, message: '请选择课程类型', trigger: 'change' }
        ],
        courseStartTime: [
          { required: true, message: '请选择开始时间', trigger: 'change' }
        ],
        courseEndTime: [
          { required: true, message: '请选择结束时间', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    init() {
      this.loadCourseList()
    },
    loadCourseList() {
      this.loading = true
      const coachId = JSON.parse(sessionStorage.getItem('CurUser')).id
      
      this.$axios.get(`/api/coach/course/list/${coachId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.courseList = response.data.data
          }
        })
        .catch(error => {
          console.error('加载课程列表失败:', error)
          this.$message.error('加载课程列表失败')
        })
        .finally(() => {
          this.loading = false
        })
    },
    addCourse() {
      this.$refs.courseForm.validate((valid) => {
        if (valid) {
          this.saving = true
          const coachId = JSON.parse(sessionStorage.getItem('CurUser')).id
          const courseData = {
            ...this.courseForm,
            coachUser: coachId,
            coachName: JSON.parse(sessionStorage.getItem('CurUser')).name
          }
          
          this.$axios.post('/api/coach/course/upload', courseData)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('课程时间上传成功')
                this.showAddDialog = false
                this.resetForm()
                this.loadCourseList()
              } else {
                this.$message.error(response.data.message || '上传失败')
              }
            })
            .catch(error => {
              console.error('上传失败:', error)
              this.$message.error('上传失败')
            })
            .finally(() => {
              this.saving = false
            })
        }
      })
    },
    deleteCourse(row) {
      this.$confirm('确定要删除该课程时间吗？', '确认操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`/api/coach/course/${row.courseInformationId}`)
          .then(response => {
            if (response.data.code === 200) {
              this.$message.success('删除成功')
              this.loadCourseList()
            } else {
              this.$message.error(response.data.message || '删除失败')
            }
          })
          .catch(error => {
            console.error('删除失败:', error)
            this.$message.error('删除失败')
          })
      })
    },
    viewCourseDetail(row) {
      this.selectedCourse = row
      this.showDetailDialog = true
    },
    resetForm() {
      this.courseForm = {
        courseName: '',
        courseTypes: '',
        courseStartTime: '',
        courseEndTime: '',
        courseHours: 1
      }
      this.$refs.courseForm.resetFields()
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
.course-schedule {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  text-align: right;
}
</style>
