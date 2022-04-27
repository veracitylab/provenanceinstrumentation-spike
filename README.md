# provenanced-service-spike


## Building and Running the Services

1. cd to `procenanced-service`
2. run `mvn jetty:run`


To check this, point HTTP client (browser, curl, ..) to `localhost:8080` to verify that server is running


## Query Service -- Baseline Implementation (No Provenance) 

1. run server (as above)
2. query premium for young driver/client: `curl localhost:8080/provenanced/baseline/premiumcalculator/18`
3. query premium for older driver/client: `curl localhost:8080/provenanced/baseline/premiumcalculator/25`


(or post URLs to browser, note that clients have only one field `age` so this is the only query attribute at the end of the URL)


