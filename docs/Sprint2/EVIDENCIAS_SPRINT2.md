# Sprint 2: Gestión Avanzada y Fidelización - Kiwisha E-Commerce

Este documento detalla las Historias de Usuario (US) implementadas durante el Sprint 2, enfocadas en la seguridad, administración del catálogo y herramientas de fidelización del cliente.

---

### S2-US01: Registro de Nuevos Clientes
**Historia:** Como visitante, quiero registrarme con mi correo y contraseña para tener un historial de mis pedidos.
**Criterios de Aceptación:**
**Escenario: Registro exitoso con email válido**

**Dado** que soy un visitante y no tengo una cuenta registrada
**Y** ingreso un correo con formato válido y una contraseña
**Cuando** envío el formulario/solicitud de registro
**Entonces** el sistema persiste el nuevo usuario en el backend
**Y** muestra/devuelve un mensaje de éxito del registro
**Implementación:** `AuthController.java` -> `/api/auth/register`
**Cómo evidenciar:** Realiza una petición `POST` a `http://localhost:8081/api/auth/register` con un JSON que contenga `"email"` y `"password"`. El sistema devolverá un mensaje de confirmación exitosa.

---

### S2-US02: Inicio de Sesión y Seguridad
**Historia:** Como cliente, quiero iniciar sesión de forma segura para acceder a mi perfil y carritos guardados.
**Criterios de Aceptación:**
**Escenario: Inicio de sesión y manejo de errores**

**Dado** que existe una pantalla de login accesible para el usuario
**Cuando** ingreso credenciales válidas
**Entonces** el sistema valida las credenciales y me autentica
**Y** me redirige al inicio

**Pero si** ingreso un usuario inexistente o una contraseña incorrecta
**Entonces** el sistema rechaza el acceso
**Y** muestra un mensaje de error visible
**Implementación:** `LoginController.java` y `login.html`
**Cómo evidenciar:** Ve a `http://localhost:8081/login`. Ingresa el usuario `admin@kiwisha.com` y clave `123`. Si es correcto, te redirigirá al inicio; de lo contrario, mostrará un mensaje de error en rojo.

---

### S2-US03: Panel de Administración (Dashboard)
**Historia:** Como administrador, quiero ver un resumen de ventas y alertas para gestionar el negocio.
**Criterios de Aceptación:**
**Escenario: Dashboard disponible solo para administradores**

**Dado** que soy un usuario con rol administrativo
**Cuando** accedo al endpoint del dashboard
**Entonces** veo métricas clave (por ejemplo: total de ventas y pedidos del día)
**Y** veo alertas de productos con stock bajo (crítico)

**Y dado** que soy un usuario sin rol administrativo
**Cuando** intento acceder al endpoint del dashboard
**Entonces** el sistema restringe el acceso
**Implementación:** `ProductAdminController.java` -> `/api/admin/dashboard/stats`
**Cómo evidenciar:** Accede a `http://localhost:8081/api/admin/dashboard/stats`. Verás un JSON con `totalSales`, `ordersCount` y `lowStockAlerts` calculados en tiempo real desde la base de datos H2.

---

### S2-US04: Gestión de Inventario (CRUD)
**Historia:** Como administrador, quiero crear y editar productos para mantener el catálogo al día.
**Criterios de Aceptación:**
**Escenario: Administrador gestiona el inventario (CRUD)**

**Dado** que soy un administrador
**Cuando** creo un producto con Nombre, SKU, Precio y Stock
**Entonces** el producto queda registrado y disponible en el catálogo

**Y dado** que existe un producto previamente registrado
**Cuando** edito cualquiera de sus campos
**Entonces** los cambios se guardan y se reflejan en el catálogo

**Y dado** que existe un producto registrado
**Cuando** lo doy de baja o lo elimino
**Entonces** el producto deja de estar disponible en el catálogo
**Implementación:** `ProductAdminController.java` (Endpoints POST, PUT, DELETE)
**Cómo evidenciar:** Usa herramientas como Postman o `curl` para enviar un `PUT` a `/api/admin/products/1` con nuevos datos de stock. Luego verifica en `/productos` que el cambio se refleja inmediatamente.

---

### S2-US05: Gestión de Pedidos (Admin)
**Historia:** Como administrador, quiero gestionar los estados de los pedidos para informar sobre el avance del envío.
**Criterios de Aceptación:**
**Escenario: Administrador actualiza el estado de un pedido**

**Dado** que soy un administrador
**Y** existen pedidos recibidos en el sistema
**Cuando** solicito la lista de pedidos
**Entonces** veo todos los pedidos recibidos

