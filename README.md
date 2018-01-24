curl http://localhost:9090/stocks/?currency=ETH

curl 'http://localhost:9090/stocks/?currency=ETH&currency=BTC&currency=LTC'

# Running Spirng Boot with different profiles

When starting the backend part of the application with:

```./gradlew backend:bootRun ``` 

it will start the application 
with default profile, i.e. stub repositories will be used.

To start the app with endpoints to Coinbase use "coinbase" profile 

```./gradlew backend:bootRun -Dspring.profiles.active=coinbase```
