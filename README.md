# ServicioWeb

Aplicación JavaFX que consulta la tabla `customers` de Supabase (API REST) y la muestra en una tabla con búsqueda por nombre, correo o ciudad.

## Configuración

| Variable | Obligatoria | Descripción |
|---|---|---|
| `SUPABASE_API_KEY` | Sí | Clave de la API de Supabase |
| `SUPABASE_URL` | No | URL base del proyecto (por defecto, la del proyecto actual) |

## Ejecutar

```bash
export SUPABASE_API_KEY=tu_clave
./mvnw clean javafx:run
```

Requiere JDK 21.
