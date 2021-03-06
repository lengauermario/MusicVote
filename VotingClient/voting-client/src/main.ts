import Vue from 'vue'
import './plugins/vuetify'
import Vuetify from 'vuetify'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import store from './store/index'
import './registerServiceWorker'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.config.productionTip = false
Vue.config.devtools = true

Vue.use(Vuetify, {
  iconfont: 'md' // 'md' || 'mdi' || 'fa' || 'fa4'
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')