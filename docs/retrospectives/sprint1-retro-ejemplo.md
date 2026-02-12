# Retrospectiva Sprint 2 -  "Estabilización del Core"

**Fecha:** 2026-02-01  
**Facilitador:** GitHub Copilot  
**Participantes:** Equipo Kiwisha  

---

## 1. 🌈 ¿Qué salió bien?
*   Se completó la migración de Lombok a POJOs estándar, eliminando errores de compilación.
*   El diseño del catálogo con Thymeleaf es mucho más amigable que el JSON inicial.
*   Se implementó un sistema de navegación global (Navbar) funcional.

## 2. 🌪️ ¿Qué se puede mejorar?
*   **Gestión de imágenes:** Las URLs externas de Unsplash a veces traen contenido no deseado (como el famoso gallo 🐓).
*   **Ambiente local:** Conflictos frecuentes con el puerto 8081 al no cerrar procesos previos.

## 3. 💡 Ideas y Sugerencias
*   Crear una biblioteca local de imágenes para los productos principales.
*   Automatizar la limpieza de puertos en el script de arranque.

## 4. ✅ Plan de Acción (Compromisos)
| Acción | Responsable | Estado | Ticket JIRA |
| :--- | :--- | :--- | :--- |
| Implementar fallbacks con emojis para todas las imágenes | Dev Team | Completado | KIWI-105 |
| Documentar el proceso de retrospectivas en GitHub | Scrum Master | En progreso | TECH-55 |
| Crear script de limpieza de puerto 8081 | DevOps | Pendiente | KIWI-106 |

