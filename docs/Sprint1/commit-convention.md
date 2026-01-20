# Convención de Commits - Kiwisha

Seguimos el estándar de **Conventional Commits** para mantener un historial de cambios legible y facilitar la automatización de logs.

## Estructura del Mensaje
```
<tipo>(<alcance>): <descripción corta>

[cuerpo del mensaje opcional]

[pie de página opcional (ej. Closes #12)]
```

## Tipos Permitidos
- **feat**: Una nueva funcionalidad (ej. `feat(cart): agregar calculo de envio`).
- **fix**: Corrección de un error (ej. `fix(checkout): error en validacion de DNI`).
- **docs**: Cambios solo en la documentación (ej. `docs: actualizar readme con guia local`).
- **style**: Cambios que no afectan el significado del código (espacios, formato, etc.).
- **refactor**: Cambio en el código que ni corrige un error ni añade una funcionalidad.
- **perf**: Mejora de rendimiento.
- **test**: Añadir o corregir tests.
- **chore**: Tareas de mantenimiento, configuración de herramientas, dependencias.

## Ejemplos
- `docs(sprint1): crear estrategia de ramas y convencion de commits`
- `feat(catalog): implementar filtrado por categoria`
- `fix(model): remover dependencia de lombok por conflicto de entorno`
