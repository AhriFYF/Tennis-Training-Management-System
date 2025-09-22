<template>
  <div class="course-requests">
    <el-card>
      <div slot="header" class="card-header">
        <span>预约请求管理</span>
        <el-button type="primary" @click="loadData">刷新</el-button>
      </div>

      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <!-- 待确认的预约请求 -->
        <el-tab-pane label="待确认预约" name="pending">
          <el-table :data="pendingRequests" v-loading="loading">
            <el-table-column prop="courseName" label="课程名称" width="150"></el-table-column>
            <el-table-column prop="studentName" label="学员姓名" width="120"></el-table-column>
            <el-table-column prop="parentsPhoneNumber" label="联系电话" width="130"></el-table-column>
            <el-table-column prop="courseStartTime" label="课程时间" width="180">
              <template slot-scope="scope">
                {{ formatDateTime(scope.row.courseStartTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="courseHours" label="课时" width="80"></el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                <el-tag type="warning">待确认</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="requestTime" label="申请时间" width="180">
              <template slot-scope="scope">
                {{ formatDateTime(scope.row.requestTime) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="success" size="mini" @click="handleRequest(scope.row, '2')">
                  确认
                </el-button>
                <el-button type="danger" size="mini" @click="handleRequest(scope.row, '0')">
                  拒绝
                </el-button>
                <el-button type="info" size="mini" @click="viewRequestDetail(scope.row)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 已确认的预约 -->
        <el-tab-pane label="已确认预约" name="confirmed">
          <el-table :data="confirmedRequests" v-loading="loading">
            <el-table-column prop="courseName" label="课程名称" width="150"></el-table-column>
            <el-table-column prop="studentName" label="学员姓名" width="120"></el-table-column>
            <el-table-column prop="parentsPhoneNumber" label="联系电话" width="130"></el-table-column>
            <el-table-column prop="courseStartTime" label="课程时间" width="180">
              <template slot-scope="scope">
                {{ formatDateTime(scope.row.courseStartTime) }}
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
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button type="info" size="mini" @click="viewRequestDetail(scope.row)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 预约详情对话框 -->
    <el-dialog title="预约详情" :visible.sync="requestDetailVisible" width="600px">
      <div v-if="selectedRequest">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="课程名称">{{ selectedRequest.courseName }}</el-descriptions-item>
          <el-descriptions-item label="课程类型">{{ selectedRequest.courseTypes }}</el-descriptions-item>
          <el-descriptions-item label="学员姓名">{{ selectedRequest.studentName }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ selectedRequest.parentsPhoneNumber }}</el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ formatDateTime(selectedRequest.courseStartTime) }}</el-descriptions-item>
          <el-descriptions-item label="结束时间">{{ formatDateTime(selectedRequest.courseEndTime) }}</el-descriptions-item>
          <el-descriptions-item label="课时">{{ selectedRequest.courseHours }}</el-descriptions-item>
          <el-descriptions-item label="申请时间">{{ formatDateTime(selectedRequest.requestTime) }}</el-descriptions-item>
          <el-descriptions-item label="状态" :span="2">
            <el-tag v-if="selectedRequest.status === '0'" type="info">无人预约</el-tag>
            <el-tag v-else-if="selectedRequest.status === '1'" type="warning">有人预约</el-tag>
            <el-tag v-else-if="selectedRequest.status === '2'" type="success">已确认</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="requestDetailVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "CourseRequests",
  data() {
    return {
      activeTab: 'pending',
      loading: false,
      pendingRequests: [],
      confirmedRequests: [],
      requestDetailVisible: false,
      selectedRequest: null
    }
  },
  methods: {
    init() {
      this.loadData()
    },
    loadData() {
      this.loading = true
      const coachId = JSON.parse(sessionStorage.getItem('CurUser')).id
      
      // 加载待确认的预约请求
      this.$axios.get(`/api/coach/course/pending-requests/${coachId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.pendingRequests = response.data.data
          }
        })
        .catch(error => {
          console.error('加载待确认预约请求失败:', error)
          this.$message.error('加载待确认预约请求失败')
        })

      // 加载已确认的预约
      this.$axios.get(`/api/coach/course/confirmed/${coachId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.confirmedRequests = response.data.data
          }
        })
        .catch(error => {
          console.error('加载已确认预约失败:', error)
          this.$message.error('加载已确认预约失败')
        })
        .finally(() => {
          this.loading = false
        })
    },
    handleTabClick(tab) {
      this.activeTab = tab.name
    },
    handleRequest(row, status) {
      const action = status === '2' ? '确认' : '拒绝'
      this.$confirm(`确定要${action}该预约请求吗？`, '确认操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.put(`/api/coach/course/process/${row.courseInformationId}?status=${status}`)
          .then(response => {
            if (response.data.code === 200) {
              this.$message.success(`${action}成功`)
              this.loadData()
            } else {
              this.$message.error(response.data.message || `${action}失败`)
            }
          })
          .catch(error => {
            console.error(`${action}失败:`, error)
            this.$message.error(`${action}失败`)
          })
      })
    },
    viewRequestDetail(row) {
      this.selectedRequest = row
      this.requestDetailVisible = true
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
.course-requests {
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
