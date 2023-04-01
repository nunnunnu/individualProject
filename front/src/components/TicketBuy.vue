<template>
    <b-container>
        <div class="popup_wrap">
            <div class="popup">
                <div align="right">
                    <button class="btn btn-white" @click="closePopup">&times;</button>
                </div>
                <br>
                <h4>{{ ticket.name }} 구매</h4>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
                    <label class="form-check-label" for="flexRadioDefault1">
                        휴대폰
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2">
                    <label class="form-check-label" for="flexRadioDefault2">
                        신용카드
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault3">
                    <label class="form-check-label" for="flexRadioDefault3">
                        카카오페이
                    </label>
                </div>
                <br>
                <button class="btn btn-success" @click="buy">구매</button>
            </div>
        </div>
    </b-container>
</template>
<script>
    import Cookies from 'js-cookie'
    import axios from 'axios'

    export default {
        name: 'ticketBuy',
        props: {
            ticket: Object
        },
        data() {
            return {
                isLogin: null,
            }
        },
        mounted() {
            if (Cookies.get("accessToken") == null) {
                alert("로그인 후 이용가능한 서비스입니다.")
                this.$router.push("/login");
            }
            // this.ticket = this.$route.params.ticket
            console.log(this.ticket)
        },
        methods: {
            closePopup() {
                this.$emit("closePopup")
            },
            buy(){
                axios.put("/ticket/"+this.ticket.seq, {}, 
                {
                    headers: {
                        "Content-Type": `application/json`,
                        Authorization: 'Bearer ' + Cookies.get("accessToken")
                        }
                })
                .then((e) => {
                    console.log(e)
                    if(e.data.status==false){
                        alert(e.data.message)
                    }else{
                        alert("구매완료")
                    }
                    this.$emit("closePopup")
                })
                .catch((error)=>{
                    const member = Cookies.get('member')
                    const refresh = Cookies.get('refreshToken')
                    axios.post("/member/refresh", {
                    id:member,
                    refresh:refresh
                    })
                    .then((e)=>{
                    console.log(e.data.token)
                    Cookies.set('accessToken', e.data.token)
                    this.loadUserInfo(e.data.token)
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
                })
            }
        }
    }
</script>
<style>
    .ticket {
        display: flex;
        padding: 30px;
        /* background-color: black; */
        /* color: white; */
        /* padding-bottom: 102px; */

    }

    .box {
        padding-bottom: 102px;
    }
</style>