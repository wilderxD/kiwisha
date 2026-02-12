$token = $env:GITHUB_TOKEN
$owner = "Kiwisha-Team"
$repo = "Kiwisha"

$headers = @{
    "Authorization" = "Bearer $token"
    "Accept"        = "application/vnd.github+json"
    "X-GitHub-Api-Version" = "2022-11-28"
}

$issue = @{
    title = "S2-TS04: Documentar Retrospectiva Sprint 1"
    body = "Como equipo, queremos documentar las retrospectivas para mejora continua. Se ha creado la plantilla y un ejemplo basado en el Sprint 1."
    labels = @("technical-story", "documentacion")
}

$bodyJson = $issue | ConvertTo-Json
try {
    $result = Invoke-RestMethod -Uri "https://api.github.com/repos/$owner/$repo/issues" -Method Post -Headers $headers -Body $bodyJson
    Write-Host "Issue creada exitosamente: $($result.html_url)"
} catch {
    Write-Error "Error al crear la issue: $_"
}
