Vue.createApp({

     data() {
          return {
               charging: true,
               client: [],
               clients: [],
               clients_sort: [],
               accounts: [],
               loans:[],
               currentClientAccounts: [],
          }
     },

     created(){
          
          axios.get(`/api/clients/current`)
               .then(datos => {
                    this.client = datos.data
                    this.accounts = datos.data.accounts.sort((a, b) => {return a.id - b.id;})
                    this.loans = datos.data.clientLoans
               }),

          setTimeout(() => { this.charging = false }, 2000)
     },


     methods:{

          checkButtons(){
               axios.get('/api/clients/current/accounts')
               .then(data => {
                    addAccountButton = document.querySelector("#create_account_button")
                    this.currentClientAccounts = data.data.length
                         if(this.currentClientAccounts == 3){
                         addAccountButton.style.visibility = "hidden";
                    }
               })

               axios.get('/api/clients/current/clientLoans')
               .then(data => {
                    addLoanButton = document.querySelector("#create_loan")
                    this.currentClientLoans = data.data.length
                         if(this.currentClientLoans >= 3){
                              addLoanButton.style.display = "none";
                    }
               })
          },

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

          createAccount(){
               axios.post('/api/clients/current/accounts')
                    .then(response =>
                         location.reload(),
                         console.log('The account was created successfully!!!'))
                    .catch( error => error.message + "The account could not be created")
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