<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">곡 정보</h2>
        <br>
        <div v-if="data!=null">
            <div class="row">
                <div class="col-3">
                    <img :src="`http://localhost:8250/image/album/${data.album.uri}`"
                        style="max-width: 100%; height: auto;" align="right" class="rounded float-start">
                </div>
                <div class="col-5">
                    <!-- <p align="left">[{{ data.type }}]</p> -->
                    <h2 align="left">{{ data.name }}</h2>
                    <tr v-for="item in data.artists" :key="item.seq">
                        <router-link :to="{name:'artistChannel', params:{seq:item.seq}}" style="font-size:25px">
                            {{ item.name }}</router-link>
                    </tr>
                    <br>
                    <div align="left">
                        <router-link :to="{name:'albumDetail', params:{seq:data.album.seq}}" style="font-size:20px">앨범명
                            : {{ data.album.name }}
                        </router-link>
                        <p align="left">발매일 : {{ data.regDt }}</p>
                        <p align="left">장르 : {{ data.genre }}</p>
                    </div>
                </div>
                <div class="col-3">
                    <p align="left">좋아요 수 : {{ data.likes}}</p>
                </div>
            </div>
            <br>
            <hr>
            <br>
            <h3 class="pb-4 mb-4 fst-italic border-bottom">가사</h3>
            <details align="left">
                <summary>펼치기</summary>
                <div v-html="data.lyrics"></div>
            </details>
            <br>
            <h3 class="pb-4 mb-4 fst-italic border-bottom">작사/작곡</h3>
            <div class="creator">
                <tr v-for="creator in data.creates" :key="creator.seq">
                    <div class="card">
                        <img :src="`http://localhost:8250/image/artist/${creator.uri}`" class="card-img-top">
                        <div class="card-body">
                            <p class="card-title">{{creator.name}}</p>
                            <p class="card-text">{{creator.type}}</p>
                            <router-link :to="{name:'artistChannel', params:{seq:creator.seq}}">상세보기</router-link>
                        </div>
                    </div>
                </tr>
            </div>
            <br>
            <div v-if="data.movie!=null">
            <h3 class="pb-4 mb-4 fst-italic border-bottom">뮤직비디오</h3>
            <div v-html="movie()"></div>
            <br>
        </div>
    </div>

    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'albumDetail',
        props : ['seq'],
        data() {
            return {
                data: null,
            }
        },
        created() {
            // this.seq = this.$route.params.seq;
            this.loadPage(this.seq)
            console.log(this.seq)
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/song/detail/" + seq)
                    .then((e) => {
                        this.data = e.data.data
                    })
            },
            movie() {    
                return this.data.movie
            }
        }
    }
</script>

<style>
    p {
        font-size: 20px
    }

    .creator {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        object-fit: cover;

    }

    .card-img-top {
        height: 150px;
        overflow: hidden;
    }
</style>