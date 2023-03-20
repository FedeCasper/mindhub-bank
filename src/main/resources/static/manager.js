
Vue.createApp({


     
     data() {
          return {
               clientes: [],
               jsonClientes: [],
               nombre:"",
               apellido: "",
               email: "",
               nombre_edit: "",
               apellido_edit: "",
               email_edit: "",

          }
     },


     created(){
          axios.get(`http://localhost:8080/rest/clients`)
               .then(datos => {
                    this.jsonClientes = datos.data
                    this.clientes = datos.data._embedded.clients
               })

     },


     methods:{

          agregar_cliente(){
               axios.post(`http://localhost:8080/rest/clients` , {
                    first_name: this.nombre,
                    last_name: this.apellido,
                    email: this.email,
               })
               .then(response => console.log(response))
               .catch(error => console.log(error))
          },

          editar_cliente(id) {
               axios.patch(id , {
                    first_name: this.nombre_edit,
                    last_name: this.apellido_edit,
                    email: this.email_edit,
               })
               .then(location.reload())
               .catch(error => console.log(error))
          },

          borrar_cliente(id) {
               axios.delete(id)
               .then(location.reload())
               .catch(error => console.log(error))
          },


     }, // Cierre de (methods)



}).mount('#app')
