<template>
  <div class="login-page  img3">
    <div class="box">
      <el-card>
        <!-- form 表单部分 -->
        <div class="title">Register</div>
        <el-row :gutter="35" style="margin-top: 10px;">
            <el-col :span='11'>
              <img src="https://img.zcool.cn/community/018a145e8be3b5a80120a89592780b.jpg@1280w_1l_2o_100sh.jpg" class="image">
            </el-col>
            <el-col :span='1'>
            <el-divider  direction="vertical"></el-divider>
            </el-col>

          <el-col :span="13">
            <el-form ref="form" :model="form" :rules="rules">
          <div class="usernameHZ PD">
            <!-- <div class="img2"><img src="" alt="" /></div> -->
            <el-form-item prop="username" label="用户名">
              <el-input
                placeholder="请输入用户名"
                v-model="form.username"
                style="width: 300px;"
              ></el-input>
            </el-form-item>
          </div>

          <div class="usernameHZ PD">
            <!-- <div class="img2"><img src="" alt="" /></div> -->
            <el-form-item prop="mobile" label="手机号">
              <el-input
                placeholder="请输入手机号"
                v-model="form.mobile"
                style="width: 300px;"
              ></el-input>
            </el-form-item>
          </div>

          <div class="usernameHZ">
            <!-- <div class="img2"><img src="" alt="" /></div> -->
            <el-form-item prop="password" label="密 码">
              <el-input
                placeholder="请输入密码"
                v-model="form.password"
                :type="inputType"
                show-password
                style="width: 300px;"
              ></el-input>
            </el-form-item>
          </div>

          <div class="usernameHZ">
            <!-- <div class="img2"><img src="" alt="" /></div> -->
            <el-form-item prop="confirmPassword" label="确认密码">
              <el-input
                placeholder="请确认密码"
                v-model="form.confirmPassword"
                :type="inputType"
                show-password
                style="width: 300px;"
              ></el-input>
            </el-form-item>
          </div>

          <el-form-item class="tc">
          </el-form-item>
        </el-form></el-col>
        </el-row>
        <el-row :gutter="16">
              <el-col :span="12">
                <el-button type="primary" @click="login">登录</el-button>
              </el-col>
              <el-col :span="12">
                <el-button type="primary" @click="submit">注册</el-button>

              </el-col>
            </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
import { registerApi } from '@/api/user'
// import axios from "axios";
export default {
  name: 'UserRegister',

  data () {
    return {
      form: {
        username: '',
        mobile: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { min: 5, max: 11, message: '用户名长度在5-11之间', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '手机号不能为空', trigger: 'blur' },
          { pattern: /^1\d{10}$/, message: '手机号格式错误', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 5, max: 11, message: '密码长度在5-11之间', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '确认密码不能为空', trigger: 'blur' },
          { validator: this.checkPassword, trigger: 'blur' }
        ]
      },
      inputType: 'password'
    }
  },

  methods: {
    checkPassword (rule, value, callback) {
      if (value !== this.form.password) {
        callback(new Error('两次密码输入不一致'))
      } else {
        callback()
      }
    },

    login () {
      this.$router.push('/login')
    },

    submit () {
      // 对整个表单校验
      this.$refs.form.validate(async valid => {
        if (valid) {
          console.log(valid)
          // 向后端发送注册请求
          try {
            const res = await registerApi({
              userPhoneNum: this.form.mobile,
              userPassword: this.form.password,
              userName: this.form.username
            })
            // 注册成功后的操作，例如跳转到登录页面
            if (res.code === 1) {
              console.log(res.msg)
              this.$message.success(res.msg)
              this.$router.push('/login')
            } else {
              console.log(res.msg)
              this.$message.error(res.msg)
            }
          } catch (error) {
            console.error(error)
            // 注册失败的操作
          }
        }
      })
    }
  },

  mounted () {
    // 初始化页面时的操作
  }
}
</script>

<style lang="scss">
/* 样式定义 */
::v-deep .el-divider--vertical{
  height:468px
}
.image{
  width: auto;
  height: 450px;
  //margin-left: 0px;
}
.login-page {
  min-height: 100vh;
  // flex 实现水平垂直居中
  .title{
    text-align: center;
    //font-size: 26px;
    //font-weight: bold;
    font: small-caps bold 26px/1 sans-serif;
  }
  .el-card {
    ::v-deep .el-card__body {
      position: absolute;
      width: 800px;
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
    padding: 20px 0 0 10px;
    width: 100%;
    .PD {
      ::v-deep .el-input__inner {
        padding-left: 37px;
      }
    }
    .usernameHZ {
      position: relative;
      width: 100%;
      ::v-deep .el-input__inner {
        padding-left: 37px;

        position: relative;
        width: 100%;
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

    /* ::v-deep .el-button {

    } */
  }
}

.img3 {
  // width: 100%;
  // height: 100%;
  // background-repeat: no-repeat;
  // background-size: cover;
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
  height: 51px;
}
</style>
