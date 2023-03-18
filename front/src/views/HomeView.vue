<template>
  <b-container>
    <br>
    <div class="home">
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
            <router-link to="/myPage">마이페이지</router-link>
            <!-- <p>마이페이지</p> -->
          </div>
          <div class="col-5">
            <button type="submit" class="btn btn-success"
            style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;" @click="logout">로그아웃</button>
          </div>
        </div>
        <hr>
        <div v-if="user.ticket!=null">
          <p>{{ user.ticket }} 사용중</p>
        </div>
          <div v-if="user.ticket==null">
            <p>현재 이용중인 이용권이 없습니다.</p>
          </div>
      </div>
    </div>
    <div align="left" style="font-size: 20px">
      <router-link to="/album/new">최신 앨범 > </router-link>
    </div>
    <div class="row">
      <div class="col-6">
        <tr v-for="item in newAlbumList" :key="item.seq">
          <router-link :to="{name:'albumDetail', params:{seq:item.seq}}">
          <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
            <div class="col p-4 d-flex flex-column position-static">
              <strong class="d-inline-block mb-2 text-success">신규앨범</strong>
              <h3 class="mb-0">{{item.name}}</h3>
              <div class="mb-1 text-muted">{{item.regDt}}</div>
              <!-- <router-link :to="{name:'albumDetail', params:{seq:item.seq}}">상세보기</router-link> -->
            </div>
            <div class="col-auto d-none d-lg-block">
              <img :src="`${item.uri}`" width="250" height="250">
            </div>
          </div>
          </router-link>
        </tr>
      </div>
    </div>
        <h3 class="pb-4 mb-4 fst-italic border-bottom">
          대한민국 TOP10
        </h3>
        <p align="right">해당차트는 스포티파이 기준입니다.</p>
      <div v-if="tracks!=null">
        <table class="table table-sm">
          <thead>
            <th></th>
            <th>이미지</th>
            <th>곡정보</th>
            <th>가수</th>
            <th>앨범</th>
          </thead>
          <tbody>
            <tr v-for="(track, index) in tracks.slice(0,10)" :key="track.id">
              <td>{{ index + 1 }}</td>
              <td>
                <img :src="track.track.album.images[0].url"  width="80" height="80">
              </td>
              <td>{{ track.track.name }}</td>
              <td>{{ track.track.artists[0].name }}</td>
              <td>{{ track.track.album.name }}</td>
              <td>{{ track.track.album.release_date }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        현재 차트를 표시할 수 없습니다. 관리자에게 문의해주세요.
      </div>
    </div>
      <div>
  </div>
  </b-container>
</template>

<script>
  import Cookies from 'js-cookie'
  import axios from 'axios'

  export default {
    // name: 'main',
    data() {
      return {
        newAlbumList: null,
        isLogin: null,
        user: null,
        tracks:null
      }
    },
    created() {
      this.loadNewAlbum()
      if (Cookies.get('accessToken') != null) {
        this.isLogin = true
        this.loadUserInfo(Cookies.get('accessToken'))
      } else {
        this.isLogin = false
      }
      if(sessionStorage.getItem("spotifyAccess")==null){
        this.getSpotifyToken()  
      }
      this.loadTracks()
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
      loginPush() {
        this.$router.push("/login")
      },
      logout() {
        const refresh =  Cookies.get('refreshToken')
        const token =  Cookies.get('accessToken')
        console.log(refresh)
        axios.post("http://localhost:8250/refreshDel", {refresh: refresh}, {
            headers: {
              "Content-Type": `application/json`,
              Authorization: 'Bearer ' + token
            }
          })
          .then((e) => {
            this.user = e.data.data
          })
        Cookies.remove('refreshToken')
        Cookies.remove('accessToken')
        this.loadNewAlbum();
        this.isLogin = false
        sessionStorage.removeItem("nowIndex")
        sessionStorage.removeItem("playlist")
        this.$router.go()
      },
      loadUserInfo(token) {
        axios.get("http://localhost:8250/myInfo", {
            headers: {
              Authorization: 'Bearer ' + token
            }
          })
          .then((e) => {
            this.user = e.data.data
          })
          .catch((error)=>{
            const member = Cookies.get('member')
            const refresh = Cookies.get('refreshToken')
            axios.post("http://localhost:8250/member/refresh", {
              id:member,
              refresh:refresh
            })
            .then((e)=>{
              console.log(e.data.token)
              Cookies.set('accessToken', e.data.token)
              this.loadUserInfo(e.data.token)
            })
            .catch((error)=>{
              alert("다시 로그인해주세요")
              Cookies.remove('refreshToken')
              Cookies.remove('accessToken')
              Cookies.remove('member')
              sessionStorage.removeItem("nowIndex")
              sessionStorage.removeItem("playlist")
              this.$router.push("/login")
            })
          })
      },
      getSpotifyToken(){
        axios.get("http://localhost:8250/spotify")
        .then((e)=>{
          console.log(e)
          sessionStorage.setItem("spotifyAccess", e.data.spotifyAccess)
          sessionStorage.setItem("spotifyRefresh", e.data.spotifyRefresh)
        })
      },
      loadTracks() {
        axios.get('https://api.spotify.com/v1/playlists/37i9dQZEVXbNxXF4SkHj9F', {
          headers: {
            Authorization: 'Bearer ' + sessionStorage.getItem("spotifyAccess")
          }
        })
        .then(response => {
          this.tracks = response.data.tracks.items;
          console.log(this.tracks);
          
        })
        .catch(error => {
          console.log(error);
          sessionStorage.removeItem("accessToken")
          sessionStorage.removeItem("refreshToken")
        });
      }
    }
  }
</script>
<style>


  a {
    text-decoration: none;
    color: black
  }

  .col-6 {
    display: flex;
    float: right;
    width: 100%;
  }
  .home{
    padding-bottom: 102px;
  }
</style>