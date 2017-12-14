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
        var token = response.body;
        localStorage.setItem('token',token)
        Vue.http.headers.common['token'] = token
        router.push('profiles')
      }, response => {
        this.email = ''
        this.password = ''
        alert('Invalid username or password')
      })
    }
  }
}

var profilesView = {
  template: '#profiles-template',
  mounted: function () {
    this.$http.get('/user').then(function (response) {
      var email = response.body.email;
      this.$store.commit('loggedIn', true)
      this.$store.commit('email', email)
    }, response => {
      router.push('login')
      alert('Please login first')
    });
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
        var token = response.body;
        localStorage.setItem('token',token)
        Vue.http.headers.common['token'] = token
        router.push('profiles')
      }, response => {
        this.email = ''
        this.password = ''
        alert(response.body)
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
      this.$store.commit('loggedIn', false)
      this.$store.commit('email', '')
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
  },
  {
    path: '/profiles',
    component: profilesView
  }
  ]
});

const store = new Vuex.Store({
  state: {
    user: {
      email: '',
      loggedIn: false
    }
  },
  mutations: {
    loggedIn (state, isLoggedIn) {
      state.user.loggedIn = isLoggedIn
    },
    email (state, email) {
      state.user.email = email
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
    }
  },
}).$mount('#app')