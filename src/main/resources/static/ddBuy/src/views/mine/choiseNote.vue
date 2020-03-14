<template>
  <div id="choiseNote">
        <van-nav-bar v-if="!show"
        title="关联笔记"
        left-text="返回"
        left-arrow
        right-text="新增"
        @click-left="onClickLeft"
      >
        <van-icon name="shop-collect-o" slot="right" >
          <van-dropdown-menu>
            <van-dropdown-item >
                <van-cell title="导出笔记" @click="daochu">
                </van-cell>
                <van-cell title="新增" @click="onClickRight">
                </van-cell>
            </van-dropdown-item>
          </van-dropdown-menu>
        </van-icon>
      </van-nav-bar>
       <van-nav-bar v-if="show"
        title="关联笔记"
        left-text="取消"
        left-arrow
        right-text="保存"
        @click-left="show= false"
        @click-right="save"
      />
        <br>
        <div  v-if="!show">
          <div v-for="item in listNote">
            <van-swipe-cell>
                  <van-cell
                    @click="gotoNote(item)"
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
        <div style="margin-left: 10px" v-if="show" >
           <van-checkbox-group v-model="result" bind:change="onChange">
            <div v-for="item in allNote">
              <van-cell
                    @click=""
                    :title="item.noteTitle"
                    :label="item.createTime"
                  >
                <van-checkbox :name="item.id" :value="item.id" style="float: right;margin-right: 13%;margin-top: 10px" /></van-cell>
            </div>
            </van-checkbox-group>
        </div>
</div>
</template>

<script type="text/javascript">
import { Dialog, Toast } from 'vant'
import { fetchDataByNoteId } from '../../api/tag';
import { fetchData } from '../../api/note';
import { addLabelByNote } from '../../api/tag';
import { deleteLabelByNote } from '../../api/tag';
import { downloadNoteByLabelId } from '../../api/share';


// 引入页面组件
import Loading from '../../components/loading/LoadingGif'

export default {
  data () {
    return {
      active:'',
      query:{
        labelId:''
      },
      aaa:false,
      listNote:[],
      result:[],
      allNote:[],
      show: false
    }
  },
  components: {
    Loading,
  },
  created () {
    this.getData();
  },
  methods: {
    gotoNote(item){
      localStorage.setItem('noteInfo',JSON.stringify(item))
      this.$router.push('zhanshi');
    },
    onClickLeft () {
      this.$router.back();
    },
    onClickRight(){
        this.show= true;
    },
    onChange(event) {
        console.log(event)
    },
    getData(){
      this.listNote=[]
      this.allNote=[]
      var ids =localStorage.getItem('noteId');
      console.log(ids)
        var urlSearchParam = new URLSearchParams();
        urlSearchParam.append("labelId",ids);
        fetchDataByNoteId(urlSearchParam).then(resg => {
            if (resg.code==0) {
               this.listNote= resg.data;
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }
          });
        fetchData(this.query).then(resg => {
            if (resg.code==0) {
              this.allNote=[]
                var list = resg.data;
                for (var i =0; i<list.length; i++) {
                    var notes = list[i].noteList;
                    for (var j =0 ; j<notes.length; j++) {
                        this.allNote.push(notes[j]);
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
      save(){
          if (this.result.length<=0) {
            Toast({
                  message: this.$t('至少选择一个笔记'),
                  duration: 800
              });
            return false
          }
         var id =localStorage.getItem('noteId');
          let param= {
            notes: this.result.toString(),
            labelId: id
          }
            addLabelByNote(param).then(resg => {
                  if (resg.code==0) {
                      Toast({
                        message: this.$t('新增成功'),
                        duration: 800
                    });
                      this.getData()
                      this.show=false
                  }else{
                    Toast({
                        message: this.$t(resg.msg),
                        duration: 800
                    });
                  }
                });
      },
      removeNote(id){
        var ids =localStorage.getItem('noteId');
        let param= {
            noteId: id,
            labelId: ids
          }
           Dialog.confirm({
          title: '提示',
          message: '确定取消关联吗？'
        }).then(() => {
          deleteLabelByNote(param).then(resg => {
                  if (resg.code==0) {
                      Toast({
                        message: this.$t('删除成功'),
                        duration: 800
                    });
                      this.getData()
                      this.show=false
                  }else{
                    Toast({
                        message: this.$t(resg.msg),
                        duration: 800
                    });
                  }
                });
        }).catch(() => {
          // on cancel
        });

      },
      daochu(labelId){
         var ids =localStorage.getItem('noteId');
        var urlSearchParam = new URLSearchParams();
        urlSearchParam.append("labelId",ids);
        window.open('http://192.168.0.24:8080/api/users/downloadNoteByLabelId?labelId='+ids, "_blank");
        
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
