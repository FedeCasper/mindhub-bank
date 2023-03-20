// function fechaFormateada(fecha){
//      console.log(typeOf(fecha))
// }
// fechaFormateada()

Vue.createApp({
     data() {
          return {
          charging: true,

          client:[],
          cards: [],
          card_color: [],

          currentClient_Cards: [],
          currentClient_Cards_Length: [],
          currentClient_Cards_Type: [],

          id: [],

          client_debit_cards: [],
          client_credit_cards: [],

          }
     },

     created(){

          axios.get('/api/clients/current')
               .then(datos => {
                    this.client = datos.data
                    this.cards = this.client.cards.sort((a, b) => {return a.id - b.id;})
                    console.log(this.cards)
                    console.log(this.cards.active)
                    this.card_color = this.cards.map(card => card.color)

               })

          axios.get('/api/clients/current/cards')
               .then(data => {
                    addCardsButton = document.querySelector("#createCardButton")
                    this.currentClient_Cards = data.data
                    this.currentClient_Cards_Length = data.data.length
                    this.client_credit_cards = this.currentClient_Cards.filter(card => card.type === 'CREDIT')
                    this.client_debit_cards = this.currentClient_Cards.filter(card => card.type === 'DEBIT')
                    this.currentClient_Cards_Type_Length = this.currentClient_Cards.map(card => card.type).length
                         if(this.currentClient_Cards_Type_Length >= 6){
                         addCardsButton.style.display = "none";}
          })

          setTimeout(() => { this.charging = false }, 2000)
     },


     methods:{

          
          formatearFecha(fecha){
               let date = new Date (fecha)
               let year = date.getFullYear()  
               let array_year = Array.from(year.toString()).slice(-2).join("")
               let month = date.getMonth() +1
               if(month < 10){
                    month = "0" + month
               }
               let month_year = month + "/" + array_year
               return month_year
          },

          compareDates(truDate){
               let date = new Date //obtiene la fecha sin formato
               let day = date.getDay() // obtiene el dia
               let month = date.getMonth() +1 // obtiene el mes actual
               let year = date.getFullYear() // obtiene el año actual
               let monthAndYear = year + "-" + month + "-" + day
               let compare = monthAndYear > truDate // compara la fecha actual con la de vencimiento
               return compare // retorna un valor V o F para ejecutar el v-if
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

          deleteCard(id){
               Swal.fire({
                    title: 'Are you sure you want to cancel!?',
                    text: "You won't be able to revert this!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#1b1c1a',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Confirm!'
               })
               .then((result) => {
                    if (result.isConfirmed) {   
                         axios.patch(`/api/clients/current/cards/` + id)
                         Swal.fire(
                         'Card Canceled!',
                         'We remove this card from your wallet',
                         'success'
                         )
                         .then(setTimeout('document.location.reload()',4000))
                    }
               })
               .catch( error => error.message + "Oops! something happened, you couldn't cancel this card" )
          }

     }, // Cierre de (methods)


     
     }).mount('#app')