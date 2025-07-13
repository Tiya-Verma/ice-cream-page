#!/bin/bash

# Clean and compile the Ice Cream Shop Pre-Order System
echo "Cleaning previous compilation..."
rm -rf bin/*

echo "Compiling source files..."
javac -d bin -cp "lib/*:src/main:src/test" src/main/model/*.java src/main/ui/*.java src/test/model/*.java

echo "Compilation complete! Run with: java -cp \"lib/*:bin\" ui.Main" 