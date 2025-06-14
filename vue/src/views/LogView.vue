<template>
  <div>
  <div style="margin-bottom: 18px">
    <el-input v-model="params.name" style="width: 160px;  font-size: 18px" placeholder="请输入操作内容"></el-input>
    <el-input v-model="params.username" style="width: 140px ;margin-left:5px;  font-size: 18px "placeholder="请输入操作人"></el-input>
    <el-button type="warning" style="margin-left: 10px;  font-size: 16px"@click="findBySearch()">查询</el-button>
    <el-button type="warning" style="margin-left: 10px;  font-size: 16px"@click="reset()">清空</el-button>
  </div>

  <div>
  <el-table
      :data="tableData" style="width: 100%;  font-size: 18px">
    <el-table-column prop="name" label="操作内容" width="280"></el-table-column>
    <el-table-column prop="time" label="操作时间" width="280"></el-table-column>
    <el-table-column prop="username" label="操作人" width="180"></el-table-column>
    <el-table-column prop="ip" label="ip" width="200"></el-table-column>
    <el-table-column  label="操作">
      <tempate slot-scope="scope">
        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
          <el-button slot="reference" type="danger" style="margin-left: 5px;  font-size: 18px">删除</el-button>
        </el-popconfirm>
      </tempate>
    </el-table-column>
  </el-table>
  </div>
    <div style="margin-top: 10px">
      <span class="demonstration"></span>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <div>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {

  data() {
    return {
     params:{
       name:'',
       username:'',
       pageNum:1,
       pageSize:10,

     },
      tableData: [ ],
      total:0,
      dialogFormVisible:false,
      form:{}
    }
  },
  //页面加载的时候，再created中做一些事情
   created() {
    this. findBySearch();
   },
  //定义一些页面控件触发的事件调用方法
  methods: {

    findBySearch() {
      request.get("/log/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;

        } else {
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }
      })

    },

    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',
        username:'',
      }
      this.findBySearch();
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    submit() {
      request.post("/log", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }

      })
    },
    del(id){
      request.delete("/log/" + id).then(res =>{
        if (res.code === '0'){
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
          this.findBySearch();
        }else{
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }
      })
    }
  }
}
</script>