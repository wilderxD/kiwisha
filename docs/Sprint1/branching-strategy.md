# Estrategia de Ramificación - Kiwisha

Para asegurar la estabilidad del proyecto y el trabajo colaborativo, utilizaremos una adaptación de **Git Flow**:

## Ramas Principales
- **`main`**: Contiene exclusivamente código estable y listo para producción. Cada merge en esta rama representa un incremento de versión. Está protegida.
- **`develop`**: Rama base para la integración de nuevas funcionalidades. Es donde se realizan las pruebas de integración. Está protegida.

## Ramas de Soporte
- **`feature/S[X]-[nombre]`**: Ramas temporales creadas a partir de `develop` para desarrollar historias de usuario o tareas técnicas. 
  - Ejemplo: `feature/S1-T02-branching-strategy`
- **`fix/[nombre]`**: Ramas para corregir errores detectados en `develop`.
- **`hotfix/[nombre]`**: Ramas críticas para corregir errores en `main` que no pueden esperar al siguiente ciclo.

## Reglas de Integración
1. Toda nueva funcionalidad nace de `develop`.
2. Para integrar en `develop` o `main`, es **obligatorio** crear un Pull Request (PR).
3. El PR debe ser revisado y aprobado por al menos un par (Peer Review).
4. Se deben resolver todos los comentarios del revisor antes del merge.
