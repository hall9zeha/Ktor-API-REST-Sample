# ktor-apirest-sample
[![English 🇬🇧](https://img.shields.io/badge/-Read%20in%20English-blue?style=for-the-badge)](README.md)

Este proyecto fue creado usando el [Generador de Proyectos de Ktor](https://start.ktor.io).

Aquí tienes algunos enlaces útiles para comenzar:

- [Documentación de Ktor](https://ktor.io/docs/home.html)
- [Repositorio de Ktor en GitHub](https://github.com/ktorio/ktor)
- [Canal de Slack de Ktor](https://app.slack.com/client/T09229ZC6/C0A974TJ9) (debes [solicitar una invitación](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up))

## Características

Estas son algunas características incluidas en este proyecto:

| Nombre                                                                   | Descripción                                                                                  |
|--------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing)                               | Provee un DSL estructurado para enrutar                                                     |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)       | Conversión automática de contenido según los encabezados `Content-Type` y `Accept`           |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization)   | Manejo de JSON con la librería kotlinx.serialization                                         |
| [Mongo DB](https://www.mongodb.com/developer/languages/kotlin/mastering-kotlin-creating-api-ktor-mongodb-atlas/) | Permite integrar MongoDB para operaciones de base de datos en aplicaciones Ktor             |
| [Json Web Tokens](https://www.jwt.io/)                                   | Transmisión segura de datos como objetos JSON, comúnmente usada para autenticación           |

## Compilación y ejecución

### Importante:
Para evitar exponer la cadena de conexión de la base de datos (en este caso MongoDB), se ha creado un archivo llamado `local.conf` dentro del directorio `resources/`. Este archivo contiene la cadena de conexión que usarás. Debes crearlo con tu propia URL de conexión:

```hocon
mongo {
    uri = "mongodb+srv://tuUsuario:tuContraseña@cluster1.pa7b55e.mongodb.net/"
}
```

Este archivo será utilizado en la clase de conexión a base de datos:

```kotlin
val config = ConfigFactory.parseResources("local.conf").resolve()
val mongoUriLocal = config.getString("mongo.uri")

val applicationConfig = ApplicationConfig("application.conf")
val mongoUri = applicationConfig.propertyOrNull("ktor.database.uri")?.getString() ?: mongoUriLocal
```

También puedes usar directamente:

```kotlin
mongoClient = MongoClient.create("tu cadena de conexión a MongoDB")
```

Todo esto está pensado para el modo de desarrollo.  
Cuando despliegues la aplicación en un servicio en la nube, deberás usar la variable de entorno `DATABASE_URL`, la cual ya está configurada en `application.conf`.

Para ello, simplemente deja el código así:

```kotlin
val applicationConfig = ApplicationConfig("application.conf")
val mongoUri = applicationConfig.propertyOrNull("ktor.database.uri")?.getString()!!
```

Para probar tu API en un servicio como Render, simplemente sube el repositorio y agrega la variable de entorno `DATABASE_URL` con tu cadena de conexión.  
El `Dockerfile` se encargará de compilar todo lo necesario:


```dockerfile
# Usa Gradle con JDK 11 para compilar el proyecto
FROM gradle:8.4-jdk11 AS build

# Copia los archivos del proyecto al contenedor con los permisos correctos
COPY --chown=gradle:gradle . /home/gradle/src

# Establece el directorio de trabajo
WORKDIR /home/gradle/src

# Construye el fat JAR con todas las dependencias
RUN gradle buildFatJar --no-daemon

# Usa una imagen ligera de Java 11 para ejecutar la app
FROM openjdk:11

# Expone el puerto 8080
EXPOSE 8080:8080

# Crea el directorio donde se copiará el JAR
RUN mkdir /app

# Copia el JAR compilado desde la etapa anterior
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-sample-api_server.jar

# Comando de entrada para ejecutar la app
ENTRYPOINT ["java","-jar","/app/ktor-sample-api_server.jar"]
```

### Postman
Este repositorio incluye un archivo que puede ser importado en Postman y contiene todas las peticiones necesarias para probar la API.

Ten en cuenta que, para realizar la petición de "obtener todos los usuarios", primero debes crear uno y luego ejecutar la petición **"login user"** para obtener un token.  
Este token debe ser utilizado como **Bearer** en la autorización de las peticiones protegidas, como la de obtener todos los usuarios.  
Esto te permitirá probar correctamente la autenticación mediante JWT.

### Comandos disponibles

| Tarea                           | Descripción                                                                 |
|--------------------------------|-----------------------------------------------------------------------------|
| `./gradlew test`               | Ejecuta los tests                                                           |
| `./gradlew build`              | Compila todo el proyecto                                                    |
| `buildFatJar`                  | Genera un JAR ejecutable con todas las dependencias                         |
| `buildImage`                   | Crea la imagen Docker para usar con el JAR                                  |
| `publishImageToLocalRegistry` | Publica la imagen Docker en el registro local                               |
| `run`                          | Ejecuta el servidor localmente                                              |
| `runDocker`                    | Ejecuta usando la imagen Docker local                                       |

Si el servidor se inicia correctamente, verás una salida como esta:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```
