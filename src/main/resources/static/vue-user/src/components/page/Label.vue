<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 标签
                </el-breadcrumb-item>
                <el-breadcrumb-item>标签页</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <span style="float: right;"><el-button type="primary" icon="el-icon-search" @click="addVisible=true">新建标签</el-button></span>
            </div>
            <br />
            <br />
              <el-carousel :interval="800" type="card" height="200px">
                <el-carousel-item v-for="item in labelLit" style="width: 300px;">
                            <el-card shadow="always">
                                <i @click="selectNotes(item)">
                                    {{item.labelName}}
                                 </i>
                              <br />
                                  <span style="float: right;">
                                     <el-button type="primary" @click="handlerNotes(item)" icon="el-icon-circle-plus-outline" circle></el-button>
                                      <el-button type="primary" icon="el-icon-edit" @click="handlerEdit(item)" circle></el-button>
                                    <el-button type="danger" icon="el-icon-delete" @click="removeLabel(item)" circle></el-button>
                                    </span>
                            </el-card>
                </el-carousel-item>
              </el-carousel>
        </div>
        <br />
        <div class="container">
            <h3>关联笔记</h3><br />
                  <el-table
                    :data="labelAndNoteList"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                >
                    <el-table-column type="selection" width="55" align="center"></el-table-column>
                    <el-table-column prop="id" label="ID" width="55" align="center">
                        <template slot-scope="scope">
                            {{scope.row.id}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="本子标题">
                        <template slot-scope="scope">
                            {{scope.row.noteTitle}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="本子类型">
                        <template slot-scope="scope">
                             <i v-if="scope.row.noteType==0">
                                <el-tag>文字</el-tag>
                            </i>
                            <i v-if="scope.row.noteType==1">
                                 <el-tag>图片</el-tag>
                            </i>
                             <i v-if="scope.row.noteType==2">
                                 <el-tag>视频</el-tag>
                            </i>
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="创建时间">
                        <template slot-scope="scope">
                            {{scope.row.createTime}}
                        </template>
                    </el-table-column>
                     <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="deleteNoteAndLabel(scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 新建标签弹出框 -->
        <el-dialog title="新建标签" :visible.sync="addVisible" width="30%">
            <el-form ref="form" :model="addForm" label-width="70px">
                <el-form-item label="标签名">
                    <el-input v-model="addForm.labelName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="addLabel">确 定</el-button>
            </span>
        </el-dialog>

         <!-- 编辑标签弹出框 -->
        <el-dialog title="编辑标签" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="updateForm" label-width="70px">
                <el-form-item label="标签 id">
                    <el-input v-model="updateForm.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="标签名">
                    <el-input v-model="updateForm.labelName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateLabel">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新增笔记弹出框 -->
        <el-dialog title="记事本新标签" :visible.sync="addNoteByLabel" width="50%">
            <el-form ref="form" :model="noteForm" label-width="70px">
                <el-form-item label="笔记">
                     <el-select multiple  v-model="noteForm.notes" filterable placeholder="请选择添加标签的笔记" style="width: 400px">
                        <el-option
                          v-for="item in noteList"
                          :key="item.id"
                          :label="item.noteTitle"
                          :value="item.id">
                        </el-option>
                      </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addNoteByLabel = false">取 消</el-button>
                <el-button type="primary" @click="noteAddLabel">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import { deleteData } from '../../api/label';
import { updateData } from '../../api/label';
import { addData } from '../../api/label';
import { fetchData } from '../../api/label';
import { fetchNoteList } from '../../api/label';
import { fetchNoteListByLabelId } from '../../api/label';
import { addLabelByNote } from '../../api/label';
import { deleteLabelByNoteData } from '../../api/label';


export default {
    name: 'baseform',
    data() {
        return {
            query:{},
           editVisible: false,
            updateForm:{},
            addVisible: false,
            addForm:{
                labelName: '',
            },
            tableData: [],
            addNoteByLabel: false,
            noteForm:{
                notes: [],
            },
            options:[],
            labelLit:[],
            noteList:[],
            labelAndNoteList:[],
            tempLabel: '',
        };
    },
    created(){
        this.getData();
    },
    methods: {
        getData(){
            fetchData(this.query).then(res => {
                    this.labelLit = res.data;
            });
            fetchNoteList(this.query).then(res => {
                    this.noteList = res.data;
            });
        },
        onSubmit() {
            this.$message.success('提交成功！');
        },
        handlerEdit(row){
            this.updateForm = row;
            this.editVisible= true
        },
        handlerNotes(row){
            this.noteForm.labelId = row.id;
            this.addNoteByLabel= true  
        },
        deleteNoteAndLabel(row){

            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                    console.log("33333")
                   const forms = {
                        noteId: row.id,
                        labelId: this.tempLabel
                   }
                    deleteLabelByNoteData(forms).then(res => {
                        if (res.code == 0) {
                          this.$message.success('删除成功');
                          this.labelAndNoteList =[]
                        }else{
                          this.$message.error(res.msg);
                        }
                    }).catch(res=>{
                        console.log(res)
                    });
                })
                .catch(() => {});

        },
        removeLabel(row){
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                   const forms = {id: row.id}
                    deleteData(forms).then(res => {
                        if (res.code == 0) {
                          this.$message.success('删除成功');
                          this.getData()
                        }else{
                          this.$message.error(res.msg);
                        }
                    }).catch(res=>{
                        console.log(res)
                    });
                })
                .catch(() => {});

        },
        addLabel(){
            addData(this.addForm).then(res => {
                if (res.code == 0) {
                  this.$message.success('更新成功');
                  this.addForm.labelName=''
                  this.addVisible=false
                  this.getData()
                  this.labelAndNoteList =[]
                }else{
                  this.$message.error(res.msg);
                }
            }).catch(res=>{
                console.log(res)
            });    
        },
        noteAddLabel(){
            if (this.noteForm.notes.length<=0) {
                this.$message.error('至少需要一篇笔记');
                return false;
            }
             var temp =this.noteForm.notes;
            this.noteForm.notes = this.noteForm.notes.toString();
             addLabelByNote(this.noteForm).then(res => {
                if (res.code == 0) {
                  this.$message.success('更新成功');
                  this.noteForm.notes=[]
                  this.addNoteByLabel=false
                  this.labelAndNoteList =[]
                }else{
                  this.$message.error(res.msg);
                  this.noteForm.notes=temp;
                }
            }).catch(res=>{
                console.log(res)
            });    
        },
        updateLabel(){
            if (this.updateForm.labelName=='') {
                 this.$message.error('标签名不能为空');
                return false
            }
            let aa={
                labelName: this.updateForm.labelName,
                id: this.updateForm.id,
            }
            updateData(aa).then(res => {
                if (res.code == 0) {
                  this.$message.success('更新成功');
                  this.updateForm={}
                  this.editVisible= false
                  this.getData();
                }else{
                  this.$message.error(res.msg);
                }
            }).catch(res=>{
                console.log(res)
            });    
        },
        selectNotes(row){
            this.tempLabel = row.id
             this.labelAndNoteList=[]
            let a={labelId: row.id }
             fetchNoteListByLabelId(a).then(res => {
                this.labelAndNoteList = res.data;
            });
        }
    }
};
</script>
<style type="text/css">
     .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 100px;
    margin: 0;
  }

</style>