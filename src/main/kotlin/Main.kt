package un4.collections
data class Tienda(val nombre: String, val clientes: List<Clientes>){
    fun obtenerConjuntoDeClientes():Set<Clientes> = clientes.toSet()

    fun obtenerCiudadesDeClientes(): Set<Ciudad> {
        var conjuntoCiudades= mutableSetOf<Ciudad>()
        clientes.forEach{conjuntoCiudades.add(it.ciudad)}
        return conjuntoCiudades.toSet()
    }
    fun obtenerClientesPorCiudad(c:Ciudad): List<Clientes> {
        return clientes.filter { it.ciudad==c }
    }
}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"
}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}
fun main(args: Array<String>) {
    val cadiz=Ciudad("Cadiz")
    val tenerife=Ciudad("Tenerife")
    val producto1=Producto("albondigas",100.1)
    val listaPedido= listOf<Producto>(producto1)
    var pedido1=Pedido(listaPedido,false)
    val pedidosCliente1= listOf<Pedido>(pedido1)
    val cliente1= Clientes("Paco",cadiz,pedidosCliente1)
    val cliente2=Clientes("Rodolfo",tenerife,pedidosCliente1)
    val cliente3=Clientes("Juan",cadiz,pedidosCliente1)
    val listaClientes= listOf<Clientes>(cliente1,cliente2,cliente3)
    val mercadona=Tienda("Mercadona",listaClientes)
    println(mercadona.obtenerCiudadesDeClientes())
    println(mercadona.obtenerClientesPorCiudad(cadiz))
    println(mercadona.obtenerConjuntoDeClientes())

}