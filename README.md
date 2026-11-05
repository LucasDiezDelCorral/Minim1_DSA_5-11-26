MINIMO 1 DSA - FLOTA DE DRONES
Lucas Diez del Corral Pons



TODO PARTE 2



Que es:
Prueba piloto para gestionar drones, pilotos, un almacen para reparar y planes de vuelo.

Como esta hecho:

* drones y pilotos en HashMap como una base de datos
* almacen = pila. pero el ultimo que se rompe es el primero que se repara
* cada dron y cada piloto tienen su lista de planes de vuelo y sus horas
* cada plan de vuelo tiene su lista de posiciones (lat, lon)
* DronManager = interfaz, DronManagerImpl = fachada singleton con logs de log4j
* DronException = 404 no existe alguno, 409 hay conflicto



Rutas REST (http://localhost:8080/dsaApp):
GET  /flota/drones/horas

POST /flota/planes
PUT  /flota/almacen/{idDron}
PUT  /flota/almacen/reparar



Swagger: http://localhost:8080/swagger/



Cosas que he decidido:

* el plan de vuelo lleva tambien el id del piloto porque sino no me cuadra el sentido, y lo había puesto en mi dibujo.
* el formato de fecha que ya lo he escrito como comentario en el codigo: 2026-11-05T10:00
* decir que dos vuelos chocan si uno empieza antes de que acabe otro



Estado:

* funciona, todo y sin problemas en las comprobaciones
* problemas, me saltan avisos pero no han interferido en mis pruebas de si funciona el programa bien
* pendiente, nada del examen

