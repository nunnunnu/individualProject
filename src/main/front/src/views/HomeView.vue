<template>

  <b-container>

    <div class="p-4 p-md-3 mb-4">
      <div class="col-md-6 px-0">
        <h1 class="display-4 fst-italic">Water Melon</h1>
      </div>
    </div>

    <div class="row mb-2">
      <tr v-for="item in newAlbumList" :key="item.seq">
      <div class="col-md-6">
          <div class="row">
            <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
              <div class="col p-4 d-flex flex-column position-static">
                <strong class="d-inline-block mb-2 text-success">신규앨범</strong>
                <h3 class="mb-0">{{item.name}}</h3>
                <div class="mb-1 text-muted">{{item.regDt}}</div>
                <!-- <div @click="detailPage(newAlbumList[0].seq)"> -->
                  <router-link :to="{name:'albumDetail', params:{seq:item.seq}}">상세보기</router-link>
                <!-- </div> -->
              </div>
              <div class="col-auto d-none d-lg-block">
                <img :src="`${item.uri}`" width="250" height="250">
              </div>
            </div>
          </div>
        </div>
      </tr>
      <!-- <div class="col-md-6">
        <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
          <div class="col p-4 d-flex flex-column position-static">
            <strong class="d-inline-block mb-2 text-success">신규앨범</strong>
            <h3 class="mb-0">{{newAlbumList[1].name}}</h3>
            <div class="mb-1 text-muted">{{newAlbumList[1].regDt}}</div>
            <router-link to="/album/detail" :seq=newAlbumList[1].seq>상세보기</router-link>
          </div>
          <div class="col-auto d-none d-lg-block">
            <img :src="`${newAlbumList[1].uri}`" width="250" height="250">
          </div>
        </div>
      </div> -->
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
  import axios from 'axios'
  export default {
    // name: 'main',
    data() {
      return {
        newAlbumList: null

      }
    },
    created() {
      this.loadNewAlbum()
    },
    methods: {
      loadNewAlbum() {
        axios.get("http://localhost:8250/album/new/two")
          .then((e) => {
            this.newAlbumList = e.data.data
          })
        },
        albumDetail(seq){
        axios.get("http://localhost:8250/album/detail/"+seq)
          .then((e) => {
            this.albumData = e
          })
      },
      detailPage(albumSeq){
        this.$router.push({name: 'AlbumDetail', params: {seq: albumSeq}})
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
</style>