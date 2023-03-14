<template>
    <b-container>
        <div class="popup_wrap">
            <div class="popup">
                <div align="right">
                    <button class="btn btn-white" @click="closePopup">&times;</button>
                </div>
                <br>
                <h4>이용권 구매</h4>
    
            </div>
        </div>
    </b-container>
    </template>
    <script>
    import Cookies from 'js-cookie'
    import axios from 'axios'
    
    export default {
        name: 'ticketBuy',
        props:{
            ticket:Object
            },
        data() {
            return {
                isLogin: null,
            }
        },
        mounted(){
            if(Cookies.get("accessToken")==null){
                alert("로그인 후 이용가능한 서비스입니다.")
                this.$router.push("/login");
            }
            // this.ticket = this.$route.params.ticket
            console.log(this.ticket)
        },
        methods:{
            
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