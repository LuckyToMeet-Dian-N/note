<template>
        <div>
                <div  class="crumbs">
                        <el-breadcrumb  separator="/">
                                <el-breadcrumb-item>
                                        <i  class="el-icon-lx-cascades"></i>  反馈管理
                                </el-breadcrumb-item>
                        </el-breadcrumb>
                </div>
                <div  class="container">
                        <div  class="handle-box">
                              反馈状态&nbsp;
                                <el-select  v-model="payName"  placeholder="请选择">
                                        <el-option  label="待反馈"  value="0"  @click.native="selectState(0,'待反馈')"></el-option>
                                      <el-option  label="已反馈"  value="1"  @click.native="selectState(1,'已反馈')"  ></el-option>
                                    </el-select>
                                    &nbsp;&nbsp;
                                <el-button  type="primary"  icon="el-icon-search"  @click="handleSearch">搜索</el-button>
                                &nbsp;&nbsp;<el-button  type="primary"  icon="el-icon-search"  @click="reSet">重置</el-button>
                        </div>
                        <br><br>
                        <el-table
                                :data="tableData"
                                border
                                class="table"
                                ref="multipleTable"
                                header-cell-class-name="table-header">
                                <el-table-column  type="selection"  width="55"  align="center"></el-table-column>
                                <el-table-column  prop="id"  label="ID"  width="55"  align="center">
                                        <template  slot-scope="scope">
                                                {{scope.row.id}}
                                        </template>
                                </el-table-column>
                                <el-table-column  prop="userId"  label="用户id">
                                        <template  slot-scope="scope">
                                                {{scope.row.userId}}
                                        </template>
                                </el-table-column>
                                  <el-table-column  prop="userName"  label="用户名">
                                        <template  slot-scope="scope">
                                                {{scope.row.userName}}
                                        </template>
                                </el-table-column>
                              
                                <el-table-column  prop="message"  label="反馈信息">
                                                <template  slot-scope="scope">
                                                {{scope.row.message}}
                                        </template>
                                        </el-table-column>
                                <el-table-column  prop="payState"  label="状态">
                                                <template  slot-scope="scope">
                                            
                                                <i  v-if="scope.row.isReply==0">
                                                          <el-tag>待回复</el-tag>
                                                </i>
                                                <i  v-if="scope.row.isReply==1">
                                                          <el-tag>已回复</el-tag>
                                                </i>
                                        </template>
                                </el-table-column>
                                <el-table-column  prop="reply"  label="回复信息">
                                        <template  slot-scope="scope">
                                                {{scope.row.reply}}
                                        </template>
                                </el-table-column>
                                <el-table-column  label="创建时间">
                                        <template  slot-scope="scope">
                                        {{scope.row.createTime}}
                                    </template>
                                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >回复</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                ></el-pagination>
            </div>
        </div>

        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="id">
                    <el-input v-model="form.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="用户明">
                    <el-input v-model="form.userName"  :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="反馈信息">
                    <el-input v-model="form.message"  :disabled="true"></el-input>
                </el-form-item>

                <el-form-item label="回复内容">
                    <el-input v-model="form.reply" placeholder="请输入回馈信息"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { fetchData } from '../../api/feedBack';
import { updateData } from '../../api/feedBack';
import { deleteData } from '../../api/feedBack';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                isReply: '',
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
                    // this.$message.success('删除成功');
                    // this.tableData.splice(index, 1);
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
            this.query.isReply =state;
        },
        reSet(){
            this.query.isReply = '';
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