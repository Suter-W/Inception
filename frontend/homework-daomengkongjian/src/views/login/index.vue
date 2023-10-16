<template>
  <div class="login-page img3">
    <div class="box">
      <el-card class="card">
        <!-- form 表单部分 -->
        <div class="title">Welcome To</div>
        <div class="title">Inception Space</div>

        <el-form ref="form" :model="form" :rules="rules">
          <div class="usernameHZ PD">
            <!-- <div class="img2"><img src="../../assets/loinSJ.png" alt="" /></div> -->
            <el-form-item prop="loginPhoneNum" label="手机号">
              <!-- <svg-icon icon-class="user" /> -->
              <el-input
                placeholder="请输入手机号"
                v-model="form.loginPhoneNum"
                style="width: 420px;"
              ></el-input>
            </el-form-item>
          </div>

          <div class="usernameHZ PD">
            <!-- <div class="img2"><img src="../../assets/loginS.png" alt="" /></div> -->
            <el-form-item prop="password" label="密码">
              <el-input
                placeholder="请输入密码"
                v-model="form.password"
                :type="inputType"
                show-password
                style="width: 420px;"
              ></el-input>
            </el-form-item>

          </div>

          <div class="usernameHZ">
            <!-- <div class="img2"><img src="../../assets/loginS.png" alt="" /></div> -->
            <el-form-item prop="code" label="验证码">
              <el-input
                placeholder="请输入验证码"
                v-model="form.code"
                ref="code"
                style="width: 320px;"
              ></el-input>
            </el-form-item>
            <img :src="imgurl" alt="" class="img11" @click="imgyzm" />
            <!-- <img :src=" 'https://likede2-java.itheima.net/api/user-service/user/imageCode/' + from.clientToken " alt="" @click="createRandom"> -->
          </div>

          <el-form-item class="tc">
            <el-row :gutter="15">
              <el-col :span="12">
                <el-button type="primary" @click="register">注册</el-button>
              </el-col>
              <el-col :span="12">
                <el-button type="primary" @click="logintest">登录</el-button>
                <!-- @click="submit"-->
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
// 调整第三方组件的样式
// 1. 直接给组件直接加一个类名 ===> 自动加在在组件的根元素上
// 定制化某一个组件（card）的样式 不会影响其他的（card）组件
// 额外加一个样式

// 2. 利用组件原有的类名+scoped
// 利用组件原有的类名 ==>  一改全改 + scoped + 穿透选择器 ::v-deep (scss)   /deep/ less
// 注意点
// scoped的作用，可以让样式在当前组件起作用（局部样式）
// 默认scoped样式，不会向下渗透，只会影响到子组件的根元素，再深就不行了
// import imgurl from '../../assets/login.png'
import { loginApi } from '@/api/user'
import axios from 'axios'
export default {
  name: 'UserLogin',

  data () {
    return {
      form: {
        loginPhoneNum: '',
        password: '',
        code: '',
        loginType: 0,
        clientToken: ''
      },
      imgurl: '',

      rules: {
        loginPhoneNum: [
          { required: true, message: '手机号 必填项', trigger: 'blur' },
          { min: 11, max: 11, message: '手机号格式错误', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码 必填项', trigger: 'blur' },
          { pattern: /^\w{5,10}$/, message: '密码 格式错误', trigger: 'blur' }
          // { min: 5, max: 11, message: '手机号长度在5-11', trigger: 'blur' }
        ],
        code: [{ required: true, message: '验证码 必填项', trigger: 'blur' }]
      },
      inputType: 'password'
    }
  },

  mounted () {
    this.imgyzm()
  },

  methods: {
    logintest(){
    	this.$router.push('/home')
    },
    register () {
      this.$router.push('/register')
    },
    submit () {
      // 对整个 表单校验
      // form 组件的validate
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          console.log(valid)
          // 向后端 发登录请求  ==> 需要登录请求 ==> 封装/引入/使用
          try {
            // this.loading = true
            const res = await loginApi({
              userPhoneNum: this.form.loginPhoneNum,
              userPassword: this.form.password
            })

            if (res.code === 1) {
              // commit
              this.$store.commit('user/updateToken', '123213')
              // mapMutations('模块名称-user', ['updateToken'])
              this.$router.push('/home')
            } else {
              this.imgyzm()
              this.$message.error(res.msg)
            }
          } finally {
            // this.loading = false
            console.log(1)
          }
          // el-button loading 属性
          // element-ui ===> 提供的自定义指令 v-loading
          // 拿到token ==> 存在Vuex里
          // 去首页
        }
      })
    },

    async imgyzm () {
      const random = Math.floor(Math.random() * 100)
      const res = await axios({
        url: `https://likede2-admin.itheima.net/likede/api/user-service/user/imageCode/${random}`,
        responseType: 'blob'
      })
      this.imgurl = res.config.url
      this.form.clientToken = random
    },

    createRandom () {
      this.form.clientToken = nanoid()
    }
  }
  // 点击按钮，实现登录
  // 校验通过（form 组件的validate）之后 在向后端 发请求
  // image.png
}
</script>

