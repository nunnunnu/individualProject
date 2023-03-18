<template>
    <b-container style="max-width: 400px;">
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">로그인</h2>
        <form @submit.prevent="submitForm">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">아이디</span>
                <input type="text" class="form-control" v-model="id" aria-describedby="basic-addon1">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">비밀번호</span>
                <input type="password" class="form-control" v-model="pwd" aria-describedby="basic-addon1">
            </div>
            <div class="d-grid gap-2 col-6 mx-auto">
                <button type="submit" class="btn btn-success">로그인</button>
                <br>
            </div>
        </form>
        <div>
            <img @click="loginWithKakao" class="clickable" src="@/assets/image/kakao_login_medium_wide.png" style="width: 100%">
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    export default {
        name: 'loginPage',
        data() {
            return {
                id:null,
                pwd:null,
                returnUrl:null
            }
        },
        created() {
            if(Cookies.get("accessToken")!=null){
                alert("이미 로그인되었습니다.")
                this.$router.go(-1);
            }
        },
        methods: {
            submitForm() {
                const data = {
                    id: this.id,
                    pwd: this.pwd
                }
                axios.post('http://localhost:8250/member/login', data)
                    .then(response => {
                        const token = response.data.token
                        Cookies.set('accessToken', token.accessToken)
                        Cookies.set('refreshToken', token.refreshToken)
                        Cookies.set('member', response.data.member)
                        if(Cookies.get('beforePage')!=null){
                            Cookies.remove('beforePage')
                            this.$router.push("/");
                        }else{
                            this.$router.go(-1);
                        }
                    })
                    .catch(error => {
                        alert(error.response.data.message);
                    });
            },
            loginWithKakao() {
                const authUrl = "https://kauth.kakao.com/oauth/authorize";
                const clientId = "7774d49dca8e78175a8edb237dd6761e";
                const redirectUri = "http://localhost:8080/kakao";
                const responseType = "code";
                const state = "kakao"; // 이 값은 보안을 위해 랜덤한 문자열을 생성해야 합니다.

                const url = `${authUrl}?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=${responseType}&state=${state}`;

                window.location.href=url

            }
            
        }
    }

</script>
<style>
.clickable:hover {
  cursor: pointer;
}
</style>