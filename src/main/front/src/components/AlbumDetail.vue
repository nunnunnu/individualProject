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
                    <p align="left">가수명 : {{ data.artist.name }}</p>
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
                            <td>{{ item.name }}</td>
                            <td>{{item.likes}}</td>
                            <td></td>
                            <td></td>
                            <td></td>
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
                <p v-html="explan"></p>
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
                seq: null,
                data: null,
            }
        },
        created() {
            this.seq = this.$route.params.seq;
            this.loadPage(this.seq)
        },
        computed: {
            explan(value) {
                return this.data.explan.replace(/(?:\r\n|\r|\n)/g,"</br>");
            }
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/album/detail/" + seq)
                    .then((e) => {
                        this.data = e.data.data
                    })
            }
        }
    }
</script>

<style>
</style>