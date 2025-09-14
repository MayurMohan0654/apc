# 100% FREE MySQL Hosting for College Projects

## Recommended: db4free.net (Best Free Option)

### Why db4free.net?
- ✅ **Completely FREE forever**
- ✅ **200MB storage** (enough for college projects)
- ✅ **MySQL 8.0**
- ✅ **No credit card required**
- ✅ **No time limits**
- ✅ **Reliable uptime**

### Setup Steps for db4free.net:

1. **Go to** https://db4free.net
2. **Click "Sign up"**
3. **Fill the form:**
   - Database name: `movievault` (no spaces/special chars)
   - Username: Choose a username
   - Password: Choose a strong password
   - Email: Your email
4. **Check email** for confirmation
5. **Login** to get your connection details

### Your db4free.net Connection Details:
```
Host: db4free.net
Port: 3306
Database: movievault (or whatever you chose)
Username: your-username
Password: your-password
```

### Connection String for Render:
```
jdbc:mysql://db4free.net:3306/movievault?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

---

## Alternative: FreeSQLDatabase.com

### Setup:
1. **Go to** https://freesqldatabase.com
2. **Sign up** (free)
3. **Create database** 
4. **Check email** for connection details

### Example connection details:
```
Host: sql12.freesqldatabase.com
Database: sql12xxxxxx
Username: sql12xxxxxx
Password: xxxxxxxxx
```

---

## Alternative: Aiven (1 Month Free Trial)

### For longer projects:
1. **Go to** https://aiven.io
2. **Sign up** with student email
3. **Get 1 month free** MySQL
4. **Upgrade later** if needed (often has student discounts)

---

## Update Your Render Environment Variables

### For db4free.net:
```
DATABASE_URL=jdbc:mysql://db4free.net:3306/movievault?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=your-db4free-username
DB_PASSWORD=your-db4free-password
```

### For FreeSQLDatabase:
```
DATABASE_URL=jdbc:mysql://sql12.freesqldatabase.com:3306/sql12xxxxxx?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=sql12xxxxxx
DB_PASSWORD=xxxxxxxxx
```

---

## Important Notes for Free MySQL:

1. **Backup your data** - Free services may have limitations
2. **Use simple table names** - Some free services have restrictions
3. **Keep connections reasonable** - Don't spam the free database
4. **Test locally first** - Make sure your app works before deploying

---

## Cost Breakdown:
- **Database hosting**: $0 (completely free)
- **Render app hosting**: $0 (free tier)
- **Total cost**: $0

Perfect for college projects! 🎓

---

## Quick Start Commands:

After setting up your free MySQL:

```bash
# Test locally with your free MySQL
export DATABASE_URL="jdbc:mysql://db4free.net:3306/your-db:3306?useSSL=false&serverTimezone=UTC"
export DB_USERNAME="your-username"
export DB_PASSWORD="your-password"

# Build and test
./mvnw clean package
java -jar target/movie-vault-1.0-SNAPSHOT.jar
```
