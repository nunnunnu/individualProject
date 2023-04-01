<template>
<b-container>
    <div class="box">
    <br>
    <h3 align="left" class="pb-4 mb-4 fst-italic border-bottom">이용권 구매</h3>
    <div class="row">
        <div v-for="data in ticket" :key="data.seq" class="row" style="border: 1px solid gainsboro" >
            <div class="ticket">
                <div class="col-8">
                    <h4>{{ data.name }}</h4>
                    {{ data.ex }}
                </div>
                <div class="col-2">
                    {{ data.price }}
                </div>
                <div class="col-1">
                    <button class="btn btn-dark" @click="openPopup(data)">구매하기</button>
                </div>
            </div>
        </div>
    </div>
</div>
<buyPopup v-if="popup" @closePopup="closePopup" :ticket="data"/>
</b-container>
</template>
<script>
import Cookies from 'js-cookie'
import axios from 'axios'
import buyPopup from '../components/TicketBuy.vue'

export default {
    name: 'ticketView',
    components:{
        buyPopup
    },
    data() {
        return {
            isLogin: null,
            ticket:null,
            popup:false,
            data:null
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
            axios.get("/ticket",{
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
                    axios.post("/member/refresh", {
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
        },
        openPopup(data){
            this.data = data
            this.popup = true
            console.log(this.popup)
        },
        closePopup(){
            this.popup=false
        }
    }
}
</script>
<style>
.ticket{
    display: flex;
    padding: 30px;
    /* background-color: black; */
    /* color: white; */
    /* padding-bottom: 102px; */
    
}
.box{
    padding-bottom: 102px;
}
</style>