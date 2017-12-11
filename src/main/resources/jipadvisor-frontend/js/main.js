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
          this.login()
          return
        }
      });
    },
    login: function (event) {
      var user = {email:this.email, password:this.password};
      this.$http.post('/login', user).then(function (response){
        console.log(response)
      }, response => {
        this.email = ''
        this.password = ''
        alert('Invalid username or password')
      })
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
          this.register()
          return;
        }
      });
    },
    register: function () {
      var user = {email:this.email, password:this.password};
      this.$http.post('/register', user).then(function (response){
        localStorage.setItem('token',response.body)
      }, response => {
        this.email = ''
        this.password = ''
        console.log(response)
      });
    }
  }
}

Vue.component('main-menu', {
  name: 'main-menu',
  template: '#menu-template',
  computed: {
    email () {
      return this.$store.state.user.email
    }
  },
  methods: {
    logout: function () {
      localStorage.clear()
      router.push('home')
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

const store = new Vuex.Store({
  state: {
    user: {
      email: '',
      loggedIn: false
    }
  }
  
})

Vue.use(VeeValidate);
Vue.use(VueRouter);

const app = new Vue({
  router,
  store,
  beforeCreate: function () {
    var token = localStorage.getItem('token')
    if(token) {
      Vue.http.headers.common['token'] = token
      this.$http.get('/user').then(function (response) {
        this.user = response.body
      });
    }
  },
}).$mount('#app')