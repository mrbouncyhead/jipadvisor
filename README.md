## JipAdvisor

### Start the development server

First build the docker image, this will create the container and compile the app
docker build -t jipadvisor-server .

Then run the built container, forwarding the containers port 4567 to port 80 on the local machine
docker run -p 80:4567 jipadvisor-server

Rebuild the container by passing --no-cache

## Create a java keystore

keytool -genkey -keyalg RSA -keysize 1024 -alias jipadvisor -keystore jipadvisor.jks
password: jipadvisor

## Register a user using curl
curl -d '{"fullName":"Joe Bloggs", "email":"joe.bloggs@example.com", "password":"password"}' -H "Content-Type: application/json" -X POST http://localhost:4567/register