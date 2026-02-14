# 📖 Documentación de la API - Sistema de Constructora

Todas las peticiones (excepto el Login) deben incluir en los Headers el token de autenticación:
`Authorization: Bearer <tu_token_jwt>`
`Content-Type: application/json`

---

## 🔐 1. Autenticación

### Login de Usuario
* **Método:** `POST`
* **Endpoint:** `/api/auth/login` 
* **Body Request:**
  ```json
  {
      "dni": "12345678",
      "password": "mi_password_seguro"
  }
  ```
* **Response (200 OK):**
  ```json
  {
      "token": "eyJhbGciOiJIUzI1NiIsInR5c...",
      "role": "OBRERO",
      "userId": 1,
      "nombreCompleto": "Pedro Pérez"
  }
  ```

---

## 📊 2. Dashboard (Administrador)

### Resumen General del Día
* **Método:** `GET`
* **Endpoint:** `/api/dashboard/summary`
* **Response (200 OK):**
  ```json
  {
      "asistenciaHoy": 42,
      "totalEmpleados": 1200.00,
      "penalizaciones": 45.50,
  }
  ```

### Alertas de Stock Crítico
* **Método:** `GET`
* **Endpoint:** `/api/dashboard/critical-stock`
* **Descripción:** Devuelve un array con los materiales donde el `stock` actual es menor o igual al 10% del `stockMaximo`.

### Últimos Materiales Añadidos (Estado en tiempo real)
* **Método:** `GET`
* **Endpoint:** `/api/dashboard/material-states`
* **Response (200 OK):** *(Máximo 3 productos)*
  ```json
  [
      {
          "id": 1,
          "nombre": "Cemento Portland",
          "estado": "SALUDABLE" 
      }
  ]
  ```

---

## 👷 3. Gestión de Empleados

### Resumen de Nómina
* **Método:** `GET`
* **Endpoint:** `/api/employees/summary`
* **Response (200 OK):**
  ```json
  {
      "empleadosTotales": 154,
      "empleadosActivos": 128,
      "nominaDiaria": 18400.00
  }
  ```

### Listar Empleados
* **Método:** `GET`
* **Endpoint:** `/api/employees`
* **Params (Opcional):** `?search=juan`
* Busca los obreros por nombre

### Crear Nuevo Empleado
* **Método:** `POST`
* **Endpoint:** `/api/employees`
* **Body Request:**
  ```json
  {
      "nombreCompleto": "Juan Pérez",
      "dni": "98765432",
      "rol": "OBRERO",
      "password": "password123",
      "cargo": "Albañil",
      "salarioDiario": 150.00
  }
  ```

### Editar Empleado
* **Método:** `PUT`
* **Endpoint:** `/api/employees/{id}`
* **Body Request:**
  ```json
  {
      "nombreCompleto": "Juan Pérez",
      "dni": "98765432",
      "rol": "OBRERO",
      "cargo": "Maestro de Obra",
      "salarioDiario": 200.00,
      "estado": true,
      "password": "nuevo_password" 
  }
  ```

### Activar / Desactivar Empleado
* **Método:** `PUT`
* **Endpoint:** `/api/employees/{id}/toggle`
* **Descripción:** No requiere Body. Invierte el estado actual del empleado.

---

## 🧱 4. Control de Inventario

### Resumen de Inventario
* **Método:** `GET`
* **Endpoint:** `/api/inventory/summary`
* **Response (200 OK):**
  ```json
  {
      "totalArticulos": 150,
      "stockBajo": 12,
      "stockCritico": 3
  }
  ```

### Añadir Nuevo Material al Catálogo
* **Método:** `POST`
* **Endpoint:** `/api/inventory`
* **Body Request:**
  ```json
  {
      "nombre": "Varilla de Acero 1/2",
      "descripcion": "Acero corrugado",
      "stock": 0,
      "stockMaximo": 1000
  }
  ```

### Listar Inventario
* **Método:** `GET`
* **Endpoint:** `/api/inventory`
* **Response (200 OK):**
  ```json
  [
      {
          "id": 1,
          "nombre": "Cemento",
          "descripcion": "Portland Grado 43",
          "stock": 450,
          "maxStock": 600,
          "porcentajeCantidad": 75,
          "estado": "Saludable"
      }
  ]
  ```

### Registrar Transacción (Entrada/Salida)
* **Método:** `POST`
* **Endpoint:** `/api/inventory/{materialId}/transaction`
* **Body Request:**
  ```json
  {
      "tipo": "ENTRADA", 
      "cantidad": 50,
      "nota": "Pedido a proveedor Holcim"
  }
  ```

### Historial de Movimientos
* **Método:** `GET`
* **Endpoint:** `/api/inventory/history`
* **Response (200 OK):**
  ```json
  [
      {
          "motivo": "ENTRADA de Cemento",
          "descripcion": "+50 unidades",
          "fechaActividad": "2026-02-14T09:30:00" 
      }
  ]
  ```

### Actualizar Material
* **Método:** `PUT`
* **Endpoint:** `/api/inventory/{id}`
* **Body Request:**
  ```json
  {
      "nombre": "Cemento cola", 
      "descripcion": "Descripcion",
      "stock": 21,
      "maxStock": 40,
  }
  ```

---

## 📍 5. Portal del Obrero (Asistencia Móvil)

### Verificar Estado de Hoy
* **Método:** `GET`
* **Endpoint:** `/api/attendance/status?usuarioId=1`
* **Response (200 OK):**
  ```json
  {
      "yaMarco": true,
      "horaMarcado": "08:15 AM",
      "estado": "A TIEMPO"
  }
  ```

### Registrar Asistencia (GPS)
* **Método:** `POST`
* **Endpoint:** `/api/attendance/mark`
* **Body Request:**
  ```json
  {
      "usuarioId": 1,
      "latitud": -17.3895,
      "longitud": -66.1568
  }
  ```
* **Posibles Respuestas HTTP:**
  * `200 OK`: "Asistencia registrada exitosamente."
  * `400 Bad Request`: "Estás fuera del radio permitido..." o "La jornada ha terminado."
  * `409 Conflict`: "Ya has registrado tu asistencia el día de hoy."