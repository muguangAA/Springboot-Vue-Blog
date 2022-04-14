import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

// const whiteList = ['/login', ] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  //不需要token的view直接跳转就行
  if (to.meta.needToken === false) {
    next()
    return Promise.resolve()
  }

  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/admin/main' })
      return Promise.resolve()
    } else {
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
        return Promise.resolve()
      } else {
        try {
          // get user info
          await store.dispatch('user/getInfo')
          next()
          return Promise.resolve()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || '获取信息发生错误了')
          next(`/login?redirect=${to.path}`)
          return Promise.resolve()
        }
      }
    }
  } else {
    // 没有token的情况直接挑转
    if (to.path === '/login') {
      next()
    }else {
      next(`/login?redirect=${to.path}`)
    }
    return Promise.resolve()
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})

export default router
