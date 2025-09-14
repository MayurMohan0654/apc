# Free MySQL Hosting Options for College Project

## Recommended: PlanetScale (Best for College Projects)

### Why PlanetScale?
- ✅ **Completely Free** tier (no credit card needed initially)
- ✅ **MySQL Compatible** (no code changes needed)
- ✅ **1GB Storage** (plenty for college projects)
- ✅ **Easy setup** with connection strings
- ✅ **Built-in security** and backups

### Setup Steps for PlanetScale:

1. **Sign up** at https://planetscale.com
2. **Create a new database**
   - Name: `movie-vault` or similar
   - Region: Choose closest to your location
3. **Get connection details**
   - Go to "Connect" in your database dashboard
   - Select "Java" or "General" 
   - Copy the connection string

### Example PlanetScale Connection String:
```
mysql://username:password@host.us-east-4.psdb.cloud:3306/database-name?sslMode=REQUIRE
```

### Update Your Render Environment Variables:
```
DATABASE_URL=mysql://username:password@host.us-east-4.psdb.cloud:3306/database-name?sslMode=REQUIRE
DB_USERNAME=username
DB_PASSWORD=password
```

---

## Alternative: FreeSQLDatabase.com

### If you prefer traditional MySQL:

1. **Sign up** at https://freesqldatabase.com
2. **Create database** (free 10MB)
3. **Get connection details** from email

### Example FreeSQLDatabase Connection:
```
DATABASE_URL=jdbc:mysql://sql12.freesqldatabase.com:3306/sql12xxxxx
DB_USERNAME=sql12xxxxx  
DB_PASSWORD=xxxxxxxxx
```

---

## Alternative: Railway (Student Friendly)

### Railway Setup:
1. **Sign up** at https://railway.app with GitHub
2. **Add MySQL database** to your project
3. **$5 monthly credit** (usually enough for college projects)
4. **Copy connection variables** from Railway dashboard

---

## Configuration for External MySQL

### Your application.properties already supports external MySQL:
```properties
spring.datasource.url=${DATABASE_URL:jdbc:mysql://localhost:3306/vault}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:password}
```

### SSL Configuration (for secure connections):
Add these if your hosting provider requires SSL:
```properties
spring.datasource.url=${DATABASE_URL}?useSSL=true&requireSSL=true&serverTimezone=UTC
```

---

## Deployment Steps:

1. **Choose a MySQL hosting provider** (PlanetScale recommended)
2. **Create your database** and get connection details
3. **Update render.yaml** with your actual connection details
4. **Deploy to Render** with environment variables set
5. **Test your deployment**

---

## Cost Breakdown for College Project:
- **PlanetScale**: Free forever (1GB limit)
- **Railway**: $5/month credit (usually covers usage)
- **FreeSQLDatabase**: Free (10MB limit)
- **Render hosting**: Free tier (750 hours/month)

**Total monthly cost: $0-5** depending on choice

---

## Important Notes:
- For college projects, **PlanetScale free tier** is usually the best choice
- Make sure to set `spring.jpa.hibernate.ddl-auto=update` for automatic table creation
- Keep your database credentials secure (use environment variables)
- Most providers give you enough free resources for demo/college projects
