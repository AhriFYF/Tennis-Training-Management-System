<template>
  <div class="coach-dashboard">
    <!-- 头部信息 -->
    <el-card class="header-card">
      <div class="header-content">
        <div class="coach-info">
          <h1>{{ '欢迎，' + user.name + '教练！' }}</h1>
          <p>教练ID: {{ user.id }} | 角色: 教练</p>
        </div>
        <div class="quick-stats">
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="stat-item">
                <div class="stat-number">{{ stats.pendingSelections || 0 }}</div>
                <div class="stat-label">待确认双选</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="stat-item">
                <div class="stat-number">{{ stats.pendingRequests || 0 }}</div>
                <div class="stat-label">待确认预约</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="stat-item">
                <div class="stat-number">{{ stats.totalStudents || 0 }}</div>
                <div class="stat-label">我的学员</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="stat-item">
                <div class="stat-number">{{ stats.confirmedCourses || 0 }}</div>
                <div class="stat-label">确认课程</div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-card>

    <!-- 功能导航 -->
    <el-row :gutter="20" class="function-nav">
      <el-col :span="8">
        <el-card class="nav-card" @click.native="navigateTo('CoachProfile')">
          <div class="nav-content">
            <i class="el-icon-user-solid nav-icon"></i>
            <h3>个人信息管理</h3>
            <p>查看和修改个人信息</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="nav-card" @click.native="navigateTo('CoachSelection')">
          <div class="nav-content">
            <i class="el-icon-s-check nav-icon"></i>
            <h3>双选关系管理</h3>
            <p>处理学员选择请求</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="nav-card" @click.native="navigateTo('CourseSchedule')">
          <div class="nav-content">
            <i class="el-icon-time nav-icon"></i>
            <h3>课程时间管理</h3>
            <p>上传和管理课程时间</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="function-nav">
      <el-col :span="8">
        <el-card class="nav-card" @click.native="navigateTo('CourseRequests')">
          <div class="nav-content">
            <i class="el-icon-s-order nav-icon"></i>
            <h3>预约请求管理</h3>
            <p>处理学员预约请求</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="nav-card" @click.native="navigateTo('MyStudents')">
          <div class="nav-content">
            <i class="el-icon-s-custom nav-icon"></i>
            <h3>我的学员</h3>
            <p>查看和管理学员信息</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="nav-card" @click.native="logout">
          <div class="nav-content">
            <i class="el-icon-switch-button nav-icon"></i>
            <h3>退出登录</h3>
            <p>退出当前账号</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近活动 -->
    <el-card class="activity-card">
      <div slot="header" class="card-header">
        <span>最近活动</span>
        <el-button type="text" @click="refreshActivities">刷新</el-button>
      </div>
      <el-timeline>
        <el-timeline-item
            v-for="activity in recentActivities"
            :key="activity.id"
            :timestamp="activity.time"
            :type="activity.type"
        >
          {{ activity.content }}
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CoachDashboard",
  data() {
    return {
      user: {},
      stats: {
        pendingSelections: 0,
        pendingRequests: 0,
        totalStudents: 0,
        confirmedCourses: 0
      },
      recentActivities: [
        {
          id: 1,
          content: "学员张三申请选择您作为教练",
          time: "2024-01-15 10:30",
          type: "primary"
        },
        {
          id: 2,
          content: "学员李四预约了您的课程时间",
          time: "2024-01-15 09:15",
          type: "success"
        },
        {
          id: 3,
          content: "您上传了新的课程时间",
          time: "2024-01-14 16:20",
          type: "info"
        }
      ]
    }
  },
  methods: {
    init() {
      try {
        const userData = sessionStorage.getItem('CurUser');
        if (userData) {
          this.user = JSON.parse(userData);
        } else {
          console.error('用户数据不存在于sessionStorage中');
          this.$router.push('/');
        }
      } catch (error) {
        console.error('解析用户数据失败:', error);
        this.$router.push('/');
      }
      this.loadStats()
    },
    loadStats() {
      // 这里可以调用API获取统计数据
      // 暂时使用模拟数据
      this.stats = {
        pendingSelections: 3,
        pendingRequests: 2,
        totalStudents: 15,
        confirmedCourses: 8
      }
    },
    navigateTo(routeName) {
      console.log('尝试导航到路由:', routeName);
      this.$router.push({ name: routeName })
          .then(() => {
            console.log('导航成功');
          })
          .catch(error => {
            console.error('导航失败:', error);
            if (error.name === 'NavigationDuplicated') {
              console.log('路由重复，忽略错误');
            } else {
              this.$message.error('导航失败，请检查控制台获取详细信息');
            }
          });
    },
    logout() {
      // 清除用户信息
      sessionStorage.removeItem('CurUser');
      // 跳转到登录页面
      this.$router.push('/').then(() => {
        this.$message.success('已退出登录');
      }).catch(error => {
        console.error('跳转失败:', error);
      });
    },
    refreshActivities() {
      // 刷新最近活动
      this.$message.success('活动列表已刷新')
    }
  },
  created() {
    this.init()
  }
}
</script>

<style scoped>
/* 保持原有的样式不变 */
.coach-dashboard {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.header-card {
  margin-bottom: 20px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.coach-info h1 {
  margin: 0 0 10px 0;
  color: #409EFF;
}

.coach-info p {
  margin: 0;
  color: #666;
}

.quick-stats {
  width: 60%;
}

.stat-item {
  text-align: center;
  padding: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 8px;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}

.function-nav {
  margin-bottom: 20px;
}

.nav-card {
  cursor: pointer;
  transition: all 0.3s;
  height: 150px;
}

.nav-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.15);
}

.nav-content {
  text-align: center;
  padding: 20px;
}

.nav-icon {
  font-size: 48px;
  color: #409EFF;
  margin-bottom: 15px;
}

.nav-content h3 {
  margin: 10px 0;
  color: #333;
}

.nav-content p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.activity-card {
  margin-top: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>