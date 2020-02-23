<template>
  <div id="collection">
      <van-nav-bar title="文稿" left-arrow>
          <van-icon name="shop-collect-o" slot="left" />
        </van-nav-bar>
      <van-grid clickable :column-num="2">
            <van-grid-item
              icon="notes-o"
              link-type="navigateTo"
              @click="goToPage('quills')"
              text="新文稿"
            />
            <van-grid-item
              icon="photo"
              link-type="reLaunch"
              @click="goToPage('image')"
              text="上传图片"
            />
          </van-grid>
          <van-grid clickable :column-num="2">
             <van-grid-item
              icon="video"
              link-type="navigateTo"
              text="上传视频"
              @click="goToPage('video')"
            />
            <van-grid-item
              icon="bill"
              link-type="reLaunch"
              @click="goToPage('word')"
              text="导入word"
            />
          </van-grid>
    </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/collection';
import { deleteCollection } from '../../api/collection';


export default {
  data () {
    return {
      collectionList: [
      ],
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
    }
  },
  created(){
    this.getData()
  },
  methods: {
    goToPage (name) {
      this.$router.push({ name });
    },
    getData(){
       fetchData(this.query).then(resg => {
            if (resg.code==0) {
                this.collectionList = resg.data
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
