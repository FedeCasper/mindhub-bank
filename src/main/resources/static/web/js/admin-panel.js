Vue.createApp({
     
     data() {
          return {
               charging: true,
               client:[],
               clients:[],
          }
     },

     created(){

          axios.get('/api/clients/current')
               .then(datos => {
                    this.client = datos.data
               })

          axios.get('/api/clients')
               .then(datos => {
                    this.clients = datos.data
                    console.log(this.clients)
               })

               setTimeout(() => { this.charging = false }, 2000)
     },

     methods:{

          logout(){
               Swal.fire({
                    title: 'Do you want to leave the site?',
                    text: "This will close your session",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#1b1c1a',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Confirm!'
               })
               .then((result) => {
                    if (result.isConfirmed) {   
                         axios.post('/api/logout')
                         .then(window.location.href = '/web/index.html')
                    }
               })
               .catch( error => error.message + "Oops! something happened, you couldn't log out" )
          },

     }, // Cierre de (methods)

}).mount('#app')

