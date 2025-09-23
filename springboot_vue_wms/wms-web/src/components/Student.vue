<template>
  <div class="student-container">
    <!-- 顶部导航栏 -->
    <el-header class="student-header">
      <h1>乒乓球培训管理系统 - 学员中心</h1>
      <div class="user-info">
        <el-avatar :size="40" :src="currentUser.photoUrl"></el-avatar>
        <div class="user-name">{{ currentUser.name }}</div>
        <div class="user-role">学员</div>
      </div>
    </el-header>

    <el-container class="main-content">
      <!-- 侧边导航 -->
      <el-aside width="200px" class="sidebar">
        <el-menu
            :default-active="$route.path"
            class="el-menu-vertical-demo"
            @select="handleSelect"
            background-color="#f5f5f5"
            text-color="#333"
            active-text-color="#409EFF"
        >
          <el-menu-item index="/student-profile">
            <i class="el-icon-user"></i>
            <span slot="title">个人信息</span>
          </el-menu-item>
          <el-menu-item index="/student-bookings">
            <i class="el-icon-date"></i>
            <span slot="title">我的预约</span>
          </el-menu-item>
          <el-menu-item index="/student-coach-search">
            <i class="el-icon-search"></i>
            <span slot="title">教练查询</span>
          </el-menu-item>
          <el-menu-item index="/student-coach-application">
            <i class="el-icon-edit"></i>
            <span slot="title">教练申请</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 主内容区 -->
      <el-main class="content-wrapper">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Student",
  computed: {
    currentUser() {
      return JSON.parse(sessionStorage.getItem("CurUser") || "{}");
    }
  },
  methods: {
    handleSelect(key) {
      this.$router.push(key);
    }
  }
};
</script>

<style scoped>
.student-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.student-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-name {
  font-size: 18px;
  font-weight: bold;
}

.user-role {
  font-size: 14px;
  color: #999;
}

.sidebar {
  background-color: #f5f5f5;
}

.content-wrapper {
  padding: 20px;
  background-color: #ffffff;
}
</style>