# WeatherPet
Spring + thymeleaf + migration
### application.properties
```
db.url=${your_db_url} looks like jdbc:postgresql://localhost:5432/test_db
db.driver=${your_db_Driver} org.postgresql.Driver
db.username=${your_db_user} by default postgres if using postgresql
db.password=${your_db_password}

liquibase.changelog=classpath:db/master-changelog.yaml

#hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect not required
hibernate.hbm2ddl.auto=validate
hibernate.show_sql=true

session.extend.time.days=1
session.extend.time.minutes=5

open.weather.api.key=${your_api_key} 
open.weather.api=https://api.openweathermap.org/data/2.5
open.weather.api.geo=http://api.openweathermap.org/geo/1.0/
open.weather.api.limit=5
```
# API key  
>you can generate your key free by url https://home.openweathermap.org/api_keys
# example weather api call
>https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}  
- you can found more info by url https://openweathermap.org/current
# example geo api call
>http://api.openweathermap.org/geo/1.0/direct?q={city name},{state code},{country code}&limit={limit}&appid={API key}  
- you can found more info by url https://openweathermap.org/api/geocoding-api
