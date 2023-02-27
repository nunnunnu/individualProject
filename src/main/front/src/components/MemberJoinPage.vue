<template>
    <b-container>
    <br>
    <h2 class="pb-4 mb-4 fst-italic border-bottom">회원가입</h2>
    <br>
    <form class="row g-3" onsubmit="return false;">
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="아이디">
            </div>
            <div class="col-auto">
                <input type="text" class="form-control" id="inputPassword2" v-model="inputId">
            </div>
            <div class="col-auto">
                <button class="btn btn-success mb-3" @click="idCheck(inputId)">중복확인</button>
            </div>
            <div class="col-auto">
                <a v-if="isDuplicate">중복 아이디입니다.</a>
                <a v-if="isDuplicate!=null && !isDuplicate">사용가능한 아이디입니다</a>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="비밀번호">
            </div>
            <div class="col-auto">
                <input type="password" class="form-control" id="inputPassword2" v-model="pwd">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="비밀번호 확인">
            </div>
            <div class="col-auto">
                <input type="password" class="form-control" id="inputPassword2" v-model="checkPwd">
            </div>
            <div class="col-auto">
                <p v-if="pwd!=null && checkPwd!=null && pwd==checkPwd"> 비밀번호가 일치합니다.</p>
                <font v-if="pwd!=null && checkPwd!=null && pwd!=checkPwd" color="red"> 비밀번호가 일치하지않습니다.</font>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="이름">
            </div>
            <div class="col-auto">
                <input type="text" class="form-control" id="inputPassword2" v-model="name">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="닉네임">
            </div>
            <div class="col-auto">
                <input type="text" class="form-control" id="inputPassword2" v-model="nickname">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="나이">
            </div>
            <div class="col-auto">
                <input type="number" class="form-control" id="inputPassword2" v-model="age">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="전화번호">
            </div>
            <div class="col-auto">
                <input type="text" class="form-control" id="inputPassword2" v-model="phone">
            </div>
            <div class="col-auto">
                <p>형식 : 010-0000-0000 or 000-000-0000 or 00-000-0000</p>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="이메일">
            </div>
            <div class="col-auto">
                <input type="email" class="form-control" id="inputPassword2" v-model="email">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="생년월일">
            </div>
            <div class="col-auto">
                <input type="date" class="form-control" id="inputPassword2" v-model="birth">
            </div>
        </div>
        <button type="submit" class="btn btn-success" @click="memberJoin">회원가입</button>
    </form>
    </b-container>
</template>
<script>
import Cookies from 'js-cookie'
import axios from 'axios'
import router from '@/router'

export default {
    
    name:"joinPage",
    data(){
        return{
            isDuplicate:null,
            inputId:null,
            pwd: null,
            name: null,
            age: null,
            phone: null,
            email: null,
            birth: null,
            nickname:null
        }
    },
    created() {
        if(Cookies.get('accessToken')!=null){
            alert("이미 로그인하셨습니다.")
            this.$router.push("/")
        }
    },
    watch: {
        inputId(){
            this.isDuplicate=null
        }
    },
    methods:{
        idCheck(id){
            if(id==undefined || id=="" || id==""){
                alert("아이디를 입력하지않으셨습니다")
            }else{
                axios.get("http://localhost:8250/member/id?id="+id)
                .then((e) => {
                            if(e.data.status){
                                this.isDuplicate=false
                                this.inputId=id
                            }else{
                                this.isDuplicate=true
                                // this.inputId=null
                            }
                        })
            }
        },
        memberJoin(){
            if(this.isDuplicate || this.isDuplicate==null){
                alert("아이디 중복 확인을 하지않으셨습니다.")
            }else if(this.inputId==null || this.inputId=="" ||this.inputId==undefined){
                alert("아이디를 입력하지 않으셨습니다.")
            }else if(this.pwd==null || this.pwd=="" ||this.pwd==undefined){
                alert("비밀번호를 입력하지 않으셨습니다.")
            }else if(this.pwd != this.checkPwd){
                alert("확인 비밀번호가 일치하지않습니다.")
            }else{
                const data = {
                    id: this.inputId,
                    pwd: this.pwd,
                    name: this.name,
                    age: this.age,
                    phone: this.phone,
                    email: this.email,
                    birth: this.birth,
                    nickName: this.nickname
                }
                axios.put("http://localhost:8250/member/join", data)
                    .then((e) => {
                        alert(e.data.message)
                                if(e.data.status){
                                    Cookies.set('beforePage', true)
                                    this.$router.push("/login")
                                }
                            })
            }
        }
    }
}
</script>
<style>

</style>