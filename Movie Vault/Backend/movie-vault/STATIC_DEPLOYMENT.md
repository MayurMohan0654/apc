# Deployment Guide for Movie Vault (Static Files + API)

## Your Setup
- ✅ **Backend API**: Spring Boot with controllers
- ✅ **Frontend**: Static HTML/CSS/JS files in `src/main/resources/static/`
- ✅ **Database**: MySQL on db4free.net (free)
- ✅ **Hosting**: Render (free tier)

## File Structure (after deployment):
```
https://movie-vault.onrender.com/
├── /                          → index.html (homepage)
├── /movies.html               → movies page
├── /user-dashboard.html       → user dashboard  
├── /theater-dashboard.html    → theater dashboard
├── /booking-confirmation.html → booking page
├── /css/                      → stylesheets
├── /js/                       → javascript files
└── /api/                      → REST API endpoints
```

## Access URLs (after deployment):
- **Frontend**: `https://movie-vault.onrender.com/`
- **API Base**: `https://movie-vault.onrender.com/api/`
- **Health Check**: `https://movie-vault.onrender.com/actuator/health`

## JavaScript API Calls Update

Your frontend JavaScript should call APIs like:
```javascript
// Instead of localhost:8080
const API_BASE = window.location.origin + '/api';

// Example API calls:
fetch(`${API_BASE}/movies`)           // Get movies
fetch(`${API_BASE}/users/login`)      // User login  
fetch(`${API_BASE}/bookings`)         // Get bookings
```

## Deployment Steps:

### 1. Update Your JavaScript Files
Make sure your `src/main/resources/static/js/utils.js` uses relative URLs:
```javascript
const API_BASE = '/api'; // Remove localhost references
```

### 2. Build and Deploy
```bash
# Build your application
./mvnw clean package -DskipTests

# Your JAR will include both API and static files
ls target/movie-vault-1.0-SNAPSHOT.jar
```

### 3. Deploy to Render
1. **Push code** to GitHub
2. **Connect repository** to Render
3. **Set environment variables** (already in your render.yaml):
   ```
   DATABASE_URL=jdbc:mysql://db4free.net:3306/movie_vault?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   DB_USERNAME=nishan2024
   DB_PASSWORD=nishan137
   SPRING_PROFILES_ACTIVE=prod
   ```

### 4. Access Your App
- **Homepage**: https://movie-vault.onrender.com/
- **All static files** served automatically
- **API endpoints** available at `/api/*`

## Benefits of This Setup:
- ✅ **Single deployment** - no separate frontend hosting needed
- ✅ **No CORS issues** - frontend and backend on same domain
- ✅ **Free hosting** - everything on Render free tier
- ✅ **Simple URLs** - clean, professional links

## Important Notes:
- Your static files are automatically served from `/`
- API endpoints are available at `/api/*`
- No need for separate frontend deployment
- Perfect for college project demonstrations!

## Testing Locally:
```bash
# Run locally
java -jar target/movie-vault-1.0-SNAPSHOT.jar

# Access at:
# Frontend: http://localhost:8080/
# API: http://localhost:8080/api/
```

Your setup is perfect for a college project! 🎓
