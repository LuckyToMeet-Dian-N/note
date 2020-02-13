<template>
  <div id="myOrder">
     <van-nav-bar
        title="分享列表"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      /><br>
     <p> &nbsp; &nbsp;我的分享</p>
     <br>
   <div class="content">
        <div v-for="item in shareInfo.myShare">
            <van-swipe-cell>
                  <van-cell
                    @click=""
                    :title="item.noteTitle"
                    :label="item.createTime"
                  />
                  <template slot="right">
                    <van-button square type="danger"
                      @click="removeShare(item.id)"
                      square
                      text="取消分享"
                    />
                  </template>
                </van-swipe-cell>
        </div>
  </div>
  <br>
  <p> &nbsp; &nbsp;全部分享</p>
     <br>
  <div class="content">
    <div v-for="item in shareInfo.allShare">
            <van-swipe-cell>
                  <van-cell
                    @click=""
                    :title="item.noteTitle"
                    :label="item.createTime"
                  />
                  <template slot="right">
                    <van-button v-if="userInfo.id ==item.userId" square type="danger"
                      @click="removeShare(item.id)"
                      square
                      text="取消分享"
                    />
                    <van-button square type="danger"
                      @click="collection(item.noteId)"
                      square
                      text="收藏"
                    />
                  </template>
                </van-swipe-cell>
        </div>
        
  </div>
</div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/share';
import { insertCollection } from '../../api/collection';


export default {
  data () {
    return {
      userInfo:{},
      shareInfo:{},
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      query:{}
    }
  },
  created(){
    this.getData()
  },
  methods: {
    onClickLeft () {
      this.$router.back();
    },
    getData(){
      var info = JSON.parse(localStorage.getItem('userInfo'))
      this.userInfo=info
     fetchData(this.query).then(resg => {
            if (resg.code==0) {
                this.shareInfo = resg.data
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
    },
     back() {
      this.$router.go(-1);
    },
    removeShare(id){


    },
    collection(id){
        let param = {
          noteId: id
        }
        insertCollection(param).then(resg => {
            if (resg.code==0) {
                Toast({
                  message: this.$t('收藏成功'),
                  duration: 800
              });
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
</style>
