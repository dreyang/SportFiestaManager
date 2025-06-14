<template>
  <div class="register-container">
    <div style="width: 400px; height: 400px; margin: 150px auto; background-color:rgba(107,149,224,0.5); border-radius: 10px">
      <div style="width: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #4a5ed0">欢迎注册</div>
      <div style="margin-top: 25px; text-align: center; height: 320px;">
        <el-form :model="admin">
          <el-form-item>
            <el-input v-model="admin.name" prefix-icon="el-icon-user" style="width: 80%" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="admin.password" show-password prefix-icon="el-icon-lock" style="width: 80%" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="admin.role" placeholder="请选择" style="width: 80%">
              <el-option label="裁判" value="裁判"></el-option>
              <el-option label="教师组" value="教师组"></el-option>
              <el-option label="学生组" value="学生组"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 80%; margin-top: 10px" type="primary" @click="register()">注册</el-button>
          </el-form-item>
          <div style="text-align: center">
           已有账号？<a href="javascript:void(0)" style="text-decoration: none" @click="navLogin">点击登录</a>
          </div>
        </el-form>

      </div>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name:"Register",

  data() {
    return {
      admin:{}
    }
  },
  //页面加载的时候，再created中做一些事情
   created() {

   },
  //定义一些页面控件触发的事件调用方法
  methods: {
    register(){

      request.post("/admin/register",this.admin).then(res =>{
        if (res.code ==='0'){
          this.$message({
            message: '注册成功！',
            type: 'success'
          });
          //注册成功后跳转到登录页面
          this.$router.push("/login");
        }else{
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    navLogin(){
      this.$router.push('/Login')
    }
  }
}
</script>
<style scoped>
.register-container{
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/111.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

}
</style>
