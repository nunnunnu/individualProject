<template>
    <b-container>
    <div class="myPage">
            
            <h2 class="pb-4 mb-4 fst-italic border-bottom">마이 뮤직</h2>
            <div class="area">
                <h4 class="pb-4 mb-4 fst-italic border-bottom">선호 장르</h4>
                <Pie :data="chartDataReturn()" :options="options" />
            </div>
            <br>
            <br>
            <br>
            <br>
            <br>
        <h4 class="pb-4 mb-4 fst-italic border-bottom">플레이이스트</h4>
    </div>
    </b-container>
</template>
<script>
    import Cookies from 'js-cookie'
    import axios from 'axios'
    import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
    import { Pie } from 'vue-chartjs';
    // import * as chartConfig from '@/components/chartConfig.js';

    ChartJS.register(ArcElement, Tooltip, Legend);
    
    export default {
        name: 'myPage',
        components: {
            Pie,
        },
        data() {
            return {
                chart:null,
                labels:null,
                isLogin:null,
                options : {
                    responsive: true,
                    maintainAspectRatio: false
                }
            }
        },
        created() {
            if (Cookies.get('accessToken') != null) {
                this.isLogin = true
                this.loadPage()
            } else {
                alert("로그인 후 이용가능한 서비스입니다.")
                this.isLogin = false
                this.$router.push("/login")
            }
        },
        methods: {
            loadPage() {
                axios.get("http://localhost:8250/listen/genre", {
                    headers: {
                    Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((e) => {
                    this.chart = e.data.data
                    this.labels = e.data.data.labels
                    this.data = e.data.data.data
                    // console.log(this.chart)
                })
            },
            chartDataReturn(){
                return {
                    labels: this.labels,
                    datasets: [
                        {
                            backgroundColor: ['#B9F3E4', '#EA8FEA', '#FFAACF', '#F6E6C2', '#F6F7C1','#BEF0CB','#D1FFF3','#C1AEFC'],
                            data: this.data
                        }
                    ],
                }//https://colorhunt.co/
            }
        }
    }
</script>

<style>
    .typeInfo {
        font-size: 15px;
        color: gray;
    }

    p {
        font-size: 15px;
    }

    .albums {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        object-fit: cover;
    }
    .area {height:300px;}
    .myPage{
        padding-bottom: 102px;
    }
</style>