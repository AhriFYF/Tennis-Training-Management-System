
import VueRouter from 'vue-router';

const routes = [
    {
        path:'/',
        name:'login',
        component:()=>import('../components/Login')
    },
    {
        path:'/SuperAdmin',
        name:'superAdmin',
        component:()=>import('../components/SuperAdmin')
    },
    {
        path:'/Index',
        name:'index',
        component:()=>import('../components/Index'),
        children:[
            {
                path:'', // 默认路径，当访问 /Index 时匹配
                redirect:'/Home' // 重定向到 /Home
            },
            {
                path:'/Home',
                name:'home',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/Home')
            },
            /*{
                path:'/Admin',
                name:'admin',
                meta:{
                    title:'管理员管理'
                },
                component:()=>import('../components/admin/AdminManage.vue')
            },
            {
                path:'/User',
                name:'user',
                meta:{
                    title:'用户管理'
                },
                component:()=>import('../components/user/UserManage.vue')
            },*/
        ]
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})

export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export  default router;