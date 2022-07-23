Vue.createApp({

     data() {
          return {
               client_current_accounts: [],
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
               this.client_current_accounts = data.data.sort((a, b) => {return a.id - b.id})
          })
     },

     methods:{

          create_own_transfer(){
               if(this.ownSourceAccountNumber == 0 || this.ownDestinationAccountNumber == 0 || this.ownAmount == 0 || this.ownDescription == 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'There are incomplete fields!',
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
               if(this.elseSourceAccountNumber == 0 || this.elseDestinationAccountNumber == 0 || this.elseAmount == 0 || this.elseDescription == 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'There are incomplete fields!',
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
                         document.location.reload()
                    }
               }))   
               .catch( error => error.message + "Oops! something happened, you couldn't make the transfer" )
          }
          }

     },

}).mount('#app')