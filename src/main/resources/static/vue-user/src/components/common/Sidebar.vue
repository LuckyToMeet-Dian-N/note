<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'dashboard',
                    title: '新建文稿'
                },
                {
                    icon: 'el-icon-lx-cascades',
                    index: '1',
                    title: '标签管理',
                     subs: [
                        {
                            index: 'label',
                            title: '标签列表'
                        },
                    ]
                },
                {
                    icon: 'el-icon-lx-cascades',
                    index: '2',
                    title: '文件夹管理',
                     subs: [
                        {
                            index: 'note',
                            title: '我的资源'
                        },
                    ]
                },
                 {
                    icon: 'el-icon-lx-cascades',
                    index: '3',
                    title: '分享管理',
                     subs: [
                        {
                            index: 'share',
                            title: '分享列表'
                        },
                    ]
                },
                {
                    icon: 'el-icon-lx-cascades',
                    index: '4',
                    title: '收藏管理',
                     subs: [
                        {
                            index: 'collections',
                            title: '我的收藏'
                        },
                    ]
                },
                {
                    icon: 'el-icon-lx-cascades',
                    index: '5',
                    title: '个人信息管理',
                     subs: [
                        {
                            index: 'userInfo',
                            title: '我的信息'
                        },
                    ]
                },
               
               
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
