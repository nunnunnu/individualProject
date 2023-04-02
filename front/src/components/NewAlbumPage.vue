<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">최신 앨범</h2>
        <div class="albums">
            <tr v-for="data in data" :key="data.seq">
                <div class="card mb-3" style="max-width: 640px;">
                    <router-link :to="{name:'albumDetail', params:{seq:data.seq}}" style="font-size:15px">
                    <div class="row g-0">
                        <div class="col-md-4">
                        <div class="aspect-ratio-1-1" style="position: relative;">
                            <img :src="`http://15.164.21.92:8250/image/album/${data.uri}`"
                                style="max-width: 250px; height: auto; position: relative;" align="center" class="rounded float-start">
                        </div>
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">

                                <h6 class="card-title">{{ data.name }}</h6>
                                <router-link :to="{name:'artistDetail', params:{seq:data.artistSeq}}"
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
                                
                                </div>
                            </div>
                        </div>
                        </router-link>
                </div>
            </tr>
        </div>
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
                axios.get("/album/new?page="+this.currentPage)
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

<style scoped>
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
    }

    .card {
    width: 618px;
    margin-right: 20px;
    margin-bottom: 20px;
    }

    .card img {
    width: 100%;
    height: auto;
    }

    .card-body {
    height: 150px;
    overflow: hidden;
    text-overflow: ellipsis;
    }
    .pagearea{
        padding-bottom: 102px;
    }
/* .col-4 {
padding: 15px;
}

.card-body {
    padding: 50px;
}

.col-5 {
    padding: 15px;
}

.col-3 {
    padding: 15px;
} */
</style>