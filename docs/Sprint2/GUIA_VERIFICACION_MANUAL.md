# Guía de Verificación Manual - Sprint 2 y 3 (Navegación por Botones)

Este documento describe cómo validar las Historias de Usuario utilizando la interfaz gráfica y el sistema de navegación por botones implementado. **Ya no es necesario escribir las URLs manualmente.**

---

### 1. Sistema de Administración (S2-US03)
*   **Acción:** En la barra de navegación superior de la página de inicio, haz clic en el botón amarillo **⚙ Admin**.
*   **Resultado esperado:** Se abre el Dashboard Administrativo con métricas en tiempo real de ventas, pedidos y stock.

### 2. Validación de Cupones (S2-US09)
*   **Acción:** Desde el **Dashboard Admin**, haz clic en el enlace **"Probar Cupones"** situado en la barra de navegación superior.
*   **Resultado esperado:** Se muestra la validación visual del cupón `KIWISHA2026` con su respectivo ticket de descuento.

### 3. Historial de Pedidos del Cliente (S2-US06)
*   **Acción:** En cualquier página del sitio (Inicio, Catálogo), haz clic en el enlace **Mis Pedidos 📦** de la barra de navegación.
*   **Resultado esperado:** Se visualiza la tabla de pedidos del usuario actual ("Juan" para efectos de la demo) con estados actualizados.

### 4. Lista de Deseos - Wishlist (S2-US08)
*   **Acción:** Haz clic en el enlace **Lista de Deseos ❤️** en la barra de navegación.
*   **Resultado esperado:** Se abre la página de Wishlist con productos guardados y botones para "Mover al Carrito".

### 5. Registro y Recuperación (S2-US01 / S2-US10)
*   **Registro:** Haz clic en **Login** y luego en el botón **"Regístrate gratis"**. Completa el formulario visual.
*   **Recuperación:** En la pantalla de Login, haz clic en **"¿Olvidaste tu contraseña? Recupérala aquí"**.
*   **Resultado esperado:** Interfaces fluidas y visuales para la gestión de cuentas de usuario.

---

**Nota Técnica:** Todas las páginas cuentan con una barra de navegación persistente para facilitar el flujo del usuario sin errores de escritura en los endpoints.
