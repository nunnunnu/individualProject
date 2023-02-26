<template>
    <b-container>
    <br>
    <h2 class="pb-4 mb-4 fst-italic border-bottom">회원가입</h2>
    <form class="row g-3">
        <div class="col-auto">
            <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="아이디">
        </div>
        <div class="col-auto">
            <input type="text" class="form-control" id="inputPassword2" v-model="id">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-success mb-3" @click="idCheck">중복확인</button>
        </div>
    </form>
    </b-container>
</template>
<script>
export default {
    name:"joinPage",
    data(){
        return{

        }
    },
    methods:{
        idCheck(id){
            
        }
    }
}
</script>
<style>

</style>