<template>
  <header class="header">
    <div class="header-container">
      <div class="logo">
        <img src="@/assets/header-icon.png" alt="KKAB Logo">
      </div>

      <nav class="nav">
        <ul>
          <li><router-link to="/HomePage">ホーム</router-link></li>
        </ul>
      </nav>

      <div class="language-switch">
        <router-link to="/login" class="login-link">Login</router-link>
        <span></span>
        <a href="#" class="active"></a>
        <span></span>
        <a href="#"></a>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: 'HeaderComponent'
};
</script>

<style scoped>
.header {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  padding: 20px 5%;
  color: #333;
  z-index: 1000;
  box-sizing: border-box;
  background-color: rgb(245, 245, 220); /* 黑色半透明背景 */
  backdrop-filter: blur(5px); /* 可选：给背景添加模糊效果 */
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.logo img {
  height: 45px; /* 根据实际图片大小调整 */
}

.nav ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  gap: 30px;
}

.nav a {
  color: #333;
  text-decoration: none;
  font-size: 1rem;
  font-weight: 500;
}

.nav a:hover {
  text-decoration: underline;
}

.language-switch a {
  color: #333;
  text-decoration: none;
  margin: 0 5px;
}

.language-switch a.active {
  font-weight: bold;
  text-decoration: underline;
}

.login-link {
  display: inline-block;
  margin-right: 30px;
}

</style>