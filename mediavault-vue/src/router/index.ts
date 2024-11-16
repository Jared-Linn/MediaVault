   import { createRouter, createWebHashHistory } from 'vue-router';

   const routes = [
     {
       path: '/',
       redirect: '/home'
     },

     {
       path: '/login',
       name: 'login',
       component: () => import('../views/login.vue')
     },

       {
           path: '/space',
           name: 'index',
           component: () => import('../views/personalspace/index.vue')
       },

     {
       path: '/home',
       name: 'home',

       component: () => import('../views/home/index.vue')
     }
   ];

   const router = createRouter({
     history: createWebHashHistory(),
     routes
   });

   // 是一个全局前置守卫，可以在每次路由切换之前执行一些逻辑
   // router.beforeEach((to, from, next) => {
   //   next();
   // });

   export default router;
   