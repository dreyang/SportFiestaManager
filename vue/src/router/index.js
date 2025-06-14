import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import AdminView from "@/views/AdminView.vue";
import LayoutView from "@/views/Layout.vue";
import RegisterView from "@/views/RegisterView";
import BisaiView from "@/views/BisaiView";
import GonggaoView from "@/views/GonggaoView";
import AuditView from "@/views/AuditView";
import BmView from "@/views/BmView";
import LogView from "@/views/LogView";
import NoticeView from "@/views/NoticeView";
import EterView from "@/views/EterView";






Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name:'Login',
    component: LoginView
  },
  {
    path: '/register',
    name:'Register',
    component: RegisterView
  },

  {
    path: '/',
    name:'Layout',
    component: LayoutView,
    children: [
        {
      path: '',
      component: HomeView
    },
      {
        path: 'admin',
        component: AdminView
      },
      {
        path: 'bisai',
        component: BisaiView
      },
      {
        path: 'gonggao',
        component: GonggaoView
      },
      {
        path: 'audit',
        component: AuditView
      },
      {
        path: 'bm',
        component: BmView
      },
      {
        path: 'log',
        component: LogView
      },
      {
        path: 'notice',
        component: NoticeView
      },
      // {
      //   path: 'eenter',
      //   component: EenterView
      // },
      {
        path: 'eter',
        component: EterView
      },
    ]
  },
  ]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


// 路由守卫`
router.beforeEach((to ,from, next) => {

  const user = localStorage.getItem("user");
  if (!user && to.path !== '/login' && to.path !=='/register') {
    return next("/login");
  }
  next();
})
//但是呢，鉴权放在前端是不安全的，因为是可以人为篡改的，所以要放在服务端
export default router
