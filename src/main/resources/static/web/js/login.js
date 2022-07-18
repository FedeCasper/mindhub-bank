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
               }else if(this.password.length <= 4 || this.password.length >= 15){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'The password is either to short or too long!',
                    })
               }else if(!this.email.includes("@") || !this.email.includes(".") ){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'Please check the email!',
                    })
               }else{
               axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
               {headers:{'content-type':'application/x-www-form-urlencoded'}})
               .then(response => 
                    console.log("Registered!"))
               .catch(function(error){
                    if (error.response.status === 403) {
                         Swal.fire({
                              icon: 'error',
                              title: 'Oops...',
                              text: 'There is already a user with that email!',
                         })
                         console.log('Error', error.message);
                    }else {
                         Swal.fire({
                              icon: 'error',
                              title: 'Oops...',
                              text: 'Something went wrong!',
                         })
                         console.log('Error', error.message);
                    }
               })
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
          },

          show(){
               document.querySelector('.form-group span').addEventListener('click', e => {
                    const passwordInput = document.querySelector('#psw');
                    if (e.target.classList.contains('show')) {
                         e.target.classList.remove('show');
                         // e.target.textContent = 'Ocultar';
                         passwordInput.type = 'text';
                    } else {
                         e.target.classList.add('show');
                         // e.target.textContent = 'Mostrar';
                         passwordInput.type = 'password';
                    }
               })
          }

     },

}).mount('#app')

