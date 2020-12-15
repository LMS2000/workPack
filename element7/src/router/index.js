import Vue from 'vue'
import {getCookie} from '../static/Cookie.js'
import {passOrNo} from '../static/auth.js'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect:'/login',
  },
  {
    path:'/login',
    name:'login',
    component:()=>import('../views/login.vue')
    
  },
  {
    path:'/home',
    name:'home',
    component:()=>import('../views/appMain.vue'),
    children:[
      {
        path:'classOrder',
        name:'classOrder',
        component:()=>import('../components/class/classOrder.vue')
      },
      {
        path:'courseOrder',
        name:'courseOrder',
        component:()=>import('../components/course/courseOrder.vue')
      },
      {
        path:'learningOrder',
        name:'learningOrder',
        component:()=>import('../components/learning/learningOrder.vue')
      },
      {
        path:'courseChoosing',
        name:'courseChoosing',
        component:()=>import('../components/course/courseChoosing.vue')
      },
      {
        path:'courseFinalScore',
        name:'courseFinalScore',
        component:()=>import('../components/course/courseFinalScore.vue')
      }
    ]
  },
  {
    path:'/adminHome',
    name:'adminHome',
    component:()=>import('../views/appMain.vue'),
    children:[
      {
        path:'dailyRecord',
        name:'dailyRecord',
        component:()=>import('../components/AOP/dailyRecord.vue')
      },
      {
        path:'teacherOrder',
        name:'teacherOrder',
        component:()=>import('../components/teacher/teacherOrder.vue')
      },
      {
        path:'courseAdmin',
        name:'courseAdmin',
        component:()=>import('../components/course/courseAdmin.vue')
      },
			{
				path:'classMaster',
				name:'classMaster',
				component:()=>import('../components/class/classMaster.vue')
			}
    ]
  },
  {
    path:'/teacherHome',
    name:'teacherHome',
    component:()=>import('../views/appMain.vue'),
    children:[
      {
        path:'classManagement',
        name:'classManagement',
        component:()=>import('../components/class/classManagement.vue')
      },
      {
        path:'substitute',
        name:'substitute',
        component:()=>import('../components/course/substitute.vue')
      },
      {
        path:'msgTeacher',
        name:'msgTeacher',
        component:()=>import('../components/msg/msgTeacher.vue')
      }
    ]
  }
]


const router = new VueRouter({
  mode:'history',
  routes
});
router.beforeEach((to,from,next)=>{
  let roleId=getCookie();
  let path = to.path;
  let children='';
	if(roleId=='1')
	{
		children=routes[2].children;
	}else if(roleId=='2')
	{
		children=routes[4].children;
	}else if(roleId=='3')
	{
		children=routes[3].children;
	}
  let is_pass=  passOrNo(roleId,children,path);
   console.log(is_pass)
  if(is_pass==true){
    next();
  }
  
})
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push (location) {

return originalPush.call(this, location).catch(err => err)

}

export default router
