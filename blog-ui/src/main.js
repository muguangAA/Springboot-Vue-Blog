import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import '@/styles/index.scss' // global css
import App from './App'
import store from './store'
import router from './permission'
import '@/permission' // permission control
import '@/icons' // icon

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'default-passive-events'
import './plugins/element.js'


Vue.use(mavonEditor)

if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
