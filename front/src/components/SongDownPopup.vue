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
export default {
    data() {
        
    },
    methods:{
        closeDown(){
            this.$emit("closeDown")
        }
    }
}
</script>