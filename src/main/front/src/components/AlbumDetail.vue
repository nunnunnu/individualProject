<template>
    <b-container>
    <h2>앨범정보</h2>
    <br>
    <div class="row">
        <div class="col-3">
            <img :src="`http://localhost:8250/image/album/${data.uri}`" width="250" height="250" align="right" class="rounded float-start">
        </div>
        <div class="col-5">
            <p align="left" >{{ data.name }}</p>
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
    <h5 class="pb-4 mb-4 fst-italic border-bottom">곡 정보({{ data.songCount }})</h5>
    </b-container>
</template>
<script>
import axios from 'axios'
export default {
    name: 'albumDetail',
    props:{
    },
    data() {
        return {
            seq:null,
            data: null
        }
    },
    created() {
        this.seq= this.$route.params.seq;
        this.loadPage(this.seq)
    },
    methods:{
        loadPage(seq){
            axios.get("http://localhost:8250/album/detail/"+seq)
            .then((e) => {
            this.data = e.data.data
          })
        }
    }
}
</script>

<style>
</style>