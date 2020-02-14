<template>
  <div id="myOrder">
        <van-nav-bar title="最新文稿"  left-arrow>
          <van-icon name="search" slot="left" />
        </van-nav-bar>
        <br>
        <div >
          <div v-for="item in listNote">
            <van-swipe-cell>
                  <van-cell
                    @click=""
                    :title="item.noteTitle"
                    :label="item.createTime"
                  />
                  <template slot="right">
                    <van-button square type="danger"
                      @click="removeNote(item.id)"
                      square
                      text="删除"
                    />
                  </template>
                </van-swipe-cell>
        </div>


        </div>
</div>
</template>

<script type="text/javascript">
import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/note';
import { deleteNote } from '../../api/note';


// 引入页面组件
import Loading from '../../components/loading/LoadingGif'

export default {
  computed: {
  },
  
  mounted () {

  },
  data () {
    return {
      active:'',
      query:{
        abc:'12312'
      },
      listNote:[]
    }
  },
  components: {
    Loading,
  },
  created () {
    this.getData();
  },
  methods: {
    onChange(){
    },
    getData(){
      console.log(this.query)
        fetchData(this.query).then(resg => {
            if (resg.code==0) {
                var list = resg.data;
                for (var i =0; i<list.length; i++) {
                    var notes = list[i].noteList;
                    for (var j =0 ; j<notes.length; j++) {
                        this.listNote.push(notes[j]);
                    }
                } 
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }
          });
    },
    removeNote(id){
      let param={
        id: id
      }
        deleteNote(param).then(resg => {
            if (resg.code==0) {
                Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
                this.listNote=[]
                this.getData()
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }
          });

    }
  },
}
</script>

<style lang="less" scoped>
#home {
  background-color: "#f5f5f5";
  padding-bottom: 3rem;
  .head {
    margin-top: -3rem;
    width: 100%;
    background-image: url("http://518taole.7-orange.cn/backImage.png");
  }
}
.van-divider {
  background-color: #f5f5f5;
  margin: 0;
}
</style>
