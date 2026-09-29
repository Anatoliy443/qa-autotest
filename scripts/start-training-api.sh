#!/usr/bin/env bash
set -euo pipefail

# Отдельная база в памяти: перезапуск очищает учебные данные.
project_dir="$(cd "$(dirname "$0")/.." && pwd)"
archive="${1:-$HOME/Downloads/oa_at_train_project-1.0-SNAPSHOT.jar.zip}"
if [[ ! -f "$archive" ]]; then
    echo "Архив не найден: $archive" >&2
    exit 1
fi
mkdir -p "$project_dir/build/training-api"
jar_path="$project_dir/build/training-api/oa_at_train_project-1.0-SNAPSHOT.jar"
unzip -p "$archive" oa_at_train_project-1.0-SNAPSHOT.jar > "$jar_path"
java_command="java"
if [[ -n "${JAVA_HOME:-}" ]]; then
    java_command="$JAVA_HOME/bin/java"
fi
exec "$java_command" -jar "$jar_path" \
    --server.address=127.0.0.1 \
    '--spring.datasource.url=jdbc:h2:mem:homework4;DB_CLOSE_DELAY=-1' \
    --spring.h2.console.enabled=false
