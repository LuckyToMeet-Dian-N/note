<template>
        <div>
                <el-row  :gutter="20">
                        <el-col  :span="8">
                                <el-card  shadow="hover"  class="mgb20"  style="height:252px;">
                                        <div  class="user-info">
                                                <img  src="../../assets/img/img.jpg"  class="user-avator"  alt  />
                                                <div  class="user-info-cont">
                                                        <div  class="user-info-name">{{name}}</div>
                                                        <div>{{role}}</div>
                                                </div>
                                        </div>
                                        <div  class="user-info-list">
                                                上次登录时间：
                                                <span>2019-11-01</span>
                                        </div>
                                        <div  class="user-info-list">
                                                上次登录地点：
                                                <span>杭州</span>
                                        </div>
                                </el-card>
                                <el-card  shadow="hover"  style="height:252px;">
                                        <div  slot="header"  class="clearfix">
                                                <span>简单进度</span>
                                        </div>开发
                                        <el-progress  :percentage="71.3"  color="#42b983"></el-progress>测试
                                        <el-progress  :percentage="24.1"  color="#f1e05a"></el-progress>
                                </el-card>
                        </el-col>
                        <el-col  :span="16">
                                <el-row  :gutter="20"  class="mgb20">
                                        <el-col  :span="8">
                                                <el-card  shadow="hover"  :body-style="{padding:  '0px'}">
                                                        <div  class="grid-content  grid-con-1">
                                                                <i  class="el-icon-lx-people  grid-con-icon"></i>
                                                                <div  class="grid-cont-right">
                                                                        <div  class="grid-num">{{allUserNum}}</div>
                                                                        <div>用户量</div>
                                                                </div>
                                                        </div>
                                                </el-card>
                                        </el-col>
                                        <el-col  :span="8">
                                                <el-card  shadow="hover"  :body-style="{padding:  '0px'}">
                                                        <div  class="grid-content  grid-con-2">
                                                                <i  class="el-icon-lx-notice  grid-con-icon"></i>
                                                                <div  class="grid-cont-right">
                                                                        <div  class="grid-num">{{allShareNum}}</div>
                                                                        <div>用户分享数量</div>
                                                                </div>
                                                        </div>
                                                </el-card>
                                        </el-col>
                                        <el-col  :span="8">
                                                <el-card  shadow="hover"  :body-style="{padding:  '0px'}">
                                                        <div  class="grid-content  grid-con-3">
                                                                <i  class="el-icon-lx-goods  grid-con-icon"></i>
                                                                <div  class="grid-cont-right">
                                                                        <div  class="grid-num">{{allNoteNum}}</div>
                                                                        <div>笔记数量</div>
                                    </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-card shadow="hover" style="height:403px;">
                    <div slot="header" class="clearfix">
                        <span>用户今日分享</span>
                    </div>
                    <el-table :show-header="false" :data="todoList" style="width:100%;">
                        <el-table-column width="40">
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.id"></el-checkbox>
                            </template>
                        </el-table-column>
                        <el-table-column>
                            <template slot-scope="scope">
                                <div class="todo-item">{{scope.row.noteTitle}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="60">
                            <template slot-scope="scope">
                                <button @click="handleEdit(scope.row)">查看</button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="bar" class="schart" canvasId="bar" :options="options"></schart>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="line" class="schart" canvasId="line" :options="options2"></schart>
                </el-card>
            </el-col>
        </el-row>

        <!-- 编辑弹出框 -->
        <el-dialog title="查看" :visible.sync="editVisible" width="50%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="id">
                    <el-input v-model="form.id" :disabled="true"></el-input>
                </el-form-item>
                 <el-form-item label="用户名">
                    <el-input v-model="form.userName"  :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.noteTitle"  :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="内容">
                   <div>
                        {{form.noteContent}}
                   </div>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="form.createTime"  :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">关 闭</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import Schart from 'vue-schart';
import bus from '../common/bus';
import { fetchConsoleData } from '../../api/index';
export default {
    name: 'dashboard',
    data() {
        return {
            name: 'admin',
            allNoteNum:'',
            allShareNum:'',
            allUserNum:'',
            query:{

            },
            form:{},
            editVisible: false,
            todoList: [],
            options: {
                type: 'bar',
                title: {
                    text: '最近几月用户分享数量'
                },
                xRorate: 25,
                labels: ['10月', '11月', '12月', '1月', '2月'],
                datasets: [
                    {
                        label: '分享',
                        data: [1,2]
                    }
                ]
            },
            options2: {
                type: 'line',
                title: {
                    text: '最近几月用户增长情况'
                },
                labels: ['10月', '11月', '12月', '1月', '2月'],
                datasets: [
                    {
                        label: '用户',
                        data: [1,1]
                    }
                ]
            }
        };
    },
    components: {
        Schart
    },
    computed: {
        role() {
            return this.name === 'admin' ? '超级管理员' : '普通用户';
        }
    },
    created(){
        this.getDate();
    },
    methods: {
        getDate(){
            fetchConsoleData(this.query).then(res => {
                this.options.datasets[0].data= res.data.shareNumber;
                this.options2.datasets[0].data= res.data.userNumber;
                this.allNoteNum=res.data.allNoteNum;
                this.allShareNum = res.data.allShareNum
                this.allUserNum = res.data.allUserNum
                this.todoList = res.data.nodeVos;


            });
        },
        handleEdit(row){
            this.form = row;
            this.editVisible =true;
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