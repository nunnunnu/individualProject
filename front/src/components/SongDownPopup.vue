<template>
    <b-container>
        <div class="popup_wrap">
        <div class="popup">
            <div align="right">
                <button class="btn btn-white" @click="closeDown">&times;</button>
            </div>
            <br>
            <h4>음원 다운로드</h4>
            
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
            axios.get("http://localhost:8250/song/file/"+this.song.seq , {
                        headers: {
                            Authorization: `Bearer `+Cookies.get('accessToken')
                        }
                    })
                    .then((e)=>{
                        this.data = e.data
                        console.log(e)
                    })
                    .catch((error)=>{
                        console.log(error.response.status)
                        if(error.response.status==403){
                            const member = Cookies.get('member')
                            const refresh = Cookies.get('refreshToken')
                            axios.post("http://localhost:8250/member/refresh", {
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
        }
    }
}
</script>