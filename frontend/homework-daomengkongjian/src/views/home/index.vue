<template>
  <div class="weibo-list">
    <el-card
      class="weibo-card"
      v-for="(post, index) in weiboPosts"
      :key="post.id"
    >
      <div class="weibo-header">
        <div @click="goToUserInfo(post,index)" style="cursor: pointer;">
          <el-avatar
            class="weibo-avatar"
            :src="post.avatarUrl"
            size="large"
          ></el-avatar>
        </div>
        <!-- <el-avatar :size="large" class="weibo-avatar"
          :src="post.avatarUrl" style="overflow: hidden;">
          <img style="width: 80px; height: 40px; object-fit: cover;" :src="post.avatarUrl" alt="Avatar">
        </el-avatar> -->
        <span class="weibo-username">
          &nbsp;{{ post.username }}
        </span>
      </div>
      <div class="weibo-content">
        &nbsp;&nbsp;{{ post.content }}
      </div>
      <!-- <el-divider></el-divider> -->
      <div class="weibo-actions">
        <!-- <font-awesome-icon :icon="['far', 'heart']" style="margin-right: 12px" /> -->
        <!-- <font-awesome-icon icon="fa-solid fa-user-secret" /> -->
        <!-- @click="post.up = !post.up" -->
        <el-badge :value="post.likes" :max="99" 
          class="item" :style="{marginRight:'24px'}">
          <font-awesome-icon
          style="margin-right: 12px"
          :class="{ upActive: post.up }"
          :style="{ fontSize: '24px',
                  cursor:'pointer'}"
          :icon="post.up ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"
          @click="like(post,index)"
          />
        </el-badge>
        
        <el-badge :value="post.comments.length" :max="10" 
          class="item" :style="{marginRight:'24px',marginLeft:'24px'}">
          <font-awesome-icon
          icon="fa-regular fa-comment"
          :style="{ fontSize: '24px',
                  cursor:'pointer'}"
          @click="openComment(post, index)"
        />
        </el-badge>
        <!-- @click="post.star = !post.star" -->

        <font-awesome-icon
          style="margin-right: 12px"
          :class="{ starActive: post.star }"
          :style="{ fontSize: '24px',
                  'margin-left':'24px',
                  cursor:'pointer' }"
          :icon="post.star ? 'fa-solid fa-star' : 'fa-regular fa-star'"
          @click="star(post,index)"
        />
        <!-- <el-divider></el-divider> -->
        <!-- <v-icon name="heart"  style="margin-right: 12px" :class="{'upActive':post.up}" @click="post.up=!post.up"/>
        <v-icon name="comments"  style="margin-right: 12px" />
        <v-icon name="fa-star"  :class="{'starActive':post.star}" @click="post.star=!post.star"/> -->
      </div>
      <el-divider class="myDivider"></el-divider>
      <!-- <div class="commentStyle">
        <el-collapse accordion v-if="post.comments && post.comments.length > 0">
        <el-collapse-item>
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
                  >
                </el-avatar>
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
      </div> -->
      <div class="commentStyle">
        <div class="commentContent" v-if="post.showComments">
          <div class="zeroComments" v-if="post.comments.length === 0">暂无评论</div>
          <div class="haveComments" v-if="post.comments.length > 0">
            <template
            v-if="post.comments.length"
            v-for="comment in post.comments"
          >
            <div>
              <div class="comment-head weibo-header">
                <div class="ava-box">
                  <el-avatar
                    class="weibo-avatar"
                    :src="comment.avatarUrl"
                    size="small"
                  >
                </el-avatar>
                  <span class="weibo-username" style="font-size: 14px;">&nbsp;{{ comment.name }}</span>
                </div>
                <span>{{ comment.time }}</span>
              </div>
              <div style="text-indent: 2em;font-size:14px;font-family:'Microsoft YaHei';">{{ comment.content }}</div>
              <el-divider class="commentDivider"></el-divider>
            </div>
          </template>
          </div>
        </div>
        <div class="showButton" @mouseenter="onHover(post,index)" @mouseleave="onLeave(post,index)" @click="commentClick(post,index)">
          <span class="centerInfo">
            <i class="el-icon-caret-bottom" v-if="!post.showComments"></i>
            <span style="font-size: 16px;" v-if="!post.showComments && post.hoverComments">&nbsp;&nbsp;显示评论</span>
            <i class="el-icon-caret-top" v-if="post.showComments"></i>
            <span style="font-size: 16px;" v-if="post.showComments && post.hoverComments">&nbsp;&nbsp;隐藏评论</span>
          </span>
        </div>
      </div>
    </el-card>

    <el-button
      class="weibo-add-button"
      type="text"
      icon="el-icon-s-home"
      @click="showMoreOptions"
    >
    </el-button>
    <div class="weibo-more-options" v-show="showOptions">
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="我的收藏"
          placement="bottom"
        >
          <el-button type="text" icon="el-icon-star-off" @click="handleLink('collect')"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip class="item" effect="light" content="写梦" placement="top">
          <el-button type="text" icon="el-icon-edit" @click="handleLink('addlog')"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="历史梦境"
          placement="top"
          ><el-button @click="handleLink('log')" type="text" icon="el-icon-moon-night"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="账号及设置"
          placement="bottom"
          ><el-button type="text" icon="el-icon-setting" @click="handleLink('setting')"></el-button>
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
import dayjs from 'dayjs' // 导入日期js
import { mapState } from 'vuex'
import {
  getDreamsApi,
  getDreamCommentListApi,
  publishCommentApi,
  likeApi,
  cancelLikeApi,
  favoriteApi,
  cancelFavoriteApi
} from '@/api/dream'
import { getUserInfoApi } from '@/api/user'
export default {
  data () {
    return {
      fits:'fill',
      dialogVisible: false,

      formData: {
        comment: ''
      },

      // 单个评论
      comment: {
        name: '', content: '', time: ''
      },

      // 单个梦境
      // dream: {
      //   id: 0,
      //   avatarUrl: '',
      //   username: '',
      //   content: '',
      //   likes: 0,
      //   commentsCount: 0,
      //   favorites: 0,
      //   up: false, // 是否点赞
      //   comments: [], // 评论列表

      //   star: false // 是否收藏
      // },
      dream: {
        id: 0,
        avatarUrl: '',
        username: '',
        content: '',
        likes: 0,
        commentsCount: 0,
        favorites: 0,
        up: false, // 是否点赞
        comments: [], // 评论列表
        star: false // 是否收藏
      },

      // 浏览列表
      weiboPosts: [
        // {
        //   id: 1,
        //   avatarUrl: "avatar1.jpg",
        //   username: "User1",
        //   content: "This is the content of the first post.",
        //   likes: 10,
        //   comments: 5,
        //   favorites: 3,
        //   up: false, //是否点赞
        //   comments: [
        //     { name: "z", content: "啊阿斯顿", time: "2023-10-12 10:00:00" },
        //     { name: "z", content: "啊阿斯顿123", time: "2023-10-12 10:00:00" },
        //   ], //评论

        //   star: false, //是否收藏
        // },
        // {
        //   id: 2,
        //   avatarUrl: "avatar2.jpg",
        //   username: "User2",
        //   content: "Another interesting post goes here.",
        //   likes: 15,
        //   comments: 8,
        //   favorites: 7,
        //   up: false, //是否点赞
        //   comments: [], //评论

        //   star: false, //是否收藏
        // },
      ],

      showOptions: false,
      actionPost: {
        // 记录当前操作的微博项
        index: '',
        post: null,
        dreamId: ''
      }
    }
  },

  computed: {
    ...mapState('user', ['token'])
  },

  mounted () {
    this.$emit('route-change','主页',0);
  },

  created () {
    this.loadData()
  },

  methods: {
    goToUserInfo(post,index){
      if(this.weiboPosts[index].status != 0){
        return;
      }
      const goToUserId = this.weiboPosts[index].userID;
      this.$router.push({
        name: 'followUserInfo', 
        params: { showUserId: goToUserId }
      });
      // alert("Hello");
    },
    async loadData () {
      this.getDreams()
    },

    async getDreams () {
      let queue = []
      try {
        const res = await getDreamsApi({
          userID: this.token
        })
        queue = res.data
      } catch (e) {
      }

      for (let i = 0; i < queue.length; i++) {
        const comments = await this.getDreamCommentList(queue[i].dreamID)
        if (queue[i].dreamStatus === 1){
          if(this.token === queue[i].userID){
            queue[i].userName = queue[i].userName + "(匿名发布)";
          }
          else{
            queue[i].userName = "匿名用户";
            queue[i].userAvatar = 'https://inception-avatar.oss-cn-shanghai.aliyuncs.com/25844be697ab452b8b0440b873cdf9cc.jpg';
          }
        }

        // 封装dream
        const newDream = {
          status: queue[i].dreamStatus,
          id: queue[i].dreamID,
          userID:queue[i].userID,
          avatarUrl: queue[i].userAvatar,
          username: queue[i].userName,
          content: queue[i].dreamContent,
          likes: queue[i].likeCount,
          up: queue[i].isLike,
          star: queue[i].isFavorite,
          comments: comments,
          commentsCount: comments.length,
          showComments: false,
          hoverComments: false,
        }
        // 将dream装入weiboPost
        this.weiboPosts.push(newDream)
      }
    },

    // 获取某梦境的评论列表
    async getDreamCommentList (dreamId) {
      try {
        const comments = []
        const res = await getDreamCommentListApi({
          dreamID: dreamId
        })
        for (let i = 0; i < res.data.length; i++) {
          const comment = {
            name: await this.getCommentUserName(res.data[i].userID),
            content: res.data[i].commentContent,
            time: res.data[i].commentTime,
            avatarUrl: await this.getCommentUserAvatar(res.data[i].userID)
          }
          comments.push(comment)
        }
        return comments
      } catch (e) {
      }
    },

    // 获取评论的用户名
    async getCommentUserName (userID) {
      try {
        let userName = ''
        const res = await getUserInfoApi({
          id: userID
        })
        userName = res.data.userName
        return userName
      } catch (e) {
      }
    },

    async getCommentUserAvatar(userID) {
      try{
        let userAvatar = '';
        const res = await getUserInfoApi({
          id:userID
        })
        userAvatar = res.data.userAvatar;
        return userAvatar
      }catch(e){

      }
    },

    handleLink (linknName) {
      this.$router.push({
        name: linknName
      })
    },
    showMoreOptions () {
      this.showOptions = !this.showOptions
    },
    openComment (row, index) {
      this.actionPost.index = index
      this.actionPost.post = row
      this.actionPost.dreamId = this.weiboPosts[index].id
      // console.log('当前操作项信息', this.actionPost)

      this.dialogVisible = true
    },
    handleClose () {
      this.formData = {
        comment: ''
      }
      this.dialogVisible = false
    },

    async submitForm () {
      // 在这里执行提交评论的逻辑
      // 可以使用 this.formData.comment 访问评论内容
      // 这里仅演示如何关闭对话框
      /* 模拟评论输入交互 */
      const username = await this.getCommentUserName(this.token)
      const userAvatar = await this.getCommentUserAvatar(this.token)

      try {
        const res = await publishCommentApi({
          dreamID: this.actionPost.dreamId,
          userID: this.token,
          commentContent: this.formData.comment
        })
        if (res.code === 1) {
          // this.actionPost.post  当前操作的微博项
          this.weiboPosts[this.actionPost.index].comments.push({
            name: username,
            avatarUrl: userAvatar,
            content: this.formData.comment,
            time: dayjs().format('YYYY-MM-DD HH:mm:ss')
          })
          this.handleClose()
        }
      } catch (e) {
      }
    },

    onHover(row,index){
      // this.actionPost.index = index;
      // this.actionPost.post = row;
      this.weiboPosts[index].hoverComments = true;
    },
    onLeave(row,index){
      // this.actionPost.index = index;
      // this.actionPost.post = row;
      this.weiboPosts[index].hoverComments = false;
    },

    commentClick(row,index){
      this.actionPost.index = index;
      this.actionPost.post = row;
      for(let i = 0;i < this.weiboPosts.length;i ++){
        if(i != index){
          this.weiboPosts[i].showComments = false;
        }
      }
      this.weiboPosts[index].showComments = !this.weiboPosts[index].showComments;
    },

    // 用户点赞
    async like (row,index) {
      this.actionPost.index = index
      this.actionPost.post = row
      this.actionPost.dreamId = this.weiboPosts[index].id
      try {
        if (!this.actionPost.post.up) {
          const res = await likeApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.up = true
            this.actionPost.post.likes ++;
          }
        } else {
          const res = await cancelLikeApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.up = false
            this.actionPost.post.likes --;
          }
        }
      } catch (e) {
      }
    },

    // 用户收藏
    async star (row,index) {
      this.actionPost.index = index
      this.actionPost.post = row
      this.actionPost.dreamId = this.weiboPosts[index].id
      try {
        if (!this.actionPost.post.star) {
          const res = await favoriteApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.star = true
          }
        } else {
          const res = await cancelFavoriteApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.star = false
          }
        }
      } catch (e) {
      }
    }
  }
}
</script>

<style scoped lang="scss">

.commentStyle{
  text-align: center;
  .commentContent{
    padding-left: 4%;
    padding-right: 0%;
    margin-bottom: 20px;
    .zeroComments{
      text-align: center;
      padding-top: 20px;
      height: 20px;
      line-height: 20px;
    }
    .haveComments{
      padding-top: 16px;
      text-align: left;
    }
  }
  .showButton{
    cursor: pointer;
    display: inline-block;
    width: 100%;
    height: 40px;
    background-color: white;
    border-width: 1px;
    border-color: #e4e7ed;
    border-style: solid;
    line-height: 40px;
    text-align: center;
    .centerInfo{
      text-align: center;
      color:gray;
    }
  }
}
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
  // background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
}
// .el-avatar > img{
//     // width: 40px;
//     // height: 40px;
//     object-fit: cover;
// }

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
  color:#443B77
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

.myDivider{
  margin-bottom: 0;
}
.commentDivider{
  margin-top: 2px;
}
</style>
