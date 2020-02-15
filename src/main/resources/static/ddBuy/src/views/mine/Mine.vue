<template>
  <div id="mine">
    <van-nav-bar 
                 :fixed=true
                 :border=false
                 style="height:2.5rem" />
    <van-cell-group style="margin-top:2.4rem">
      <van-cell style="background-color: #3bba63;color:#FFF;"
                label-class="labelClass"
                is-link
                center>
        <template slot="title">
          <!-- 已登录状态 -->
          <div class="personMsg"
               v-if="userInfo.token"
               @click="goToPage('userInfos')">
            <img class="iconImage"
                 :src="user_image.login_icon"
                 alt="">
            <div style="margin-left: 10px">
                用户名:{{userInfo.userName}}<br>
                账号：{{userInfo.email}}<br>
                类型：{{userInfo.typeName}}
            </div>
          </div>
          <!-- 未登录状态 -->
          <div class="personMsg"
               v-if="!userInfo.token">
            <img class="iconImage"
                 :src="user_image.noLogin_icon"
                 alt="">
            <div class="personInfo"
                 v-if="!userInfo.token">
              <div @click="goToPage('login')">立即登录</div>
            </div>
          </div>
        </template>
      </van-cell>
    </van-cell-group>
    <!-- 订单相关-->
    <van-cell-group>
      <van-grid :border=false :column-num="3">
          <van-grid-item >
            <b style="font-size: 30px">{{allCount}}</b>
            <i style="font-size: 12px">总文稿</i>
          </van-grid-item>
          <van-grid-item icon="photo-o" >
            <b style="font-size: 30px">{{shareCount}}</b>
            <i style="font-size: 12px">分享</i>
          </van-grid-item>
          <van-grid-item icon="photo-o" text="文字" >
            <b style="font-size: 30px">{{allCollectionCount}}</b>
            <i style="font-size: 12px">收藏</i>
          </van-grid-item>
      </van-grid>
    </van-cell-group>
    <van-cell-group style="margin-top:0.4rem">
       <van-cell title="我的订单"
                icon="label"
                is-link
                @click="goToPage('orders')">
      </van-cell>
      <van-cell title="我的收藏"
                icon="todo-list"
                is-link
                @click="goToPage('collection')" />
      <van-cell title="我的分享"
                icon="todo-list"
                is-link
                @click="goToPage('share')" />
     <van-cell title="我的标签"
                icon="todo-list"
                is-link
                @click="goToPage('tag')" />
      <van-cell title="修改密码"
                icon="todo-list"
                is-link
                @click="goToPage('updatePassword')" />
    </van-cell-group>

    <van-cell-group style="margin-top:0.4rem">
      <van-cell is-link
                icon="vip-card"
                @click="goToPage('vip')">
        <template slot="title">
          <span class="custom-title">我的会员</span>
          <van-tag type="danger"
                   :round=true>NEW</van-tag>
        </template>
      </van-cell>
    </van-cell-group>
    <van-cell-group style="margin-top:0.4rem">
      <van-cell title="关于我们"
                icon="comment-circle"
                is-link
                @click="goToPage('about')" />
     <van-cell title="我的信箱"
                icon="comment-circle"
                is-link
                @click="onFeedBack" />
      <van-cell title="联系客服"
                icon="phone"
                value="客服时间 07:00-22:00"
                is-link />
      <van-cell title="意见反馈"
                icon="comment-circle"
                is-link
                @click="goToPage('feedBack')" />
    </van-cell-group>
    <div class="version">当前版本{{version}}</div>
    <!--路由的出口-->
    <transition name="router-slider"
                mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script type="text/javascript">
// 引入vuex
import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/note';
import { allCollections } from '../../api/collection';
import { allShare } from '../../api/share';


export default {
  data () {
    return {
      allCount: 0,
      allCollectionCount:0,
      shareCount:0,
      userInfo:{},
      temp:{},
      // 头像
      user_image: {
        login_icon: require('./../../images/mine/defaultImg.jpeg'),
        noLogin_icon: require('./../../images/login/grey.jpg'),
        female: require('./../../images/mine/female.png'),
        male: require('./../../images/mine/male.png')
      },
      version: '22',           // 版本信息
    }
  },
  computed: {
  
  },
  created(){
      this.getInfo()
      this.getData()
  },
  methods: {
    getInfo(){
       var info = JSON.parse(localStorage.getItem('userInfo'))
      
       if (info ==undefined ||info==null) {
          this.userInfo.token =false;
       }else{
            this.userInfo.userName= info.userName
            this.userInfo.email = info.email
            this.userInfo.userType = info.userType
            if (this.userInfo.userType==1) {
              this.userInfo.typeName= '会员'
            }else{
               this.userInfo.typeName= '非会员'
            }
            this.userInfo.token =true;
       }
       console.log(this.userInfo)
    },
    getData(){
         fetchData(this.query).then(resg => {
            if (resg.code==0) {
                 var j =0;
                 for (var i =0; i< resg.data.length; i++) {
                     j=j+resg.data[i].noteList.length;
                    console.log(resg.data[i].noteList)
                 }
                 this.allCount= j;
            }else{
              Toast(resg.msg);
            }
          });

        allCollections(this.query).then(resg => {
            if (resg.code==0) {
                 this.allCollectionCount= resg.data.length;
            }else{
              Toast(resg.msg);
            }
          });
        allShare(this.query).then(resg => {
            if (resg.code==0) {
                 this.shareCount= resg.data.myShare.length;
            }else{
              Toast(resg.msg);
            }
          });

    },
    // 跳转到我的订单
    goTomyOrder (index) {
      if (index !== 3) {
        return this.$router.push({ name: "myOrder", params: { active: index + 1 } });
      }
      // 跳转到售后退款界面
      Toast({
        message: '退款页面尚未开通啦!',
        duration: 1500
      })
    },
    goToPage (name) {
      this.$router.push({ name });
    },
    // 意见反馈
    onFeedBack () {
      Dialog.alert({
        confirmButtonText: '记得点个小星❤️哦~',
        title: '💘感谢您的关注💘',
        message: 'GitHub上搜索 \nGeek-James/ddBuy \n🦉欢迎提出优化建议🙉'
      }).then(() => {
        // on close
      });
    }
  }
}
</script>

<style lang="less" scoped>
#mine {
  width: 100%;
  background-color: #f5f5f5;
  margin-bottom: 3rem;
  .version {
    margin: 0 auto;
    text-align: center;
    font-size: 0.6rem;
    background-color: #ffffff;
    height: 2rem;
    color: grey;
    line-height: 2rem;
  }
  .van-nav-bar {
    background-color: #3bba63;
    font-size: 0.6rem;
  }
  .van-nav-bar__title {
    color: white;
  }
  .personMsg {
    display: flex;
    align-items: center;
    .sex {
      position: absolute;
      top: 3.5rem;
      left: 3.8rem;
      width: 0.1rem;
      height: 0.1rem;
      img {
        width: 1rem;
        height: 1rem;
      }
    }
    img {
      width: 4rem;
      height: 4rem;
      border-radius: 50%;
    }

    .personInfo {
      display: flex;
      flex-direction: column;
      margin-left: 0.8rem;
    }
  }

  .van-cell__left-icon {
    color: #45c763;
    font-size: 1.2rem;
  }
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
}
</style>
