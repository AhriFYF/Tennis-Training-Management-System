import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from "axios";
import VueRouter from 'vue-router';
import router from './router';
import store from './store';
Vue.prototype.$axios=axios;
Vue.prototype.$http = axios
Vue.prototype.$httpUrl='http://172.17.5.101:8080'
axios.defaults.baseURL = 'http://172.17.5.101:8080'
axios.defaults.withCredentials = true;
Vue.config.productionTip = false
//Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(ElementUI,{size:'small'});

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
