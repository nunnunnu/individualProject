<template>
    <b-container>
        <div class="popup_wrap">
        <div class="popup">
            <div align="right">
                <button class="btn btn-white" @click="closeDown">&times;</button>
            </div>
            <br>
            <h4>음원 다운로드</h4>
            <div v-for="file in data" :key="file.seq">
                <button class="btn btn-success" @click="down(file)">{{ file.quality}}</button>
                <!-- <a :href="`http://localhost:8250/songfile/${file.uri}/${song.seq}`">다운</a> -->
            </div>
        </div>
    </div>
    </b-container>
</template>
<script>
import axios from 'axios'
import Cookies from 'js-cookie'
export default {
    name:"songDown",
    props:{
        song:Object
    },
    data() {
        return{
            data:null
        }
    },
    mounted(){
        console.log(this.song)
        this.loadDown()
    },
    methods:{
        closeDown(){
            this.$emit("closeDown")
        },
        loadDown(){
            axios.get("/song/file/"+this.song.seq , {
                        headers: {
                            Authorization: `Bearer `+Cookies.get('accessToken')
                        }
                    })
                    .then((e)=>{
                        this.data = e.data
                        console.log(this.data)
                    })
                    .catch((error)=>{
                        console.log(error.response.status)
                        if(error.response.status==403){
                            const member = Cookies.get('member')
                            const refresh = Cookies.get('refreshToken')
                            axios.post("/member/refresh", {
                                id:member,
                                refresh:refresh
                            })
                            .then((e)=>{
                                Cookies.set('accessToken', e.data.token)
                                this.loadDown()
                            })
                            .catch((error)=>{
                                alert("다시 로그인해주세요")
                                Cookies.remove('refreshToken')
                                Cookies.remove('accessToken')
                                Cookies.remove('member')
                                sessionStorage.removeItem("nowIndex")
                                sessionStorage.removeItem("playlist")
                                this.$router.push("/login")
                            })
                        }
                    })
        },
        down(file){
            axios.get("/ticket/downcheck/"+this.song.seq,{
                headers: {
                    Authorization: `Bearer `+Cookies.get('accessToken')
                }
            }).then((e)=>{
                console.log(e)
                if(confirm(e.data.message)){
                    axios.get("/songfile/"+file.uri+"/"+this.song.seq+"?type=down",{
                        headers: {
                            Authorization: `Bearer `+Cookies.get('accessToken')
                        },
                        responseType: "blob"
                    })
                    .then((e)=>{
                        const url = window.URL.createObjectURL(new Blob([e.data]))
                        const link = document.createElement('a')
                        const fileName = file.uri + '.' + file.quality;
                        link.href = url
                        link.setAttribute('download', fileName);
                        document.body.appendChild(link)
                        link.click()
                    })
                    .catch((error)=>{
                        console.log(error.response.status)
                        if(error.response.status==403){
                            const member = Cookies.get('member')
                            const refresh = Cookies.get('refreshToken')
                            axios.post("/member/refresh", {
                                id:member,
                                refresh:refresh
                            })
                            .then((e)=>{
                                Cookies.set('accessToken', e.data.token)
                                this.down()
                            })
                            .catch((error)=>{
                                alert("다시 로그인해주세요")
                                Cookies.remove('refreshToken')
                                Cookies.remove('accessToken')
                                Cookies.remove('member')
                                sessionStorage.removeItem("nowIndex")
                                sessionStorage.removeItem("playlist")
                                this.$router.push("/login")
                            })
                        }
                    })
                }
            })
            .catch((error)=>{
                console.log(error)
                if(error.response.status==403){
                    const member = Cookies.get('member')
                    const refresh = Cookies.get('refreshToken')
                    axios.post("/member/refresh", {
                        id:member,
                        refresh:refresh
                    })
                    .then((e)=>{
                        Cookies.set('accessToken', e.data.token)
                        this.down()
                    })
                    .catch((error)=>{
                        alert("다시 로그인해주세요")
                        Cookies.remove('refreshToken')
                        Cookies.remove('accessToken')
                        Cookies.remove('member')
                        sessionStorage.removeItem("nowIndex")
                        sessionStorage.removeItem("playlist")
                        this.$router.push("/login")
                    })
                }else{
                    alert(error.response.data.message)
                }
            })
            // const FileDownload = FileDownload('mp3');
            
        }
    }
}
</script>