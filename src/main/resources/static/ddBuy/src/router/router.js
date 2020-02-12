import Vue from 'vue'
import Router from 'vue-router'

// 解决多次点击重复路由报错
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

// 我的订单

// 注册登录
const Login = () => import('../views/login/Login.vue');
Vue.use(Router)

const router = new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/',
            component: () => import('../views/home/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                   path: '/home',
                   name: 'home',
                    component: () => import( '../views/home/Home.vue'),
                    meta: { title: '首页',showTabBar: true, }
                },
            ]
        },
        {
           path: '/my',
           name: 'my',
            component: () => import( '../views/mine/Mine.vue'),
            meta: { title: '我的',showTabBar: true,  }
        },
        {
           path: '/collection',
           name: 'collection',
            component: () => import( '../views/mine/Collection.vue'),
            meta: { title: '我的收藏',showTabBar: true,  }
        },
        {
           path: '/userInfos',
           name: 'userInfos',
            component: () => import( '../views/mine/UserInfo.vue'),
            meta: { title: '我的收藏',showTabBar: true,  }
        },
         {
           path: '/share',
           name: 'share',
            component: () => import( '../views/mine/Share.vue'),
            meta: { title: '我的分享',showTabBar: false,  }
        },
         {
           path: '/updatePassword',
           name: 'updatePassword',
            component: () => import( '../views/mine/UpdatePassword.vue'),
            meta: { title: '我的分享',showTabBar: false,  }
        },
        {
           path: '/feedBack',
           name: 'feedBack',
            component: () => import( '../views/mine/FeedBack.vue'),
            meta: { title: '我的分享',showTabBar: false,  }
        },
        {
            path: '/login',
            component: () => import( '../views/login/Login.vue'),
            meta: { title: '登录',showTabBar: false,  }
        },
        
    ]


})

export default router