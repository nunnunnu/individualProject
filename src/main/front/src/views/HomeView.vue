<template>
  <b-container>
    <br>
    <div class="row">
      <div class="col-8 p-4">
        <h1 align="left">Water Melon</h1>
      </div>
      <div v-if="!this.isLogin" class="col-4 p-5" style="background-color:#f2f2f2; border:1px solid gainsboro;">
        <div class="d-grid gap-2 col-6 mx-auto">
          <router-link to="/join" align="right">회원가입 ></router-link>
          <!-- <a align="right">회원가입 ></a> -->
          <button type="submit" class="btn btn-success" @click="loginPush">로그인</button>
        </div>
      </div>
      <div v-if="this.isLogin && this.user!=null" class="col-4 p-4"
        style="background-color:#f2f2f2; border:1px solid gainsboro;">
        <!-- <div class="d-grid gap-2 col-6 mx-auto"> -->
        <p></p>
        <div class="row">
          <div class="col-3">
            <p>{{ user.nickname }}님</p>
          </div>
          <div class="col-4">
            <p>마이페이지</p>
          </div>
          <div class="col-5">
            <button type="submit" class="btn btn-success"
            style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;" @click="logout">로그아웃</button>
          </div>
        </div>
        <hr>
        <div v-if="user.ticket!=null">
          <p>{{ user.ticket }} 사용중</p>
          <div v-if="user.ticket==null">
            <p>현재 이용중인 이용권이 없습니다.</p>
          </div>
        </div>
      </div>
      <!-- </div> -->
    </div>
    <!-- <div class="p-4 p-md-3 mb-4">
      <div class="col-md-4 px-0">
        <h1 class="display-4 fst-italic">Water Melon</h1>
      </div>
      <div class="col-md-15 px-0">
        <button type="submit" class="btn btn-success">로그인</button>
      </div>
    </div> -->
    <div align="left" style="font-size: 25px">
      <router-link to="/album/new">최신 앨범 > </router-link>
    </div>
    <div class="row">
      <div class="col-6">
        <tr v-for="item in newAlbumList" :key="item.seq">
          <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
            <div class="col p-4 d-flex flex-column position-static">
              <strong class="d-inline-block mb-2 text-success">신규앨범</strong>
              <h3 class="mb-0">{{item.name}}</h3>
              <div class="mb-1 text-muted">{{item.regDt}}</div>
              <router-link :to="{name:'albumDetail', params:{seq:item.seq}}">상세보기</router-link>
            </div>
            <div class="col-auto d-none d-lg-block">
              <img :src="`${item.uri}`" width="250" height="250">
            </div>
          </div>
        </tr>
      </div>
    </div>
    <div class="row g-5">
      <div class="col-md-8">
        <h3 class="pb-4 mb-4 fst-italic border-bottom">
          일간차트
        </h3>

        <table class="table table-sm">
          <thead>
            <th>순위</th>
            <th></th>
            <th>곡정보</th>
            <th>앨범</th>
            <th>좋아요</th>
            <th>듣기</th>
          </thead>
          <tbody>
            <td>1</td>
            <td>
              <img src="http://localhost:8250/image/album/nctdream_candy" width="75" height="75">
            </td>
            <td>Candy</td>
            <td>Candy - Winter Special Mini Album</td>
            <td>132,223</td>
            <td>
              ▶
            </td>
          </tbody>
        </table>

      </div>

      <div class="col-md-4">
        <h3 class="pb-4 mb-4 fst-italic border-bottom">
          player
        </h3>
        <div class="HTML_Audio_player">
          <div class="Audio_Player_image"><img style="border-radius: 60px;"
              src="http://localhost:8250/image/album/nctdream_candy" /></div>
          <div class="player-content">
            <div class="player-info">
              <!-- <a class="song-name" target="_blank" href="https://youtu.be/BWdZjZV6bEk">Kontinuum - Aware [NCS Release]</a> -->
              <a class="song-name" target="_blank">Candy</a>
              <a class="artist" href="#">NCT Dream</a>
            </div>
            <div class="k2_audio_player">
              <audio controls style="width: 80%;">
                <source src="http://localhost:8250/song/NCT DREAM-01-Candy-Candy - Winter Special Mini Album-320/1/1"
                  type="audio/mpeg" />
              </audio>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-container>
