<template>
  <div class="homepage">
    <header-component />

    <section class="hero-section">
      <div class="hero-content">
        <div class="hero-text-block">
          <h2>藍星会社選んで</h2>
          <router-link to="/login" class="btn-primary">未来へ</router-link>
        </div>
        <div class="hero-right-column">
          <div class="hero-card">
            <h3 class="hero-card-title">乒乓球培训管理系统</h3>
            <ul>
              <li> 管 理 员 系 统</li>
              <li> 教 练 系 统</li>
              <li> 学 生 系 统</li>
            </ul>
            <p class="hero-card-text">
              帮助完成学生报名、教练入职、选课、排课、学费管理、训练环节记录和比赛管理等功能
            </p>
          </div>
        </div>
      </div>
    </section>

    <section class="experience-section">
      <div class="section-header">
        <span class="section-tag">STAY WITH US</span>
        <h2 class="section-title">Table-tennis Training Management System</h2>
        <p class="section-subtitle">
          意識のあるところに、道は開ける
        </p>
      </div>
      <div class="card-container">
        <div class="experience-card">
          <span class="card-tag">Administrator System</span>
          <h3 class="card-title">管理员管理系统</h3>
          <p class="card-text">
            管理员系统提供了全面的培训机构管理功能。超级管理员负责管理所有校区，包括创建校区、指定校区管理员并管理其信息。校区管理员则专注于管理各自校区，可以对学员和教练员信息进行增删改查，并能管理课程预约。系统还提供了系统日志功能，管理员可以随时查询所有用户的重要操作记录，如信息修改、课程预订和缴费等，确保管理的透明和可追溯。此外，校区管理员还可以帮助学员进行线下缴费的录入，并对教练员信息进行修改。
          </p>
        </div>
        <div class="experience-card">
          <span class="card-tag">Coaching System</span>
          <h3 class="card-title">教练管理系统</h3>
          <p class="card-text">
            教练系统旨在帮助教练员高效地管理学员和课程。教练员通过系统注册并等待管理员审核，系统会根据级别指定收费标准。在与学员建立双选关系后，教练员可以接收和确认课程预约请求，并管理自己的课程表。系统提供上课前一小时的提醒功能，并能在学员发起取消时进行确认。教练员还可以在系统上记录学员的表现，并对训练效果进行评价，这些评价都可供后续查询。此外，教练员还可以通过系统接收和处理学员更换教练的申请。
          </p>
        </div>
        <div class="experience-card">
          <span class="card-tag">Student System</span>
          <h3 class="card-title">学生管理系统</h3>
          <p class="card-text">
            学生系统为学员提供了一站式的乒乓球培训服务。学员注册时无需审核，可以维护个人信息。系统提供了两种查询教练员的方式：按条件查询或浏览所有教练员，方便学员选择。学员通过“选中教练”功能可以发起双选申请，在关系建立后，可以预约课程并选择球台。系统支持在线（微信/支付宝）和线下支付方式，并能自动扣除和退还课时费。学员可以随时查询自己的课表，并能收到上课提醒。此外，学员还可以通过系统对教练员进行评价，并参与每月一次的月赛报名。
          </p>
        </div>
      </div>
    </section>

  </div>
</template>

<script>
import HeaderComponent from './HeaderComponent.vue';

export default {
  name: 'HomePage',
  components: {
    HeaderComponent
  }
};
</script>

<style scoped>
/* 基本样式和布局 */
.homepage {
  background-color: #fafaf3;
  font-family: 'Hiragino Kaku Gothic ProN', sans-serif;
  color: #333;
  line-height: 1.6;
}

/* Hero Section */
.hero-section {
  background:
      linear-gradient(rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0.5)),
      url('~@/assets/home.png') no-repeat center center/cover;
  padding: 100px 5% 50px;
}

.hero-content {
  display: flex;
  gap: 50px;
  max-width: 1200px;
  margin: 0 auto;
}

.hero-text-block {
  flex: 1;
  color: #0a3449;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
  position: relative;
  top: 30px;
  left: 50px;
}

.hero-text-block h2 {
  font-size: 3rem;
  margin-bottom: 20px;
  font-weight: 500;
}

.hero-text-block p {
  font-size: 1.2rem;
  margin-bottom: 20px;
  max-width: 600px;
}

.btn-primary {
  display: inline-block;
  padding: 12px 24px;
  background-color: rgba(255, 255, 255, 0.2);
  border: 1px solid #fff;
  color: #000000;
  text-decoration: none;
  border-radius: 4px;
  font-size: 40px;
  position: relative;
  top: 20px;
  left: 300px;
}

.hero-right-column {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.hero-card {
  background-color: rgba(255, 255, 255, 0.9);
  padding: 30px;
  border-radius: 8px;
  width: 400px;
  font-size: 20px;
  text-align: center;
}

.hero-card-title {
  font-size: 1.5rem;
  font-weight: 500;
  margin-bottom: 15px;
}

.hero-card ul {
  list-style: none;
  padding: 0;
  margin: 0 0 20px;
}

.hero-card li::before {
  content: '✓';
  color: #3a855d;
  font-weight: bold;
  display: inline-block;
  width: 1em;
  margin-left: -1em;
}

/* Experience Section */
.experience-section {
  padding: 80px 5%;
  max-width: 1200px;
  margin: 0 auto;
}

.section-header {
  text-align: center;
  margin-bottom: 50px;
}

.section-tag {
  font-size: 0.9rem;
  color: #999;
  letter-spacing: 2px;
  font-weight: bold;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 500;
  margin: 10px 0;
}

.section-subtitle {
  color: #666;
  max-width: 800px;
  margin: 0 auto;
}

.card-container {
  display: flex;
  justify-content: space-between;
  gap: 30px;
}

.experience-card {
  flex: 1;
  padding: 30px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.card-tag {
  font-size: 0.8rem;
  color: #999;
  letter-spacing: 1px;
  font-weight: bold;
}

.card-title {
  font-size: 1.3rem;
  font-weight: 500;
  margin: 10px 0;
}

.card-text {
  color: #666;
}
</style>