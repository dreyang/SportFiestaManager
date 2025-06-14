<!--<template>-->
<!--  <div>-->
<!--    <div>-->
<!--    <div style="margin-bottom: 15px">-->
<!--      <el-input v-model="params.name" style="width: 160px" placeholder="请输入参赛人员姓名"></el-input>-->
<!--      <el-button type="warning" style="margin-left: 10px"@click="findBySearch()">查询</el-button>-->
<!--      <el-button type="warning" style="margin-left: 10px"@click="reset()">清空</el-button>-->
<!--      <el-button type="primary" style="margin-left: 10px"@click="add()">录入</el-button>-->
<!--    </div>-->
<!--    </div>-->
<!--    <div>-->
<!--      <el-table-->
<!--          :data="tableData" style="width: 100%">-->
<!--        <el-table-column prop="name" label="姓名" width="180"></el-table-column>-->
<!--        <el-table-column prop="xiangmu" label="比赛项目" width="180"></el-table-column>-->
<!--        <el-table-column prop="grade" label="成绩"></el-table-column>-->
<!--        <el-table-column prop="mingci" label="排名"></el-table-column>-->
<!--        <el-table-column  label="操作">-->
<!--          <tempate slot-scope="scope">-->
<!--            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>-->
<!--            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">-->
<!--              <el-button slot="reference" type="danger" style="margin-left: 5px">删除</el-button>-->
<!--            </el-popconfirm>-->
<!--          </tempate>-->
<!--        </el-table-column>-->
<!--      </el-table>-->
<!--    </div>-->
<!--    <div style="margin-top: 10px">-->
<!--      <span class="demonstration"></span>-->
<!--      <el-pagination-->
<!--          @size-change="handleSizeChange"-->
<!--          @current-change="handleCurrentChange"-->
<!--          :current-page="params.pageNum"-->
<!--          :page-sizes="[5, 10, 15, 20]"-->
<!--          :page-size="params.pageSize"-->
<!--          layout="total, sizes, prev, pager, next"-->
<!--          :total="total">-->
<!--      </el-pagination>-->
<!--    </div>-->
<!--    <div>-->
<!--      <el-dialog title="录入成绩信息" :visible.sync="dialogFormVisible" width="30%">-->
<!--        <el-form :model="form">-->
<!--          <el-form-item label="姓名" label-width="15%">-->
<!--            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="比赛项目" label-width="15%">-->
<!--            <el-input v-model="form.xiangmu" autocomplete="off" style="width: 90%"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="成绩" label-width="15%">-->
<!--            <el-input v-model="form.grade" autocomplete="off" style="width: 90%"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="排名" label-width="15%">-->
<!--            <el-input v-model="form.mingci" autocomplete="off" style="width: 90%"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--        <div slot="footer" class="dialog-footer">-->
<!--          <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--          <el-button type="primary" @click="submit()">确 定</el-button>-->
<!--        </div>-->
<!--      </el-dialog>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->


<!--import request from "@/utils/request";-->

<!--export default {-->

<!--  data() {-->
<!--    return {-->
<!--      params:{-->
<!--        name:'',-->
<!--        pageNum:1,-->
<!--        pageSize:5,-->

<!--      },-->
<!--      tableData: [ ],-->
<!--      total:0,-->
<!--      dialogFormVisible:false,-->
<!--      form:{}-->
<!--    }-->
<!--  },-->
<!--  //页面加载的时候，再created中做一些事情-->
<!--  created() {-->
<!--    this. findBySearch();-->
<!--  },-->
<!--  //定义一些页面控件触发的事件调用方法-->
<!--  methods: {-->

<!--    findBySearch() {-->
<!--      request.get("/eenter/search", {-->
<!--        params: this.params-->
<!--      }).then(res => {-->
<!--        if (res.code === '0') {-->
<!--          this.tableData = res.data.list;-->
<!--          this.total = res.data.total;-->

<!--        } else {-->
<!--          this.$message({-->
<!--            message: res.msg,-->
<!--            type: 'success'-->
<!--          });-->
<!--        }-->
<!--      })-->

<!--    },-->
<!--    add() {-->
<!--      this.form = {};-->
<!--      this.dialogFormVisible = true;-->
<!--    },-->
<!--    edit(obj){-->
<!--      this.form = obj;-->
<!--      this.dialogFormVisible = true;-->
<!--    },-->
<!--    reset() {-->
<!--      this.params = {-->
<!--        pageNum: 1,-->
<!--        pageSize: 5,-->
<!--        name: '',-->
<!--        phone: ''-->
<!--      }-->
<!--      this.findBySearch();-->
<!--    },-->
<!--    handleSizeChange(pageSize) {-->
<!--      this.params.pageSize = pageSize;-->
<!--      this.findBySearch();-->
<!--    },-->
<!--    handleCurrentChange(pageNum) {-->
<!--      this.params.pageNum = pageNum;-->
<!--      this.findBySearch();-->
<!--    },-->
<!--    submit() {-->
<!--      request.post("/eenter", this.form).then(res => {-->
<!--        if (res.code === '0') {-->
<!--          this.$message({-->
<!--            message: '操作成功！',-->
<!--            type: 'success'-->
<!--          });-->
<!--          this.dialogFormVisible = false;-->
<!--          this.findBySearch();-->
<!--        } else {-->
<!--          this.$message({-->
<!--            message: res.msg,-->
<!--            type: 'error'-->
<!--          });-->
<!--        }-->

<!--      })-->
<!--    },-->
<!--    del(id){-->
<!--      request.delete("/eenter/" + id).then(res =>{-->
<!--        if (res.code === '0'){-->
<!--          this.$message({-->
<!--            message: '删除成功！',-->
<!--            type: 'success'-->
<!--          })-->
<!--          this.findBySearch();-->
<!--        }else{-->
<!--          this.$message({-->
<!--            message: res.msg,-->
<!--            type: 'success'-->
<!--          });-->
<!--        }-->
<!--      })-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->