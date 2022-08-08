Vue.createApp({

     data() {
          return {
               charging: true,
               client_accounts: [],
               ownAmount: 0,
               ownDescription: '',
               ownSourceAccountNumber: '',
               ownDestinationAccountNumber: '',
               elseAmount: 0,
               elseDescription: '',
               elseSourceAccountNumber: '',
               elseDestinationAccountNumber: '',
          }
     },

     created(){

          axios.get('/api/clients/current/accounts')
          .then(data => {
               this.client_accounts = data.data.sort((a, b) => {return a.id - b.id})
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

          create_own_transfer(){
               if(this.ownSourceAccountNumber == 0 || this.ownDestinationAccountNumber == 0 || this.ownDescription == 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'There are incomplete fields!',
                    })
               }else if(this.ownAmount <= 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'The amount cannot be less than or equal to 0!',
                    })
               }else{
               axios.post(`/api/transactions`,
               `amount=${this.ownAmount}&description=${this.ownDescription}&sourceAccount=${this.ownSourceAccountNumber}&destinationAccount=${this.ownDestinationAccountNumber}`,
               {headers:{'content-type':'application/x-www-form-urlencoded'}})
               .then(
                    Swal.fire({
                         position: 'center',
                         icon: 'success',
                         title: 'Transaction realized!',
                         showConfirmButton: true,
                    })
               .then((result) => {
                    if (result.isConfirmed) {   
                         console.log("transfer has been realized!")
                         document.location.reload()
                    }
               }))   
               .catch( error => error.message + "Oops! something happened, you couldn't make the transfer" )
          }
          },

          create_else_transfer(){
               if(this.elseSourceAccountNumber == 0 || this.elseDestinationAccountNumber == 0 || this.elseDescription == 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'There are incomplete fields!',
                    })
               }else if(this.elseAmount <= 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'The amount cannot be less than or equal to 0!',
                    })
               }else{
               axios.post(`/api/transactions`,
               `amount=${this.elseAmount}&description=${this.elseDescription}&sourceAccount=${this.elseSourceAccountNumber}&destinationAccount=${this.elseDestinationAccountNumber}`,
               {headers:{'content-type':'application/x-www-form-urlencoded'}})
               .then(
                    Swal.fire({
                         position: 'center',
                         icon: 'success',
                         title: 'Transaction realized!',
                         showConfirmButton: true,
                    })
               .then((result) => {
                    if (result.isConfirmed) {   
                         console.log("transfer has been realized!")
                         setTimeout(() => { document.location.reload() }, 2000)
                    }
               }))   
               .catch( error => error.message + "Oops! something happened, you couldn't make the transfer" )
          }
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

     },

}).mount('#app')