<template>
  <div class="weibo-list">
    <el-card
      class="weibo-card"
      v-for="(post, index) in weiboPosts"
      :key="post.id"
    >
      <div class="weibo-header">
        <el-avatar
          class="weibo-avatar"
          :src="post.avatarUrl"
          size="small"
        ></el-avatar>
        <span class="weibo-username">{{ post.username }}</span>
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

    <el-button
      class="weibo-add-button"
      type="text"
      icon="el-icon-edit"
      @click="showMoreOptions"
    >
    </el-button>
    <div class="weibo-more-options" v-show="showOptions">
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="我的收藏"
          placement="left"
        >
          <el-button type="text" icon="el-icon-star-off"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip class="item" effect="light" content="写梦" placement="top">
          <el-button type="text" icon="el-icon-plus" @click="handleLink('addlog')"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="历史梦境"
          placement="top"
          ><el-button type="text" icon="el-icon-moon-night"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="账号及设置"
          placement="right"
          ><el-button type="text" icon="el-icon-setting"></el-button>
        </el-tooltip>
      </div>
    </div>
    <!-- 评论弹窗 -->
    <el-dialog :visible.sync="dialogVisible" title="评论" width="700px">
      <el-form ref="commentForm" :model="formData" label-width="0px">
        <el-form-item label="">
          <el-input
            type="textarea"
            :rows="6"
            v-model="formData.comment"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-button @click="handleClose">取消</el-button>
          </el-col>
          <el-col :span="12">
            <el-button type="primary" @click="submitForm">确定</el-button>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import dayjs from "dayjs"; // 导入日期js
export default {
  data() {
    return {
      dialogVisible: false,
      formData: {
        comment: "",
      },
      weiboPosts: [
        {
          id: 1,
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
      showOptions: false,
      actionPost: {
        //记录当前操作的微博项
        index: "",
        post: null,
      },
    };
  },
  methods: {
    handleLink(linknName){
      this.$router.push({
        name:linknName
      })
    },
    showMoreOptions() {
      this.showOptions = !this.showOptions;
    },
    openComment(row, index) {
      this.actionPost.index = index;
      this.actionPost.post = row;

      this.dialogVisible = true;
    },
    handleClose() {
      this.formData = {
        comment: "",
      };
      this.dialogVisible = false;
    },
    submitForm() {
      // 在这里执行提交评论的逻辑
      // 可以使用 this.formData.comment 访问评论内容
      // 这里仅演示如何关闭对话框
      /* 模拟评论输入交互 */

      //this.actionPost.post  当前操作的微博项
      this.weiboPosts[this.actionPost.index].comments.push({
        name: "z",
        content: this.formData.comment,
        time: dayjs().format("YYYY-MM-DD HH:mm:ss"),
      });
      this.handleClose();
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
  position: fixed;
  bottom: 60px;
  right: 100px;
  width: 45px;
  height: 45px;
}

.weibo-more-options {
  position: fixed;
  bottom: 60px;
  right: 100px;
  display: flex;
  flex-direction: column;
  div {
    width: 40px;
    height: 40px;
    transition: transform 0.3s; /* 设置位移过渡 */
  }
  .el-button {
    height: 100%;
    padding: 0;
  }
  div:nth-child(1) {
    position: absolute;
    left: -102px;
    bottom: 0px;
  }

  div:nth-child(2) {
    position: absolute;
    left: -72px;
    bottom: 55px;
  }
  div:nth-child(3) {
    position: absolute;
    left: -9px;
    bottom: 54px;
  }
  div:nth-child(4) {
    position: absolute;
    left: 22px;
    bottom: 0px;
  }
}
.ava-box{
  display: flex;
  align-items: center;
  font-size: 16px;
}
</style>
