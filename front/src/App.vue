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
      <div class="HTML_Audio_player">
            <div class="Audio_Player_image"><img style="border-radius: 60px;"
                    src="" /></div>
            <div class="player-content">
                <div class="player-info">
                    <a class="song-name" target="_blank">Candy</a>
                    <a class="artist" href="#">NCT Dream</a>
                </div>
                <div v-if="mp3!=null" class="k2_audio_player">
                  <audio controls style="width: 80%;">
                    <source :src="mp3" type="audio/mpeg" />
                  </audio>
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
      mp3:null,
      seq:null
    }
  },
  created() {
    this.nowPlaying()
    this.seq = sessionStorage.getItem('nowplayingSeq');
    // this.mp3 = this.nowPlaying()
    console.log(this.mp3)
  },
  watch:{
    seq(){
      this.nowPlaying
    }
  },
  methods:{
    searchClick(keyword){
      this.$router.push({
        name: "totalSearch",
        params: { key: keyword },
      });
    },
    nowPlaying() {
      const uri = sessionStorage.getItem('nowplayingUri');
      const seq = sessionStorage.getItem('nowplayingSeq');
      axios.get(`http://localhost:8250/songfile/${uri}/${seq}`, {
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

  nav-item{
    padding-left:10px;

  padding-right:10px;
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