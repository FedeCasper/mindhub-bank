Vue.createApp({

     data() {
          return {
          charging: true,
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

          setTimeout(() => { this.charging = false }, 2000)
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
                         .then(setTimeout('document.location.reload()',3000))
                    }
               })
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