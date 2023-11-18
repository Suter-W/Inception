<template>
  <div class="content">
    <div class="mine">
      <h2 class="section-title">账号设置</h2>

      <div class="setting-item">
        <h3>头像设置</h3>
        <!-- <myUpload field="img"
		        :width="300"
		        :height="300"
		        url="/upload"
		        :params="params"
		        :headers="headers"
		        :value.sync="show"
		        img-format="png"></myUpload> -->
        <!-- <input type="file" @change="handleAvatarChange" accept="image/*" class="el-input"> -->
        <!-- <a class="btn" @click="handleAvatarChange">设置头像</a> -->
	        <!-- <my-upload ></my-upload> -->
	      <!-- <img :src="imgDataUrl"> -->
        <input type="file" @change="handleAvatarChange" accept="image/*" v-if="exitInfoOrSaveInfo === '保存设置'">
        <!-- <img :src="avatarUrl" alt="Avatar" class="avatar" v-if="exitInfoOrSaveInfo === '编辑个人信息'"> -->
        <el-avatar
          class="weibo-avatar"
          :src="avatarUrl"
          size="large"
          v-if="exitInfoOrSaveInfo === '编辑个人信息'"
        ></el-avatar>
        <!-- <input type="file" @change="handleAvatarChange" accept="image/*"> -->
        <!-- <img :src="avatarUrl" alt="Avatar" class="avatar-preview"> -->
      </div>

      <div class="setting-item">
        <h3>
          <span>昵称设置</span>
          <span>
            <el-button 
              icon="el-icon-star-off" 
              circle 
              class="save-button"
              @click="saveUserName">
            </el-button>
          </span>
        </h3>
        <input type="text" v-model="nickname" class="el-input">
      </div>

      <div class="setting-item">
        <h3>          
          <span>个性签名设置</span>
          <span>
            <el-button 
              icon="el-icon-star-off" 
              circle
              class="save-button"
              @click="saveUserSignature">
            </el-button>
          </span>
          </h3>
            <textarea v-model="bio" class="el-textarea"></textarea>
      </div>

      <el-button @click="saveSettings" type="primary">{{ exitInfoOrSaveInfo }}</el-button>
    </div>
  </div>
</template>

<script>
import { getUserInfoApi,updateAvatarApi, updateNameApi,updateSignatureApi } from '@/api/user'
import { mapState } from 'vuex'
// import 'babel-polyfill'; // es6 shim


