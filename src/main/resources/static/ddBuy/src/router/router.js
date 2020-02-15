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
           path: '/choiseNote',
           name: 'choiseNote',
            component: () => import( '../views/mine/choiseNote.vue'),
            meta: { title: '为笔记加标签',showTabBar: false,  }
        },
        {
           path: '/edit',
           name: 'edit',
            component: () => import( '../views/home/edit.vue'),
            meta: { title: 'dd',showTabBar: true,  }
        },
        {
           path: '/file',
           name: 'file',
            component: () => import( '../views/home/File.vue'),
            meta: { title: '为笔记加标签',showTabBar: true,  }
        },
        {
           path: '/video',
           name: 'video',
            component: () => import( '../views/home/uploadVideo.vue'),
            meta: { title: '上传',showTabBar: true,  }
        },
         {
           path: '/image',
           name: 'image',
            component: () => import( '../views/home/uploadImage.vue'),
            meta: { title: '上传',showTabBar: true,  }
        },
        {
           path: '/quills',
           name: 'quills',
            component: () => import( '../views/home/quill.vue'),
            meta: { title: '上传',showTabBar: false,  }
        },
        {
           path: '/zhanshi',
           name: 'zhanshi',
            component: () => import( '../views/home/zhanshi.vue'),
            meta: { title: '上传',showTabBar: false,  }
        },
         {
           path: '/word',
           name: 'word',
            component: () => import( '../views/home/word.vue'),
            meta: { title: '上传',showTabBar: true,  }
        },
          {
           path: '/pay',
           name: 'pay',
            component: () => import( '../views/mine/chongzhi.vue'),
            meta: { title: '充值',showTabBar: false,  }
        },
        {
           path: '/about',
           name: 'about',
            component: () => import( '../views/mine/about.vue'),
            meta: { title: '关于我们',showTabBar: false,  }
        },
         {
           path: '/tag',
           name: 'tag',
            component: () => import( '../views/mine/tag.vue'),
            meta: { title: '标签',showTabBar: false,  }
        },
        {
            path: '/login',
            component: () => import( '../views/login/Login.vue'),
            meta: { title: '登录',showTabBar: false,  }
        },
        
    ]


})

export default router