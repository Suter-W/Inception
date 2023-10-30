<template>
  <div class="content">
    <div class="mine">
      <h2 class="section-title">账号设置</h2>

      <div class="setting-item">
        <h3>头像设置</h3>
        <input type="file" @change="handleAvatarChange" accept="image/*" class="el-input">
        <img :src="avatarUrl" alt="Avatar" class="avatar-preview">
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
export default {
  data() {
    return {
      avatarUrl: '',
      nickname: '',
      bio: ''
    };
  },
  methods: {
    handleAvatarChange(event) {
      const file = event.target.files[0];
      this.avatarUrl = URL.createObjectURL(file);
    },
    saveSettings() {
      console.log('保存设置');
      console.log('昵称:', this.nickname);
      console.log('头像URL:', this.avatarUrl);
      console.log('个性签名:', this.bio);
    }
  }
};
</script>

<style scoped lang="scss">

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
.el-button--default{
  
  span {
    color: #000;
  }
}
</style>


