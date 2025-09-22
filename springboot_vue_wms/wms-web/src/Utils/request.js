// src/utils/request.js
import axios from 'axios'

// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8080', // 后端地址
    timeout: 10000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
    config => {
        return config
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)

// 响应拦截器
service.interceptors.response.use(
    response => {
        const res = response.data
        return res
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)

export default service