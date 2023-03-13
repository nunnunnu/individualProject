<template>
<b-container>
    <h1>이용권 구매</h1>
    <div class="row">
        <div v-for="data in ticket" :key="data.seq" class="row" style="border: 1px solid gainsboro">
            <!-- <div class="ticket"> -->
                <div class="col-7">
                    <h4>{{ data.name }}</h4>
                    {{ data.ex }}
                </div>
                <div class="col-3">
                    {{ data.price }}
                </div>
                <div class="col-auto">
                    <button class="btn btn-dark">구매하기</button>
                </div>
            <!-- </div> -->
        </div>
    </div>
</b-container>
</template>
<script>
import Cookies from 'js-cookie'
import axios from 'axios'

export default {
    name: 'ticketView',
    data() {
        return {
            isLogin: null,
            ticket:null
        }
    },
    mounted(){
        if(Cookies.get("accessToken")==null){
            alert("로그인 후 이용가능한 서비스입니다.")
            this.$router.push("/login");
        }else{
            this.loadTicket()
        }

    },
    methods:{
        loadTicket(){
            axios.get("http://localhost:8250/ticket",{
                headers: {
                    Authorization: 'Bearer ' + Cookies.get("accessToken")
                }
            })
            .then((e)=>{
                this.ticket = e.data
            })
            .catch((error)=>{
                if(error.response.status==403){
                    const member = Cookies.get('member')
                    const refresh = Cookies.get('refreshToken')
                    axios.post("http://localhost:8250/member/refresh", {
                        id:member,
                        refresh:refresh
                    })
                    .then((e)=>{
                        Cookies.set('accessToken', e.data.token)
                        this.loadPage(this.seq)
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
<style>
.ticket{
    /* padding: 5px; */
    /* background-color: black; */
    /* color: white; */
    
}
</style>