<style lang="scss">
.card{
  width: 500px;
}
.login-page {
  min-height: 100vh;
  // flex 实现水平垂直居中
  .title{
    text-align: center;
    //font-size: 26px;
    //font-weight: bold;
    //font-family: JetBrains Mono Medium;
    //color: #ffffff;
    font: small-caps bold 26px/1 sans-serif;
  }
  .el-card {
    ::v-deep .el-card__body {
      position: absolute;
      width: 518px;
      height: auto;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);

      .img1 {
        position: absolute;
        left: 50%;
        margin-left: -48px;
        top: -46px;

        img {
          width: 96px;
          height: 96px;
        }
      }
    }
  }
  .el-form {
    padding: 10px 0 0 10px;
    .PD {
      ::v-deep .el-input__inner {
        padding-left: 37px;
      }
    }
    .usernameHZ {
      display: flex;
      position: relative;

      ::v-deep .el-input__inner {
        padding-left: 37px;
        //padding-right: 40px;
        position: relative;
        width: 500px;
        height: 52px;
        border-radius: 0px;
        background-color: #fff;
        color: #999;
        caret-color: #999;
      }
      .img2 {
        position: absolute;
        top: 17px;
        left: 12px;
        z-index: 1111111;
        width: 12px;
        height: 12px;
        border-radius: 8px 0 0 8px;
        img {
          width: 15px;
          height: 15px;
        }
      }
      .box1 {
        position: absolute;
        top: 5px;
        right: 15px;
        width: 20px;
        height: 50px;
        line-height: 53px;
        img {
          cursor: pointer;
        }
      }
      .box2 {
        position: absolute;
        top: 1px;
        right: 119px;
      }
    }

    .el-CheckBox {
      display: flex;
      width: 85px;
      height: 19.5px;
      cursor: pointer;
      margin: 0px 0px 20px 25px;
      input {
        border: 1px solid red;
      }
      span {
        margin-left: 5px;
        width: 66px;
        height: 19.5px;
        font-size: 14px;
      }
    }
  }
  .tc {
    text-align: center;
    margin-top: 10px;

    /* ::v-deep .el-button {

    } */
  }
}

.img3 {
  //width: 100%;
  //height: 100%;
  //background-repeat: no-repeat;
  //background-size: cover;
  //background: url('/src/assets/background.jpg');
  min-height: 100%;width: 100%;
  font-family: JetBrains Mono Medium;
  display: flex;
  align-items: center;
  justify-content: center;
  /* background-color: #0e92b3; */
  background: url('/src/assets/background.jpg');
  background-size: cover;
  position: fixed;
}

.svg-container {
  padding: 6px 5px 6px 15px;
  vertical-align: middle;
  width: 30px;
  display: inline-block;
}

.img11 {
  position: absolute;
  right: 7px;
  width: 102px;
  height: 54px;
    top: 39px;
}
</style>
