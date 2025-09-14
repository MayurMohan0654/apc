# Movie Vault Deployment Guide

## Render Deployment

### Prerequisites
1. GitHub repository with your code
2. Render account
3. Database service (MySQL/PostgreSQL)

### Deployment Steps

#### Option 1: Using Render Dashboard (Recommended)

1. **Create Database**
   - Go to Render Dashboard
   - Create a new PostgreSQL/MySQL database
   - Note the connection details

2. **Create Web Service**
   - Connect your GitHub repository
   - Choose "Web Service"
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/movie-vault-1.0-SNAPSHOT.jar`

3. **Environment Variables**
   Set these in Render dashboard:
   ```
   SPRING_PROFILES_ACTIVE=prod
   PORT=8080
   DATABASE_URL=<your-database-url>
   DB_USERNAME=<your-db-username>
   DB_PASSWORD=<your-db-password>
   JWT_SECRET=<generate-long-random-string>
   JWT_EXPIRATION=86400000
   ALLOWED_ORIGINS=https://your-frontend-domain.onrender.com
   ```

#### Option 2: Using render.yaml

1. Push the `render.yaml` file to your repository
2. Render will automatically detect and deploy

### Local Testing Commands

```bash
# Build the application
./mvnw clean package

# Run locally
java -jar target/movie-vault-1.0-SNAPSHOT.jar

# Or use the build script
./build.sh
```

### Production URLs
- Backend: `https://your-app-name.onrender.com`
- API Base: `https://your-app-name.onrender.com/api`

### Important Notes

1. **Database**: Use environment variables for database connection
2. **CORS**: Configure allowed origins for your frontend domain
3. **JWT Secret**: Use a strong, random secret key
4. **Port**: Render assigns the PORT environment variable
5. **Profiles**: Use `prod` profile for production settings

### Troubleshooting

1. **Build Fails**: Check Java version compatibility (using Java 21)
2. **Database Connection**: Verify DATABASE_URL format
3. **CORS Issues**: Update ALLOWED_ORIGINS with your frontend URL
4. **Memory Issues**: Render free tier has memory limits

### Health Check Endpoint
- GET `/actuator/health` - Application health status
