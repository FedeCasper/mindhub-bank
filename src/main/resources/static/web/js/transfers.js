Vue.createApp({

     data() {
          return {
               client_current_accounts: [],
               amount: 0,
               description: '',
               sourceAccountNumber: '',
               destinationAccountNumber: '',
          }
     },

     created(){

          axios.get('http://localhost:8080/api/clients/current/accounts')
          .then(data => {
               this.client_current_accounts = data.data.sort((a, b) => {return a.id - b.id})
          })
     },

     methods:{

          create_transaction(){
               axios.post(`http://localhost:8080/api/transactions`,`amount=${this.amount}&description=${this.description}&sourceAccount=${this.sourceAccountNumber}&destinationAccount=${this.destinationAccountNumber}`,
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

}).mount('#app')