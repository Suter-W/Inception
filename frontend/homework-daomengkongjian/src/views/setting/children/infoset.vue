<template>
  <div class="content">
    <div class="mine">
      <h2 class="section-title">账号设置</h2>

      <div class="setting-item">
        <h3>头像设置</h3>
        <input type="file" @change="handleAvatarChange" accept="image/*" class="el-input">
        <!-- <img :src="avatarUrl" alt="Avatar" class="avatar-preview"> -->
      </div>

      <div class="setting-item">
        <h3>昵称设置</h3>
        <input type="text" v-model="nickname" class="el-input">
      </div>

      <div class="setting-item">
        <h3>个性签名设置</h3>
        <textarea v-model="bio" class="el-textarea"></textarea>
      </div>

      <el-button @click="saveSettings" type="primary">保存设置</el-button>
    </div>
  </div>
</template>

<script>
import { getUserInfoApi,updateAvatarApi } from '@/api/user'
import { mapState } from 'vuex'

export default {

  data () {
    return {
      userInfo: {},
      avatarUrl: '',
      nickname: '',
      bio: ''
    }
  },

  computed: {
    ...mapState('user', ['token'])
  },

  created () {
    // console.log(this.token)
    this.getUserInfo()
  },

  methods: {
    // 获取用户信息
    async getUserInfo () {
      try {
        this.userInfo = await getUserInfoApi({
          id: this.token
        })
        this.nickname = this.userInfo.data.userName
        this.avatarUrl = this.userInfo.data.userAvatar,
        this.bio = this.userInfo.data.userSignature
      } catch (e) {}
    },

    handleAvatarChange (event) {
      const file = event.target.files[0]
      this.avatarUrl = URL.createObjectURL(file)
    },
    
    // 保存用户信息修改(后端待完善)
    async saveSettings () {
      // console.log('保存设置')
      // console.log('昵称:', this.nickname)
      // console.log('头像URL:', this.avatarUrl)
      // console.log('个性签名:', this.bio)
      // const res = await publishCommentApi({
      //     dreamID: this.actionPost.dreamId,
      //     userID: this.token,
      //     commentContent: this.formData.comment
      //   })
      //   if (res.code === 1) {
      //     // this.actionPost.post  当前操作的微博项
      //     this.weiboPosts[this.actionPost.index].comments.push({
      //       name: username,
      //       content: this.formData.comment,
      //       time: dayjs().format('YYYY-MM-DD HH:mm:ss')
      //     })
      //     this.handleClose()
      //   }
      try{
        const avatarResponse = await fetch(this.avatarUrl);
        const avatarBlob = await avatarResponse.blob();
        const formData = new FormData();
        formData.append("file",avatarBlob);
        formData.append("userName",this.nickname);
        formData.append("userSignature",this.bio);
        formData.append("userID",this.token);
        const res = await updateAvatarApi(formData)
        // {
        //   file:"D:/Y7000P/DreamRecord/Inception/frontend/homework-daomengkongjian/src/assets/default/defaultAvatar.jpg",
        //   userID: this.token
        // }
        console.log(this.avatarUrl);
        console.log(avatarBlob);
        if(res.code === 1){
          this.$message.success("修改成功！")
          this.$emit('avatar-change',5);
        }
      }catch(e){

      }
    }
  }
}
</script>

<style scoped lang="scss">
.content {
  flex: 1;
  padding:40px;
  // background-color: #ecf5ff;
  //background: url('../../src/assets/background.jpg');
}

.mine {
  padding: 30px;
  width: 80%;
  margin: 0 auto;
  border: 1px solid #eee;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  opacity: 0.9;
}
.mine1 {
  padding: 30px;
  width: 80%;
  margin: 0 auto;
  border: 1px solid #eee;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  opacity: 0.9;
}
.section-title {
  font-size: 28px;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.setting-item {
  margin-bottom: 25px;
  h3 {
    font-size: 20px;
    margin-bottom: 10px;
    color: #444;
  }
}

input[type="file"],
input[type="text"],
textarea {
  display: block;
  width: 100%;
  margin-top: 8px;
  padding: 10px;
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 5px;
}

textarea {
  height: 150px;
}
.el-button{
  width: 100%;
  height: 52px;
  padding: 10px 20px;
  box-shadow: 0 2px 9px 1px #443B77;
  border-radius: 8px;
  border: 0px;
  span {
    font-size: 16px;
    color: #fff;
  }
}
.el-buttons{
  width: 100%;
  height: 52px;
  padding: 10px 20px;
  box-shadow: 0 2px 9px 1px #443B77;
  border-radius: 8px;
  border: 0px;
  span {
    font-size: 16px;
    color: #fff;
  }
}
.el-button--default{
  
  span {
    color: #000;
  }
}
</style>


