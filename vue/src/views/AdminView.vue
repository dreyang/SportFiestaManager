<template>
  <div>
  <div style="margin-bottom: 30px">
    <el-input v-model="params.name" style="width: 120px ;font-size: 18px" placeholder="请输入姓名"></el-input>
    <el-input v-model="params.phone" style="width: 180px;margin-left: 5px ; font-size: 18px" placeholder="请输入电话"></el-input>
    <el-button type="warning" style="margin-left: 10px;  font-size: 15px"@click="findBySearch()">查询</el-button>
    <el-button type="warning" style="margin-left: 10px;  font-size: 15px"@click="reset()">清空</el-button>
    <el-button type="primary" style="margin-left: 10px;  font-size: 15px" @click="add()">新增</el-button>
  </div>

  <div>
  <el-table
      :data="tableData" style="width: 100%;height: 300px;  font-size:15px">
    <el-table-column prop="name" label="姓名" width="180"></el-table-column>
    <el-table-column prop="sex" label="性别" width="180"></el-table-column>
    <el-table-column prop="age" label="年龄"></el-table-column>
    <el-table-column prop="phone" label="电话" width="180"></el-table-column>
    <el-table-column prop="role" label="角色"></el-table-column>
    <el-table-column  label="操作">
      <tempate slot-scope="scope">
        <el-button type="primary" style="font-size: 15px" @click="edit(scope.row)">编辑</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
          <el-button slot="reference" type="danger" style="margin-left: 5px ;font-size: 15px">删除</el-button>
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
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="性别" label-width="15%">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="年龄" label-width="15%">
            <el-input v-model="form.age" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="电话" label-width="15%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="角色" label-width="15%">
            <el-select v-model="form.role" placeholder="请选择">
              <el-option label="裁判" value="裁判"></el-option>
                <el-option label="参赛人员" value="参赛人员"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div style="margin: 0 auto; width:80%; margin-top: 40px">
      <div id="bar" style="width: 100%; height: 400px;"></div>
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
       phone:'',
       pageNum:1,
       pageSize:5,

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
  mounted() {
    this.initBar();
  },
  //定义一些页面控件触发的事件调用方法
  methods: {

    findBySearch() {
      request.get("/admin/search", {
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
        phone: ''
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
      request.post("/admin", this.form).then(res => {
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
      request.delete("/admin/" + id).then(res =>{
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

    initBar(){
      let chartDom = document.getElementById('bar');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '用户类型统计图',
          textStyle:{
            color:'white',
            fontSize:40
          },
          subtext: '统计维度：用户类型分类（人数）',
          subtextStyle:{
            color:'white',
            fontSize:20
          },
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: ['管理员', '教师组', '学生组', '裁判'],
          axisLabel: {
            color: '#ffffff', // x 轴刻度标签颜色
            fontSize: 20, // x 轴刻度标签字体大小
          },
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            color: '#ffffff', // y 轴刻度标签颜色
            fontSize: 20, // y 轴刻度标签字体大小
          },
        },
        series: [
          {
            data: [15, 22, 35, 18],
            type: 'bar'
          }
        ]
      };

      option && myChart.setOption(option);

    }



  }
}
</script>

