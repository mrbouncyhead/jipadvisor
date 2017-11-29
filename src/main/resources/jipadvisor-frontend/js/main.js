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
      var user = {fullName:'', email:this.email, password:this.password};
      this.$http.post('/register', user).then(function (response){
        localStorage.setItem('token',response.body);
      });
    }
  }
}

Vue.component('main-menu', {
  name: 'main-menu',
  template: '#menu-template',
  data: function () {
    return {
      token: localStorage.getItem('token');
    }
  },
  methods: {
    logout: function () {
      localStorage.clear();
      router.push('home');
    }
  }
})

var router = new VueRouter({
  routes: [{
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: defaultView
  },
  {
    path: '/login',
    component: loginView
  },
  {
    path: '/register',
    component: registerView
  }]
})

const app = new Vue({
  router
}).$mount('#app')