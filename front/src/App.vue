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
                <router-link to="/top">TOP50</router-link>
              </li>
              <li class="nav-item">
                <router-link to="/ticket">이용권 구매</router-link>
              </li>
            </ul>
            <!-- <form class="d-flex" role="search"> -->
            <div class="row">
              <div class="col-8">
                <input class="form-control me-2" type="search" placeholder="Search" v-model="key" @change="searchClick(key)">
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
    <router-view ref="routerView" @setPlayList="setPlayList" @nowPlayingAdd="nowPlayingAdd" @changeChanel="changeChanel" :artistSeq="artistSeq"/>
    <footer v-if="!$route.meta.hideNavbar" class="pt-5">
    
      <playList @setPlayList="setPlayList" @nowPlaying="nowPlaying" ref="list" @changeChanel="changeChanel"/>
    </footer>
  </div>
</template>
<script>
  import PlayList from './components/PlayList.vue'

  export default {
  components: { PlayList },
    // name: 'main',
    data() {
      return {
        keywordSearch: null,
        artistSeq:null
      }
    },
    created() {
    },
    methods: {
      searchClick(keyword) {
        this.$router.push({
          name: "totalSearch",
          params: {
            key: keyword
          },
        })
      },
      setPlayList(){
        console.log("sss")
        this.$refs.list.setPlayList()
      },
      changeChanel(seq){
        this.artistSeq=seq
        console.log(this.artistSeq)
      }
    }
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
  .onclick:hover{
    color: #0081C9
}
</style>
