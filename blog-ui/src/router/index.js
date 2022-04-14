import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export let constantRoutes
constantRoutes = [
  {
    path: '/',
    redirect: '/guest/main',
    meta: { needToken: false },
    hidden: true
  },
  {
    path: '/guest',
    name: 'Guest',
    redirect: '/guest/main',
    guest: true,
    meta: { needToken: false},
    hidden: true,
    component: () => import('../components/Menu/index'),
    children: [
      {
        path: 'main',
        name: 'GuestMain',
        meta: { needToken: false, title: '主页'},
        component: () => import('../views/frontDesk/main')
      },
      {
        path: 'articles',
        name: 'Articles',
        meta: { needToken: false, title: '文章列表' },
        component: () => import('../views/frontDesk/articles')
      },

      {
        path: 'timeLine',
        name: 'TimeLine',
        meta: { needToken: false, title: '时间线' },
        component: () => import('../views/frontDesk/timeLine')
      },
      {
        path: 'search',
        name: 'Search',
        meta: { needToken: false, title: '搜索' },
        component: () => import('../views/frontDesk/search'),
      },
    ]
  },
  {
    path: '/guest/article/:id',
    name: 'Article',
    meta: { needToken: false, title: '文章' },
    hidden: true,
    component: () => import('@/views/frontDesk/article')
  },
  {
    path: '/login',
    component: () => import('@/views/backDesk/login/index'),
    hidden: true
  },
  {
    path: '/admin',
    component: Layout,
    children: [{
      path: 'main',
      name: 'AdminMain',
      component: () => import('@/views/backDesk/dashboard/index'),
      meta: { title: '主页', icon: 'dashboard' }
    }]
  },
  {
    path: '/article',
    component: Layout,
    redirect: '/article/allArticle',
    name: 'Admin',
    meta: { title: '文章', icon: 'form' },
    children: [
      {
        path: 'allArticle',
        name: 'AllArticle',
        component: () => import('@/views/backDesk/articles/index'),
        meta: { title: '文章列表', icon: 'table'}
      },
      {
        path: 'search',
        name: 'Search',
        component: () => import('@/views/backDesk/search/index'),
        meta: {title: '搜索文章', icon: 'dashboard'},
      },
      {
        path: 'write',
        name: 'Write',
        component: () => import('@/views/backDesk/write/index'),
        meta: { title: '写文章', icon: 'tree' }
      },
      {
        path: 'tags',
        name: 'Tags',
        component: () => import('@/views/backDesk/tags/index'),
        meta: {title: '标签管理', icon: 'tree'}
      },
    ]
  },
  // 404 page must be placed at the end !!!
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true,
    meta: {needToken: false}
  },
  // {
  //   path: '*',
  //   redirect: '/404',
  //   hidden: true,
  // }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
