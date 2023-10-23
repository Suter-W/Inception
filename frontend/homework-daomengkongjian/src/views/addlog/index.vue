<template>
  <div class="weibo">
    <!-- ... 其他微博内容 ... -->
    <div class="content">

      <el-card class="weibo-card weibo-post-card">
      <div class="weibo-header">
        <el-avatar class="weibo-avatar" :src="yourAvatarUrl" size="small"></el-avatar>
        <span class="weibo-username">Your Name</span>
        <div class="weibo-post-buttons">
          <el-button type="primary" size="mini" @click="submitNormalPost">正常发布</el-button>
          <el-button type="primary" size="mini" @click="submitAnonymousPost">匿名发布</el-button>
          <el-button type="primary" size="mini" @click="submitPrivatePost">私密发布</el-button>
        </div>
      </div>
      <div class="weibo-content">
        <el-input
          type="textarea"
          :autosize="{ minRows: 20}"
          placeholder="分享你的新鲜事..."
          v-model="postContent"
        ></el-input>
      </div>
    </el-card>
    </div>


    <!-- ... 其他微博内容 ... -->
  </div>
</template>

<script>
export default {
        name:'addlog',

  data() {
    return {
      postContent: "", // 用来存储发布内容
      // yourAvatarUrl: "your_avatar.jpg", // 设置用户头像的URL
    };
  },
  methods: {
    submitNormalPost() {
      // 正常发布逻辑，你可以在这里将 postContent 提交到后端或执行其他操作
      this.$router.push({
        name:'home'
      })
      this.clearPostForm();
    },
    submitAnonymousPost() {
      // 匿名发布逻辑
      // 可以设置发布标志为匿名，并提交内容
      this.$router.push({
        name:'home'
      })
      this.clearPostForm();
    },
    submitPrivatePost() {
      // 私密发布逻辑
      // 可以设置发布标志为私密，并提交内容
      this.$router.push({
        name:'home'
      })
      this.clearPostForm();
    },
    clearPostForm() {
      // 清空发布表单内容
      this.postContent = "";
    },
  },
};
</script>

<style scoped>
.weibo{
  width: 1000px;
  margin: 30px auto;
}
.weibo-post-card {
  width: 100%; /* 与其他微博卡片宽度相同 */
  padding: 20px;
  /* 添加其他样式 */
}
.weibo-header {
  display: flex;
  align-items: center;
}
.weibo-avatar {
  margin-right: 10px;
}

.weibo-username {
  font-weight: bold;
}

.weibo-content {
  padding: 20px 0;
}
.weibo-post-buttons {
  margin-left: auto; /* 将按钮移到右侧 */
  display: flex;
  align-items: center;
}
.weibo-post-buttons el-button {
  margin-left: 10px;
}
</style>
