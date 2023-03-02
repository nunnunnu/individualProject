<template>
  <div>
    <nav v-if="!$route.meta.hideNavbar" class="navbar navbar-expand-lg bg-light">
      <div class="container">
        <div class="container-fluid">
          <!-- <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
            aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button> -->
          <router-link to="/">Water Melon</router-link>
          <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <router-link to="/album/new">최신 앨범</router-link>
              </li>
              <!-- <li class="nav-item"> -->
              <!-- <a class="nav-link" href="#">일간차트</a> -->
              <!-- </li> -->
              <li class="nav-item">
                <router-link to="/song/new">최신 음악</router-link>
              </li>
              <li class="nav-item">
                <!-- <router-link :to="{name:'loginPage', params:{url:this.$router}}">로그인</router-link> -->
              </li>
            </ul>
            <!-- <form class="d-flex" role="search"> -->
            <div class="row">
              <div class="col-8">
                <input class="form-control me-2" type="search" placeholder="Search" v-model="key">
              </div>
              <div class="col-2">
                <button class="btn btn-outline-success" type="submit" @click="searchClick(key)">Search</button>
              </div>
            </div>
            <!-- </form> -->
          </div>
        </div>
      </div>
    </nav>
    <router-view />
    <footer v-if="!$route.meta.hideNavbar" class="pt-5">
    <div v-if="songs!=null && songs.length!=0">
      <div class="HTML_Audio_player">
        <div class="Audio_Player_image"><img style="border-radius: 60px;" :src="img" /></div>
        <div class="player-content">
          <div class="player-info">
              <a class="song-name" target="_blank">
                <router-link :to="{name:'songDetail', params:{seq:songs[index].seq}}">{{ songs[index].name }}
                </router-link>
                <a class="btn btn-dark" data-bs-toggle="offcanvas" href="#offcanvasExample" role="button" aria-controls="offcanvasExample" style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;">
                  재생목록
                </a>
                <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel">
                  <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasExampleLabel">재생목록</h5>
                    <hr>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                  </div>
                  <div class="offcanvas-body">
                    <tr v-for="(s, idx) in songs" :key="s.seq">
                    <div @click="indexChange(idx)">
                        <div class="row">
                          <div class="col-auto">
                            <img style="border-radius: 60px;" :src="imgLoad(s.albumUri)" width="40" />
                          </div>
                          <div class="col-auto">
                          <p>{{s.name}}</p>
                          </div>
                        <div class="col-auto">
                          <tr v-for="a in s.artists" :key="a.seq">
                            <p>{{a.name}}</p>
                          </tr>
                        </div>
                        <div class="col-auto">
                          <div v-if="idx==index">
                            재생중
                          </div>
                        </div>
                      </div>
                      <hr>
                      </div>
                    </tr>
                    <div class="dropdown mt-3">
                
                    </div>
                  </div>
                </div>
              </a>
              <tr v-for="artist in songs[index].artists" :key="artist.seq">
                <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px">
                  {{ artist.name }}</router-link>
              </tr>
            </div>
            <div v-if="mp3!=null" class="k2_audio_player">
              <audio ref="myAudio" controls autoplay  style="width: 80%;">
                <source :src="mp3"  :ended="nextTrack" type="audio/mpeg" />
              </audio>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
      <div class="HTML_Audio_player">
        <div class="Audio_Player_image"><img style="border-radius: 60px;" src="https://downloadwap.com/thumbs2/wallpapers/p2ls/2019/abstract/26/fdb51ff613237395.jpg" /></div>
        <div class="player-content">
          <div class="player-info">
            <p>재생할 곡이 없습니다.</p>
          </div>
        </div>
      </div>
    </div>
    </footer>
  </div>
</template>
<script>
  import axios from 'axios'
  import Cookies from 'js-cookie'

  export default {
    // name: 'main',
    data() {
      return {
        keywordSearch: null,
        mp3: null,
        // song: JSON.parse(sessionStorage.getItem('nowPlayingSeq')),
        // uri: sessionStorage.getItem('nowplayingUri'),
        // album: sessionStorage.getItem('nowplayingAlbum'),
        index: sessionStorage.getItem('nowIndex'),
        songs: JSON.parse(sessionStorage.getItem('playlist')),
        img:null
      }
    },
    created() {
      // console.log(this.songs[this.index])
      this.nowPlaying()

      // this.seq = sessionStorage.getItem('nowplayingSeq');
      // this.mp3 = this.nowPlaying()
    },
    watch:{
      mp3: function(){
        // this.nowPlaying();
        // this.$refs.myAudio
      }
    },
    // computed: {
    //   sessionValue() {
    //     return sessionStorage.getItem('nowplayingSeq');
    //   }
    // },
    // watch:{
    //   seq(){
    //     this.nowPlaying
    //   }
    // },
    methods: {
      searchClick(keyword) {
        this.$router.push({
          name: "totalSearch",
          params: {
            key: keyword
          },
        });
      },
      nowPlaying() {
        if (this.songs!=null && this.songs.length != 0) {
          axios.get(`http://localhost:8250/songfile/${this.songs[this.index].files[0].uri}/${this.songs[this.index].seq}`, {
              headers: {
                Authorization: 'Bearer ' + Cookies.get('accessToken')
              },
              responseType: 'blob'
            })
            .then((response) => {
              const blobUrl = URL.createObjectURL(response.data);
              if (blobUrl) {
                this.mp3 = blobUrl;
              }
            })
        }
      },
      imgLoad(albumUri) {
        this.img = `http://localhost:8250/image/album/${albumUri}`
      },
      indexChange(idx){
        sessionStorage.setItem('nowIndex', idx)
        this.index = idx
        this.nowPlaying()
        this.$router.go()
      },
      nextTrack(){
        console.log("???")
        if(this.songs.length<=this.index-1){
          sessionStorage.setItem('nowIndex', 0)
          this.index= 0
        }else{
          sessionStorage.setItem('nowIndex', this.index+1)
          this.index= this.index+1
        }
        
      }
    }
    // mounted() {
    //   this.nowPlaying();
    // }
  }
</script>
<style>
  @import "@/assets/css/fonts.css";
  @import "@/assets/css/reset.css";

  #app {
    text-align: center;
  }

  nav-item {
    padding-left: 10px;

    padding-right: 10px;
  }

  nav a {
    font-weight: bold;
    color: #000000;
  }

  nav a.router-link-exact-active {
    color: #097d11;
  }

  .HTML_Audio_player {
    z-index: 99999;
    background: linear-gradient(135deg, #f4f8ff 0, #f4f8ff 49%, #e5efff 49%, #e9e8f2 100%);
    color: inherit;
    min-height: 120px;
    max-height: 120px;
    display: flex;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
    flex-direction: row;
    padding: 20px 10px 20px;
    position: fixed;
    bottom: 0;
    width: 100%;
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
</style>