# 💾 User Registry API

🔥 API RESTful para registro y autenticación de usuarios con validaciones de seguridad y documentación Swagger. 🔥

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Maven](https://img.shields.io/badge/Maven-3.6+-orange)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

---

## 📌 Características

- **Registro de usuarios** con validaciones robustas
- **Autenticación JWT** segura
- **Base de datos H2** en memoria con consola web
- **Documentación Swagger/OpenAPI** interactiva
- **Validación configurable** de email y contraseña mediante expresiones regulares
- **Configuración de seguridad** con Spring Security

---

## 📋 Requisitos Previos

- Java 21
- Maven 3.6+
- Postman o cURL para pruebas

---

## 🏗️ Instalación y Ejecución

### Paso Nº 1 , Clonar y compilar el proyecto
```bash
git clone https://github.com/TheAkylino/bs-user-registry-api.git
git checkout feature/INCTSAAYP-1922
mvn clean install
```
---

### Paso Nº 2 , Se usa este comando  de maven para arrancar la aplicacion:
```shell  
mvn spring-boot:run
```
---
### 🌐 URLs de Acceso
-   **API Base URL:** [http://localhost:8089/user-registry](http://localhost:8089/user-registry)

-   **Swagger UI:** [http://localhost:8089/user-registry/swagger-ui.html](http://localhost:8089/user-registry/swagger-ui.html)

-   **OpenAPI Docs:** [http://localhost:8089/user-registry/v3/api-docs](http://localhost:8089/user-registry/v3/api-docs)

-   **H2 Console:** [http://localhost:8089/user-registry/h2-console](http://localhost:8089/user-registry/h2-console)
---
### 🔐 Credenciales H2 Console
![IMAGEN_1.jpg](src/main/resources/IMAGEN_1.jpg)

-   **JDBC URL:** jdbc:h2:mem:developmentdb
-   **Usuario:** dev
-   **Contraseña:** dev
---
### 🗄️ Estructura de las tablas de la Base de Datos
```sql
CREATE TABLE USERS (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL,
    TOKEN VARCHAR(255),
    CREATED TIMESTAMP NOT NULL,
    MODIFIED TIMESTAMP NOT NULL,
    LAST_LOGIN TIMESTAMP NOT NULL,
    ACTIVE BOOLEAN NOT NULL
);

CREATE TABLE PHONES (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NUMBER VARCHAR(50) NOT NULL,
    CITY_CODE VARCHAR(10) NOT NULL,
    COUNTRY_CODE VARCHAR(10) NOT NULL,
    USER_ID BIGINT NOT NULL,
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);
```
---
### Paso Nº 3, Identificar los EndPoints para el consumo de esta API, en lo cuales son los siguiente:
##### 3.1 )  Es necesario Loguearse para Obtener un Token,para poder asi Registrar el Usuario
**login:** [http://localhost:8089/user-registry/v1/api/auth/login](http://localhost:8089/user-registry/v1/api/auth/login) <br>
⚠️Hay un usuario predeterminado para poder asi registar usuario, una vez que se registre, podrà usar ese mismo usuario y contraseña para poder loguearse y poder registrar mas usuarios.<br>
📝Este es el usuario predeterminado
```json
{
   "email":"aolazo@theakylino.com",
   "password":"Caracas1234567@2025#"
}
```
![IMAGEN_2.jpg](src/main/resources/IMAGEN_2.jpg)
![IMAGEN_3.jpg](src/main/resources/IMAGEN_3.jpg)
⚠️ El JWT expira en 60 minutos (1 hora) <br>
⚠️ Si se desea cambiar la cantidad de horas/minutos, este valor está parametrizado en el archivo `application.yml`
![IMAGEN_4.jpg](src/main/resources/IMAGEN_4.jpg) <br>
⚠️ El token generado es:  `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhb2xhem9AdGhlYWt5bGluby5jb20iLCJpYXQiOjE3NTY5Mjk1NjQsImV4cCI6MTc1NjkzMzE2NH0.VSo9NxLOqfBAddxkvaquyvGm8wtw36eq_c4CfsB2jfI`<br>
⚠️ Se comprueba que el token generado corresponda al usuario `aolazo@theakylino.com`
![IMAGEN_5.jpg](src/main/resources/IMAGEN_5.jpg)
---
##### 3.2 )  Despues de Loguearse, y de forma `Opcional` se puede ingresar EndPoint para validar que el Token Generado esta OK
**Validate:** [http://localhost:8089/user-registry/v1/api/auth/validate](http://localhost:8089/user-registry/v1/api/auth/validate) <br>
![IMAGEN_6.jpg](src/main/resources/IMAGEN_6.jpg)
---
##### 3.3 )  Despues de Obtener el Token del paso 3.1, se procede a Registrar un Usuario.
⚠️ Tener en cuenta que para `Registrar un usuario`, se debe de tener el `TOKEN` y que `NO` este expirado por su tiempo<br>
⚠️ Al estar Expirado por su tiempo no podrar Registrar y le dara un error HTTP 401 Unauthorized<br>
⚠️ Se comprueba que el Token se coloco en el Header Authorization Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhb2xhem9AdGhlYWt5bGluby5jb20iLCJpYXQiOjE3NTY5Mjk1NjQsImV4cCI6MTc1NjkzMzE2NH0.VSo9NxLOqfBAddxkvaquyvGm8wtw36eq_c4CfsB2jfI<br>
![IMAGEN_7.jpg](src/main/resources/IMAGEN_7.jpg) <br>
![IMAGEN_8.jpg](src/main/resources/IMAGEN_8.jpg) <br>
⚠️ Como se puede apreciar que el Token está expirado, y para comprobar que se encuentra en ese estado,utilizamos el `El EndPoint validate` del paso Nº `3.2` <br>
⚠️ Recordando que el token generado es:  `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhb2xhem9AdGhlYWt5bGluby5jb20iLCJpYXQiOjE3NTY5Mjk1NjQsImV4cCI6MTc1NjkzMzE2NH0.VSo9NxLOqfBAddxkvaquyvGm8wtw36eq_c4CfsB2jfI`<br>


![IMAGEN_9.jpg](src/main/resources/IMAGEN_9.jpg)<br>
💪🏻 **Se procede a generar de nuevo un nuevo Token, seguir el paso Nº`3.1` ** <br>
⚠️ Una vez ya generado el nuevo token es:  `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhb2xhem9AdGhlYWt5bGluby5jb20iLCJpYXQiOjE3NTY5NDM5MDAsImV4cCI6MTc1Njk0NzUwMH0.z0LlKjBAbD12LWy-aSZA9dPFgG-0OUkxBqHmdc2gJVA`<br>
⚠️ Recuerde que `tiene 1 Hora para registrarse, despues de ahi el token muere y se tiene que volver a loguearse`<br>
⚠️ Registramos el Usuario<br>
💪🏻 **El Request de prueba es el siguiente:** <br>
```json
{
  "name":"Juan Rodriguez",
  "email":"juan@rodriguez.org",
  "password":"hunter2",
  "phones":[
    {
      "number":"1234567",
      "citycode":"1",
      "contrycode":"57"
    }
  ]
}
```

👨🏼‍💻 **Se está tomando el mismo `Request de ejemplo` del documento PDF que se me envio** <br>
⚠️ El request, No tiene niguna validacion camelCase,por ende,no se va a caer Exception por escribir en camelCase,se puede escribir en Mayuscula, Minuscula, o ambas, ya que en el `requerimiento tecnico No se me solicito, por ende No se agrego`<br>
⚠️ Se procede a Registrar Usuario<br>

**Registrar Usuario:** [http://localhost:8089/user-registry/v1/api/users/userRegistration](http://localhost:8089/user-registry/v1/api/users/userRegistration) <br>
![IMAGEN_10.jpg](src/main/resources/IMAGEN_10.jpg)
![IMAGEN_11.jpg](src/main/resources/IMAGEN_11.jpg)
😲 **Hay un error‼️, pero no te preocupes 😵‍💫** <br>
💪🏻 **Recuerda algo‼️, se solicito que la clave debe seguir una expresión regular para validar que formato sea el correcto. El valor de la expresión regular debe ser configurable** <br>
![IMAGEN_12.jpg](src/main/resources/IMAGEN_12.jpg)
<br>
✅ **Reglas de Validación de la Contraseña**
- **La contraseña debe ser** Mínimo 8 caracteres
- **Al menos 1 letra mayúscula (A-Z)**
- **Al menos 1 letra minúscula (a-z)**
- **Al menos 1 número (0-9)**
- **Al menos 1 carácter especial (@, #, $, %, !)**
  <br>
  <br>
✅ **Reglas de Validación del Email** 
- **Formato válido de email (usuario@dominio.ext)**
- **Debe ser único en el sistema**

💪🏻 **Ahora teniendo en cuenta Ciertas Reglas, se procede de nuevo a registrar un usuario** <br>
🥳 **Recordando que el Request se debe de agregar la clave que cumpla con las reglas de validacion ya antes mencionadas** <br>
```json
{
  "name":"Juan Rodriguez",
  "email":"juan@rodriguez.org",
  "password":"Lima1234567@2025#",
  "phones":[
    {
      "number":"1234567",
      "citycode":"1",
      "contrycode":"57"
    }
  ]
}
```
🥳 **Se procede a crear un nuevo Registro de Usuario** <br>
![IMAGEN_13.jpg](src/main/resources/IMAGEN_13.jpg)<br>
💪🏻 **Se comprueba en la base de datos!!!! ** <br>
<br>
![IMAGEN_14.jpg](src/main/resources/IMAGEN_14.jpg)<br>
<br>
![IMAGEN_15.jpg](src/main/resources/IMAGEN_15.jpg)<br>
---

👨🏼‍💻 **Casos de Prueba** <br>
![IMAGEN_16.jpg](src/main/resources/IMAGEN_16.jpg)<br><br>
![IMAGEN_17.jpg](src/main/resources/IMAGEN_17.jpg)<br><br>
---
#### ✅ Casos de Prueba Nº1, `Responder el código de status HTTP adecuado`
![IMAGEN_18.jpg](src/main/resources/IMAGEN_18.jpg)<br><br>

#### ✅ Casos de Prueba Nº2
- **id**: ID del usuario (puede ser el generado por la BD; idealmente un **UUID**).
- **created**: fecha de creación del usuario.
- **modified**: fecha de la última actualización del usuario.
- **last_login**: fecha/hora del último ingreso (en un usuario nuevo coincide con `created`).
- **token**: token de acceso de la API (puede ser **UUID** o **JWT**).
- **isactive**: indica si el usuario sigue habilitado dentro del sistema.
<br><br>
![IMAGEN_19.jpg](src/main/resources/IMAGEN_19.jpg)<br><br>

#### ✅ Casos de Prueba Nº3
- **Si caso el correo conste en la base de datos, deberá retornar un error "El correo ya
  registrado"** <br><br>
![IMAGEN_20.jpg](src/main/resources/IMAGEN_20.jpg)<br><br>

#### ✅ Casos de Prueba Nº4
- **El correo debe seguir una expresión regular para validar que formato sea el correcto.** <br><br>
![IMAGEN_21.jpg](src/main/resources/IMAGEN_21.jpg)<br><br> 

#### ✅ Casos de Prueba Nº5
- **La clave debe seguir una expresión regular para validar que formato sea el correcto. (El
  valor de la expresión regular debe ser configurable)** <br><br>
![IMAGEN_12.jpg](src/main/resources/IMAGEN_12.jpg)
⚠️ Tomar en cuanta las Reglas de Validación de la Contraseña escrita anteriormente

#### ✅ Casos de Prueba Nº6
- **El token deberá ser persistido junto con el usuario** <br><br>
  <br>
  ![IMAGEN_14.jpg](src/main/resources/IMAGEN_14.jpg)<br>
  <br>
  ![IMAGEN_15.jpg](src/main/resources/IMAGEN_15.jpg)<br>

#### ✅ Casos de Prueba Nº7
- **Sin la propiedad name** <br><br>
![IMAGEN_22.jpg](src/main/resources/IMAGEN_22.jpg)<br>

#### ✅ Casos de Prueba Nº8
⚠️ Una vez creado el usuario `juan@rodrigueza.org` y su clave `Lima1234567@2025#` tambien puede crear un nuevo Token y crear Registrar Usuarios
![IMAGEN_23.jpg](src/main/resources/IMAGEN_23.jpg)<br><br>
![IMAGEN_24.jpg](src/main/resources/IMAGEN_24.jpg)<br><br>