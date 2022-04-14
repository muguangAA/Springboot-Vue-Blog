import axios from 'axios'
import { MessageBox, Notification } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log("requestError:" + error) // for debug
    Notification.error({
      title: '请求失败',
      message: '请求失败',
      duration: 1000 * 3
    })
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data

    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 200) {
      if (res.code === 500) {
        console.log("serverError")
        console.log(res)
        Notification.error({
          title: '错误',
          message: res.message || "服务端发生错误",
          duration: 1000 * 3
        })
      }
      if (res.code === 401 || res.code === 403) {
        // to re-login
        MessageBox.confirm('您的登录信息已过期', '请重新登录', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    const { data } = error.response
    console.log("Error")
    console.log(data)
    if (data.code === 500) {
      Notification.error({
        title: '错误',
        message: '服务器发生错误',
        duration: 1000 * 3
      })
    } else {
      Notification.error({
        title: '错误',
        message: data.message || '数据接收发送错误',
        duration: 1000 * 3
      })
    }
    return Promise.reject(error)
  }
)

export default service
