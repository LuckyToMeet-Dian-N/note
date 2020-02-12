<template>
  <div id="dashboard">
    <van-tabbar v-model="active"
                :safe-area-inset-bottom=true
                class="active_tab"
                active-color="#75a342">
      <!-- 给购物车的Tabbar加个ID 方便做加入购物车的小球动画 -->
      <van-tabbar-item v-for="(item,index) in tabbars"
                       :key="index"
                       :id="index==3?'buycar':''"
                       @click="tab(index,item.name)"
                       :info="item.name=='cart'?goodsNum:''">
        <span :class="currIndex == index ? active:''">{{item.title}}</span>
        <template slot="icon"
                  slot-scope="props">
          <img :src="props.active ? item.active : item.normal">
        </template>
      </van-tabbar-item>
    </van-tabbar>

    <!-- 缓存界面选择加载 -->
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive" />
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive" />

  </div>
</template>

<script type="text/javascript">
export default {
  name: "DashBoard",
  mounted () {
  },
  created () {
    //通过路由跳转绑定Tabbar的选中
    this.tabbarSelected(this.$route.name);
  },
  data () {
    return {
      currIndex: 0,
      active: 0,
      tabbars: [
        {
          name: "home",
          title: this.$t('home.home'),
          normal: require("@/images/tabbar/home_default.png"),
          active: require("@/images/tabbar/home_selected.png")
        },
        {
          name: "category",
          title: this.$t('home.category'),
          normal: require("@/images/tabbar/category_default.png"),
          active: require("@/images/tabbar/category_selected.png")
        },
        {
          
          name: "cart",
          title: this.$t('home.cart'),
          normal: require("@/images/tabbar/shoppingcart_default.png"),
          active: require("@/images/tabbar/shoppingcart_selected.png"),
          num: 5
        },
        {
          name: "mine",
          title: this.$t('home.mine'),
          normal: require("@/images/tabbar/mine_default.png"),
          active: require("@/images/tabbar/mine_selected.png")
        }
      ]
    };
  },
  components: {
  },
  mounted () {
    this._initData();
  },
  computed: {
     
  },
  methods: {
    // 0. 延展mutations方法
    // 1.点击tabbar触发的方法
    tab (index, val) {
      this.currIndex = index;
      this.$router.push(val);
    },
    // 2.初始化购物车数据
    _initData () {
    },
    tabbarSelected (item) {
      switch (item) {
        case 'home':
          this.active = 0;
          break;
        case 'category':
          this.active = 1;
          break;
        case 'cart':
          this.active = 2;
          break;
        case 'my':
          this.active = 3;
          break;
        default:
          break;
      }
    }
  }
}
</script>       
<style lang="less" scoped>
/*转场动画*/
.router-slider-enter-active,
.router-slider-leave-active {
  transition: all 0.3s;
}
.router-slider-enter,
.router-slider-leave-active {
  transform: translate3d(2rem, 0, 0);
  opacity: 0;
}
@keyframes mymove {
  0% {
    transform: scale(1);
  }
  25% {
    transform: scale(0.8);
  }
  50% {
    transform: scale(1.1);
  }
  75% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
  }
}
@-moz-keyframes mymove {
  0% {
    transform: scale(1);
  }
  25% {
    transform: scale(0.8);
  }
  50% {
    transform: scale(1.1);
  }
  75% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
  }
}
@-webkit-keyframes mymove {
  0% {
    transform: scale(1);
  }
  25% {
    transform: scale(0.8);
  }
  50% {
    transform: scale(1.1);
  }
  75% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
  }
}
@-o-keyframes mymove {
  0% {
    transform: scale(1);
  }
  25% {
    transform: scale(0.8);
  }
  50% {
    transform: scale(1.1);
  }
  75% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
  }
}
.moveToCart {
  animation: mymove 0.5s ease-in-out;
}
</style>