</template>

<script>
  import router from '@/router'
  import Cookies from 'js-cookie'
  import axios from 'axios'

  //   const api = axios.create({
  //   baseURL: 'http://localhost:8250/',
  //   withCredentials: true, // 쿠키를 전송하기 위해 withCredentials를 true로 설정합니다.
  // });
  export default {
    // name: 'main',
    data() {
      return {
        newAlbumList: null,
        isLogin: null,
        user: null
      }
    },
    created() {
      this.loadNewAlbum()
      this.loadWeeklyChart()
      if (Cookies.get('accessToken') != null) {
        this.isLogin = true
        this.loadUserInfo(Cookies.get('accessToken'))
      } else {
        this.isLogin = false
      }
    },
    methods: {
      loadNewAlbum() {
        axios.get("http://localhost:8250/album/new/two")
          .then((e) => {
            this.newAlbumList = e.data.data
          })
      },
      albumDetail(seq) {
        axios.get("http://localhost:8250/album/detail/" + seq)
          .then((e) => {
            this.albumData = e
          })
      },
      detailPage(albumSeq) {
        this.$router.push({
          name: 'AlbumDetail',
          params: {
            seq: albumSeq
          }
        })
      },
      loadWeeklyChart() {
        let today = new Date();

        // 저번 주 월요일 구하기
        let monday = new Date(today.getFullYear(), today.getMonth(), today.getDate() - today.getDay() - 6);

        // 결과 출력
        console.log(monday)
        const year = monday.getFullYear()
        const tmp = monday.getMonth() + 1
        const tmp2 = monday.getDate()
        const month = tmp < 10 ? '0' + tmp : tmp
        const days = tmp2 < 10 ? '0' + tmp2 : tmp2

        // axios.get("http://dev.guyso.me/api/v3/chart/melon/weekly/20230213")
        // .then((e) => {
        //     console.log(e)
        //   })
      },
      loginPush() {
        this.$router.push("/login")
      },
      logout() {
        Cookies.remove('refreshToken')
        Cookies.remove('accessToken')
        this.loadNewAlbum();
        this.loadWeeklyChart()
        this.isLogin = false
      },
      loadUserInfo(token) {
        axios.get("http://localhost:8250/myInfo", {
            headers: {
              Authorization: 'Bearer ' + token
            }
          })
          .then((e) => {
            console.log(e)
            this.user = e.data.data
          })
      }
    }
  }
</script>
<style>
  .HTML_Audio_player {
    z-index: 10;
    background: linear-gradient(135deg, #f4f8ff 0, #f4f8ff 49%, #e5efff 49%, #e9e8f2 100%);
    color: inherit;
    min-height: 120px;
    max-height: 150px;
    display: flex;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
    flex-direction: row;
    padding: 20px 10px 20px;
  }

  .Audio_Player_image {
    width: 150px;
    display: flex;
    justify-content: center;
  }

  .player-content {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
  }

  .player-info {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-left: 10px;
  }

  .song-name {
    font-size: 18px;
    font-weight: 600;
  }

  .k2_audio_player {
    display: flex;
  }

  audio {
    flex-grow: 1;
    height: 40px;
  }

  audio::-webkit-media-controls-play-button {
    background-color: #B1D4E0;
    border-radius: 50%;
  }

  audio::-webkit-media-controls-play-button:hover {
    background-color: rgba(177, 212, 224, .7);
  }

  audio::-webkit-media-controls-panel {
    background: #e9e8f2;
  }

  .Audio_Player_image img:hover {
    animation: rotating 3s linear infinite
  }

  @keyframes rotating {
    from {
      transform: rotate(0deg)
    }

    to {
      transform: rotate(360deg)
    }
  }

  a {
    text-decoration: none;
    color: black
  }

  .col-6 {
    display: flex;
    float: right;
    width: 100%;
  }
</style>