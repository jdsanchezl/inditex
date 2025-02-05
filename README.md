# Inditex API

Este proyecto consiste en una API desarrollada en **Spring Boot** para gestionar los precios de productos de una cadena específica (pvp) y las tarifas aplicables entre un rango de fechas. La API permite realizar consultas y crear registros de precios, facilitando la consulta del precio final de un producto en función de criterios específicos, como la marca y el rango de fechas.

## Tabla de Contenidos
- [Introducción](#introducción)
- [Arquitectura](#arquitectura)
- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Ejecución con Docker](#ejecución-con-docker)
- [Configuración de Base de Datos](#configuración-de-base-de-datos)
- [Documentación de la API](#documentación-de-la-api)
- [Cobertura de Código](#cobertura_de_codigo)
- [Ejemplos de Solicitudes](#ejemplos-de-solicitudes)

## Introducción

El objetivo de esta API es proporcionar una solución eficiente para la gestión y consulta de precios de productos dentro del ecosistema de **Inditex**. A través de esta API, se pueden realizar las siguientes acciones:
- Consultar precios de un producto para una marca y un rango de fechas específicos.
- Almacenar nuevos precios en el sistema.
- Listar todos los precios disponibles en la base de datos.

La API sigue principios de **arquitectura limpia** y está alineada con los estándares de diseño como **DDD (Domain-Driven Design)** y **Hexagonal Architecture**, lo que garantiza flexibilidad y mantenibilidad a largo plazo.

## Arquitectura

El proyecto está diseñado siguiendo principios de arquitectura hexagonal, lo que permite una clara separación entre las capas de dominio, infraestructura y aplicación. Esto asegura que el núcleo del dominio esté desacoplado de la infraestructura externa, facilitando futuras extensiones y modificaciones.

La API está dividida en tres capas principales:
- **Capa de dominio**: Contiene las entidades de negocio y las reglas del dominio.
- **Capa de aplicación**: Expone los servicios que la API ofrece al mundo exterior, gestionando la lógica de negocio.
- **Capa de infraestructura**: Incluye la implementación de persistencia de datos, en este caso utilizando JPA y bases de datos H2 (en memoria) o MySQL (opcional).

## Características

- **Consulta de todos los precios**: Recupera la lista completa de precios almacenados en el sistema.
- **Consulta condicional de precios**: Permite obtener el precio aplicable a un producto en función de la fecha, identificador del producto y de la cadena.
- **Almacenamiento de precios**: Permite almacenar nuevos precios de productos específicos.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Maven**
- **Liquibase** (para el control de versiones de la base de datos)
- **H2 Database** (base de datos en memoria para entornos de desarrollo y prueba)
- **Docker** (para contenedores de la aplicación)
- **Swagger OpenAPI** (para la documentación de la API)
- **JaCoCo** (para medición de cobertura de código)

## Requisitos Previos

- **Java JDK 21** o superior
- **Docker** y **Docker Compose** instalados.
- **Maven** para construir el proyecto.

## Instalación y Ejecución

###### **1. Clona el repositorio:**
   
```bash
git clone https://github.com/juanda2984/inditex.git
```
###### **2. Navega al directorio del proyecto:**

```bash
cd inditex
```

###### **3. Ejecuta la aplicación:**

Construir la aplicación

```bash
./mvn clean install
```
Luego ejecutar la aplicación

```bash
./mvnw spring-boot:run
```
La aplicación estará disponible en http://localhost:8080.

## Configuración de la Base de Datos

Por defecto, la aplicación utiliza una base de datos H2 en memoria. Puedes acceder a la consola de la base de datos en:

```bash
http://localhost:8080/h2-console
```

Las credenciales de acceso están configuradas en el archivo application.properties.

Si deseas utilizar otra base de datos (por ejemplo, MySQL), puedes modificar las propiedades de configuración en el archivo application.properties o crear un perfil de entorno específico.

## Ejecución con Docker

Si prefieres ejecutar la aplicación en un contenedor Docker, sigue estos pasos:

###### **1. Construye la imagen Docker:**

```bash
cd inditex
```

###### **2. Ejecuta el contenedor:**

```bash
docker-compose up --build
```

## Documentación de la API

La documentación de la API está disponible a través de Swagger OpenAPI. Puedes acceder a la interfaz gráfica de Swagger para probar los endpoints directamente en tu navegador accediendo a:

```bash
http://localhost:8080/swagger/swagger-ui/index.html
```

## Cobertura de Código

El proyecto utiliza **Jacoco** para generar informes de cobertura de código. Actualmente, se ha alcanzado una cobertura de aproximadamente **70%**, lo cual es considerado aceptable para un proyecto nuevo. La cobertura puede ser mejorada a medida que se agreguen más pruebas unitarias y de integración.

El informe de cobertura se genera al ejecutar las pruebas y se puede consultar en el directorio `target/site/jacoco/index.html` después de construir el proyecto.

## Ejemplos de Solicitudes

###### **1. Consultar todos los precios**

```bash
curl http://localhost:8080/prices
```

###### **2. Consultar precios por condiciones específicas**

```bash
curl http://localhost:8080/prices/{apply}/{productId}/{brandId}
```

Nota: Reemplaza {apply}, {productId} y {brandId} con los valores correspondientes.

###### **3. Crear un nuevo precio**

```bash
curl -X POST http://localhost:8080/prices/ -H "Content-Type: application/json" -d '{
  "price": 35.50,
  "brandId": 1,
  "productId": 35455,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "curr": "EUR"
}'
```

