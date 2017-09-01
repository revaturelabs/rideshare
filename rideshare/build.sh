#!/bin/sh
# This is a shell script for executing the entired build process for the Revature RideShare project.

# Install all JavaScript dependencies
(cd {ABSOLUTEPATHOFWORKSPACE} && npm install)
# Run webpack
(cd {ABSOLUTEPATHOFWORKSPACE} && npm run build)
