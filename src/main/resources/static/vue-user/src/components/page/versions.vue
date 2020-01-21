<template>
        <div>
                <div  class="crumbs">
                        <el-breadcrumb  separator="/">
                                <el-breadcrumb-item>
                                        <i  class="el-icon-lx-cascades"></i>  历史版本
                                </el-breadcrumb-item>
                        </el-breadcrumb>
                </div>
                <div  class="container">
                        <el-table
                                :data="tableData"
                                border
                                class="table"
                                ref="multipleTable"
                                header-cell-class-name="table-header"
                        >
                                <el-table-column  type="selection"  width="55"  align="center"></el-table-column>
                                <el-table-column  prop="id"  label="ID"  width="55"  align="center">
                                        <template  slot-scope="scope">
                                                {{scope.row.id}}
                                        </template>
                                </el-table-column>
                                <el-table-column  prop="desc"  label="版本">
                                                <template  slot-scope="scope">
                                                {{scope.row.version}}
                                        </template>
                                </el-table-column>
                                <el-table-column  prop="desc"  label="版本描述">
                                                <template  slot-scope="scope">
                                                {{scope.row.description}}
                                        </template>
                                        </el-table-column>
                                <el-table-column  prop="noteId"  label="版本发布路径">
                                        <template  slot-scope="scope">
                                                {{scope.row.path}}
                                        </template>
                                </el-table-column>
                                <el-table-column  label="创建时间">
                                        <template  slot-scope="scope">{{scope.row.createTime}}</template>
                                </el-table-column>
                                <el-table-column  label="操作"  width="180"  align="center">
                                        <template  slot-scope="scope">
                                            <!--      <el-button
                                                        type="text"
                                                        icon="el-icon-edit"
                                                        @click="handleEdit(scope.$index,  scope.row)"
                                                >查看详情</el-button>  -->
                                                <el-button
                                                        type="text"
                                                        icon="el-icon-delete"
                                                        class="red"
                                                        @click="handleDelete(scope.$index,  scope.row)"
                                                >删除</el-button>
                                        </template>
                                </el-table-column>
                        </el-table>
                        <div  class="pagination">
                                <el-pagination
                                        background
                                        layout="total,  prev,  pager,  next"
                                        :current-page="query.pageIndex"
                                        :page-size="query.pageSize"
                                        :total="pageTotal"
                                ></el-pagination>
                        </div>
                </div>

                <!--  编辑弹出框  -->
                <el-dialog  title="编辑"  :visible.sync="editVisible"  width="80%">
                        <el-form  ref="form"  :model="form"  label-width="70px">
                                <el-form-item  label="id" >
                    <el-input v-model="form.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="用户明">
                    <el-input v-model="form.userName"  :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="分享内容">
                    <div>
                        <i v-if="form.noteType == 0">
                            <textarea style="width: 100%;height:300px" :disabled="true">{{form.noteContent}}</textarea>
                         </i>                        
                        <i v-if="form.noteType == 1"> 
                            <textarea v-if="form.noteContent!=''" :disabled="true" style="width: 100%;height:300px">{{form.noteContent}}</textarea>
                            <el-image style="width: 100%;height: 400px;" src="https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg"></el-image>
                        </i> 
                        <i v-if="form.noteType == 2"> 
                            <textarea v-if="form.noteContent!=''" :disabled="true" style="width: 100%;height:300px">{{form.noteContent}}</textarea>
                            <video  style="width: 100%;height: 400px"      
                                     class="video-js vjs-default-skin vjs-big-play-centered" 
                                     controls 
                                     preload="auto"  
                                     autoplay= "muted"  
                                    >          
                                <source :src="form.filePath" type="video/mp4" />  
                            </video>     
                        </i> 
                    </div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">关 闭</el-button>
        
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { fetchData } from '../../api/versions';
import { deleteData } from '../../api/versions';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                noteType: '',
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            addUser: false,
            pageTotal: 0,
            addForm:{

            },
            payName: '',
            updateForm:{},
            form: {
                
            },
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取 easy-mock 的模拟数据
        getData() {
            fetchData(this.query).then(res => {
                this.tableData = res.data;
                this.pageTotal = res.data.length;
            });
        },
        // 删除操作
        handleDelete(index, row) {
            let param = {id: row.id}
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    deleteData(param).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                        }else{
                           this.$message.success(res.msg);
                           this.getData();
                        }
                    });

                })
                .catch(() => {});
        },
        handleSearch(){
            this.getData();
        },
        selectState(state,payName){
            this.payName = payName;
            this.query.noteType =state;
        },
        reSet(){
            this.query.noteType = '';
            this.payName =''
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;

            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.form.createTime=null
            this.form.updateData=null
           var update =  this.updateForm
            update.id = this.form.id
            update.userId =this.form.userId;
            update.reply = this.form.reply
            updateData(update).then(res => {
                if (res.code !=0) {
                     this.$message.error(res.msg);
                }else{
                   this.$message.success(res.msg);
                   this.editVisible = false;
                   this.getData();
                }
            });

            // this.$set(this.tableData, this.idx, this.form);
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        }
    }
};
</script>

<style scoped>
.handle-input {
    width: 150px;
}
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>