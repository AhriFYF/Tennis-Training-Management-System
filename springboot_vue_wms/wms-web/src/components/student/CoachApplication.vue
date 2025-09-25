<!-- CoachApplication.vue 教练申请管理组件 -->
<template>
  <div class="coach-application-container">
    <h2>教练管理</h2>

    <!-- 导航标签页 -->
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="所有教练" name="allCoaches"></el-tab-pane>
      <el-tab-pane label="我的教练" name="myCoaches"></el-tab-pane>
      <el-tab-pane label="教练申请" name="applications"></el-tab-pane>
    </el-tabs>

    <!-- 所有教练 -->
    <div v-if="activeTab === 'allCoaches'">
      <el-card class="search-card">
        <div slot="header" class="clearfix">
          <span>查询条件</span>
        </div>
        <el-form :model="searchForm" label-width="80px" :inline="true">
          <el-form-item label="姓名">
            <el-input v-model="searchForm.name" placeholder="请输入教练姓名"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="searchForm.gender" placeholder="请选择性别" clearable>
              <el-option label="男" value="M"></el-option>
              <el-option label="女" value="F"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchCoaches">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <el-card class="results-card" style="margin-top: 20px;">
        <div slot="header" class="clearfix">
          <span>教练列表</span>
        </div>
        <div v-if="allCoaches.length === 0" class="empty-tip">
          <el-alert title="暂无教练信息" type="info" show-icon></el-alert>
        </div>

        <el-row :gutter="20">
          <el-col :span="8" v-for="coach in allCoaches" :key="coach.coachId" style="margin-bottom: 20px;">
            <el-card class="coach-card">
              <div class="coach-header">
                <el-avatar :size="80" :src="coach.photoUrl" v-if="coach.photoUrl"></el-avatar>
                <el-avatar :size="80" icon="el-icon-user-solid" v-else></el-avatar>
                <div class="coach-info">
                  <h3>{{ coach.name }}</h3>
                  <p>{{ getLevelText(coach.level) }}教练</p>
                </div>
              </div>

              <div class="coach-details">
                <p><strong>性别:</strong> {{ coach.gender === 'M' ? '男' : '女' }}</p>
                <p><strong>年龄:</strong> {{ coach.age }}</p>
                <p><strong>课时费:</strong> {{ coach.hourlyRate }}元/小时</p>
                <p><strong>校区:</strong> {{ coach.campusName }}</p>
              </div>

              <div class="achievements">
                <h4>比赛成绩:</h4>
                <p>{{ coach.achievements || '暂无成绩信息' }}</p>
              </div>

              <el-button
                  type="primary"
                  @click="applyCoach(coach)"
                  :disabled="myCoaches.length >= 2 || isApplied(coach.coachId)"
              >
                {{ isApplied(coach.coachId) ? '已申请' : '申请教练' }}
              </el-button>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </div>

    <!-- 我的教练 -->
    <div v-if="activeTab === 'myCoaches'">
      <el-card class="my-coaches-card">
        <div slot="header" class="clearfix">
          <span>我的教练</span>
        </div>
        <div v-if="myCoaches.length === 0" class="empty-tip">
          <el-alert title="暂无教练" type="info" show-icon></el-alert>
        </div>
        <el-row :gutter="20">
          <el-col :span="8" v-for="coach in myCoaches" :key="coach.coachId" style="margin-bottom: 20px;">
            <el-card class="coach-card">
              <div class="coach-header">
                <el-avatar :size="80" :src="coach.photoUrl" v-if="coach.photoUrl"></el-avatar>
                <el-avatar :size="80" icon="el-icon-user-solid" v-else></el-avatar>
                <div class="coach-info">
                  <h3>{{ coach.name }}</h3>
                  <p>{{ getLevelText(coach.level) }}教练</p>
                </div>
              </div>

              <div class="coach-details">
                <p><strong>性别:</strong> {{ coach.gender === 'M' ? '男' : '女' }}</p>
                <p><strong>年龄:</strong> {{ coach.age }}</p>
                <p><strong>课时费:</strong> {{ coach.hourlyRate }}元/小时</p>
                <p><strong>校区:</strong> {{ coach.campusName }}</p>
              </div>

              <div class="achievements">
                <h4>比赛成绩:</h4>
                <p>{{ coach.achievements || '暂无成绩信息' }}</p>
              </div>

              <el-button type="danger" @click="releaseCoach(coach)">解除关系</el-button>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </div>

    <!-- 教练申请 -->
    <div v-if="activeTab === 'applications'">
      <el-alert
          title="每位学员最多选择两位教练"
          type="info"
          show-icon
          style="margin-bottom: 20px;"
      ></el-alert>

      <el-card class="application-card">
        <div slot="header" class="clearfix">
          <span>教练申请记录</span>
        </div>

        <div v-if="applications.length === 0" class="empty-tip">
          <el-alert title="暂无教练申请记录" type="info" show-icon></el-alert>
        </div>

        <el-table :data="applications" style="width: 100%" stripe>
          <el-table-column label="教练信息">
            <template slot-scope="scope">
              <div class="coach-info">
                <el-avatar :size="50" :src="scope.row.photoUrl" v-if="scope.row.photoUrl"></el-avatar>
                <el-avatar :size="50" icon="el-icon-user-solid" v-else></el-avatar>
                <div class="info-text">
                  <strong>{{ scope.row.name }}</strong>
                  <p>{{ getLevelText(scope.row.level) }}教练 · {{ scope.row.campusName }}</p>
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="120">
            <template slot-scope="scope">
              <el-tag :type="getStatusTagType(scope.row.status)">
                {{ scope.row.status }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "CoachApplication",
  data() {
    return {
      activeTab: 'allCoaches',
      applications: [],
      allCoaches: [],
      myCoaches: [],
      searchForm: {
        name: '',
        gender: ''
      }
    };
  },
  methods: {
    getLevelText(level) {
      switch(level) {
        case 1: return '初级';
        case 2: return '中级';
        case 3: return '高级';
      }
    },
    getStatusTagType(status) {
      switch(status) {
        case '已确认': return 'success';
        case '待确认': return 'warning';
        case '已拒绝': return 'danger';
        default: return '';
      }
    },
    formatDateTime(dateTime) {
      return new Date(dateTime).toLocaleString();
    },
    handleTabClick(tab) {
      switch(tab.name) {
        case 'allCoaches':
          this.loadAllCoaches();
          break;
        case 'myCoaches':
          this.loadMyCoaches();
          break;
        case 'applications':
          this.loadApplications();
          break;
      }
    },
    // 所有教练相关方法
    loadAllCoaches() {
      this.$axios.get(`${this.$httpUrl}/api/student/coach/list`)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.allCoaches = res.data;
            }
          });
    },
    searchCoaches() {
      this.$axios.post(`${this.$httpUrl}/api/student/coach/search`, this.searchForm)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.allCoaches = res.data;
            }
          });
    },
    resetSearch() {
      this.searchForm = {
        name: '',
        gender: ''
      };
      this.loadAllCoaches();
    },
    // 我的教练相关方法
    loadMyCoaches() {
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.$axios.get(`${this.$httpUrl}/api/student/coach/my/list/${user.id}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.myCoaches = res.data;
              }
            });
      }
    },
    releaseCoach(coach) {
      this.$confirm(`确定要解除与教练 ${coach.name} 的关系吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const user = JSON.parse(sessionStorage.getItem('CurUser'));
        this.$axios.delete(`${this.$httpUrl}/api/student/coach/release/${user.id}/${coach.coachId}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.$message.success(res.msg);
                this.loadMyCoaches();
              } else {
                this.$message.error(res.msg);
              }
            });
      });
    },
    // 教练申请相关方法
    loadApplications() {
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.$axios.get(`${this.$httpUrl}/api/student/coach/application/list/${user.id}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.applications = res.data;
              }
            });
      }
    },
    cancelApplication(application) {
      this.$confirm('确定要撤销此教练申请吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`${this.$httpUrl}/api/student/coach/application/cancel/${application.id}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.$message.success(res.msg);
                this.loadApplications();
              } else {
                this.$message.error(res.msg);
              }
            });
      });
    },
    replaceCoach(application) {
      this.$prompt('请输入新教练姓名', '更换教练', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        this.$axios.post(`${this.$httpUrl}/api/student/coach/replace`, {
          oldCoachId: application.coachId,
          newCoachName: value
        }).then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.$message.success('更换申请已提交，等待三方确认');
              } else {
                this.$message.error(res.msg);
              }
            });
      });
    },
    // 申请教练
    applyCoach(coach) {
      // 检查是否已申请
      if (this.isApplied(coach.coachId)) {
        this.$message.warning('您已申请该教练');
        return;
      }

      // 检查是否已达到申请上限
      if (this.myCoaches.length >= 2) {
        this.$message.warning('每位学员最多选择两位教练');
        return;
      }

      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      this.$axios.post(`${this.$httpUrl}/api/student/coach/apply`, {
        studentId: user.id,
        coachId: coach.coachId
      }).then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.$message.success('申请已提交，等待教练确认');
              // 重新加载数据
              this.loadAllCoaches();
              this.loadApplications();
            } else {
              this.$message.error(res.msg);
            }
          });
    },
    // 检查是否已申请某教练
    isApplied(coachId) {
      // 检查在申请中
      const inApplication = this.applications.some(app => app.coachId === coachId && app.status === '待确认');
      // 检查已确认
      const confirmed = this.myCoaches.some(coach => coach.coachId === coachId);
      return inApplication || confirmed;
    }
  },
  mounted() {
    this.loadAllCoaches();
  }
};
</script>

<style scoped>
.coach-application-container {
  padding: 20px;
}

.coach-info {
  display: flex;
  align-items: center;
}

.coach-info .el-avatar {
  margin-right: 10px;
}

.info-text {
  line-height: 1.4;
}

.info-text p {
  margin: 0;
  font-size: 0.9em;
  color: #666;
}

.empty-tip {
  text-align: center;
  padding: 20px;
}
</style>