export default {

  data () {
    return {
      userInfo: {},
      avatarUrl: '',
      nickname: '',
      bio: '',
      exitInfoOrSaveInfo: '编辑个人信息'
    }
  },

  computed: {
    ...mapState('user', ['token']),
		// 'my-upload': myUpload
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
    // handleAvatarChange(event) {
    //   const file = event.target.files[0];
    //   this.resizeImage(file,72, 72) // 调整图片大小，确保长和宽均至少为72
    //   .then(resizedFile => {
    //     this.avatarUrl = URL.createObjectURL(resizedFile);
    //   });
    // },
    // resizeImage(file, minWidth, minHeight) {
    //   return new Promise((resolve) => {
    //     const img = document.createElement('img');
    //     const canvas = document.createElement('canvas');
    //     const ctx = canvas.getContext('2d');
    //     img.onload = () => {
    //       let width = img.width;
    //       let height = img.height;
    //       if (width < minWidth && height < minHeight) {
    //         const ratio = Math.max(minWidth / width, minHeight / height);
    //         width *= ratio;
    //         height *= ratio;
    //       }
    //     canvas.width = width;
    //     canvas.height = height;
    //     ctx.drawImage(img, 0, 0, width, height);
    //     canvas.toBlob((blob) => {
    //       resolve(new File([blob], file.name, { type: file.type }));
    //     }, file.type);
    //   };
    //   const reader = new FileReader();
    //   reader.onload = (e) => {
    //     img.src = e.target.result;
    //   };
    //   reader.readAsDataURL(file);
    //   });
    // },
    // resizeImage(file, width, height) {
    //   return new Promise((resolve) => {
    //     const img = document.createElement('img');
    //     const canvas = document.createElement('canvas');
    //     const ctx = canvas.getContext('2d');

    //     img.onload = () => {
    //       canvas.width = width;
    //       canvas.height = height;
    //       ctx.drawImage(img, 0, 0, width, height);
    //       canvas.toBlob((blob) => {
    //         resolve(new File([blob], file.name, { type: file.type }));
    //       }, file.type);
    //     };
    //     const reader = new FileReader();
    //     reader.onload = (e) => {
    //       img.src = e.target.result;
    //     };
    //     reader.readAsDataURL(file);
    //   });
    // },

    // handleAvatarChange(event) {
    //   const file = event.target.files[0];
    //   this.resizeImage(file, 72, 72) // 调整图片大小为 72x72
    //   .then(resizedFile => {
    //     this.avatarUrl = URL.createObjectURL(resizedFile);
    //   });
    // },
    // handleAvatarChange (event) {
    //   // const file = event.target.files[0]
    //   // this.avatarUrl = URL.createObjectURL(file)
    //   const file = event.target.files[0];
    //   this.resizeImage(file, 72, 72) // 调整图片大小为 300x300
    //   .then(resizedFile => {
    //     this.avatarUrl = URL.createObjectURL(resizedFile);
    //   });
    // },
    handleAvatarChange(event) {
      const file = event.target.files[0];
      this.resizeImage(file, 80, 80) // 调整图片大小为 80 × 80
      .then(resizedFile => {
        this.avatarUrl = URL.createObjectURL(resizedFile);
      });
    },
    resizeImage(file, width, height) {
      return new Promise((resolve) => {
        const img = document.createElement('img');
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        img.onload = () => {
          canvas.width = width;
          canvas.height = height;
          ctx.drawImage(img, 0, 0, width, height);
          canvas.toBlob((blob) => {
            resolve(new File([blob], file.name, { type: file.type }));
          }, file.type);
        };
        const reader = new FileReader();
        reader.onload = (e) => {
          img.src = e.target.result;
        };
        reader.readAsDataURL(file);
      });
    },
    // handleAvatarChange(event) {
    //   const file = event.target.files[0];
    //   this.resizeImage(file,72, 72) // 调整图片大小，确保长和宽均至少为72
    //   .then(resizedFile => {
    //     this.avatarUrl = URL.createObjectURL(resizedFile);
    //   });
    // },
    // resizeImage(file, minWidth, minHeight) {
    //   return new Promise((resolve) => {
    //     const img = document.createElement('img');
    //     const canvas = document.createElement('canvas');
    //     const ctx = canvas.getContext('2d');
    //     img.onload = () => {
    //       let width = img.width;
    //       let height = img.height;
    //       if (width < minWidth && height < minHeight) {
    //         const ratio = Math.max(minWidth / width, minHeight / height);
    //         width *= ratio;
    //         height *= ratio;
    //       }
    //     canvas.width = width;
    //     canvas.height = height;
    //     ctx.drawImage(img, 0, 0, width, height);
    //     canvas.toBlob((blob) => {
    //       resolve(new File([blob], file.name, { type: file.type }));
    //     }, file.type);
    //   };
    //   const reader = new FileReader();
    //   reader.onload = (e) => {
    //     img.src = e.target.result;
    //   };
    //   reader.readAsDataURL(file);
    //   });
    // },
    // resizeImage(file, width, height) {
    //   return new Promise((resolve) => {
    //     const img = document.createElement('img');
    //     const canvas = document.createElement('canvas');
    //     const ctx = canvas.getContext('2d');

    //     img.onload = () => {
    //       canvas.width = width;
    //       canvas.height = height;
    //       ctx.drawImage(img, 0, 0, width, height);
    //       canvas.toBlob((blob) => {
    //         resolve(new File([blob], file.name, { type: file.type }));
    //       }, file.type);
    //     };
    //     const reader = new FileReader();
    //     reader.onload = (e) => {
    //       img.src = e.target.result;
    //     };
    //     reader.readAsDataURL(file);
    //   });
    // },

    // handleAvatarChange(event) {
    //   const file = event.target.files[0];
    //   this.resizeImage(file, 72, 72) // 调整图片大小为 72x72
    //   .then(resizedFile => {
    //     this.avatarUrl = URL.createObjectURL(resizedFile);
    //   });
    // },
    // handleAvatarChange (event) {
    //   // const file = event.target.files[0]
    //   // this.avatarUrl = URL.createObjectURL(file)
    //   const file = event.target.files[0];
    //   this.resizeImage(file, 72, 72) // 调整图片大小为 300x300
    //   .then(resizedFile => {
    //     this.avatarUrl = URL.createObjectURL(resizedFile);
    //   });
    // },
    
    // 保存用户信息修改(后端待完善)
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

    async saveUserName(){
      try {
        const formData = new FormData();
        formData.append("userName",this.nickname);
        formData.append("userID",this.token);
        // alert("Hello!");
        const res = await updateNameApi(formData)
        if (res.code === 1) {
          this.$message.success("昵称修改成功！")
          // // this.actionPost.post  当前操作的微博项
          // this.weiboPosts[this.actionPost.index].comments.push({
          //   name: username,
          //   avatarUrl: userAvatar,
          //   content: this.formData.comment,
          //   time: dayjs().format('YYYY-MM-DD HH:mm:ss')
          // })
          // this.handleClose()
        }
      } catch (e) {
      }
    },
    async saveUserSignature(){
      try {
        const formData = new FormData();
        formData.append("userSignature",this.bio);
        formData.append("userID",this.token);
        const res = await updateSignatureApi(formData)
        if (res.code === 1) {
          this.$message.success("个性签名修改成功！")
          // // this.actionPost.post  当前操作的微博项
          // this.weiboPosts[this.actionPost.index].comments.push({
          //   name: username,
          //   avatarUrl: userAvatar,
          //   content: this.formData.comment,
          //   time: dayjs().format('YYYY-MM-DD HH:mm:ss')
          // })
          // this.handleClose()
        }
      } catch (e) {
      }
    },

    async saveSettings () {
      try{
        if(this.exitInfoOrSaveInfo === '编辑个人信息'){
          this.exitInfoOrSaveInfo = "保存设置"
          return;
        }
        const avatarResponse = await fetch(this.avatarUrl);
        const avatarBlob = await avatarResponse.blob();
        // this.avatarUrl = avatarBlob
        const formData = new FormData();
        // formData.append("file",avatarBlob);
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
          this.exitInfoOrSaveInfo = "编辑个人信息"
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
    display: flex;
    justify-content: space-between;
    // position: relative;
    span{
      .save-button{
        // position: absolute;
        // right: 20px;
        padding: 0;
        text-align: center;
        display: inline-block;
        width: 40px;
        height: 40px;
        border-radius: 20px;
        font-size: 16px;
        // background-color: #409EFF;
        background-color: #409EFF;
        box-shadow: none;
        color: white;
        // margin-left: 10px;
    }
    }

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
  height: 120px;
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


