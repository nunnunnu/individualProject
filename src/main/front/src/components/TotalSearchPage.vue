<template>
    <b-container>
        <h4 align="left" class="pb-4 mb-4 fst-italic border-bottom">곡 명으로 검색</h4>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">곡정보</th>
                    <th scope="col">앨범</th>
                    <th scope="col">좋아요</th>
                    <th scope="col">듣기</th>
                    <th scope="col">담기</th>
                    <th scope="col">다운</th>
                    <th scope="col">뮤비</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in songName" :key="item.seq">
                    <th scope="row">{{ index+1 }}</th>
                    <td align="left">
                        
                        <router-link :to="{name:'songDetail', params:{seq:item.seq}}">
                            <span v-if="item.title" class="badge text-bg-primary">title</span>
                            <span v-html="keywordTag(item.name)"></span>
                        </router-link>
                        <br>
                <tr v-for="artist in item.artist" :key="artist.seq">
                    <router-link :to="{name:'artistChannel', params:{seq:artist.seq}}" style="font-size:12px">
                        {{ artist.name }}</router-link>
                </tr>
                </td>
                <td>
                    <router-link :to="{name:'albumDetail', params:{seq:item.album.seq}}" style="font-size:15px">
                        {{ item.album.name }}</router-link>
                </td>
                <td>{{item.like}}</td>
                <td>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-play-fill" viewBox="0 0 16 16">
                        <path
                            d="m11.596 8.697-6.363 3.692c-.54.313-1.233-.066-1.233-.697V4.308c0-.63.692-1.01 1.233-.696l6.363 3.692a.802.802 0 0 1 0 1.393z" />
                    </svg>
                </td>
                <td>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-plus-square" viewBox="0 0 16 16">
                        <path
                            d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z" />
                        <path
                            d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
                    </svg>
                </td>
                <td>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-download" viewBox="0 0 16 16">
                        <path
                            d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z" />
                        <path
                            d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z" />
                    </svg>
                </td>
                <td>
                    <div v-if="item.movie!=null">
                        <router-link :to="{name:'movie', params:{tag:item.movie}}">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                class="bi bi-tv-fill" viewBox="0 0 16 16">
                                <path
                                    d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM2 2h12s2 0 2 2v6s0 2-2 2H2s-2 0-2-2V4s0-2 2-2z" />
                            </svg>
                        </router-link>
                    </div>
                    <div v-else-if="item.movie==null">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                            class="bi bi-tv" viewBox="0 0 16 16">
                            <path
                                d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM13.991 3l.024.001a1.46 1.46 0 0 1 .538.143.757.757 0 0 1 .302.254c.067.1.145.277.145.602v5.991l-.001.024a1.464 1.464 0 0 1-.143.538.758.758 0 0 1-.254.302c-.1.067-.277.145-.602.145H2.009l-.024-.001a1.464 1.464 0 0 1-.538-.143.758.758 0 0 1-.302-.254C1.078 10.502 1 10.325 1 10V4.009l.001-.024a1.46 1.46 0 0 1 .143-.538.758.758 0 0 1 .254-.302C1.498 3.078 1.675 3 2 3h11.991zM14 2H2C0 2 0 4 0 4v6c0 2 2 2 2 2h12c2 0 2-2 2-2V4c0-2-2-2-2-2z" />
                        </svg>
                    </div>
                </td>
                </tr>
            </tbody>
        </table>
        <div align="right">
            <router-link :to="{name:'songNameSearch', params:{key:this.childKeyword}}">곡 명으로 검색 전체 보기 ></router-link>
        </div>
        <h4 align="left" class="pb-4 mb-4 fst-italic border-bottom">아티스트 명으로 검색</h4>
        <div class="artists">
            <tr v-for="art in artist" :key="art.seq">
                <!-- <div class="col"> -->
                <div class="card">
                    <img :src="`http://localhost:8250/image/artist/${art.uri}`" class="card-img-top">
                    <div class="card-body">
                        <div class="card-title"><div v-html="keywordTag(art.name)"></div></div>
                        <a :href="`http://localhost:8080/artist/channel${art.seq}`">상세보기</a>
                    </div>
                </div>
            </tr>
        </div>
        <div align="right">
            <router-link :to="{name:'searchArtistName', params:{key:this.childKeyword}}">아티스트 이름 검색 전체 보기 ></router-link>
        </div>
        <h4 align="left" class="pb-4 mb-4 fst-italic border-bottom">가사로 검색</h4>
        <tr v-for="song in songLyrics" :key="song.seq">
        <div align="left">
                <router-link :to="{name:'songDetail', params:{seq:song.seq}}">{{ song.name }}</router-link>
                <br>
                <router-link :to="{name:'songDetail', params:{seq:song.seq}}"><div v-html="stringFind(song.lyrics)"></div>
                </router-link>
                <div>
                <tr v-for="art in song.artist" :key="art.seq">
                    <router-link :to="{name:'artistChannel', params:{seq:art.seq}}" style="font-size:12px">
                        {{ art.name }}</router-link>
                </tr>
                </div >
                <router-link :to="{name:'albumDetail', params:{seq:song.album.seq}}" style="font-size:12px">
                    {{ song.album.name }}</router-link>
                </div>
            <hr>
        </tr>
        <div align="right">
            <router-link :to="{name:'searchSongLyrics', params:{key:this.childKeyword}}">가사검색 전체 보기 ></router-link>
        </div>
        <h4 align="left" class="pb-4 mb-4 fst-italic border-bottom">앨범 명으로 검색</h4>
        <div class="albums">
            <tr v-for="data in albums" :key="data.seq">
                <div class="card mb-3" style="max-width: 423px;">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img :src="`http://localhost:8250/image/album/${data.uri}`"
                                style="max-width: 100%; height: auto;" align="right" class="rounded float-start">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">

                                <h6 class="card-title"><span v-html="keywordTag(data.name)"></span></h6>
                                <!-- <router-link :to="{name:'artistChannel', params:{seq:data.artistSeq}}"
                                    style="font-size:15px">{{ data.artistName }}</router-link> -->
                                <!-- <br>
                                <br> -->
                                <!-- <div class="row"> -->
                                <!-- <div class="col-5" align="center">
                                        <p class="card-text"><small class="text-muted">{{ data.regDt }}</small></p>
                                    </div>
                                    <div class="col-3" align="center">
                                        <p class="card-text"><small class="text-muted">{{ data.songCount }}곡</small></p>
                                    </div>
                                    <div class="col-4" align="center">
                                        <p class="card-text"><small class="text-muted">[{{ data.type }}]</small></p>
                                    </div> -->
                                <!-- </div> -->
                                <!-- <br> -->
                                <router-link :to="{name:'albumDetail', params:{seq:data.seq}}" style="font-size:15px">
                                    상세보기</router-link>
                            </div>
                        </div>
                    </div>
                </div>
            </tr>
        </div>
        <div align="right">
            <router-link :to="{name:'searchAlbumName', params:{key:this.childKeyword}}">앨범 검색 전체 보기 ></router-link>
        </div>
        <br>
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'totalSearch',
        // props : ['key'],
        data() {
            return {
                songName: null,
                songLyrics: null,
                albums: null,
                artist: null,
                childKeyword: null
            }
        },
        created() {
            // this.childKeyword = this.keyword
            this.childKeyword = this.$route.params.key;
            this.loadPage()
        },
        methods: {
            loadPage() {
                axios.get("http://localhost:8250/search/total?keyword=" + this.childKeyword)
                    .then((e) => {
                        this.songName = e.data.songName
                        this.songLyrics = e.data.songLyrics
                        console.log(this.songLyrics)
                        this.albums = e.data.albums
                        this.artist = e.data.artist
                    })
            },
            stringFind(lyrics) {
                console.log(this.childKeyword)
                var pos = lyrics.indexOf(this.childKeyword);
                console.log(pos)
                var subLyrics = lyrics.substr(pos, 200)+'...'
                return subLyrics.replace(new RegExp(this.childKeyword, 'gi'), match => `<span class="highlight">${match}</span>`)
            },
            keywordTag(str){
                return str.replace(new RegExp(this.childKeyword, 'gi'), match => `<span class="highlight">${match}</span>`)
            }
        }
    }
</script>
<style>
.highlight {
  color: red;
}
</style>