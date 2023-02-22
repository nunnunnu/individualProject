<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">앨범 정보</h2>
        <br>
        <div v-if="data!=null">
            <div class="row">
                <div class="col-3">
                    <img :src="`http://localhost:8250/image/album/${data.uri}`" width="280" height="280" align="right"
                        class="rounded float-start">
                </div>
                <div class="col-5">
                    <p align="left">[{{ data.type }}]</p>
                    <p align="left">{{ data.name }}</p>
                <div align="left">
                    <router-link :to="{name:'artistChanel', params:{seq:data.artist.seq}}">가수명 : {{ data.artist.name }}</router-link>
                </div>
                    <p align="left"></p>
                    <p align="left">장르 : {{ data.genreList }}</p>
                    <p align="left">소속사 : {{ data.agencyName }}</p>
                    <p align="left">발매사 : {{ data.pubName }}</p>
                    <p align="left">발매일 : {{ data.regDt }}</p>
                </div>
                <div class="col-3">
                    <p align="left">댓글 수 : {{ data.comment }}</p>
                    <p align="left">평점 : {{ data.grade }}</p>
                </div>
            </div>
            <br>
            <hr>
            <br>
            <h5 class="pb-4 mb-4 fst-italic border-bottom">수록곡({{ data.songCount }})</h5>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">번호</th>
                        <th scope="col">곡정보</th>
                        <th scope="col">좋아요</th>
                        <th scope="col">듣기</th>
                        <th scope="col">담기</th>
                        <th scope="col">다운</th>
                        <th scope="col">뮤비</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in data.song" :key="item.seq">
                            <th scope="row">{{ item.order }}</th>
                            <td align="left">
                                {{ item.name }}
                                <br>
                                <tr v-for="artist in item.artists" :key="artist.seq">
                                    <p style="font-size:12px">{{ artist.name }} </p>
                                </tr>
                            </td>
                            <td>{{item.likes}}</td>
                            <td>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-play-fill" viewBox="0 0 16 16">
  <path d="m11.596 8.697-6.363 3.692c-.54.313-1.233-.066-1.233-.697V4.308c0-.63.692-1.01 1.233-.696l6.363 3.692a.802.802 0 0 1 0 1.393z"/>
</svg>
                            </td>
                            <td>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
  <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
</svg>
                            </td>
                            <td>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-download" viewBox="0 0 16 16">
  <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
  <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
</svg>
                            </td>
                            <td>
                                <div v-if="item.movie!=null">
                                    <a :href="`${item.movie}`"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-tv-fill" viewBox="0 0 16 16">
  <path d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM2 2h12s2 0 2 2v6s0 2-2 2H2s-2 0-2-2V4s0-2 2-2z"/>
</svg></a>
                                </div>
                                <div v-else-if="item.movie==null">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-tv" viewBox="0 0 16 16">
  <path d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM13.991 3l.024.001a1.46 1.46 0 0 1 .538.143.757.757 0 0 1 .302.254c.067.1.145.277.145.602v5.991l-.001.024a1.464 1.464 0 0 1-.143.538.758.758 0 0 1-.254.302c-.1.067-.277.145-.602.145H2.009l-.024-.001a1.464 1.464 0 0 1-.538-.143.758.758 0 0 1-.302-.254C1.078 10.502 1 10.325 1 10V4.009l.001-.024a1.46 1.46 0 0 1 .143-.538.758.758 0 0 1 .254-.302C1.498 3.078 1.675 3 2 3h11.991zM14 2H2C0 2 0 4 0 4v6c0 2 2 2 2 2h12c2 0 2-2 2-2V4c0-2-2-2-2-2z"/>
</svg>
                                </div>
                            </td>
                    </tr>
                </tbody>
            </table>
            <br>
            <h5 class="pb-4 mb-4 fst-italic border-bottom">앨범소개</h5>
            <!-- <p v-html="explan"></p> -->
            <details align="left">
                <summary>펼치기</summary>
                <div v-html="albumExplan"></div>
            </details>
            <br>
            <hr>
            <br>
            <h5 class="pb-4 mb-4 fst-italic border-bottom">댓글</h5>
            <br>
            <div class="row">
                <div class="col-11">
                    <div class="input-group">
                        <span class="input-group-text">댓글</span>
                        <textarea class="form-control" aria-label="With textarea"></textarea>
                    </div>
                </div>
                <div class="col-1">
                    <button class="btn btn-success btn-lg">입력</button>
                </div>
            </div>
            <br>
            <p align="left">총 {{ data.comment }}개</p>
            <hr>
            
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'albumDetail',
        props: {},
        data() {
            return {
                data: null,
                albumExplan: ""
            }
        },
        created() {
            this.seq = this.$route.params.seq;
            this.loadPage(this.seq)
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/album/detail/" + seq)
                    .then((e) => {
                        this.data = e.data.data
                        this.albumExplan = e.data.data.explan
                    })
            },
            explan() {    
                return  this.data.explan
            }
        }
    }
</script>

<style>
</style>