Vue.createApp({
     data() {
          return {
     
               account_vin001: [],
               transactions_vin001: [],
          }
     },

     created(){
          const urlParams = new URLSearchParams(window.location.search);
          const id = urlParams.get('id');
          console.log(id)

          axios.get('/api/accounts/' + id)
               .then(datos => {
                    this.account_vin001 = datos.data
                    console.log(this.account_vin001)
                    this.transactions_vin001 = datos.data.transactions.sort((a, b) => {return a.id - b.id;})

               })

     
     },


     methods:{
          
     }, // Cierre de (methods)


     
     }).mount('#app')