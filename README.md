## Requirements for exam


* [x] The order service and shipping service should communicate using RabbitMQ message queues.
* When you send a post request with path:http://localhost:8080/api/order it sends http cal to payment service.
* After receiving call from order-service payment-service message "paymentcompleted" to order-service and shipping-service. order-service updates status to  "paymentcompleted" in database and 
* shipping-service sends message to order-service that "shippingcompleted" after which order-service updates status in database to "shippingcompleted".
* All the process in order-service after one post request is shown in console screenshot below.


![](../../../../../var/folders/mk/qhyy46vj1ngdgz4gc8rr5c4r0000gn/T/TemporaryItems/NSIRD_screencaptureui_DH1JI4/Screenshot 2022-11-24 at 22.23.19.png)

* And the image of table after last message update


![](../../../../../var/folders/mk/qhyy46vj1ngdgz4gc8rr5c4r0000gn/T/TemporaryItems/NSIRD_screencaptureui_1nl4Tj/Screenshot 2022-11-24 at 22.26.15.png)


* [-] The integration tests should be using RabbitMQ testcontainers for these.
* [x] There should be a Gateway Service, that runs on port 8080, that all requests are made against, and does the routing for the other three services.
* [x] Spring Caches should be implemented where appropriate.
* [x] There should be a Discovery Service, using Eureka Server/Client, and the Gateway should also use Eureka for discovery.
* [x] Queries to the databases should implement Pagination.
* [-] There should be custom exceptions for things like “Order not found”, and “User not found”, etc. These exceptions should be handled globally using @ControllerAdvice.
* [x] There should be CircuitBreakers implemented where appropriate, with appropriate fallback methods.
* [x] Provide Dockerfiles for all services.
* [x] Additionally, the docker-compose should be able to start up the discovery, gateway, order, shipping, and payment services using profiles.
* I have provided docker-compose file for all services which you can run for each service or you can run one in gateway-service which will create one container and several databases.
* [x] If I run the docker compose, I should get Postgres and rabbitmq containers.
* [ ] If I run the docker compose, I should get Postgres and rabbitmq containers. If I run the docker-compose with -p flags, then it should be able to start those services as well. 
* Not sure if I have fixed it correct. When I run docker-compose up in gateway-service it runs all database services. 
*




Tests

* I have created endpoint tests for 3 services. Order-service, payment-service and shipping-service.
* All endpoint tests are using port:8080.
* But I have some issues with tests:
* Post request tests in order-service are passing when all services are up. I guess the reason is message queue.
* Put request tests are not passing for any service because for some reason "id" field is not found. But I commented them out, so you can see them.


* Endpoints and example data for post request:
* Order-service:
* http://localhost:8080/api/order
* {
  "product_name": "test product 100",
  "product_type": "test type 100"
  }

* payment-service
* http://localhost:8080/api/payment
* {
  "account_number": "3846475645634675",
  "amount": "5000000 kr"
  }


* shipping-service:
* http://localhost:8080/api/shipping
* {
  "tracking_no": "2938742893754872AZ"
  }

  