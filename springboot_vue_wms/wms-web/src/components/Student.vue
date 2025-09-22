<template>
  <div class="student-container">
    <!-- 顶部栏 -->
    <el-header class="student-header">
      <h2>🏓 乒乓球培训管理系统 - 学员中心</h2>
      <div class="user-info">
        欢迎您，{{ currentUser.name }}（学员）
      </div>
    </el-header>

    <el-main class="student-main">
      <el-row :gutter="20">
        <!-- 1. 个人信息维护 -->
        <el-col :span="24" :md="12" :lg="8">
          <el-card class="info-card">
            <div slot="header" class="card-header">
              <span>🔄 个人信息维护</span>
            </div>
            <div>
              <p>修改您的联系方式、年龄、性别等信息</p>
              <el-button type="primary" size="small" @click="goToProfile">
                修改信息
              </el-button>
            </div>
          </el-card>
        </el-col>

        <!-- 2. 查询教练 -->
        <el-col :span="24" :md="12" :lg="8">
          <el-card class="info-card">
            <div slot="header" class="card-header">
              <span>🔍 查询教练</span>
            </div>
            <div>
              <p>查找合适的教练，支持按条件筛选</p>
              <el-button type="primary" size="small" @click="queryCoach">
                查询教练
              </el-button>
            </div>
          </el-card>
        </el-col>

        <!-- 3. 选择教练（仅按钮，无功能） -->
        <el-col :span="24" :md="12" :lg="8">
          <el-card class="info-card">
            <div slot="header" class="card-header">
              <span>👤 申请教练</span>
            </div>
            <div>
              <p>选择教练作为您的主管教练（功能演示）</p>
              <el-button type="success" size="small" @click="applyCoach">
                🙋 申请教练
              </el-button>
            </div>
          </el-card>
        </el-col>

        <!-- 4. 我的预约（预留） -->
        <el-col :span="24" :md="12" :lg="8">
          <el-card class="info-card">
            <div slot="header" class="card-header">
              <span>📅 我的预约</span>
            </div>
            <div>
              <p>查看和管理您的课程预约</p>
              <el-button type="warning" size="small" @click="viewBookings">
                查看预约
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>

<script>
export default {
  name: "Student",
  data() {
    return {
      currentUser: {
        name: "张三", // 模拟当前登录的学员姓名，实际应从 Vuex / Session 获取
      },
    };
  },
  methods: {
    goToProfile() {
      this.$message.info("跳转到个人信息修改页面");
      // TODO: 实际跳转到 /student/profile
    },
    queryCoach() {
      this.$message.info("跳转到教练查询页面");
      // TODO: 实际跳转到 /student/coach/list 或查询页面
    },
    applyCoach() {
      // ⚠️ 仅前端展示，不实现功能
      this.$message({
        message: "您已点击申请教练（此为演示按钮，暂未实现功能）",
        type: "info",
        duration: 3000,
      });
    },
    viewBookings() {
      this.$message.info("跳转到我的预约管理页面");
      // TODO: 实际跳转到 /student/booking
    },
  },
};
</script>

<style scoped>
.student-container {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.student-header {
  background-color: #409eff;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.student-header h2 {
  margin: 0;
}

.user-info {
  font-size: 16px;
}

.student-main {
  margin-top: 20px;
}

.info-card {
  margin-bottom: 20px;
  height: 140px;
}

.card-header {
  font-weight: bold;
  font-size: 16px;
}
</style>