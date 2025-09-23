
import VueRouter from 'vue-router';

const routes = [
    {
        path:'/',
        name:'HomePage',
        meta:{
            title:'主页'
        },
        component:()=>import('../components/HomePage.vue'),
    },
    {
        path:'/HomePage',
        name:'HomePage',
        meta:{
            title:'主页'
        },
        component:()=>import('../components/HomePage.vue'),
    },
    {
        path:'/login',
        name:'login',
        component:()=>import('../components/Login.vue')
    },
    {
        path:'/Register',
        name:'Register',
        component:()=>import('../components/Register.vue')
    },
    {
        path:'/SuperAdmin',
        name:'superAdmin',
        component:()=>import('../components/SuperAdmin'),
        children:[

        ]
    },
    {
        path:'/Admin',
        name:'Admin',
        component:()=>import('../components/Admin.vue'),
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

    // 教练模块路由
    {
        path: '/coach-profile',
        name: 'CoachProfile',
        meta: {
            title: '个人信息管理'
        },
        component: () => import('../components/coach/CoachProfile')
    },
    {
        path: '/coach-selection',
        name: 'CoachSelection',
        meta: {
            title: '学员双选管理'
        },
        component: () => import('../components/coach/CoachSelection')
    },
    {
        path: '/coach-schedule',
        name: 'CourseSchedule',
        meta: {
            title: '课程时间管理'
        },
        component: () => import('../components/coach/CourseSchedule')
    },
    {
        path: '/coach-requests',
        name: 'CourseRequests',
        meta: {
            title: '预约请求管理'
        },
        component: () => import('../components/coach/CourseRequests')
    },
    {
        path: '/coach-students',
        name: 'MyStudents',
        meta: {
            title: '我的学员管理'
        },
        component: () => import('../components/coach/MyStudents')
    },
    {
        path: '/coach-courses',
        name: 'ConfirmedCourses',
        meta: {
            title: '已确认课程'
        },
        component: () => import('../components/coach/ConfirmedCourses')
    },
    {
        path: '/coach-dashboard',
        name: 'coach',
        component: () => import('../components/coach/CoachDashboard'),
        children: [

        ]
    },
    {
        path: '/student',
        name: 'student',
        component: () => import('../components/Student'),
        children: [

        ]
    },
    /*
    {
        path:'/Index',
        name:'index',
        component:()=>import('../components/Index'),
        children:[
            {
                path:'/Home',
                name:'home',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/Home')
            }
        ]
    }
    */
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