Vue.createApp({
     data() {
          return {
               client: [],
               charging: true,
               account_vin001: [],
               transactions_vin001: [],
               client_accounts: [],
          }
     },

     created(){
          const urlParams = new URLSearchParams(window.location.search);
          const id = urlParams.get('id');
          console.log(id)

          axios.get(`/api/clients/current`)
               .then(datos => {
                    this.client = datos.data
               }),

          axios.get('/api/clients/current/accounts')
               .then(data => {
                    this.client_accounts = data.data.sort((a, b) => {return a.id - b.id;})
               }),

          axios.get('/api/accounts/' + id)
               .then(datos => {
                    this.account_vin001 = datos.data
                    this.transactions_vin001 = datos.data.transactions.sort((a, b) => {return a.id - b.id;})
               })
               
          setTimeout(() => { this.charging = false }, 2000)
     },


     methods:{
          
          formatearFecha(fecha){
               let date = new Date (fecha)
               let year = date.getFullYear()  
               let day = date.getDay() 
               let array_year = Array.from(year.toString()).slice(-2).join("")
               let month = date.getMonth() +1
               if(month < 10){
                    month = "0" + month
               }
               let day_month_year = day + "/" + month + "/" + array_year
               return day_month_year
          },

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