**Y dado** que selecciono un pedido
**Cuando** cambio su estado (Pendiente -> Enviado -> Entregado)
**Entonces** el sistema actualiza el estado del pedido
**Y** registra la fecha del cambio de estado para trazabilidad
**Implementación:** `OrderController.java` -> `PATCH /api/orders/{id}/status`
**Cómo evidenciar:** Envía una petición `PATCH` a `/api/orders/1/status` con el cuerpo `{"status": "Entregado"}`. El sistema confirmará el cambio de estado del pedido.

---

### S2-US06: Historial de Pedidos (Cliente)
**Historia:** Como cliente, quiero ver mis pedidos realizados anteriormente para llevar un control.
**Criterios de Aceptación:**
**Escenario: Cliente consulta su historial de pedidos**

**Dado** que soy un cliente autenticado
**Cuando** accedo a la sección "Mis Pedidos" en mi perfil
**Entonces** veo una lista cronológica de mis compras con su estado actual
**Y** puedo ver el detalle de cada orden (expandible o consultable)
**Implementación:** `OrderController.java` -> `GET /api/orders/history/{client}`
**Cómo evidenciar:** Accede a `http://localhost:8081/api/orders/history/Juan`. Obtendrás la lista de pedidos asociados a ese usuario con sus respectivos montos y estados.

---

### S2-US07: Valoración y Reseñas
**Historia:** Como cliente, quiero calificar los productos para compartir mi experiencia con otros.
**Criterios de Aceptación:**
**Escenario: Cliente publica una reseña visible en el producto**

**Dado** que soy un cliente
**Y** estoy en el detalle de un producto
**Cuando** envío una valoración de 1 a 5 estrellas
**Y** opcionalmente agrego un comentario de texto
**Entonces** el sistema guarda la reseña
**Y** la reseña queda visible públicamente en el detalle del producto
**Implementación:** `FeatureController.java` -> `POST /api/features/reviews`
**Cómo evidenciar:** Envía un `POST` a `/api/features/reviews` con una calificación y comentario. El endpoint retornará un mensaje confirmando que la reseña ha sido guardada.

---

### S2-US08: Lista de Deseos (Wishlist)
**Historia:** Como cliente, quiero guardar productos en una lista de deseos para comprarlos en el futuro.
**Criterios de Aceptación:**
**Escenario: Cliente gestiona su wishlist**

**Dado** que soy un cliente
**Cuando** selecciono la opción "Añadir a Wishlist" en un producto
**Entonces** el producto se agrega a mi lista de deseos

**Y dado** que tengo una sección privada para mi wishlist
**Cuando** accedo a mi wishlist
**Entonces** puedo ver y gestionar los productos guardados

**Y dado** que tengo un producto en la wishlist
**Cuando** elijo moverlo al carrito
**Entonces** el producto se agrega al carrito y se elimina de la wishlist
**Implementación:** `FeatureController.java` -> `POST /api/features/wishlist`
**Cómo evidenciar:** Utiliza el endpoint `/api/features/wishlist` enviando el código del producto deseado. Recibirás una confirmación de que ha sido agregado a tu lista personal.

---

### S2-US09: Cupones de Descuento
**Historia:** Como cliente, quiero aplicar cupones para obtener descuentos en mi compra.
**Criterios de Aceptación:**
**Escenario: Cliente valida y aplica un cupón de descuento**

**Dado** que estoy en el carrito
**Cuando** ingreso un código de cupón
**Entonces** el sistema valida en tiempo real su existencia y vigencia

**Y dado** que el cupón es válido
**Cuando** el sistema aplica el cupón
**Entonces** el descuento se refleja en el total final antes del checkout
**Implementación:** `FeatureController.java` -> `GET /api/features/coupons/validate/{code}`
**Cómo evidenciar:** Prueba el código `KIWISHA2026` en la URL `http://localhost:8081/api/features/coupons/validate/KIWISHA2026`. Verás un JSON confirmando el descuento del 15%.

---

### S2-US10: Recuperación de Contraseña
**Historia:** Como usuario, quiero recuperar mi cuenta si olvidé mi clave para no perder mi acceso.
**Criterios de Aceptación:**
**Escenario: Usuario inicia recuperación de contraseña**

**Dado** que estoy en la pantalla de login
**Cuando** selecciono la opción "¿Olvidaste tu contraseña?"
**Entonces** puedo iniciar el flujo de recuperación

**Y dado** que ingreso un correo registrado
**Cuando** envío la solicitud de recuperación
**Entonces** el sistema confirma el envío (simulado) del enlace/mensaje de recuperación
**Y** me permite continuar un flujo seguro para asignar una nueva contraseña
**Implementación:** `AuthController.java` -> `/api/auth/recovery`
**Cómo evidenciar:** Realiza un `POST` a `/api/auth/recovery` enviando tu email. El sistema simulará el envío de un enlace de recuperación confirmando el destino por pantalla.


---
**Nota:** Todas las implementaciones han sido validadas en el entorno local (Puerto 8081) y cumplen con los criterios de aceptación definidos para el Sprint 2.
