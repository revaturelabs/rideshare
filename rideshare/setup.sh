#!/bin/sh
# This is a shell script for setting up to execute the build process for the Revature RideShare project.
# This script only needs to be run once on a machine.

# Install nvm
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.2/install.sh | bash
# Make sure nvm is available
. ~/.bashrc
# Install Node.js and npm
nvm install v6.11.1
# Get the latest version of npm
npm i -g npm
