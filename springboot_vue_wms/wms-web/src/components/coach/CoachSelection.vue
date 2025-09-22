<template>
  <div class="coach-selection">
    <el-card>
      <div slot="header" class="card-header">
        <span>双选关系管理</span>
        <el-button type="primary" @click="loadData">刷新</el-button>
      </div>

      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <!-- 待确认的双选请求 -->
        <el-tab-pane label="待确认请求" name="pending">
          <el-table :data="pendingSelections" v-loading="loading">
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
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                <el-tag type="warning">待确认</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="success" size="mini" @click="handleSelection(scope.row, 'accepted')">
                  同意
                </el-button>
                <el-button type="danger" size="mini" @click="handleSelection(scope.row, 'rejected')">
                  拒绝
                </el-button>
                <el-button type="info" size="mini" @click="viewStudentDetail(scope.row)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 已确认的双选关系 -->
        <el-tab-pane label="已确认关系" name="accepted">
          <el-table :data="acceptedSelections" v-loading="loading">
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
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                <el-tag type="success">已确认</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template slot-scope="scope">
                <el-button type="info" size="mini" @click="viewStudentDetail(scope.row)">
                  详情
                </el-button>
                <el-button type="danger" size="mini" @click="removeSelection(scope.row)">
                  移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
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
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="studentDetailVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "CoachSelection",
  data() {
    return {
      activeTab: 'pending',
      loading: false,
      pendingSelections: [],
      acceptedSelections: [],
      studentDetailVisible: false,
      selectedStudent: null
    }
  },
  methods: {
    init() {
      this.loadData()
    },
    loadData() {
      this.loading = true
      const coachId = JSON.parse(sessionStorage.getItem('CurUser')).id
      
      // 加载待确认的双选请求
      this.$axios.get(`/api/coach/selection/pending/${coachId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.pendingSelections = response.data.data
          }
        })
        .catch(error => {
          console.error('加载待确认双选请求失败:', error)
          this.$message.error('加载待确认双选请求失败')
        })

      // 加载已确认的双选关系
      this.$axios.get(`/api/coach/selection/accepted/${coachId}`)
        .then(response => {
          if (response.data.code === 200) {
            this.acceptedSelections = response.data.data
          }
        })
        .catch(error => {
          console.error('加载已确认双选关系失败:', error)
          this.$message.error('加载已确认双选关系失败')
        })
        .finally(() => {
          this.loading = false
        })
    },
    handleTabClick(tab) {
      this.activeTab = tab.name
    },
    handleSelection(row, status) {
      const action = status === 'accepted' ? '同意' : '拒绝'
      this.$confirm(`确定要${action}该学员的双选请求吗？`, '确认操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.put(`/api/coach/selection/process/${row.selectionId}?status=${status}`)
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
    removeSelection(row) {
      this.$confirm('确定要移除该学员的双选关系吗？', '确认操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`/api/coach/selection/${row.selectionId}`)
          .then(response => {
            if (response.data.code === 200) {
              this.$message.success('移除成功')
              this.loadData()
            } else {
              this.$message.error(response.data.message || '移除失败')
            }
          })
          .catch(error => {
            console.error('移除失败:', error)
            this.$message.error('移除失败')
          })
      })
    },
    viewStudentDetail(row) {
      this.selectedStudent = row
      this.studentDetailVisible = true
    }
  },
  created() {
    this.init()
  }
}
</script>

<style scoped>
.coach-selection {
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
