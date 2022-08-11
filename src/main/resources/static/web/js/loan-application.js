Vue.createApp({


     data() {
          return {
               client: [],
               charging: true,
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
               currentClientLoansRequested: [],
               taxes: 0,
          }
     },


     created(){
          axios.get('/api/loans')
          .then(data => {
               this.available_loans = data.data
          }),

          axios.get('/api/clients/current')
          .then(data => {
               this.client = data.data
               this.currentClientLoansRequested = data.data.clientLoans.map(loan => loan.loanId)
          })

          axios.get('/api/clients/current/accounts')
          .then(data => {
               this.currentClientAccounts = data.data
          })

          setTimeout(() => { this.charging = false }, 2000)

          
     },


     methods:{

          filtro(){
               this.filtered_loan = this.available_loans.filter(prestamos => prestamos.id == this.loan_selected)
               this.loan_payments = this.filtered_loan[0].payments

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
                         text: 'The amount cannot be less than or equal to 0!',
                    })
               }
               else if(this.currentClientLoansRequested.includes(this.loan_selected)){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'This type of laon has alredy been requested.',
                    })
               }
               else if(this.amount > this.filtered_loan[0].maxAmount){
                    Swal.fire({
                         icon: 'error',
                         title: 'Oops...',
                         text: 'The amount requested is greater than the amount allowed for this loan.',
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
                         Swal.fire({
                              title: 'Loan requested!',
                              text: "You can now find it in your Loans Storage",
                              icon: 'success',
                              showCancelButton: false,
                              confirmButtonColor: '#1b1c1a',
                              confirmButtonText: 'Confirm!'
                         // al confirmar se ejecuta el método post
                         })
                         .then((result) => {
                              if (result.isConfirmed) {setTimeout(() => { document.location.reload() }, 2000)}
                         })
                         }
                         
                    })

               }
               
          },

          loanIdFormat(id){
               if(id === 1){
                    id = "Mortgage"
               }else if(id === 2){
                    id = "Personal"
               }else if(id === 3){
                    id = "Automotive"
               }else{
                    return "None"
               }
               return id
          },

          amountFormat(amount){
               if(amount > 999 && amount < 1000000){
                    let amountSelected = this.amount
                    let newNumber = Array.from(amountSelected.toString())
                    newNumber.splice(-3, 0, '.');
                    let finalNumber = newNumber.join("")
                    return finalNumber
               }else if(amount > 999999){
                    let amountSelected = this.amount
                    let newNumber = Array.from(amountSelected.toString())
                    let firstPoint = newNumber.splice(-3, 0, '.');
                    firstPoint = newNumber.splice(-7, 0, '.');
                    let finalNumber = newNumber.join("")
                    return finalNumber
               }else{
                    return this.amount
               }
          },

          calculateTaxes(){
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
                              text: 'The amount cannot be less than or equal to 0!',
                         })
               }else{
                    this.taxes = this.amount * 1.20 / this.payment_selected
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

     }, // Cierre de (methods)


     
     }).mount('#app')