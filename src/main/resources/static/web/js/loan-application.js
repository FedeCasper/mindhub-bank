Vue.createApp({


     data() {
          return {
               // array de tipos de loans
               available_loans: [],
               // v-model capturados
               loan_selected: [],
               payment_selected: [],
               // inputs formulario
               amount: 0,
               destination_account: "",
               description: "",
               // función filtro
               filtered_loan: [],
               loan_payments:[],

               currentClientAccounts:[],
               taxes: 0,
          }
     },


     created(){
          axios.get('/api/loans')
          .then(data => {
               this.available_loans = data.data
               console.log(this.available_loans)
          }),

          axios.get('/api/clients/current/accounts')
          .then(data => {
               this.currentClientAccounts = data.data
               console.log(this.currentClientAccounts)
          })
     },


     methods:{
          filtro(){
               this.filtered_loan = this.available_loans.filter(prestamos => prestamos.id == this.loan_selected)
               console.log(this.filtered_loan)
               this.loan_payments = this.filtered_loan[0].payments
               console.log(this.loan_payments)
          },

          confirmLoan(){
               if(this.amount == 0 || this.payment_selected == 0 || this.destination_account == 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'There are incomplete fields!',
                    })
               }else if(this.amount <= 0){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'The requested amount cannot be a negative value!',
                    })
               }
               else{
                    // defino el objeto que le paso a mi método post
                    let requested_loan = {
                         id: this.loan_selected,
                         amount: this.amount,
                         payment: this.payment_selected,
                         destinationAccountNumber: this.destination_account
                    }
                    // disparo el menu de confirmar
                    Swal.fire({
                         title: 'Do you confirm the request?',
                         text: "You won't be able to revert this!",
                         icon: 'warning',
                         showCancelButton: true,
                         confirmButtonColor: '#1b1c1a',
                         cancelButtonColor: '#d33',
                         confirmButtonText: 'Confirm!'
                    // al confirmar se ejecuta el método post
                    }).then((result) => {
                         if (result.isConfirmed) {   
                              axios.post('/api/loans', requested_loan)
                              .then(console.log("Loan has been created SUCCESFULLY!"))
                              .catch(error => error.message ) 
                         // disparo el menu confirmado
                         Swal.fire(
                         'Requested!',
                         'Thanks for trusting us',
                         'success'
                         )
                         }
                    })
               }

          },

          calculateTaxes(){
               this.taxes = this.amount * 1.20 / this.payment_selected
          }


     }, // Cierre de (methods)


     
     }).mount('#app')