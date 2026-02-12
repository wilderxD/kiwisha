$owner = "Kiwisha-Team"
$repo = "Kiwisha"
$token = $env:GITHUB_TOKEN # Se recomienda usar variable de entorno para seguridad

$headers = @{
    "Authorization" = "Bearer $token"
    "Accept"        = "application/vnd.github+json"
    "X-GitHub-Api-Version" = "2022-11-28"
}

# Obtener todos los issues abiertos
$url = "https://api.github.com/repos/$owner/$repo/issues?state=open"
$openIssues = Invoke-RestMethod -Uri $url -Headers $headers -Method Get

foreach ($issue in $openIssues) {
    Write-Host "Cerrando Issue $($issue.number): $($issue.title)..."
    $updateUrl = "https://api.github.com/repos/$owner/$repo/issues/$($issue.number)"
    $body = @{ state = "closed" } | ConvertTo-Json
    
    try {
        Invoke-RestMethod -Uri $updateUrl -Headers $headers -Method Patch -Body $body
        Write-Host "Completado."
    } catch {
        Write-Host "Error al cerrar el issue $($issue.number): $($_.Exception.Message)"
    }
}
