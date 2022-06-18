#!/bin/bash

set -e
echo "GTG# compiling bulma"
#cd /home/DEV/Workspaces/intellij/lofidrox/src/WebContent/frameworks/bulma/
#/opt/sass/sass ./bulma.sass ./bulma.css --no-source-map --stop-on-error --style compressed

echo "GTG# compiling svelte"
cd /home/DEV/Workspaces/intellij/lofidrox
npm run build

#echo "GTG# Copying resources"
rm /home/DEV/Workspaces/intellij/lofidrox/src/WebContent/project/final/images/*
cp /home/DEV/Workspaces/intellij/lofidrox/src/WebContent/project/img/* /home/DEV/Workspaces/intellij/lofidrox/src/WebContent/project/final/images/
