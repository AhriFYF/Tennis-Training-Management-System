<template>
  <div class="super-admin-container">
    <!-- 密钥生成对话框 -->
    <el-dialog title="生成超级管理员密钥" :visible.sync="showKeyGenerationDialog" width="40%" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
      <div style="text-align: center; padding: 20px;">
        <el-alert title="首次使用超级管理员账户需要生成唯一密钥" type="warning" show-icon style="margin-bottom: 20px;"></el-alert>
        <p>请注意：</p>
        <ul style="text-align: left; margin: 10px 0;">
          <li>此密钥仅能用于当前设备</li>
          <li>密钥有效期为一年</li>
          <li>请妥善保管密钥，遗失需要重新生成</li>
        </ul>
        <el-button type="primary" @click="generateKey" :loading="generatingKey">生成密钥</el-button>
      </div>
    </el-dialog>

    <!-- 密钥验证对话框 -->
    <el-dialog title="超级管理员密钥验证" :visible.sync="showKeyVerificationDialog" width="40%" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
      <div style="text-align: center; padding: 20px;">
        <el-alert title="请输入您的超级管理员密钥" type="info" show-icon style="margin-bottom: 20px;"></el-alert>
        <el-form :model="keyForm" :rules="keyRules" ref="keyForm">
          <el-form-item prop="key">
            <el-input v-model="keyForm.key" placeholder="请输入16位密钥" style="width: 300px;" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="verifyKey" :loading="verifyingKey">验证密钥</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

    <div class="welcome-container">
      <div class="welcome-header">
        <h1 class="welcome-title">
          {{'超级管理员 ' + user.name}}
        </h1>
        <p class="campus-name">{{ '管理校区：' + user.campusId }}</p>
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
            <el-descriptions-item label="密钥状态" v-if="user.roleId === 0">
              <i class="el-icon-key"></i>
              <el-tag
                  :type="keyStatus === 'verified' ? 'success' : (keyStatus === 'expired' ? 'danger' : 'warning')"
                  disable-transitions>
                {{ keyStatus === 'verified' ? '已验证' : (keyStatus === 'expired' ? '已过期' : '未验证') }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="过期时间" v-if="user.roleId === 0 && user.keyExpiredTime">
              <i class="el-icon-time"></i>
              {{ formatExpiredTime(user.keyExpiredTime) }}
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
        <DateUtils></DateUtils>
        <div style="text-align: center; padding: 10px;">
          <el-button
              type="danger"
              style="display: flex; justify-content: center; align-items: center; width: 100px; height: 50px; color: white; border-color: #ac6464; font-size: 20px"
              @click="showKeyInfo">
            密钥信息
          </el-button>
        </div>
        <div style="text-align: center; padding: 10px;">
          <el-button
              type="warning"
              style="display: flex; justify-content: center; align-items: center; width: 100px; height: 50px; color: white; border-color: #ac6464; font-size: 20px"
              @click="showVerificationDialog">
            验证密钥
          </el-button>
        </div>
        <div style="text-align: center; padding: 10px;">
          <el-button
              type="success"
              style="display: flex; justify-content: center; align-items: center; width: 100px; height: 50px; color: white; border-color: #ac6464; font-size: 20px"
              @click="showGenerationDialog">
            生成密钥
          </el-button>
        </div>
      </el-col>

      <el-col :span="14">
        <el-card class="box-card feature-card">
          <div slot="header" class="clearfix">
            <span>用户管理</span>
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
            <el-button type="info" plain @click="goTo('/SuperAdmin-campus')">校区管理</el-button>
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
      showKeyGenerationDialog: false,
      showKeyVerificationDialog: false,
      generatingKey: false,
      verifyingKey: false,
      keyForm: {
        key: ''
      },
      keyRules: {
        key: [
          { required: true, message: '请输入密钥', trigger: 'blur' },
          { min: 16, max: 16, message: '密钥长度应为16位', trigger: 'blur' }
        ]
      },
      keyStatus: 'unverified' // unverified, verified, expired
    };
  },
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem('CurUser'));
      
      // 检查是否需要生成或验证密钥
      if (this.user.roleId === 0) { // 超级管理员
        // 检查登录响应中是否有相关标识
        const loginResponse = sessionStorage.getItem('loginResponse');
        if (loginResponse) {
          const response = JSON.parse(loginResponse);
          if (response.needKeyGeneration) {
            this.showKeyGenerationDialog = true;
          } else if (response.needKeyVerification) {
            this.showKeyVerificationDialog = true;
          }
        }
        
        // 检查密钥状态
        this.checkKeyStatus();
      }
    },
    
    checkKeyStatus() {
      if (this.user.superAdminKey) {
        const now = new Date();
        const expiredTime = new Date(this.user.keyExpiredTime);
        if (expiredTime < now) {
          this.keyStatus = 'expired';
        } else {
          // 检查session中是否有验证标识
          const keyVerified = sessionStorage.getItem('superAdminKeyVerified');
          this.keyStatus = keyVerified === 'true' ? 'verified' : 'unverified';
        }
      }
    },
    
    generateKey() {
      this.generatingKey = true;
      
      // 获取设备标识（这里使用浏览器信息生成，实际应用中可以使用更复杂的设备指纹）
      const deviceId = this.generateDeviceId();
      
      this.$axios.post(`${this.$httpUrl}/user/generateSuperAdminKey`, {
        userId: this.user.id,
        deviceId: deviceId
      }).then(res => res.data)
      .then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg);
          this.showKeyGenerationDialog = false;
          
          // 更新用户信息
          this.user.superAdminKey = res.data.key;
          this.user.keyExpiredTime = res.data.expiredTime;
          this.user.deviceId = deviceId;
          this.keyStatus = 'verified';
          
          // 保存到sessionStorage
          sessionStorage.setItem('CurUser', JSON.stringify(this.user));
          sessionStorage.setItem('superAdminKeyVerified', 'true');
        } else {
          this.$message.error(res.msg || '密钥生成失败');
        }
      })
      .catch(() => {
        this.$message.error('密钥生成请求失败');
      })
      .finally(() => {
        this.generatingKey = false;
      });
    },
    
    verifyKey() {
      this.$refs.keyForm.validate((valid) => {
        if (valid) {
          this.verifyingKey = true;
          
          // 获取设备标识
          const deviceId = this.generateDeviceId();
          
          this.$axios.post(`${this.$httpUrl}/user/verifySuperAdminKey`, {
            userId: this.user.id,
            key: this.keyForm.key,
            deviceId: deviceId
          }).then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.$message.success(res.msg);
              this.showKeyVerificationDialog = false;
              this.keyStatus = 'verified';
              
              // 保存验证状态到sessionStorage
              sessionStorage.setItem('superAdminKeyVerified', 'true');
            } else {
              this.$message.error(res.msg || '密钥验证失败');
            }
          })
          .catch(() => {
            this.$message.error('密钥验证请求失败');
          })
          .finally(() => {
            this.verifyingKey = false;
          });
        }
      });
    },
    
    generateDeviceId() {
      // 生成设备标识（简化版本，实际应用中可以使用更复杂的设备指纹）
      const navigatorInfo = navigator.userAgent + navigator.platform + navigator.language;
      let hash = 0;
      for (let i = 0; i < navigatorInfo.length; i++) {
        const char = navigatorInfo.charCodeAt(i);
        hash = ((hash << 5) - hash) + char;
        hash = hash & hash; // Convert to 32bit integer
      }
      return hash.toString();
    },
    
    formatExpiredTime(time) {
      return new Date(time).toLocaleString();
    },
    
    showKeyInfo() {
      if (this.user.roleId === 0) {
        if (!this.user.superAdminKey) {
          this.$alert('您尚未生成密钥，请先生成密钥。', '提示', {
            confirmButtonText: '确定'
          });
        } else {
          this.$alert(`您的密钥信息：<br>密钥：${this.user.superAdminKey}<br>过期时间：${this.formatExpiredTime(this.user.keyExpiredTime)}<br><br>请注意：<br>1. 密钥仅能用于当前设备<br>2. 请妥善保管密钥<br>3. 过期后需要重新生成`, '密钥信息', {
            dangerouslyUseHTMLString: true,
            confirmButtonText: '确定'
          });
        }
      }
    },

    showVerificationDialog() {
      this.showKeyVerificationDialog = true;
    },

    showGenerationDialog() {
      this.showKeyGenerationDialog = true;
    },

    // 路由跳转方法
    goTo(path) {
      // 检查是否为超级管理员且密钥已验证
      if (this.user.roleId === 0) {
        if (this.keyStatus !== 'verified') {
          this.$message.warning('请先完成密钥验证');
          return;
        }
      }
      
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