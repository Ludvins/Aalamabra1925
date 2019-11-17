---
title: Memoria descriptiva 
author: 
- Luis Antonio Ortega Andrés
- Guillermo Galindo Ortuño 
- Johanna Capote Robayna
date: today
---

El objetivo de la práctica es confeccionar un software capaz de complementar una visita a la Alhambra de Granada haciendo uso de paradigmas de interacción no convencionales. Para ello, se desarrollará una aplicación para teléfonos móviles (con sistema operativo Android).

La principal característica de la aplicación es que permite proveer al usuario de un mapa donde su localización se encuentra reflejada en tiempo real. Como perseguimos que el usuario no se pierda ningún monumento importante de la Alhambra, también quedan reflejados en el mapa aquellos puntos de interés de una visita a la Alhambra.

![alt text |200x100 20%](mapa.jpg)

El usuario puede obtener información acerca de un punto de interés de distintas formas:

- Los puntos de interés del mapa se pueden pulsar, obteniendo una vista con toda la información acerca de ellos.
- El usuario puede obtener información acerca de un punto de interés apuntando hacia él con el dispositivo móvil. En caso de no tener contacto visual con el punto de interés, el usuario podrá ayudarse de la brújula presente en el mapa para intuir la direccion en la que se encuentra y utilizar el gesto de la misma forma. Con esto se intenta que aquellos usuarios a los que les resuelte complicado interactuar con el mapa puedan obtener información de lo que estan viendo en cualquier momento.
- Es posible acceder a una lista de todos los puntos de interés y obtener información de cualquiera de ellos desde la misma. Esta se trata de una alternativa bastante útil para conocer puntos de interés lejanos sin necesidad de tenerlos cerca.

![alt text](menu.jpg) ![alt text](buscarpunto.jpg) ![alt text](listapuntos.jpg) ![alt text](puntointeres.jpg)

El usuario no tendrá que preocuparse por la señal GPS recibida por la aplicación cuando se adentre en un lugar cubierto.
La aplicación detectará el descenso de señal GPS y cargará un mapa interior del lugar donde se encuentre. Aunque el usuario no pueda saber su localización exacta, también aparecerán distintos puntos de interés, situados dentro de los monumentos de la Alhambra.

![alt text](mapainterno.jpg) ![alt text](puntointerno.jpg)

Finalmente, la aplicación dispone de un juego pensado para los más pequeños. En este se mostrarán diversas preguntas de respuesta rápida sobre la visita a la Alhambra, que deberán responderse en un corto periodo de tiempo (30 segundos). Para aumentar la sensación de frenesí, las preguntas se deberán responder utilizando un gesto con el dispositivo.

- En caso de querer responder afirmativamente a la pregunta, se deberá mover el dispositivo hacia adelante y hacia atrás, con un ángulo de 90 grados.
- En caso de querer responder negativamente, se utilizará un movimiento de lado a lado, con un ángulo de 90 grados.

Tras una serie de preguntas la puntuación total es mostrada en pantalla, permitiendo al usuario volver a jugar para intentar conseguir una puntuación más alta.

![alt text](juegotiempo.jpg) ![alt text](juegobien.jpg) ![alt text](juegomal.jpg) ![alt text](puntuacion.jpg)
