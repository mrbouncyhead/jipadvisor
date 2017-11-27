var defaultView = {
  template: '#default-template'
}

var loginView = {
  template: '#login-template'
}

var registerView = {
  template: '#register-template',
  data: function () {
    return {
      email: '',
      password:'',
      confirmPassword:''
    }
  },
  methods: {
    register: function (event) {
      if(this.password === "" || this.password !== this.confirmPassword) {
        console.log('Invalid password');
      }

      debugger
    }
  }
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