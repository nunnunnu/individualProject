<template>
    <b-container>
        <div class="albums">
        <tr v-for="album in data" :key="album.seq">
            <router-link :to="{name:'albumDetail', params:{seq:album.seq}}" style="font-size:20px">
                <div class="card" style="width: 13rem; height: 30rem">
                    <img :src="`http://localhost:8250/image/album/${album.uri}`">
                    <div class="card-body">
                        <p class="typeInfo">[{{ album.type }}]</p>
                        <h6 class="card-title">{{album.name}}</h6>
                        <p class="card-text">title-{{ album.songName }}</p>
                        <p class="card-text">{{ album.regDt }}</p>
                        <p class="card-text">{{ album.songCount }}곡</p>
                        
                    </div>
                </div>
                </router-link>
            </tr>
            <div v-if="dataLength==0">
                <p>앨범정보가 없습니다.</p>
            </div>
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'artistAlbumList',
        props : ['seq'],
        data() {
            return {
                data: null,
                dataLength:null
            }
        },
        created() {
            // this.seq = this.$route.params.seq;
            this.loadPage(this.seq)
            console.log(this.seq)
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/album/artist/" + seq)
                    .then((e) => {
                        console.log(e.data);
                        if(!e.data.status){
                            this.dataLength=0
                        }else{
                            this.data = e.data.data.content
                        }
                    })
            }
        }
    }
</script>

<style>
.typeInfo{
    font-size: 15px; color: gray;
}
p{
    font-size: 15px;
}
.albums{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    object-fit: cover;
    padding-bottom: 102px;
}
.card {
    margin-right: 10px;
    margin-bottom: 10px;
    }


    .pagearea{
        padding-bottom: 102px;
    }
</style>