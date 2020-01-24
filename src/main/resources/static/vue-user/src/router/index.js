import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import('../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import( '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/404',
                    component: () => import('../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import( '../components/page/403.vue'),
                    meta: { title: '403' }
                },
                {
                    path: '/label',
                    component: () => import( '../components/page/Label.vue'),
                    meta: { title: '标签管理' }
                },
                {
                    path: '/share',
                    component: () => import( '../components/page/share.vue'),
                    meta: { title: '分享管理' }
                },
                {
                    path: '/collections',
                    component: () => import( '../components/page/collections.vue'),
                    meta: { title: '收藏管理' }
                },
                {
                    path: '/note',
                    component: () => import( '../components/page/note.vue'),
                    meta: { title: '快记' }
                },
            ]
        },

        {
            path: '/login',
            component: () => import('../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
