Vue.createApp({
     
     data() {
          return {
          
               email:'',
               password:'',
               error:'',
               firstName: '',
               lastName: '',
               login_card : document.querySelector("#login_form"),
               register_card : document.querySelector("#register_form"),
          }
     },

     created(){


     },

     methods:{


          login(){
               axios.post('/api/login',`email=${this.email}&password=${this.password}`,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(response => 
                         window.location.href = "/web/accounts.html",
                         console.log('signed in!!!'))
                    .catch(function(error){
                         window.alert("El correo no pertenece a un usuario del banco", error.message)
                    })
          },

          register(){
               axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
               {headers:{'content-type':'application/x-www-form-urlencoded'}})
               .then(response => 
                    window.location.href = "/web/accounts.html",
                    console.log('registered'))
               .then(this.login())
          },

     hideform(){
          register_form = document.querySelector("#register_form")
          register_form.style.visibility = "hidden";
     }

     }, // Cierre de (methods)

}).mount('#app')

