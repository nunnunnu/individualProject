<template>
    <b-container>
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
            </div>
        </form>
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
                        if(Cookies.get('beforePage')!=null){
                            this.$router.push("/");
                        }else{
                            this.$router.go(-1);
                        }
                    })
                    .catch(error => {
                        alert(error.response.data.message);
                    });
            }
    }
}
</script>
<style>

</style>