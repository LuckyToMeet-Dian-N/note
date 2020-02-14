<template>
  <div id="collection">
     <van-nav-bar
        title="收藏列表"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
      <div class="content">
       <div v-for="item in collectionList">
                 <van-swipe-cell>
                  <van-cell
                    @click=""
                    :title="item.note.noteTitle"
                    :value="item.note.updateTime"
                  />
                  <template slot="right">
                    <van-button square type="danger"
                      @click="removeCollection(item.id)"
                      square
                      text="取消收藏"
                    />
                  </template>
                </van-swipe-cell>
          </div>
      </div> 
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
    onClickLeft () {
      this.$router.back();
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
    removeCollection(item){
      console.log(item)
      let param= {
          id: item
      }
        Dialog.confirm({
          title: '经过',
          message: '确定取消收藏吗？'
        }).then(() => {
            deleteCollection(param).then(resg => {
            if (resg.code==0) {
                Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
                this.getData();
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
        }).catch(() => {
          // on cancel
        });

      
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
