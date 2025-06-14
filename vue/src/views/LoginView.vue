<template>
  <div class="login-container">
    <div style="width: 400px; height: 400px; margin: 150px auto; background-color:rgb(227,226,145); border-radius: 10px">
      <div style="width: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #de102f">欢迎登录</div>
      <div style="margin-top: 25px; text-align: center; height: 320px;">
        <el-form :model="admin">
          <el-form-item>
            <el-input v-model="admin.name" prefix-icon="el-icon-user" style="width: 80%" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="admin.password" show-password prefix-icon="el-icon-lock" style="width: 80%" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <div style="display: flex; justify-content: center">
              <el-input v-model="admin.verCode" prefix-icon="el-icon-user" style="width: 42%; margin-right: 10px" placeholder="请输入验证码"></el-input>
              <img :src="captchaUrl" @click="clickImg()"  width="140px" height="33px" />
            </div>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 80%; margin-top: 10px" type="primary" @click="login()">登录</el-button>
          </el-form-item>
        </el-form>
        <div style="text-align: center">
          未有账号？<a href="javascript:void(0)" style="text-decoration: none" @click="navRegister">点击注册</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name:"Login",

  data() {
    return {
      admin:{},
      key:'',
      captchaUrl:'',
    }
  },
  //页面加载的时候，再created中做一些事情
   mounted() {
        this.key = Math.random();
        this.captchaUrl = 'http://localhost:8080/api/captcha?key=' +this.key
   },
  //定义一些页面控件触发的事件调用方法
  methods: {
    login(){
      request.post("/admin/login?key=" +this.key,this.admin).then(res =>{
        if (res.code ==='0'){
          this.$message({
            message: '登录成功！',
            type: 'success'
          });
          //将登录时的信息传输到系统首页
          localStorage.setItem("user",JSON.stringify(res.data));
          //成功后就跳转到首页，我定义的首页的路由是"/"
          this.$router.push("/");
        }else{
          this.$message.error(res.msg);
          this.key = Math.random();
          this.captchaUrl = 'http://localhost:8080/api/captcha?key=' +this.key
          this.admin.verCode = ''
        }
      })
    },
    clickImg(){
      this.key = Math.random();
      this.captchaUrl = 'http://localhost:8080/api/captcha?key=' +this.key
    },
    navRegister(){
      this.$router.push("/register")
    }
  }
}
</script>
<style scoped>
.login-container{
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/login_bg.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

}
</style>


