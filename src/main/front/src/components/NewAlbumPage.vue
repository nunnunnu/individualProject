<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">최신 앨범</h2>
        <div class="albums">
            <tr v-for="data in data" :key="data.seq">
                <div class="card mb-3" style="max-width: 640px;">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img :src="`http://localhost:8250/image/album/${data.uri}`"
                                style="max-width: 100%; height: auto;" align="right" class="rounded float-start">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">

                                <h6 class="card-title">{{ data.name }}</h6>
                                <router-link :to="{name:'artistChannel', params:{seq:data.artistSeq}}"
                                    style="font-size:15px">{{ data.artistName }}</router-link>
                                <br>
                                <br>
                                <div class="row">
                                    <div class="col-5" align="center">
                                        <p class="card-text"><small class="text-muted">{{ data.regDt }}</small></p>
                                    </div>
                                    <div class="col-3" align="center">
                                        <p class="card-text"><small class="text-muted">{{ data.songCount }}곡</small></p>
                                    </div>
                                    <div class="col-4" align="center">
                                        <p class="card-text"><small class="text-muted">[{{ data.type }}]</small></p>
                                    </div>
                                </div>
                                <br>
                                <router-link :to="{name:'albumDetail', params:{seq:data.seq}}" style="font-size:15px">
                                    상세보기</router-link>
                            </div>
                        </div>
                    </div>
                </div>
            </tr>
        </div>
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
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'newAlbum',
        data() {
            return {
                data: null,
                currentPage: 0,
                totalPage: 0
            }
        },
        created() {
            this.loadPage()

        },
        methods: {
            loadPage() {
                axios.get("http://localhost:8250/album/new?page="+this.currentPage)
                    .then((e) => {
                        this.data = e.data.data.content
                        this.totalPage=e.data.data.totalPages
                    })
            },
            pageClick(page){
                console.log(page)
                console.log(this.currentPage)
                this.currentPage=page
                console.log(this.currentPage)
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
</style>