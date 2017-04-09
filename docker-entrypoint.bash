#!/bin/bash
wget -O /credentials.json --header="Metadata-Flavor: Google" http://metadata.google.internal/computeMetadata/v1/project/attributes/credentials \
  && source /setup-env.bash \
  && exec env JAVA_OPTS="$JAVA_OPTS" "$@" 2>&1