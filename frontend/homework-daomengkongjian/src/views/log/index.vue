<template>
  <div class="weibo-list">
    <el-card
      class="weibo-card"
      v-for="(post, index) in weiboPosts"
      :key="post.id"
    >
      <div class="weibo-header">
        <span class="weibo-username">{{ post.time }}</span>
      </div>
      <div class="weibo-content">
        {{ post.content }}
      </div>
      <el-divider></el-divider>
      <div class="weibo-actions">
        <!-- <font-awesome-icon :icon="['far', 'heart']" style="margin-right: 12px" /> -->
        <!-- <font-awesome-icon icon="fa-solid fa-user-secret" /> -->
        <font-awesome-icon
          style="margin-right: 12px"
          :class="{ upActive: post.up }"
          :icon="post.up ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"
          @click="post.up = !post.up"
        />
        <font-awesome-icon
          icon="fa-regular fa-comment"
          style="margin-right: 12px"
          @click="openComment(post, index)"
        />
        <font-awesome-icon
          style="margin-right: 12px"
          :class="{ starActive: post.star }"
          :icon="post.star ? 'fa-solid fa-star' : 'fa-regular fa-star'"
          @click="post.star = !post.star"
        />

        <!-- <v-icon name="heart"  style="margin-right: 12px" :class="{'upActive':post.up}" @click="post.up=!post.up"/>
        <v-icon name="comments"  style="margin-right: 12px" />
        <v-icon name="fa-star"  :class="{'starActive':post.star}" @click="post.star=!post.star"/> -->
      </div>
      <el-collapse accordion v-if="post.comments && post.comments.length > 0">
        <el-collapse-item>
          <!-- <template slot="title">
            <div>
              <div>   {{ post.comments[0].content }}</div>
            <el-divider v-if="post.comments.length > 1"></el-divider>
          </div>

          </template>

          <template
            v-if="post.comments.length > 1"
            v-for="comment in post.comments.slice(1)"
          >
            <div>
              {{ comment.content }}
              <el-divider></el-divider>
            </div>
          </template> -->
          <template
            v-if="post.comments.length"
            v-for="comment in post.comments"
          >
            <div>
              <div class="comment-head weibo-header">
                <div class="ava-box">
                  <el-avatar
                    class="weibo-avatar"
                    :src="post.avatarUrl"
                    size="small"
                  ></el-avatar>
                  <span class="weibo-username">{{ comment.name }}</span>
                </div>
                <span>{{ comment.time }}</span>
              </div>
              <div>{{ comment.content }}</div>
              <el-divider></el-divider>
            </div>
          </template>
        </el-collapse-item>
      </el-collapse>
    </el-card>


   
  </div>
</template>

<script>
import dayjs from "dayjs"; // 导入日期js
export default {
  name:'log',
  data() {
    return {
   
      weiboPosts: [
        {
          id: 1,
          time:'2023-10-21 10:10:10',
          avatarUrl: "avatar1.jpg",
          username: "User1",
          content: "This is the content of the first post.",
          likes: 10,
          comments: 5,
          favorites: 3,
          up: false, //是否点赞
          comments: [
            { name: "z", content: "啊阿斯顿", time: "2023-10-12 10:00:00" },
            { name: "z", content: "啊阿斯顿123", time: "2023-10-12 10:00:00" },
          ], //评论

          star: false, //是否收藏
        },
        {
          id: 2,
          time:'2023-10-22 10:10:10',

          avatarUrl: "avatar2.jpg",
          username: "User2",
          content: "Another interesting post goes here.",
          likes: 15,
          comments: 8,
          favorites: 7,
          up: false, //是否点赞
          comments: [], //评论

          star: false, //是否收藏
        },
      ],
    
    };
  },
  methods: {
    handleLink(linknName){
      this.$router.push({
        name:linknName
      })
    },
 
    openComment(row, index) {
      this.actionPost.index = index;
      this.actionPost.post = row;

      this.dialogVisible = true;
    },

   
  },
};
</script>

<style scoped lang="scss">
.comment-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
::v-deep .el-icon-arrow-right:before {
  content: "展开";
}
::v-deep .el-collapse-item__arrow.is-active {
  transform: rotate(0deg);
  &::before {
    content: "收起";
  }
}
.upActive {
  color: red;
}
.starActive {
  color: red;
}
.weibo-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1000px;
  margin: 0 auto;
}

.weibo-card {
  margin-top: 30px;
  padding: 20px;
  width: 100%;
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

.weibo-actions {
  display: flex;
  justify-content: flex-end;
}

.weibo-add-button {
    display: none;
}


.ava-box{
  display: flex;
  align-items: center;
  font-size: 16px;
}
</style>
