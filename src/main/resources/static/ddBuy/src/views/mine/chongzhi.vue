<template>
  <div id="myOrder">
     <van-nav-bar
        title="充值"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />

     <van-radio-group v-model="radio">
          <van-cell-group title="支付方式">
            <van-cell clickable
                      @click="radio = '1'">
              <template slot="title">
                <img src="./../../images/order/wx.png"
                     alt=""
                     width="25px"
                     height="25px"
                     style=" vertical-align: middle;padding-right:5px">
                <span>微信支付</span>
              </template>
              <van-radio slot="right-icon"
                         name="1"
                         checked-color="#07c160" />
            </van-cell>
            <van-cell clickable
                      @click="radio = '2'">
              <template slot="title">
                <img src="./../../images/order/zfb.png"
                     alt=""
                     width="25px"
                     height="25px"
                     style=" vertical-align: middle;padding-right:5px">
                <span>支付宝</span>
              </template>
              <van-radio slot="right-icon"
                         name="2"
                         checked-color="#07c160" />
            </van-cell>
            <van-cell clickable
                      @click="radio = '3'">
              <template slot="title">
                <img src="./../../images/order/hb.png"
                     alt=""
                     width="25px"
                     height="25px"
                     style=" vertical-align: middle;padding-right:5px">
                <span>花呗</span>
              </template>
              <van-radio slot="right-icon"
                         name="3"
                         checked-color="#07c160" />
            </van-cell>
          </van-cell-group>
          <br>
          <br>
        </van-radio-group>
          <van-field v-model="value" disabled   label="支付金额" />
        <div class="payButton"
         @click="clickPay">立即充值</div>
    </div>

</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { updateUserInfo } from '../../api/userInfo';
import { getUserInfo } from '../../api/login';
import { chongzhi } from '../../api/userInfo';

export default {
  data () {
    return {
      typeArray: [],
      value:'',
      showKeyboard: false,
      win: false,
      errorInfo: '',
      radio: '',
      value: 10,
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      userInfo:{
        email:'22',
        userName:'222',
        age:'',
        region:'',
        balances:'1',
      }
    }
  },
  created(){
    this.getData()
  },
  methods: {
    onClickLeft() {
      Toast('返回');
    },
    onClickRight(){
        Toast('哈哈哈');
    },
    onClickLeft () {
      this.$router.back();
    },
    getData(){
      var info = JSON.parse(localStorage.getItem('userInfo'))
    },
    save(){
    },
     back() {
      this.$router.go(-1);
    },
    onInput(key) {
      this.value = (this.value + key).slice(0, 6);
      if (this.value.length === 6) {
        this.errorInfo = '密码错误';
      } else {
        this.errorInfo = '';
      }
    },
    onDelete() {
      this.value = this.value.slice(0, this.value.length - 1);
    },
    chongzhi(){
      this.win= true;
    },
    clickPay(){
      if (this.radio=='') {
         Toast('请选择支付方式');
         return false
      }
      let param={
      }
        chongzhi(param).then(resg => {
            if (resg.code==0) {
              Toast('充值成功');
              this.$router.go(-1);
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
        }});
    }
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
 .payButton {
    text-align: center;
    position: fixed;
    bottom: 1rem;
    height: 2.5rem;
    line-height: 2.5rem;
    color: white;
    width: 90%;
    left: 5%;
    right: 5%;
    z-index: 999;
    border-radius: 1.5rem;
    background-color: #60b86a;
  }
</style>
