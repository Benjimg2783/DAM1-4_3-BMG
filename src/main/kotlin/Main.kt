package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {
    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()
    fun obtenerCiudadesDeClientes(): Set<Ciudad> {
        val conjuntoCiudades = mutableSetOf<Ciudad>()
        clientes.forEach { conjuntoCiudades.add(it.ciudad) }
        return conjuntoCiudades.toSet()
    }

    fun obtenerClientesPorCiudad(c: Ciudad): List<Clientes> = clientes.filter { it.ciudad == c }

    fun obtenerClientesPor(ciudad: Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }
    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all { it.ciudad == ciudad }
    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }
    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }
    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }
    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedByDescending { it.pedidos.size }
    fun obtenerClientesConPedidosSinEngregar(): Set<Clientes> =
        clientes.partition { it.pedidos.all { it.estaEntregado > !it.estaEntregado } }.second.toSet()

    fun obtenerProductosPedidos(c: Clientes): List<Producto> =
        clientes.filter { it == c }.flatMap { it.pedidos }.flatMap { it.productos }.toList()

    fun obtenerProductosPedidosUno(c: Clientes): Set<Producto> =
        clientes.filter { it == c }.flatMap { it.pedidos }.flatMap { it.productos }.toSet()

    fun obtenerProductosPedidosPorTodos(): Set<Producto> =
        clientes.flatMap { it.pedidos }.flatMap { it.productos }.toSet()

}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"

    fun encuentraProductoMasCaro(): Producto? = pedidos.filter { it.estaEntregado }.maxOf { it.productos }
}

data class Pedido(val productos: List<Producto>, var estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

fun main() {
    val cadiz = Ciudad("Cadiz")
    val tenerife = Ciudad("Tenerife")
    val albondigas = Producto("albondigas", 3.50)
    val acelgas = Producto("acelgas", 1.50)
    val pipas = Producto("pipas", 1.0)
    val listaPedido1 = listOf<Producto>(albondigas)
    val listaPedido2 = listOf<Producto>(acelgas)
    val listaPedido3 = listOf<Producto>(pipas)
    val pedido1 = Pedido(listaPedido1, false)
    val pedido2 = Pedido(listaPedido2, false)
    val pedido3 = Pedido(listaPedido3, true)
    val pedidosCliente1 = listOf<Pedido>(pedido1)
    val pedidosCliente2 = listOf<Pedido>(pedido2, pedido3)
    val pedidosCliente3 = listOf<Pedido>(pedido3)
    val cliente1 = Clientes("Paco", cadiz, pedidosCliente1)
    val cliente2 = Clientes("Rodolfo", tenerife, pedidosCliente2)
    val cliente3 = Clientes("Juan", cadiz, pedidosCliente3)
    val listaClientes = listOf<Clientes>(cliente1, cliente2, cliente3)
    val mercadona = Tienda("Mercadona", listaClientes)
    println(mercadona.obtenerCiudadesDeClientes())
    println(mercadona.obtenerClientesPorCiudad(cadiz))
    println(mercadona.obtenerConjuntoDeClientes())
    println(mercadona.obtenerClientesPor(cadiz))
    println(mercadona.checkTodosClientesSonDe(tenerife))
    println(mercadona.hayClientesDe(tenerife))
    println(mercadona.cuentaClientesDe(cadiz))
    println(mercadona.encuentraClienteDe(tenerife))
    println(mercadona.obtenerClientesOrdenadosPorPedidos())
    println(mercadona.obtenerClientesConPedidosSinEngregar())
    println(mercadona.obtenerProductosPedidos(cliente1))
    println(mercadona.obtenerProductosPedidosUno(cliente2))
    println(mercadona.obtenerProductosPedidosPorTodos())

}