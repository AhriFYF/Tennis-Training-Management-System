<template>
  <div class="super-admin-container">
    <div class="welcome-container">
      <div class="welcome-header">
        <h1 class="welcome-title">
          {{'管理员 ' + user.name}}
        </h1>
        <p class="campus-name">{{ '管理校区：' + (user.campusName || '加载中...') }}</p>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :span="10">
        <el-card class="box-card user-info-card">
          <div slot="header" class="clearfix">
            <span>个人中心</span>
          </div>
          <el-descriptions :column="2" size="40" border>
            <el-descriptions-item label="账号">
              <i class="el-icon-s-custom"></i>
              {{ user.no }}
            </el-descriptions-item>
            <el-descriptions-item label="电话">
              <i class="el-icon-mobile-phone"></i>
              {{ user.phone }}
            </el-descriptions-item>
            <el-descriptions-item label="性别">
              <i class="el-icon-location-outline"></i>
              <el-tag
                  :type="user.sex === '1' ? 'danger' : 'primary'"
                  disable-transitions>
                <i :class="user.sex === '1' ? 'el-icon-female' : 'el-icon-male'"></i>
                {{ user.sex === '1' ? '女' : '男' }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="角色">
              <i class="el-icon-tickets"></i>
              <el-tag
                  type="success"
                  disable-transitions>
                {{ user.roleId === 0 ? '超级管理员' : (user.roleId === 1 ? '管理员' : '用户') }}
              </el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
        <DateUtils></DateUtils>
      </el-col>

      <el-col :span="14">
        <el-card class="box-card feature-card">
          <div slot="header" class="clearfix">
            <span>用户管理</span>
          </div>
          <div class="button-group">
            <el-button type="primary" plain @click="goTo('/SuperAdmin-userManage')">用户信息管理</el-button>
            <el-button type="primary" plain @click="goTo('/SuperAdmin-coachManage')">教练信息管理</el-button>
            <el-button type="primary" plain @click="goTo('/SuperAdmin-studentManage')">学生用户管理</el-button>
          </div>
        </el-card>

        <el-card class="box-card feature-card">
          <div slot="header" class="clearfix">
            <span>课程管理</span>
          </div>
          <div class="button-group">
            <el-button type="success" plain @click="goTo('/SuperAdmin-courseInfoManage')">课程信息管理</el-button>
          </div>
        </el-card>

        <el-card class="box-card feature-card">
          <div slot="header" class="clearfix">
            <span>教学管理</span>
          </div>
          <div class="button-group">
            <el-button type="warning" plain @click="goTo('/SuperAdmin-studentCourseSelection')">学员选课管理</el-button>
            <el-button type="warning" plain @click="goTo('/SuperAdmin-coachSchedule')">教练排课管理</el-button>
            <el-button type="warning" plain @click="goTo('/SuperAdmin-coachCancel')">教练消课管理</el-button>
            <el-button type="warning" plain @click="goTo('/SuperAdmin-teachingEvaluation')">教学评价管理</el-button>
          </div>
        </el-card>

        <el-card class="box-card feature-card">
          <div slot="header" class="clearfix">
            <span>系统管理</span>
          </div>
          <div class="button-group">
            <el-button type="info" plain @click="goTo('/SuperAdmin-systemLog')">系统日志</el-button>
            <el-button type="info" plain @click="goTo('/MatchRegister')">月赛注册</el-button>
            <el-button type="info" plain @click="goTo('/MatchSchedule')">月赛安排</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import DateUtils from "./DateUtils";
export default {
  name: "SuperAdmin",
  components: {DateUtils},
  data() {
    return {
      user: {},
      campusName: '', // 新增一个属性来存储校区名称
    };
  },
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem('CurUser'));
      // 新增：如果 campusId 存在，则查询校区名称
      if (this.user.campusId) {
        this.getCampusName(this.user.campusId);
      }
    },

    // 新增方法：根据 campusId 获取校区名称
    getCampusName(campusId) {
      this.$axios.get(`${this.$httpUrl}/campus/findById?id=${campusId}`)
          .then(res => {
            if (res.data.code === 200) {
              // 成功获取数据，将校区名称赋值给 user 对象
              const campusInfo = res.data.data;
              if (campusInfo && campusInfo.length > 0) {
                this.$set(this.user, 'campusName', campusInfo[0].name);
              } else {
                this.$set(this.user, 'campusName', '未知校区');
              }
            } else {
              // 请求失败或数据不存在
              this.$set(this.user, 'campusName', '获取失败');
            }
          })
          .catch(error => {
            console.error('查询校区信息失败：', error);
            this.$set(this.user, 'campusName', '网络错误');
          });
    },

    // 路由跳转方法
    goTo(path) {
      this.$router.push(path);
    }
  },
  created() {
    this.init();
  }
};
</script>

<style scoped>
.super-admin-container {
  text-align: center;
  background-color: #f1f1f3;
  height: 100%;
  padding: 20px;
  margin: 0;
}
.welcome-title {
  font-size: 32px;
  color: #333;
  margin-bottom: 20px;
}
.box-card {
  margin-bottom: 20px;
  border-radius: 8px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.el-descriptions {
  width: 100%;
}
.button-group {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  gap: 15px; /* 按钮之间的间距 */
}
.el-button {
  margin: 0 !important; /* 移除默认的 margin */
}
.welcome-header {
  /* 开启 Flexbox 布局 */
  display: flex;
  /* 子元素垂直居中对齐 */
  align-items: center;
}

.welcome-title {
  /* 允许 .welcome-title 占据剩余空间，将 .campus-name 推到最右边 */
  flex-grow: 1;
  /* 调整字体大小和颜色 */
  font-size: 40px;
  color: #333;
  margin: 0 0 20px 100px;
}

.campus-name {
  /* 调整小标题的样式 */
  font-size: 14px;
  color: #666;
  margin-left: 10px; /* 在大标题和小标题之间添加一些间距 */
  white-space: nowrap; /* 防止文本换行 */
}
</style>