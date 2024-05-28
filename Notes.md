# Getting Started

## How to

#### Requisite
- Java 17
- Gradle 8.7

#### Run
- ./gradlew bootRun

##### Demo
- Post request to http://localhost:8080/companysearch/rest/api/company/get
- Pass x-api-key in the header
- Pass Content-Type in the header
- Pass input in the body sample and verify the result.
``` {
  "companyName" : "BBC LIMITED",
  "companyNumber" : "06500244"
}
```

## Low Level Design

#### About Packages
List of top level packages
- web -  web layer 
- backend - backend service
  - service - main service for other layers to connect
  - data
    - network - all external web calls
    - database -  database

#### About Source Code
- Efforts have been made to write an application which is readable and easy to maintain.
- Build code for every criteria in the requirement
- Created different models (POJOs) for different layer so other aspect of Rest Application can be added later without making changes to other parts. 
  - Example: Web layer model (SearchCompanyRequest && SearchCompanyResponse) is different from application backend model (CompanyDetailsDTO). Reason is you can use openAPI annotations or other annotations can be applied on web layer models.

#### About Integration tests 
Some Integration tests are written to cover how application is integrated with external libraries
- mock MVC is used to test Rest Controller
- Wire Mock is used to test how WebClient interacts with external API. One Wire Mock test is added to show the understanding of the topic.

#### About Unit tests
- I have covered only few unit tests (eg: NetworkCompanyServiceTest) to show the understanding of the topic

#### Database
- Added a separate layer but not the implementation



