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
            <el-table-column prop="name" label="学员姓名" width="120"></el-table-column>
            <el-table-column prop="parentsPhoneNumber" label="家长电话" width="130"></el-table-column>
            <el-table-column prop="phoneNumber" label="学员电话" width="130"></el-table-column>
            <el-table-column prop="durationOfClassHours" label="课时时长" width="120"></el-table-column>
            <el-table-column prop="courseHours" label="课时数" width="80"></el-table-column>
            <el-table-column prop="coursePrices" label="课程价格" width="100">
              <template slot-scope="scope">
                ¥{{ scope.row.coursePrices }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status === '0'" type="warning">待确认</el-tag>
                <el-tag v-else-if="scope.row.status === '1'" type="success">已确认</el-tag>
                <el-tag v-else-if="scope.row.status === '2'" type="danger">已拒绝</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="申请时间" width="180">
              <template slot-scope="scope">
                {{ formatDateTime(scope.row.createTime) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="success" size="mini" @click="handleRequest(scope.row.studentCourseSelectionId, '1')">
                  确认
                </el-button>
                <el-button type="danger" size="mini" @click="handleRequest(scope.row.studentCourseSelectionId, '2')">
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
            <el-table-column prop="name" label="学员姓名" width="120"></el-table-column>
            <el-table-column prop="parentsPhoneNumber" label="家长电话" width="130"></el-table-column>
            <el-table-column prop="phoneNumber" label="学员电话" width="130"></el-table-column>
            <el-table-column prop="durationOfClassHours" label="课时时长" width="120"></el-table-column>
            <el-table-column prop="courseHours" label="课时数" width="80"></el-table-column>
            <el-table-column prop="coursePrices" label="课程价格" width="100">
              <template slot-scope="scope">
                ¥{{ scope.row.coursePrices }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status === '0'" type="warning">待确认</el-tag>
                <el-tag v-else-if="scope.row.status === '1'" type="success">已确认</el-tag>
                <el-tag v-else-if="scope.row.status === '2'" type="danger">已拒绝</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="申请时间" width="180">
              <template slot-scope="scope">
                {{ formatDateTime(scope.row.createTime) }}
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
    <el-dialog title="预约详情" :visible.sync="requestDetailVisible" width="700px">
      <div v-if="selectedRequest">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="课程名称">{{ selectedRequest.courseName }}</el-descriptions-item>
          <el-descriptions-item label="课程编号">{{ selectedRequest.courseNumber }}</el-descriptions-item>
          <el-descriptions-item label="课程类型">{{ selectedRequest.courseTypes }}</el-descriptions-item>
          <el-descriptions-item label="教练姓名">{{ selectedRequest.coachName }}</el-descriptions-item>
          <el-descriptions-item label="学员姓名">{{ selectedRequest.name }}</el-descriptions-item>
          <el-descriptions-item label="学员年龄">{{ selectedRequest.studentAge }}</el-descriptions-item>
          <el-descriptions-item label="家长电话">{{ selectedRequest.parentsPhoneNumber }}</el-descriptions-item>
          <el-descriptions-item label="学员电话">{{ selectedRequest.phoneNumber }}</el-descriptions-item>
          <el-descriptions-item label="课时时长">{{ selectedRequest.durationOfClassHours }}</el-descriptions-item>
          <el-descriptions-item label="课时数">{{ selectedRequest.courseHours }}</el-descriptions-item>
          <el-descriptions-item label="课程价格">¥{{ selectedRequest.coursePrices }}</el-descriptions-item>
          <el-descriptions-item label="支付状态">{{ selectedRequest.payState }}</el-descriptions-item>
          <el-descriptions-item label="支付方式">{{ selectedRequest.payType }}</el-descriptions-item>
          <el-descriptions-item label="申请时间">{{ formatDateTime(selectedRequest.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ formatDateTime(selectedRequest.updateTime) }}</el-descriptions-item>
          <el-descriptions-item label="状态" :span="2">
            <el-tag v-if="selectedRequest.status === '0'" type="warning">待确认</el-tag>
            <el-tag v-else-if="selectedRequest.status === '1'" type="success">已确认</el-tag>
            <el-tag v-else-if="selectedRequest.status === '2'" type="danger">已拒绝</el-tag>
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
      const userInfo = JSON.parse(sessionStorage.getItem('CurUser'))
      const coachId = parseInt(userInfo.id)
      console.log('获取到的coachId:', coachId, '用户信息:', userInfo)

      if (!coachId) {
        this.$message.error('未获取到教练ID')
        this.loading = false
        return
      }

      // 使用完整的URL路径，避免相对路径问题
      const baseUrl = process.env.NODE_ENV === 'development'
          ? 'http://172.17.5.101:8080'
          : window.location.origin

      // 加载待确认的预约请求
      this.$axios.get(`${baseUrl}/api/coach-course-selection/pending-requests/${coachId}`)
          .then(response => {
            console.log(`获取待确认预约请求响应:`, response)
            if (response.data.code === 200) {
              this.pendingRequests = response.data.data || []
            } else {
              this.$message.error(response.data.msg || '加载待确认预约请求失败')
            }
          })
          .catch(error => {
            console.error('加载待确认预约请求失败:', error)
            if (error.response) {
              // 服务器响应了错误状态码
              this.$message.error(`服务器错误: ${error.response.status} - ${error.response.statusText}`)
            } else if (error.request) {
              // 请求已发送但没有收到响应
              this.$message.error('网络错误: 无法连接到服务器')
            } else {
              // 其他错误
              this.$message.error('请求配置错误')
            }
          })

      // 加载已确认的预约
      this.$axios.get(`${baseUrl}/api/coach-course-selection/confirmed/${coachId}`)
          .then(response => {
            console.log(`获取已确认预约响应:`, response)
            if (response.data.code === 200) {
              this.confirmedRequests = response.data.data || []
            } else {
              this.$message.error(response.data.msg || '加载已确认预约失败')
            }
          })
          .catch(error => {
            console.error('加载已确认预约失败:', error)
            if (error.response) {
              this.$message.error(`服务器错误: ${error.response.status} - ${error.response.statusText}`)
            } else if (error.request) {
              this.$message.error('网络错误: 无法连接到服务器')
            } else {
              this.$message.error('请求配置错误')
            }
          })
          .finally(() => {
            this.loading = false
          })
    },
    handleTabClick(tab) {
      this.activeTab = tab.name
    },
    handleRequest(id, status) {
      const action = status === '1' ? '确认' : '拒绝'
      console.log(`处理预约请求 (ID: ${id}, 状态: ${status})`)
      this.$confirm(`确定要${action}该预约请求吗？`, '确认操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.put(`/api/coach-course-selection/process/${id}?status=${status}`)
            .then(response => {
              console.log(`处理预约请求响应 (ID: ${id}):`, response)
              if (response.data.code === 200) {
                this.$message.success(`${action}成功`)
                this.loadData()
              } else {
                console.error(`处理预约请求失败 (ID: ${id}), 错误码: ${response.data.code}, 错误信息: ${response.data.msg || '未知错误'}`)
                this.$message.error(response.data.msg || `${action}失败`)
              }
            })
            .catch(error => {
              console.error(`处理预约请求失败 (ID: ${id}):`, error.response || error.message || error)
              this.$message.error(`${action}失败: ${error.response?.data?.message || error.message || '未知错误'}`)
            })
      }).catch(() => {
        console.log('取消操作')
      })
    },
    viewRequestDetail(row) {
      this.selectedRequest = row
      this.requestDetailVisible = true
    },
    formatDateTime(dateTime) {
      if (!dateTime) return ''
      try {
        return new Date(dateTime).toLocaleString('zh-CN')
      } catch (e) {
        return dateTime
      }
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