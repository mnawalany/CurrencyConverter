#Currency converter
Currency Converter is an simple application converting monetary values from one currency to another.
It uses [https://openexchangerates.org/](https://openexchangerates.org/) to get latest exchange rates.
As a data store application uses in-memory mongo database.

## Installation

The simplest way to run application in an embeded Tomcat is to execute

    ./gradlew web-client:bootRun
    
alternatively executable war may be created and run

    ./gradlew stage
    java -jar CurrencyConverter.war    

you can access application on http://localhost:8080/
