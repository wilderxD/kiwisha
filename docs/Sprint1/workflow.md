# Flujo de Trabajo Colaborativo y Peer Review

Este documento define el proceso de colaboración y los estándares de calidad para integrar cambios en el repositorio.

## 1. Flujo de Desarrollo
1. Crear un Issue para la tarea (o asignar uno existente).
2. Crear una rama `feature/` desde la última versión de `develop`.
3. Desarrollar y realizar commits siguiendo la convención.
4. Antes de subir, sincronizar con `develop` (`git pull origin develop`).
5. Abrir Pull Request hacia `develop`.

## 2. Peer Review (Revisión por Pares)
- **Obligatorio**: Todo PR requiere al menos 1 aprobación.
- **Enfoque**: Se revisa la legibilidad, cumplimiento de requerimientos técnicos, estándares de código y seguridad.
- **Iteración**: Si hay observaciones (Request Changes), el autor debe corregirlas y notificar al revisor.

## 3. Definition of Done (DoD) - General
Para que una tarea se considere finalizada:
- [ ] El código compila sin errores.
- [ ] No hay archivos basura o temporales incluidos en el commit.
- [ ] Se cumple con la lógica de negocio descrita en la HU o Tarea Técnica.
- [ ] La documentación ha sido actualizada (si aplica).
- [ ] El PR ha sido aprobado y fusionado.

## 4. Cierre de Issues
El PR debe incluir en su descripción `Closes #ID` para que el Issue se cierre automáticamente tras el merge en la rama principal.
