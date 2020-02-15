<template>
  <div id="collection">
     <van-nav-bar
        title="会员权益"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
      <br>
      <h3 style="margin-left: 5%">开通会员,权益享不挺</h3>
        <div id="vipPay">
          <div class="vipDesc">
            <van-grid :column-num="3"
                      :border=false>
              <van-grid-item icon="vip-card-o"
                             text="标签无上限"
                             style="color:#60b86a" />
              <van-grid-item icon="discount"
                             text="视频文件大于 10M"
                             style="color:#60b86a" />
              <van-grid-item icon="gem-o"
                             text="图片文稿大于 1M"
                             style="color:#60b86a" />
            </van-grid>
          </div>
        </div>
        <br>
        <br>
        <h3 style="margin-left: 5%">会员类型</h3>
        <br>
        <div class="vipType">
               <van-checkbox-group v-model="checks" bind:change="onChange">
                  <van-cell
                        @click=""
                        title="一年期会员"
                        label="￥120">
                    <van-checkbox name="a"  style="float: right;margin-right: 13%;margin-top: 10px" /></van-cell>
                </van-checkbox-group>
                
         </div>

    <van-submit-bar
        :price="12000"
        button-text="提交订单"
        @submit ="save"
      />
  </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { insertFeedBack } from '../../api/feedBack';
import { createOrders } from '../../api/orders';


export default {
  data () {
    return {
      typeArray: [],
      // 路由传递过来的数据 active
      active:'',
      money:'',
      itemsTitle:'',
      message:'',
      checks: ['a']
    }
  },
  created(){
    this.getData()
  },
  methods: {
    save() {
      let param={}
      createOrders(param).then(resg => {
            if (resg.code==0) {
              Toast({
                  message: this.$t('创建订单成功'),
                  duration: 800
              });
              this.$router.push('orders');
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
    },
    onClickLeft () {
      this.$router.back();
    },
    getData(){
      console.log(1111)
    },
     back() {
      this.$router.go(-1);
    },
  }
}
</script>

<style lang="less" scoped>
#myOrder {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 100;
  background-color: #f5f5f5;
  .van-icon {
    color: #dedede;
  }
}
.vipDesc {
    position: relative;
    padding-top: 1rem;
    width: 100%;
    background-color: #ffffff;
    p {
      text-align: center;
      font-size: 0.8rem;
      font-weight: bolder;
    }
  }

</style>
