<template>
    <b-container>
        <div class="search">
        <h4 align="left" class="pb-4 mb-4 fst-italic border-bottom">가사로 검색</h4>
        <tr v-for="song in data" :key="song.seq">
        <div align="left">
                <router-link :to="{name:'songDetail', params:{seq:song.seq}}">{{ song.name }}</router-link>
                <br>
                <router-link :to="{name:'songDetail', params:{seq:song.seq}}"><div v-html="stringFind(song.lyrics)"></div>
                </router-link>
                <div>
                <div style="display:flex; flex-direction: row;">
                                    <tr v-for="(art, index) in song.artist" :key="art.seq">
                                        <router-link :to="{name:'artistDetail', params:{seq:art.seq}}" style="font-size:12px">
                                            {{ art.name }}</router-link>
                                        <span v-if="index < song.artist.length - 1">, </span>
                                    </tr>
                                </div>
                </div >
                <router-link :to="{name:'albumDetail', params:{seq:song.album.seq}}" style="font-size:12px">
                    {{ song.album.name }}</router-link>
                </div>
            <hr>
        </tr>
        <br>
        <ul class="pagination justify-content-center">
        <li class="page-item disabled">
            <a v-if="currentPage==0" class="page-link">Previous</a>
        </li>
        <a v-if="currentPage!=0" class="page-link" @click="prePage()">Previous</a>
            <tr v-for="page in totalPage" :key="page">
                <li class="page-item">
                    <a class="page-link" @click="pageClick(page-1)">
                        <font color="red" v-if="page-1==currentPage">{{ page }}</font>
                        <font v-if="page-1!=currentPage">{{ page }}</font>
                    </a>
                </li>
            </tr>
            <li v-if="currentPage+1==totalPage" class="page-item disabled">
            <a class="page-link">Next</a>
            </li>
            <li v-if="currentPage+1!=totalPage" class="page-item">
            <a class="page-link" @click="pageClick(currentPage+1)">Next</a>
            </li>
        </ul>
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'searchSongLyrics',
        // props : ['key'],
        data() {
            return {
                data:null,
                childKeyword: null,
                currentPage: 0,
                totalPage: 0,
                size: 0
            }
        },
        created() {
            // this.childKeyword = this.keyword
            this.childKeyword = this.$route.params.key;
            this.loadPage()
        },
        methods: {
            loadPage() {
                axios.get("/search/songLyrics?keyword=" + this.childKeyword+"&page="+this.currentPage)
                    .then((e) => {
                        console.log(e)
                        this.data = e.data.data.content
                        console.log(this.data)
                        this.totalPage=e.data.data.totalPages
                        this.size=e.data.data.size        
                    })
            },
            pageClick(page){
                this.currentPage=page
                this.loadPage()
            },
            prePage(){
                this.currentPage = this.currentPage-1
                this.loadPage()
            },
            stringFind(lyrics) {
                console.log(this.childKeyword)
                var pos = lyrics.indexOf(this.childKeyword);
                console.log(pos)
                var subLyrics = lyrics.substr(pos, 200)+'...'
                return subLyrics.replace(new RegExp(this.childKeyword, 'gi'), match => `<span class="highlight">${match}</span>`)
            }
        }
    }
</script>
<style>
.artists{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    object-fit: cover;

}
.search{
    padding-bottom: 102px;
}
</style>
