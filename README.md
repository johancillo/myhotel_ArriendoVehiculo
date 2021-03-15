# myhotel_ArriendoVehiculo
Aplicación en spring boot para la administración de vehiculos

#BD hd
Sólo es necesario desplegar el aplicativo y se cargará la bd implementada en h2 se ejecutarán el script automáticamente, por lo que no necesita configuración.

#Lombok
Para el desarrollo se instala lombok project 1.18.18 en el respectivo ambiente.

#Ejecutar.
Sólo se deberá ejecutar el RUN del sprint boot App y se ejecutará la aplicación.

#Listado de EndPoints:
Get("/listar") -> Lista todos los vehiculos que se encuentran en la base de datos.

Get("/buscar/detalle/{patente}") -> Muestra los detalles de un vehiculo segun la patente ingresada.

Post("/insertar") -> Guarda el registro de un vehiculo (Automovil o Camion) con el body request que se encontrará en el      collection postman

Put("/modificar") -> Modifica registro de un vehiculo (Automovil o Camion) con el body request que se encontrará en el      collection postman

Delete("/eliminar/{patente}") -> elimina registro vehiculo (Automovil o camion).
