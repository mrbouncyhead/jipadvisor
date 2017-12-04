var defaultView = {
  template: '#default-template'
}

var loginView = {
  template: '#login-template',
  data: function () {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    validateBeforeSubmit() {
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.login();
          return;
        }
      });
    },
    login: function (event) {
      var user = {email:this.email, password:this.password};
      this.$http.post('/login', user).then(function (response){
        console.log(response);
      });
    }
  }
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
    validateBeforeSubmit() {
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.register();
          return;
        }
      });
    },
    register: function () {
      var user = {email:this.email, password:this.password};
      this.$http.post('/register', user).then(function (response){
        localStorage.setItem('token',response.body);
      });
    }
  }
}

Vue.component('main-menu', {
  name: 'main-menu',
  template: '#menu-template',
  mounted: function () {
    this.$http.get('/user').then(function (response) {
      this.user = response.body;
    });
  },
  data: function () {
    return {
      token: localStorage.getItem('token'),
      user: {
        email: ''
      }
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
});

Vue.use(VeeValidate);
Vue.use(VueRouter);

const app = new Vue({
  router,
  beforeCreate: function () {
    var token = localStorage.getItem('token');
    if(token)
      Vue.http.headers.common['token'] = token;
  },
}).$mount('#app')