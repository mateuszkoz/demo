## How to run the app
- Tasks -> application -> run or ./gradlew run
- Application by default is running on port 8080 (go to application.yml to set up custom port)
- Swagger-ui is available at http://localhost:8080/swagger-ui
- Rapidoc is available at http://localhost:8080/rapidoc
- Redoc is available at http://localhost:8080/redoc
- sampleRequest.http contains samples which can be run from the file itself (required IntelliJ IDE)
---

## Implementation Description
1. Technologies:
    * Java 17 
    * Gradle
    * Micronaut
    * JUnit5
    * Mockito
    * Openapi 
    * Lombok
2. Implementation details:
    * API accept JSON Object via POST method at uri/policy/calculate endpoint
    * Request first is validated (using javax.validation-api library). 
    * If request is valid then controller passed request mapped to Policy object to PolicyService, which pass policy to PremiumCalculator to calculate method
    * calculate for each PolicyObject calculate sum of sumInsured grouping by RiskType and call RiskType for coefficient value based on sum, multiplied by coefficient and sum all results.
    * RiskType calculate coefficient based on amount and CoefficientConfig which is a list of coefficient levels (level start, coefficient value, is start inclusive)
3. API documentation
    * API documentation is available after application run at /rapidoc, /redoc, /swagger-ui or /swagger/demo-0.0.yml
    * documentation contains example and validation description
    

## Micronaut 3.2.6 Documentation

- [User Guide](https://docs.micronaut.io/3.2.6/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.2.6/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.2.6/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)

## Feature lombok documentation

- [Micronaut Project Lombok documentation](https://docs.micronaut.io/latest/guide/index.html#lombok)

- [https://projectlombok.org/features/all](https://projectlombok.org/features/all)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature mockito documentation

- [https://site.mockito.org](https://site.mockito.org)

## Feature tomcat-server documentation

- [Micronaut Tomcat Server documentation](https://micronaut-projects.github.io/micronaut-servlet/1.0.x/guide/index.html#tomcat)

