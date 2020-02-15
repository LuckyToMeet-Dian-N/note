<template>
  <div class="content">
    <van-nav-bar
        title="新文稿"
        left-text="返回"
        right-text="保存"
        left-arrow
        @click-left="onClickLeft"
        @click-right="save"
      />
      <div v-if="noteInfo.noteType==0">
            <van-cell-group>
                <van-field
                  v-model="noteInfo.noteTitle"
                  placeholder="请输入标题"
                />
              </van-cell-group>
            <br>
            <div style="width: 100%">
               <vue-html5-editor :content="noteInfo.noteContent"  :auto-height="true" :show-module-name="false" @change="updateData"></vue-html5-editor>
            <br>
            </div>
        </div>

        <van-action-sheet
          v-model="showAction"
          :actions="actions"
          cancel-text="取消" 
          @select="onSelect"
        />
  </div>
</template>

<script>
import { createNote } from '../../api/note';
import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/note';


  export default {
    created(){
        this.getData()
    },
    data() {
      return {
      fileList:[],
      showAction: false,
       actions: [],
        messages: [],
        noteInfo:{
          noteTitle:'',
          noteContent:'',
          noteType: 0,
          path:''
        }
      }
    },
   
    methods: {
      getData(){
        this.actions=[]
           fetchData(this.query).then(resg => {

            if (resg.code==0) {
              this.fileList= resg.data;
                for(var i=0;i< resg.data.length;i++){
                   let param= {
                      name: resg.data[i].name,
                      color: '07c160'
                   } 
                   this.actions[i]=param
                }
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }
          });
      },
      onClickLeft () {
      this.$router.back();
    },
    updateData(e){  
         this.noteInfo.noteContent=e
    },
    save(){
        if (this.noteInfo.noteContent=='') {
            Toast('内容不能为空');
            return false;
        }
        if (this.noteInfo.noteTitle=='') {
            Toast('标题不能为空');
            return false;
        }
        this.showAction=true;
    },
    saveNote(){
        createNote(this.noteInfo).then(resg => {
            if (resg.code==0) {
                Toast('保存成功');
                  this.noteInfo.noteType=0;
                  this.showAction=false
                  this.onClickLeft()
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
       }});
    },
    onSelect(info){
        for (var i =0;this.fileList.length;i++) {
            if (info.name ==this.fileList[i].name) {
                this.noteInfo.path = this.fileList[i].id;
                this.saveNote();
                return
            }
        }

    }

} 
}
</script>

<style lang="scss">


</style>
