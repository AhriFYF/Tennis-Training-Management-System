<template>
  <div class="super-admin-container">
    <h1 class="welcome-title">{{'超级管理员 ' + user.name}}</h1>

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
                  :type="user.sex === '1' ? 'primary' : 'danger'"
                  disable-transitions>
                <i :class="user.sex === '1' ? 'el-icon-male' : 'el-icon-female'"></i>
                {{ user.sex === '1' ? '男' : '女' }}
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
            <span>系统用户管理</span>
          </div>
          <div class="button-group">
            <el-button type="primary" plain @click="goTo('/SuperAdmin-userManage')">用户信息管理</el-button>
            <el-button type="primary" plain @click="goTo('/SuperAdmin-adminManage')">管理员信息管理</el-button>
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
            <el-button type="warning" plain @click="goTo('/SuperAdmin-coachScheduling')">教练排课管理</el-button>
            <el-button type="warning" plain @click="goTo('/SuperAdmin-teachingEvaluation')">教学评价管理</el-button>
            <el-button type="warning" plain @click="goTo('/SuperAdmin-coachConsumption')">教练消课管理</el-button>
          </div>
        </el-card>

        <el-card class="box-card feature-card">
          <div slot="header" class="clearfix">
            <span>系统与资源管理</span>
          </div>
          <div class="button-group">
            <el-button type="info" plain @click="goTo('/SuperAdmin-campus')">校区管理</el-button>
            <el-button type="info" plain @click="goTo('/SuperAdmin-carouselManage')">系统轮播图管理</el-button>
            <el-button type="info" plain @click="goTo('/SuperAdmin-noticeManage')">通知公告管理</el-button>
            <el-button type="info" plain @click="goTo('/SuperAdmin-resourceManage')">资源管理</el-button>
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
      user: {}
    };
  },
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem('CurUser'));
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
</style>