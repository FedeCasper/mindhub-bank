Vue.createApp({

     data() {
          return {
               client: [],
               clients: [],
               clients_sort: [],
               accounts: [],
               loans:[],
          }
     },

     created(){

          axios.get(`http://localhost:8080/api/clients/current`)
               .then(datos => {
                    this.client = datos.data
                    this.accounts = datos.data.accounts.sort((a, b) => {return a.id - b.id;})
                    this.loans = datos.data.clientLoans
               }),

          axios.get('http://localhost:8080/api/clients/current/accounts')
               .then(data => {
                    addAccountButton = document.querySelector("#create_account_button")
                    this.currentClientAccounts = data.data.length
                         if(this.currentClientAccounts == 3){
                         addAccountButton.style.visibility = "hidden";
                    }
               }),

               axios.get('http://localhost:8080/api/clients/current/clientLoans')
               .then(data => {
                    addLoanButton = document.querySelector("#create_loan")
                    this.currentClientLoans = data.data.length
                         if(this.currentClientLoans >= 3){
                              addLoanButton.style.display = "none";
                    }
               })
     },


     methods:{

          logout(){
               axios.post('/api/logout')
                    .then(response => 
                         window.location.href = "http://localhost:8080/web/index.html",
                         console.log('You have successfully logged out!!!'))
                    .catch( error => error.message + "Oops! something happened, you couldn't log out" )
               },

          createAccount(){
               axios.post('http://localhost:8080/api/clients/current/accounts')
                    .then(response =>
                         location.reload(),
                         console.log('The account was created successfully!!!'))
                    .catch( error => error.message + "The account could not be created")
               },

     }, // Cierre de (methods)


     
     }).mount('#app')