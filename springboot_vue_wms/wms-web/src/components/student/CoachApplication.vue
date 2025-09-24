<!-- CoachApplication.vue 教练申请管理组件 -->
<template>
  <div class="coach-application-container">
    <h2>我的教练申请</h2>
    <el-alert
        title="每位学员最多选择两位教练"
        type="info"
        show-icon
        style="margin-bottom: 20px;"
    ></el-alert>

    <el-card class="application-card">
      <div slot="header" class="clearfix">
        <span>已申请教练</span>
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
        <el-table-column prop="applyTime" label="申请时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.applyTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
                v-if="scope.row.status === '待确认'"
                size="mini"
                type="danger"
                @click="cancelApplication(scope.row)"
            >
              撤销申请
            </el-button>
            <el-button
                v-if="scope.row.status === '已确认'"
                size="mini"
                type="info"
                @click="replaceCoach(scope.row)"
            >
              更换教练
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CoachApplication",
  data() {
    return {
      applications: []
    };
  },
  methods: {
    getLevelText(level) {
      switch(level) {
        case 1: return '初级';
        case 2: return '中级';
        case 3: return '高级';
        default: return '初级';
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
    cancelApplication(application) {
      this.$confirm('确定要撤销此教练申请吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`${this.$httpUrl}/coach/application/${application.id}`)
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
        this.$axios.post(`${this.$httpUrl}/coach/replace`, {
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
    loadApplications() {
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.$axios.get(`${this.$httpUrl}/coach/applications/${user.id}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.applications = res.data;
              }
            });
      }
    }
  },
  mounted() {
    this.loadApplications();
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