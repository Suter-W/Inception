<template>
    <div class="weibo-list">
        <div class="top-background">
            <img :src="backgroundUrl" alt="avatar" style="position: absolute;width: 100%;left: 0;height: 320px;z-index: 1;opacity: 0.6;">
        </div>
        <div class="userInfo">
            <el-avatar 
            class="weibo-avatar"
            :src="avatarUrl"
            size="large">
            </el-avatar>
            <span class="weibo-nickname">
                &nbsp;{{ nickname }}
            </span>
            <span class="selectOptions">
                <el-button  @click="followChange" v-if="tokenID != userShowID"><span>{{ followInfo }}</span></el-button>
            </span>
        </div>
        <el-divider class = "myDivider"></el-divider>
        <div class="showInfo">
            <el-tabs v-model="activeInfoName" type="card" @tab-click="handleActiveInfoNameClick">
                <el-tab-pane label="个性标签" name="userSignature">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">个性标签</span>
                    </template>
                    <div class="userSignatureShow">
                        {{ bio }}
                    </div>
                </el-tab-pane>
                <el-tab-pane label="动态" name="logDream">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">动&nbsp;&nbsp;&nbsp;&nbsp;态</span>
                    </template>
                    <div class="logDreamsDiv" v-for="(post, index) in logDreams" :key="post.id">
                        <div class="logDreamElem">
                            <div style="left: 8px;font-weight: bold;">{{ post.time }}</div>
                            <div style="left: 8px;text-indent: 2em;margin-top: 12px;">{{post.content}}</div>
                            <!-- <div class="icons" style="text-align: right;">
                                <el-badge :value="post.likeCount" :max="99" 
                                    class="item" :style="{marginRight:'24px'}">
                                    <font-awesome-icon
                                        style="margin-right: 12px"
                                        :class="{ upActive: post.isLike }"
                                        :style="{ fontSize: '24px',color: post.isLike ? 'red' : 'gray',}"
                                        :icon="post.isLike ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"
                                    />
                                </el-badge>
                                <font-awesome-icon
                                    style="margin-right: 12px"
                                    :class="{ starActive: post.isFavorite }"
                                    :style="{
                                        fontSize: '24px',
                                        marginLeft: '24px',
                                        color: post.isFavorite ? 'red' : 'gray',
                                    }"
                                    :icon="post.isFavorite ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                                />
                            </div> -->
                            <div class="weibo-actions">
        <!-- <font-awesome-icon :icon="['far', 'heart']" style="margin-right: 12px" /> -->
        <!-- <font-awesome-icon icon="fa-solid fa-user-secret" /> -->
        <!-- @click="post.up = !post.up" -->
        <el-badge :value="post.likes" :max="99" 
          class="item" :style="{marginRight:'24px'}">
          <font-awesome-icon
          style="margin-right: 12px"
          :class="{ upActive: post.up }"
          :style="{ fontSize: '24px',color: post.up ? 'red' : 'gray',
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
                  color: post.star ? 'red' : 'gray',
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
                  <span class="weibo-username" style="font-size: 14px;">&nbsp;&nbsp;{{ comment.name }}</span>
                </div>
                <!-- <span>{{ comment.time }}</span> -->
              </div>
              <div style="text-indent: 2em;margin-top: 16px;font-size:14px;font-family:'Microsoft YaHei';">{{ comment.content }}</div>
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
                            <el-divider></el-divider>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="关注列表" name="followUsers">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">关注列表</span>
                    </template>
                    <div class="followeesTables" v-for="(post, index) in followees" :key="post.id">
                        <div style="height: 40px;display: flex;">
                            <div class="everyFolloweesInfo">
                                <div>
                                    <el-avatar :src="post.userAvatarUrl" class="avatar">
                                    </el-avatar>
                                </div>
                                <span>
                                    &nbsp;{{post.name}}
                                </span>
                            </div>
                            <div class="everyIsFollowed">
                                <el-button @click="everyFollowCheck(post,index)" v-if="tokenID != post.id"><span>{{ post.isFollowShowInfo }}</span></el-button>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="粉丝列表" name="fanUsers">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">粉丝列表</span>
                    </template>
                    <div class="followeesTables" v-for="(post, index) in followers" :key="post.id">
                        <div style="height: 40px;display: flex;">
                            <div class="everyFolloweesInfo">
                                <el-avatar :src="post.userAvatarUrl" class="avatar">
                                </el-avatar>
                                <span>
                                    &nbsp;{{post.name}}
                                </span>
                            </div>
                            <div class="everyIsFollowed">
                                <el-button @click="everyFollowerCheck(post,index)" v-if="tokenID != post.id"><span>{{ post.isFollowShowInfo }}</span></el-button>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
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
    getUserInfoApi,
    isFollowApi,
    followApi,
    deleteFollowApi,
    getFolloweesApi,
    getFollowersApi,
    getShowUserLogDreamsApi
} from '@/api/user'
import {
  getDreamsApi,
  getDreamCommentListApi,
  publishCommentApi,
  likeApi,
  cancelLikeApi,
  favoriteApi,
  cancelFavoriteApi
} from '@/api/dream'
export default {

  data () {
    return {
        tokenID:0,
        tokenIDAvatarUrl:'',
        tokenIDUserName:'',
        backgroundUrl:'https://inception-avatar.oss-cn-shanghai.aliyuncs.com/f4e61b7c78fb46baafd3fae85d9d2949.',
        userShowID: 0,
        nickname:'',
        avatarUrl:'',
        bio:'',
        isFollow: '',
        followInfo:'',
        activeInfoName:'userSignature',
        followees:[],
        followers:[],
        logDreams:[],


        dialogVisible: false,

formData: {
  comment: ''
},

// 单个评论
comment: {
  name: '', content: '', time: ''
},
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
    this.tokenID = this.token
    const showUserId = this.$route.params.showUserId
    console.log(showUserId);
    this.userShowID = showUserId;
    this.getTokenUserInfo();
    this.getUserInfo();
    this.getFollowees();
    this.getFollowers();
    this.getUserLogDreams();
  },

  created () {

    // alert(showUserId);
  },

  methods: {
    handleActiveInfoNameClick(tab, event) {
        console.log(tab, event);
    },
    async getTokenUserInfo(){
        try{
            const userInfo = await getUserInfoApi({
                id: this.tokenID
            })
            this.tokenIDAvatarUrl = userInfo.data.userAvatar;
            this.tokenIDUserName = userInfo.data.userName
        }catch(e){

        }
    },
    async getUserInfo () {
      try {
        this.userInfo = await isFollowApi({
            userID:this.token,
            hostID: this.userShowID
        })
        this.nickname = this.userInfo.data.host.userName
        this.avatarUrl = this.userInfo.data.host.userAvatar
        this.bio = this.userInfo.data.host.userSignature
        this.isFollow = this.userInfo.data.isFollow
        if(!this.isFollow){
            this.followInfo = '+ 关注'
        }else{
            this.followInfo = '取消关注'
        }
      } catch (e) {}
    },
    async getFollowees(){
        let queue = []
        try {
            const res = await getFolloweesApi({
                userID:this.userShowID,
            // hostID: this.userShowID
            })
            queue = res.data
        } catch (e) {}
        for(let i = 0;i < queue.length;i ++){
            const userIsFollow = await isFollowApi({
                userID:this.token,
                hostID: queue[i].followeeID,
            })
            let boolUserIsFollow = userIsFollow.data.isFollow
            const newUser = {
                id: queue[i].followeeInfo.userID,
                name:queue[i].followeeInfo.userName,
                userAvatarUrl: queue[i].followeeInfo.userAvatar,
                IsFollowed:boolUserIsFollow,
                isFollowShowInfo:(boolUserIsFollow) ? '取消关注': '+ 关注'
            }
            this.followees.push(newUser);
        }
    },
    async getFollowers(){
        let queue = []
        try {
            const res = await getFollowersApi({
                userID:this.userShowID,
            // hostID: this.userShowID
            })
            queue = res.data
        } catch (e) {}
        for(let i = 0;i < queue.length;i ++){
            const userIsFollow = await isFollowApi({
                userID:this.token,
                hostID: queue[i].followerID,
            })
            let boolUserIsFollow = userIsFollow.data.isFollow
            const newUser = {
                id: queue[i].followerInfo.userID,
                name:queue[i].followerInfo.userName,
                userAvatarUrl: queue[i].followerInfo.userAvatar,
                IsFollowed:boolUserIsFollow,
                isFollowShowInfo:(boolUserIsFollow) ? '取消关注': '+ 关注'
            }
            this.followers.push(newUser);
        }
    },
    async getUserLogDreams(){
        let queue = []
        try {
            const res = await getShowUserLogDreamsApi({
            hostID: this.userShowID,
            userID:this.token,
        })
            queue = res.data
        } catch (e) {}
        for(let i = 0;i < queue.length;i ++){
            // const userIsFollow = await isFollowApi({
            //     userID:this.token,
            //     hostID: queue[i].followerID,
            // })
            // let boolUserIsFollow = userIsFollow.data.isFollow
            const comments = await this.getDreamCommentList(queue[i].dreamID)
            const newLogDream = {
                id:queue[i].dreamID,
                time: queue[i].dreamTime,
                content:queue[i].dreamContent,
                likes: queue[i].likeCount,
                up:queue[i].isLike,
                star:queue[i].isFavorite,
                comments: comments,
                commentsCount: comments.length,
                showComments: false,
                hoverComments: false,
            }
            this.logDreams.push(newLogDream);
        }
    },
    followChange(){
        if(this.followInfo === '+ 关注'){
            this.followHost(this.token,this.userShowID);
            this.followInfo = '取消关注'
            // alert("Hello");
        }else{
            this.deleteFollowHost(this.token,this.userShowID);
            this.followInfo = '+ 关注'
            let index = -1;
            for(let i = 0;i < this.followers.length;i ++){
                if(this.followers[i].id === this.tokenID){
                    index = i;
                    break;
                }
            }
            if(index === -1){
                return;
            }
            for(let i = index;i < this.followers.length - 1;i ++){
                this.followers[i] = this.followers[i + 1];
            }
            this.followers.pop();
        }
    },
    async followHost(temp_userID,temp_hostID){
        try{
            const res = await followApi({
                userID: temp_userID,
                hostID: temp_hostID
            })
            if(res.code === 1){
                const newUser = {
                    id: this.tokenID,
                    name:this.tokenIDUserName,
                    userAvatarUrl: this.tokenIDAvatarUrl,
                    IsFollowed:false,
                    isFollowShowInfo: '取消关注'
                }
                this.followers.push(newUser);
                this.$message({
                    message: '关注成功',
                    type: 'success'
                });
            }
        }catch(e){}
    },
    async deleteFollowHost(temp_userID,temp_hostID){
        try{
            const res = await deleteFollowApi({
                userID: temp_userID,
                hostID: temp_hostID
            })
            if(res.code === 1){
                
            }
        }catch(e){}
    },
    everyFollowCheck(post,index){
        if(this.followees[index].IsFollowed){
            this.deleteFollowHost(this.token,this.followees[index].id);
            this.followees[index].IsFollowed = false;
            this.followees[index].isFollowShowInfo = '+ 关注';
            this.followers = [];
            this.getFollowers();
        }else{
            this.followHost(this.token,this.followees[index].id);
            this.followees[index].IsFollowed = true;
            this.followees[index].isFollowShowInfo = '取消关注';
            this.followers = [];
            this.getFollowers();
        }
    },
    everyFollowerCheck(post,index){
        if(this.followers[index].IsFollowed){
            this.deleteFollowHost(this.token,this.followers[index].id);
            this.followers[index].IsFollowed = false;
            this.followers[index].isFollowShowInfo = '+ 关注';
            this.followees = [];
            this.getFollowees();
        }else{
            this.followHost(this.token,this.followers[index].id);
            this.followers[index].IsFollowed = true;
            this.followers[index].isFollowShowInfo = '取消关注';
            this.followees = [];
            this.getFollowees();
        }
    },




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
    openComment (row, index) {
      this.actionPost.index = index
      this.actionPost.post = row
      this.actionPost.dreamId = this.logDreams[index].id
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
          this.logDreams[this.actionPost.index].comments.push({
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
      this.logDreams[index].hoverComments = true;
    },
    onLeave(row,index){
      // this.actionPost.index = index;
      // this.actionPost.post = row;
      this.logDreams[index].hoverComments = false;
    },

    commentClick(row,index){
      this.actionPost.index = index;
      this.actionPost.post = row;
      for(let i = 0;i < this.logDreams.length;i ++){
        if(i != index){
          this.logDreams[i].showComments = false;
        }
      }
      this.logDreams[index].showComments = !this.logDreams[index].showComments;
    },

    // 用户点赞
    async like (row,index) {
      this.actionPost.index = index
      this.actionPost.post = row
      this.actionPost.dreamId = this.logDreams[index].id
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
      this.actionPost.dreamId = this.logDreams[index].id
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
.weibo-list {
  display: flex;
  z-index: 1;
  opacity: 1;
  background-color: white;
  flex-direction: column;
  align-items: center;
  max-width: 800px;
  border-left-style: solid;
  border-left-width: 2px;
  border-left-color: #ccc;
  border-right-style: solid;
  border-right-color: #ccc;
  border-right-width: 2px;
  margin: 0 auto;
  .top-background {
    position: relative;
    display: inline-block;
    width:100%;
    height: 320px;
    text-align: center;
  }
  .userInfo{
    display: inline-block;
    width: 100%;
    height: 64px;
    text-align: left;
    padding-left: 20px;
    position: relative;
    display: flex;
    z-index: 99;
    .weibo-avatar{
        position: absolute;
        width: 100px;
        height: 100px;
        margin-right: 10px;
        margin-top: -36px;
        // background-size: cover;
        display: flex;
        align-items: center;
        justify-content: center;
        border-color: white;
        border-width: 4px;
        border-style: solid;
    }
    .weibo-nickname{
        position: absolute;
        top: 8px;
        left: 128px;
        display: inline-block;
        font-size: 20px;
        margin-left: 10px;
    }
    .selectOptions{
        position: absolute;
        top: 8px;
        right: 50%;
        .el-button{
            width: 100px;
            height: 36px;
            border-radius: 18px;
            font-size: 20px;
            text-align: center;
            color: white;
            background-color: orange;
            box-shadow:none;
            span{
                position: absolute;
                transform:translate(-50%,-50%);
                color: white;
            }
        }
    }
  }
  .myDivider{
    margin: 0px;
    margin-top: 6px;
  }
  .myEltabs{
    width: 100%;
  }
  .showInfo{
    width: 100%;
    .userSignatureShow{
        // text-indent: 2em;
        padding: 0 20px;
        text-indent: 2em;
        min-Height: 400px;
    }
    .logDreamsDiv{
        padding: 0 20px;
    }
    .followeesTables{
        justify-content: space-between;
        padding: 0 20px;
        min-Height: 80px;
        .everyFolloweesInfo{
            flex: 2;
            display: flex;
            align-items: center;
            // justify-content: center;
            .avatar {
                margin-right: 10px;
                // background-size: cover;
                // display: flex;
                // align-items: center;
                // justify-content: center;
            }
            span{
                margin-left: 10px;
                font-weight: bold;
                font-size: 14px;
            }
        }
        .everyIsFollowed{
            flex: 1;
            .el-button{
                width: 100px;
                height: 36px;
                border-radius: 18px;
                font-size: 20px;
                text-align: center;
                color: white;
                background-color: orange;
                box-shadow:none;
                span{
                    position: absolute;
                    transform:translate(-50%,-50%);
                    color: white;
                }
            }
        }
    }
    // display: flex;
  }
    // ::v-deep .el-tabs{
    //     width: 100%;
    // }
    // ::v-deep .el-tab-pane{
    //     width: 100%;
    // }
}

.weibo-actions {
  display: flex;
  justify-content: flex-end;
}
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
</style>