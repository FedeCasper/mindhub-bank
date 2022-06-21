Vue.createApp({

     data() {
          return {
          cardType:'',
          cardColor: '',
          currentClient_Cards: [],
          }
     },


     created(){
          axios.get('/api/clients/current/cards')
          .then(data => {
               this.currentClient_Cards = data.data
          })
     },


     methods:{

          createCard(){
               Swal.fire({
                    title: 'Do you confirm the request?',
                    text: "You won't be able to revert this!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#1b1c1a',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Confirm!'
               })
               .then((result) => {
                    if (result.isConfirmed) {   
                         axios.post('/api/clients/current/cards',`type=${this.cardType}&color=${this.cardColor}`,
                         {headers:{'content-type':'application/x-www-form-urlencoded'}})
                         Swal.fire(
                         'Card Requested!',
                         'Your new card is available un cards section',
                         'success'
                         )
                         .then(setTimeout('document.location.reload()',5000))
                    }
               })
               
          }

     },

}).mount('#app')