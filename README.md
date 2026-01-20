# 🌾 Kiwisha — E-Commerce Platform (Proyecto Ágil)

Plataforma web de comercio electrónico orientada a productos andinos y derivados de kiwicha.  
Este repositorio se gestiona bajo un enfoque **ágil (Scrum)**, evidenciando trabajo colaborativo, control de versiones y buenas prácticas de desarrollo.

---

## 🎯 Objetivo del Proyecto
Optimizar el proceso de **venta online** (catálogo → carrito → checkout → pedido) y la **gestión administrativa** (productos, pedidos, clientes, contenido y configuración) mediante una solución tecnológica trazable y escalable.

---

## 🧩 Alcance Funcional (Visión)
**Usuario final**
- Navegar catálogo y buscar productos
- Filtrar productos
- Gestionar carrito
- Realizar checkout y seguimiento de pedidos
- Gestionar perfil y datos personales

**Administrador**
- Gestionar productos (estados, stock, precio, SKU)
- Gestionar pedidos (estados, cancelaciones, trazabilidad)
- Gestionar clientes y reseñas
- Gestionar contenido del sitio (páginas, etiquetas, imágenes)
- Reportes básicos

---

## 🏃‍♂️ Enfoque de Trabajo (Scrum)
El proyecto se desarrolla en **4 sprints (8 semanas)**.  
Cada sprint entrega un incremento funcional y evidencia:
- **Historias técnicas** (herramientas, colaboración, automatización, despliegue, seguridad)
- **Historias de usuario** (valor para el negocio)
- Trazabilidad en GitHub: **Issues → PRs → Reviews → Merge → Releases**

---

## ✅ Sprint 1 (Semanas 1–2) — Herramientas y Gobierno del Repositorio
En este sprint se configura el entorno de trabajo profesional y las reglas de colaboración:

- Configuración de ramas (`main`, `develop`) y estrategia de ramificación
- Convención de commits (Conventional Commits)
- Plantillas de Issues y Pull Requests
- Protección de ramas (Branch Protection Rules)
- Flujo de revisión por pares (Peer Review)
- Gestión de evidencias del sprint

📌 Evidencias y backlog del Sprint 1:
- Milestone **Sprint 1**
- Project **Kiwisha - Scrum Board**
- Issues etiquetados `sprint-1`
- PRs con aprobación obligatoria

---

## 🗂️ Organización del Repositorio
- `/docs/` → documentación por sprint (backlog, evidencias, acuerdos)
- Issues → tareas técnicas e historias de usuario
- Pull Requests → integración controlada a `develop`

---

## 🧾 Convención de Commits
Usamos **Conventional Commits**:

- `feat: ...` nueva funcionalidad
- `fix: ...` corrección
- `docs: ...` documentación
- `chore: ...` tareas de mantenimiento/configuración
- `refactor: ...` refactor sin cambio funcional
- `ci: ...` automatización/pipelines

Ejemplos:
- `docs(sprint1): agregar backlog y DoD del Sprint 1`
- `feat(templates): agregar templates de issues y PR`

---

## 🔀 Flujo de Trabajo (Branching)
- `main`: rama estable
- `develop`: rama de integración
- `feature/<tema>`: ramas de trabajo por tarea
- Todo cambio entra por **Pull Request** hacia `develop` con **aprobación**.

---

## 👥 Equipo y Roles
- **Product Owner (PO):** prioriza el backlog y valida el valor de entrega
- **Scrum Master (SM):** facilita el proceso Scrum y elimina bloqueos
- **Developers:** implementan tareas técnicas e historias de usuario, creando PRs y revisiones

> Los responsables y tareas específicas se gestionan en Issues y en el tablero del proyecto.

---

## 🔒 Seguridad y Buenas Prácticas
- PR obligatorio para merge
- Aprobación mínima por PR
- Conversaciones resueltas antes de merge
- Sin credenciales en el repositorio (usar variables de entorno)

---

## 📌 Estado
En desarrollo activo.  
La planificación y evidencias del Sprint 1 se gestionan en GitHub (Milestone + Project Board).

---
