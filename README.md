# MeetApp
Aplicación que intenta cumplir con las siguientes premisas:

## Premisas

Queremos armar las mejores meetups y para eso estamos planeando hacer una app que nos ayude a lograr que no falte lo fundamental... birras!
Tenemos un proveedor que nos vende cajas de birras de 6 unidades. El problema es que si hace entre 20 y 24 grados se toma una birra, si hace menos de 20 grados se toma 0.75 birras por persona y si hace calor (más de 24 grados) se toman 2 birras más por persona, y siempre es preferible que sobre a que falte.

* como usuario o admin quiero poder recibir notificaciones para estar al tanto de las meetups
* como admin quiero armar una meetup para poder invitar otras personas
* como usuario quiero inscribirme en una meetup para poder asistir
* como usuario quiero hacer check-in en una meetup para poder avisar que estuve allí
* :exclamation: ___como admin quiero saber cuantas cajas de birras tengo que comprar para poder provisionar el meetup___
* :exclamation: ___como admin y usuario quiero ver qué temperatura va a hacer el día de la meetup para saber si hace calor o no___

## Supuestos

Se pre supone que la Api requerirá una autenticación, que al crear una MeetUp se necesita establecer un Máximo de Attendees, que el lugar de las mismas siempre será Buenos Aires, y el proveedor será siempre el mismo y entregará siempre los mismos packs de 6 birras.

## Api

La api esta implementada en Java mediante Spring Boot y presenta varias rutas para cumplir con los puntos solicitados: Login, MeetUps, Users. Para poder acceder a las mismas es necesario previamente obtener un Token, mediante la ruta de login y cada resource devolverá los links necesarios para navegar en la misma.

Además hay rutas que solo se permiten para un Role de Admin, por lo que no puden accederse si se es un User.

## UI

Se realizará una app mediante Expo, la cual empleará un login básico para obtener el token que se almacenará en el AsyncStorage para su posterior utilización.

## Varios

La api integra Swagger2 mediante FoxSpring, por lo que una vez corriendo puede accederse a la misma mediante la siguiente URL: http://localhost:8080/swagger-ui.html
