
echo "Building Movie Vault application..."

./mvnw clean package -DskipTests

echo "Build completed successfully!"
echo "JAR file created at: target/movie-vault-1.0-SNAPSHOT.jar"

if [ -f "target/movie-vault-1.0-SNAPSHOT.jar" ]; then
    echo "Build successful - Ready for deployment"
    echo "To run locally: java -jar target/movie-vault-1.0-SNAPSHOT.jar"
else
    echo "Build failed - JAR file not found"
    exit 1
fi
