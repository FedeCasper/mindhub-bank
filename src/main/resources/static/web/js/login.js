Vue.createApp({
     
     data() {
          return {
          
               email:'',
               password:'',
               error:'',
               firstName: '',
               lastName: '',
          }
     },

     created(){

     },

     methods:{

          register(){
               if(this.firstName == 0 || this.lastName == 0 || this.email == 0 || this.password == 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'There are incomplete fields!',
                    })
               }else{
               axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
               {headers:{'content-type':'application/x-www-form-urlencoded'}})
               .then(response => 
                    console.log('registered'))
               .then(
                    Swal.fire({
                         position: 'center',
                         icon: 'success',
                         title: 'User created',
                         showConfirmButton: true,
                    })
               .then((result) => {
                    if (result.isConfirmed) {   
                         this.login()
                    }
               }))
          }
          },

          login(){
               axios.post('/api/login',`email=${this.email}&password=${this.password}`,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(response => 
                         window.location.href = "/web/accounts.html",
                         console.log('signed in!!!'))
                    .catch(function(error){
                         window.alert("El correo no pertenece a un usuario del banco", error.message)
                    })
          }

     },

}).mount('#app')

