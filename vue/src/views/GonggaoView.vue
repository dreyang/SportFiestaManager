<template>
  <div>
  <div style="margin-bottom: 15px">
    <el-input v-model="params.name" style="width: 157px; font-size: 18px" placeholder="请输入分类名称"></el-input>

    <el-button type="warning" style="margin-left: 10px; font-size: 15px"@click="findBySearch()">查询</el-button>
    <el-button type="warning" style="margin-left: 10px; font-size: 15px"@click="reset()">清空</el-button>
    <el-button type="primary" style="margin-left: 10px; font-size: 15px" @click="add()" v-show="user.role ==='管理员'">新增</el-button>
    <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()">
      <el-button slot="reference" type="danger" style="margin-left: 5px; font-size: 15px" v-show="user.role ==='管理员'">批量删除</el-button>
    </el-popconfirm>
    <el-button type="success" style="margin-left: 10px; font-size: 15px" @click="exp()">批量导出</el-button>
    <el-upload
        action="http://localhost:8080/api/gonggao/upload" style="display: inline-block; margin-left: 10px" :show-file-list="false" :on-success="successUpload">
      <el-button size="small" type="primary"style="font-size: 15px" v-show="user.role ==='管理员'">批量导入</el-button>
    </el-upload>
  </div>
  <div>
  <el-table
      :data="tableData" style="width: 100%; font-size: 17px" ref="table" @selection-change="handleSelectionChange" :row-key="getRowKeys">
    <el-table-column ref="table" type="selection" width="55" align="center" :reserve-selection="true"></el-table-column>
    <el-table-column prop="name" label="分类名称" width="180"></el-table-column>
    <el-table-column prop="zhuyi" label="注意事项" width="180"></el-table-column>
    <el-table-column prop="xinxi" label="注释"></el-table-column>
    <el-table-column  label="操作" v-show="user.role ==='管理员'">
      <tempate slot-scope="scope">
        <el-button type="primary" @click="edit(scope.row)"style="font-size: 15px">编辑</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
          <el-button slot="reference" type="danger" style="margin-left: 5px;font-size: 15px">删除</el-button>
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
          :page-sizes="[5, 10, 15, 20]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="分类名称" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="注意事项" label-width="15%">
            <el-input v-model="form.zhuyi" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="注释" label-width="15%">
            <el-input v-model="form.xinxi" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>


    <div style="width:76%; margin-top: 20px">
      <div id="bie" style="margin: 0 auto;width: 100%; height: 500px;"></div>
    </div>


  </div>
</template>

<script>


import request from "@/utils/request";
import * as echarts from "echarts";

export default {

  data() {
    return {
     params:{
       name:'',

       pageNum:1,
       pageSize:5,

     },
      tableData: [ ],
      total:0,
      dialogFormVisible:false,
      form:{},
      multipleSelection: [],
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  //页面加载的时候，再created中做一些事情
   created() {
    this. findBySearch();
   },
  mounted() {
    this.initBie();
    this.initEcharts();
  },
  //定义一些页面控件触发的事件调用方法
  methods: {

    findBySearch() {
      request.get("/gonggao/search", {
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
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    edit(obj){
      this.form = obj;
      this.dialogFormVisible = true;
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',

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
      request.post("/gonggao", this.form).then(res => {
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
      request.delete("/gonggao/" + id).then(res =>{
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
    },
    delBatch(){
       if (this.multipleSelection.length === 0){
         this.$message.warning("请勾选您要删除的项")
         return
       }
       request.put("/gonggao/delBatch",this.multipleSelection).then(res => {
         if (res.code === '0'){
           this.$message.success("批量删除成功！")
           this.findBySearch()
         }else{
           this.$message.error(res.msg)
         }
       })

    },
   handleSelectionChange(val) {
     this.multipleSelection = val;
   },
    getRowKeys(row){
      return row.id;
    },
    exp(){
      let user = localStorage.getItem("user");
        location.href = 'http://localhost:8080/api/gonggao/export?token='+JSON.parse(user).token
    },
    successUpload(res){
      if (res.code === '0'){
        this.$message.success("这里导入成功")
        this.findBySearch()
      }else{
        this.$message.error(res.msg)
      }
    },

    initEcharts(){
      request.get("/bisai/echarts/bie").then(res =>{
        if (res.code === '0'){
          this.initBie(res.data)

        }else {
          this.$message.error(res.msg)
        }
      })

    },
    initBie(data){
      let chartDom = document.getElementById('bie');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '赛事分类统计图',
          textStyle:{
            color:'white',
            fontSize:40
          },
          subtext: '统计维度：赛事分类',
          subtextStyle:{
            color:'white',
            fontSize:20
          },
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          textStyle: {
            color: 'white', // 图例文字颜色
            fontSize: 20, // 图例文字大小
          },
        },
        series: [
          {
            name: '赛事分类',
            type: 'pie',
            radius: '55%',
            data:data,
            emphasis: {
              itemStyle: {
                shadowBlur: 100,
                shadowOffsetX: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
  }
}
</script>