Vue.createApp({
     data() {
          return {
     
               account_vin001: [],
               transactions_vin001: [],
               client_accounts: [],
          }
     },

     created(){
          const urlParams = new URLSearchParams(window.location.search);
          const id = urlParams.get('id');
          console.log(id)

          axios.get('/api/clients/current/accounts')
               .then(data => {
                    this.client_accounts = data.data.sort((a, b) => {return a.id - b.id;})
                    console.log(this.client_accounts)
               }),

          axios.get('/api/accounts/' + id)
               .then(datos => {
                    this.account_vin001 = datos.data
                    console.log(this.account_vin001)
                    this.transactions_vin001 = datos.data.transactions.sort((a, b) => {return a.id - b.id;})

               })

     
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

     }, // Cierre de (methods)


     
     }).mount('#app')