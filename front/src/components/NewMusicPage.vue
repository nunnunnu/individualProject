<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">최신 음악</h2>
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
                    <tr v-for="(item, index) in data" :key="item.seq">
                            <th scope="row">{{ currentPage*size+(index+1) }}</th>
                            <td align="left">
                                <span v-if="item.title" class="badge text-bg-primary">title</span>
                                <router-link :to="{name:'songDetail', params:{seq:item.seq}}">{{ item.name }}</router-link>
                                <br>
                                <tr v-for="artist in item.artist" :key="artist.seq">
                                    <router-link :to="{name:'artistChannel', params:{seq:artist.seq}}" style="font-size:12px">{{ artist.name }}</router-link>
                                </tr>
                            </td>
                            <td>
                                <router-link :to="{name:'albumDetail', params:{seq:item.album.seq}}" style="font-size:15px">{{ item.album.name }}</router-link>
                            </td>
                            <td>{{item.like}}</td>
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
                                    <router-link :to="{name:'movie', params:{tag:item.movie}}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-tv-fill" viewBox="0 0 16 16">
  <path d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM2 2h12s2 0 2 2v6s0 2-2 2H2s-2 0-2-2V4s0-2 2-2z"/>
</svg>
</router-link>
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
    <div class="pagearea">
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
        name: 'newSong',
        data() {
            return {
                data: null,
                currentPage: 0,
                totalPage: 0,
                size: 0
            }
        },
        created() {
            this.loadPage()

        },
        methods: {
            loadPage() {
                axios.get("http://localhost:8250/song/new?page="+this.currentPage)
                    .then((e) => {
                        this.data = e.data.data.content
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
            }
        }
    }
</script>

<style>
    .typeInfo {
        font-size: 15px;
        color: gray;
    }

    p {
        font-size: 15px;
    }

    .albums {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        object-fit: cover;
    }
    .pagearea{
        padding-bottom: 102px;
    }
</style>