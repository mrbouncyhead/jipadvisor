var defaultView = {
  template: '#default-template'
}

var loginView = {
  template: '#login-template'
}

var registerView = {
  template: '#register-template'
}

Vue.component('main-menu', {
  name: 'main-menu',
  template: '#menu-template'
})

var router = new VueRouter({
  routes: [{
    path: '/', component: defaultView
  },
  {
    path: '/login', component: loginView
  },
  {
    path: '/register', component: registerView
  }]
})

const app = new Vue({
  router
}).$mount('#app')