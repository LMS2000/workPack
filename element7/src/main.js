import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js'
import ElementUI from 'element-ui'
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI);
Vue.use(axios);
Vue.prototype.$http = axios;
Vue.config.productionTip = false
new Vue({
	router,
	axios,
  render: h => h(App)
}).$mount('#app')
