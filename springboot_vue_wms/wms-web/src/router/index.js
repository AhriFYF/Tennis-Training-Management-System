
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
        component:()=>import('../components/SuperAdmin'),
        children:[

        ]
    },
    {
        path:'/SuperAdmin-userManage',
        name:'UserManage',
        meta:{
            title:'用户管理'
        },
        component:()=>import('../components/admin/UserManage'),
    },
    {
        path:'/SuperAdmin-adminManage',
        name:'AdminManage',
        meta:{
            title:'管理员管理'
        },
        component:()=>import('../components/admin/AdminManage')
    },
    {
        path:'/SuperAdmin-coachManage',
        name:'CoachManage',
        meta:{
            title:'管理员管理'
        },
        component:()=>import('../components/admin/CoachManage')
    },
    {
        path:'/SuperAdmin-studentManage',
        name:'StudentManage',
        meta:{
            title:'学生管理'
        },
        component:()=>import('../components/admin/StudentManage')
    },
    {
        path:'/SuperAdmin-courseInfoManage',
        name:'CourseInfoManage',
        meta:{
            title:'课程管理'
        },
        component:()=>import('../components/course/CourseInfoManage')
    },
    {
        path:'/SuperAdmin-studentCourseSelection',
        name:'StudentCourseSelection',
        meta:{
            title:'学生选课管理'
        },
        component:()=>import('../components/admin/StudentCourseSelection')
    },
    {
        path:'/SuperAdmin-coachSchedule',
        name:'CoachSchedule',
        meta:{
            title:'教练排课管理'
        },
        component:()=>import('../components/admin/CoachSchedule')
    },
    {
        path:'/SuperAdmin-teachingEvaluation',
        name:'TeachingEvaluation',
        meta:{
            title:'教学评价管理'
        },
        component:()=>import('../components/admin/TeachingEvaluation')
    },
    {
        path:'/SuperAdmin-coachCancel',
        name:'CoachCancel',
        meta:{
            title:'教练消课管理'
        },
        component:()=>import('../components/admin/CoachCancel')
    },
    {
        path:'/SuperAdmin-campus',
        name:'Campus',
        meta:{
            title:'校区管理'
        },
        component:()=>import('../components/admin/Campus')
    },
    {
        path:'/SuperAdmin-systemLog',
        name:'SystemLog',
        meta:{
            title:'系统日志'
        },
        component:()=>import('../components/admin/SystemLog')
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