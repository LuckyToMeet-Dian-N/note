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
                   path: '/users',
                    component: () => import( '../components/page/users.vue'),
                    meta: { title: '用户列表' }
                },
                {
                   path: '/orders',
                    component: () => import( '../components/page/orders.vue'),
                    meta: { title: '订单列表' }
                },
                {
                   path: '/feedBack',
                    component: () => import( '../components/page/Feedback.vue'),
                    meta: { title: '分享列表' }
                },
                {
                   path: '/versions',
                    component: () => import( '../components/page/versions.vue'),
                    meta: { title: '历史发布' }
                },
                {
                   path: '/depoly',
                    component: () => import( '../components/page/depoly.vue'),
                    meta: { title: '发布版本' }
                },
                {
                   path: '/share',
                    component: () => import( '../components/page/share.vue'),
                    meta: { title: '分享版本' }
                }
                
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
