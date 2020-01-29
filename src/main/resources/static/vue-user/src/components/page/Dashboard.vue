<template>
       <div class="container">
        <h3>新文稿</h3>
        <br>
            <el-form ref="form" :model="noteForm" label-width="80px">
                        <el-input v-model="noteForm.noteTitle" placeholder="请输入标题"></el-input><br><br>
                        <quill-editor ref="myTextEditor" v-model="noteForm.noteContent" :options="editorOption"></quill-editor>
                        <br>
                        <el-button class="editor-btn" type="primary" @click="submitData">提交</el-button>
                        <br>
                         
            </el-form>
    </div>
</template>

<script>
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import { quillEditor } from 'vue-quill-editor';
import { createNote } from '../../api/note';

export default {
    name: 'dashboard',
    data() {
        return {
           noteForm:{
                noteTitle: '',
                noteContent: '',
                noteType: 0,
            },
            editorOption: {
                placeholder: 'Hello World'
            }
        };
    },
    components: {
        quillEditor
    },
    computed: {
        
    },
    methods: {
         onEditorChange({ editor, html, text }) {
                this.content = html;
            },
        submitData(){
           var form =this.noteForm;
           if (form.noteTitle=='') {
               this.$message.error('标题不能为空');
                return false
           }
           if (form.noteContent=='') {
               this.$message.error('内容不能为空');
                return false
           }
        console.log(form)
           createNote(this.noteForm).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                            
                        }else{
                           this.$message.success(res.msg);
                            this.noteForm.noteType=0
                        }
            });
           
            
        }
    }
};
</script>


<style scoped>
.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>