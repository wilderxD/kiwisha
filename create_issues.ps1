
$token = $env:GITHUB_TOKEN
$owner = "Kiwisha-Team"
$repo = "Kiwisha"

# Cabeceras ajustadas para Fine-grained PAT
$headers = @{
    "Authorization" = "Bearer $token"
    "Accept"        = "application/vnd.github+json"
    "X-GitHub-Api-Version" = "2022-11-28"
}

$issues = @(
    @{title="S2-US01: Registro de Nuevos Clientes"; body="**Historia:** Como visitante, quiero registrarme con mi correo y contraseña para tener un historial de mis pedidos.`n`n**Criterios de Aceptación:**`n- [ ] Formulario de registro con validación de formato de email.`n- [ ] Persistencia de datos en el sistema (Backend).`n- [ ] Mensaje de éxito tras el registro."; labels=@("user-story", "sprint-2")},
    @{title="S2-US02: Inicio de Sesión y Seguridad"; body="**Historia:** Como cliente, quiero iniciar sesión de forma segura para acceder a mi perfil y carritos guardados.`n`n**Criterios de Aceptación:**`n- [ ] Pantalla de login accesible.`n- [ ] Validación de credenciales contra la base de datos.`n- [ ] Manejo de errores para usuarios no encontrados o contraseñas incorrectas."; labels=@("user-story", "sprint-2")},
    @{title="S2-US03: Panel de Administración (Dashboard)"; body="**Historia:** Como administrador, quiero ver un resumen de ventas y alertas para gestionar el negocio.`n`n**Criterios de Aceptación:**`n- [ ] Visualización de métricas clave (Total ventas, Pedidos del día).`n- [ ] Alerta visual de productos con stock bajo (crítico).`n- [ ] Acceso restringido solo a usuarios con rol administrativo."; labels=@("user-story", "sprint-2")},
    @{title="S2-US04: Gestión de Inventario (CRUD)"; body="**Historia:** Como administrador, quiero crear y editar productos para mantener el catálogo al día.`n`n**Criterios de Aceptación:**`n- [ ] Formulario para agregar nuevos productos (Nombre, SKU, Precio, Stock).`n- [ ] Posibilidad de editar cualquier campo de un producto existente.`n- [ ] Acción de 'Dar de baja' o eliminar producto."; labels=@("user-story", "sprint-2")},
    @{title="S2-US05: Gestión de Pedidos (Admin)"; body="**Historia:** Como administrador, quiero gestionar los estados de los pedidos para informar sobre el avance del envío.`n`n**Criterios de Aceptación:**`n- [ ] Lista de todos los pedidos recibidos.`n- [ ] Selector para cambiar estado: Pendiente -> Enviado -> Entregado.`n- [ ] Trazabilidad de la fecha de cambio de estado."; labels=@("user-story", "sprint-2")},
    @{title="S2-US06: Historial de Pedidos (Cliente)"; body="**Historia:** Como cliente, quiero ver mis pedidos realizados anteriormente para llevar un control.`n`n**Criterios de Aceptación:**`n- [ ] Sección 'Mis Pedidos' en el perfil del usuario.`n- [ ] Lista cronológica de compras con su estado actual.`n- [ ] Detalle expandible de cada orden."; labels=@("user-story", "sprint-2")},
    @{title="S2-US07: Valoración y Reseñas"; body="**Historia:** Como cliente, quiero calificar los productos para compartir mi experiencia con otros.`n`n**Criterios de Aceptación:**`n- [ ] Sistema de puntuación por estrellas (1 a 5).`n- [ ] Campo opcional de texto para comentarios.`n- [ ] Las reseñas son visibles públicamente en el detalle del producto."; labels=@("user-story", "sprint-2")},
    @{title="S2-US08: Lista de Deseos (Wishlist)"; body="**Historia:** Como cliente, quiero guardar productos en una lista de deseos para comprarlos en el futuro.`n`n**Criterios de Aceptación:**`n- [ ] Botón 'Añadir a Wishlist' en cada producto.`n- [ ] Sección privada para gestionar la lista.`n- [ ] Posibilidad de mover un producto de la Wishlist directamente al Carrito."; labels=@("user-story", "sprint-2")},
    @{title="S2-US09: Cupones de Descuento"; body="**Historia:** Como cliente, quiero aplicar cupones para obtener descuentos en mi compra.`n`n**Criterios de Aceptación:**`n- [ ] Campo especial en el carrito para ingresar el código.`n- [ ] Validación en tiempo real del cupón (Vigencia/Existencia).`n- [ ] Descuento aplicado visible en el total final antes del checkout."; labels=@("user-story", "sprint-2")},
    @{title="S2-US10: Recuperación de Contraseña"; body="**Historia:** Como usuario, quiero recuperar mi cuenta si olvidé mi clave para no perder mi acceso.`n`n**Criterios de Aceptación:**`n- [ ] Opción '¿Olvidaste tu contraseña?' en el login.`n- [ ] Envío de mensaje de confirmación al correo registrado.`n- [ ] Flujo seguro para asignar una nueva contraseña."; labels=@("user-story", "sprint-2")},
    @{title="S3-TS01: Configuración de Pipeline de CI (Jenkins)"; body="**Historia:** Como equipo de desarrollo, queremos un pipeline de CI automatizado para validar el código en cada integración.`n`n**Criterios de Aceptación:**`n- [ ] Creación de Jenkinsfile funcional.`n- [ ] Etapa de Checkout y Compilación exitosa.`n- [ ] Notificación de fallos en el pipeline."; labels=@("tech-story", "sprint-3")},
    @{title="S3-TS02: Pruebas Automáticas y Calidad de Código"; body="**Historia:** Como equipo de desarrollo, queremos que el pipeline ejecute pruebas y análisis de calidad para asegurar la estabilidad del software.`n`n**Criterios de Aceptación:**`n- [ ] Ejecución de pruebas unitarias con JUnit.`n- [ ] Reporte de cobertura con JaCoCo.`n- [ ] Análisis estático de código con Checkstyle."; labels=@("tech-story", "sprint-3")},
    @{title="S3-TS03: Gestión de Artefactos y Maven Wrapper"; body="**Historia:** Como equipo, queremos estandarizar la versión de Maven y automatizar el empaquetado para facilitar los despliegues.`n`n**Criterios de Aceptación:**`n- [ ] Configuración de .mvn/wrapper.`n- [ ] Generación automatizada del archivo .jar en el pipeline.`n- [ ] Los artefactos se archivan en Jenkins para su descarga."; labels=@("tech-story", "sprint-3")}
)

foreach ($issue in $issues) {
    $bodyJson = $issue | ConvertTo-Json
    Invoke-RestMethod -Uri "https://api.github.com/repos/$owner/$repo/issues" -Method Post -Headers $headers -Body $bodyJson
    Write-Host "Creado: $($issue.title)"
    Start-Sleep -Milliseconds 500
}
