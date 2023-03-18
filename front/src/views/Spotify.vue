<template>
<div></div>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    export default {
        name: 'kakoLogin',
        mounted(){
            const searchParams = new URLSearchParams(window.location.search);

            // code 파라미터 추출
            const code = searchParams.get('code');

            console.log(code)

            axios.get("kakao?code="+code)
            .then((e)=>{
                Cookies.set('accessToken', e.data.token.accessToken)
                Cookies.set('refreshToken', e.data.token.refreshToken)
                this.$router.push("/")
            })
        },
        methods:{
        }
        
    }
</script>
<style>
</style>