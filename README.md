# CryptoMonkey

CryptoMonkey is a Crypto tracking web-app backend which is capable of listening to different Crypto Market reporting APIs and periodically collecting Quotes, 
ranking prices, and persisting them to datastores.

## What does it do currently?

Right now, CryptoMonkey is still in early development and only supports 1 Crypto Market API (Cryptowat.ch) and cannot persist to external datastores yet. That means if CryptoMonkey crashes or is restarted, it will currently lose available pricing information.

## What do you plan to support?

### Crypto Market APIs

* Cryptowat.ch - https://docs.cryptowat.ch/rest-api/

In the short-to-medium term, CryptoMonkey is not looking to onboard other market APIs unless there is evidence that other APIs are collecting more currency types / faster API responses.

### External Datastores

* Local Datastore - This is the most basic runtime mode. Pricing data is collected on a regular basis and stored in memory. All collected data is lost in cast the service stops.
* DynamoDB Datastore (**NYI**) - Uses DDB as a datastore to persist data between runtimes. Data should be persisted for 90 days by default policy, and the partition key is QuoteId (the unique base/quote combination which forms a price quote), which minimizes hot partition (since all keys have even write-rate). Has constant write costs associated with storing data.
* CloudWatch Metrics Datastore (**NYI**) - Quote metrics should be emitted to CloudWatch to enable fluid dashboard creation and exportation. QuoteId (IE: BTC-USD) is a dimension used to distinguish pricing history of different quotes over time, as well as point-in-time ranking. This approach can become expensive at high polling rate.

Currently only Local Datastore is supported for runtime, however DynamoDB and CloudWatch are short term priorities for implementation.

### Feature Request: Price Alerts

When any given quote price exceeds 3x the value of its rolling 1hour average, we want to enable consumer apps (which interact with the FES) to be able to send a push alert to our customer.

For this situation we intend to implement an SNS topic where apps can listen and receive this notifications in real time, that they can surface to users through whatever device mechanism is available.
We will implement a mechanism for distributed access keys to developer apps to be able register/deregister to our SNS topic (to prevent replicating these messages unnecessarily). This mechanism will be supported by IAM and developer apps will be able to filter SNS messages based on QuoteId to also only provide alerts for desired QuoteIds.

### TODO List

* Unit tests are incomplete, we need to validate our behaviors. :( 
* Ranking is not implemented yet by the BackEndService
* External Datastores are not implemented
* Cleanup resources on Runner/Service termination
* Cleanup codebase and improve organization/groupings
* Add Checkstyle and code cleanliness automation
* Make runner more intelligent with detected transient internal failure and reporting it

#### Production Readiness

* The pipeline needs to be expanded to have multiple regions supported. Right now we only have an effective "prod" stage in our pipeline which just gets code smashed into it by CDK. 
We should at least have a standard Beta -> 1Box -> Prod pipeline so that we can test changes in beta before sending them to prod, and stage out changes in 1box before sending them out altheway to full prod. CodePipeline will support manual and lambda execution based workflows which can be used to validate/enforce QA checks.
* AZ redundancy - We will get this for free with our CDK setup as we scale up our host count. Scalability issues need to be addressed first though.
* Unit Testing - As mentioned in TODO section, we need unit tests! There is very little coverage because I was working fast to get the bones structured and sensible.
* Testing - We plan to add full unit testing suite to cover all code, and use a Lambda stack which invokes our service as a CodeBuild lambda invoke action and this action will run an integration test suite in beta.
* Load Testing - Ontop of integration testing, we want load testing on our front end and backend services to ensure they can handle spikes in user traffic. CodePipeline + BlazeMeter support this use case.

#### Scalability

* Quote Runner Service - The Quote Runner which is used to collect quotes on a polling basis is not scalable for multi-host deployments currently and plays very poorly in a monolithic deployment strategy. A high priority item to enable scalability is to surround the Runner in a service interface and pursue an implementation where a Controller Service/SQS Queue feeds into an Application Load Balancer that distributes "poll" requests across a worker fleet in tandem. 
* Quote Runner Scaling Formulae - Quote runner has a slightly more complex scaling pattern, as it has a fairly fixed pattern of traffic. We need to make sure that individual host bandwidth, cpu, and memory are not getting overwhelmed and scale based on physical resource characteristics. It may not change often but based on network conditions we see dift in performance.
* Monolith breakdown - In order to be able to utilize resources efficiently, we should break the monolith down into discrete services, all backed by a individually scaling fleet based on resource needs. Code is already factored to have levels of abstraction between the Service interface and the Service implementation that will make it relatively straightforward to move the code to separate stacks and point to the ALB address instead.
* DynamoDB Provisioned Capacity scaling - In order for our DDB to be performant, we need to establish correct thresholds for our read and write capacity. With fixed polling rate, we can naively calculate write capacity as *n*/*pollingSecs* where *n* is the number of QuoteIds and *pollingSecs* is the polling rate in seconds. As *pollingSecs* gets lower, DDB write capacity gets increasingly expensive, however this capacity is fixed and is easy to calculate. On the other hand, read rate would be determined by FrontEndService traffic and would require autoscaling in a likely 1:1 basis, which we can let DDB handle itself. This approach can still scale to a very large count of metrics.
* DynamoDB Write Batching Optimization - **As polling rate gets really fast**, we will need to optimize our rates so that we write an entire batch of Quotes at once. This would bring the write rate to roughly 1/*pollingSecs* assuming the write is less than 12kb (the numerator goes up by 1 for every 12kb). This is additional work here to support reading the quote records and repartitioning and the BackEndService would now need to unpack the bulk quote format or there would need to be an intermediate translation layer.

Until the above are done, especially the Quote Runner Service, we cannot effectively scale up our service, and right now

## How To Run

This service is deployed through CDK and has basic CI features which allow for automatic deployments of changes made to this repo.

### Prerequisites

1. You need to have AWS CLI, CDK CLI, and Docker installed on your development environment. Please use the newest versions available.
2. You must have configured your AWS CLI to have an programmatic access token using `aws configure`. **Configure a known default region or you may have consistency issues.**
3. Fork this repo, create a Github access token, and store this access token in AWS Secret Manager as `github-access-token` with a key with the name `github-access-token` and the value is your access token.
4. In your forked repo, change `CryptoMonkeyPipelineStack.java#L48-49` to have your GitHub repo / owner name. Now your service will be in sync with your forked repo.

**NOTE:** If you want to have your service automatically track changes in this GitHub repo rather than fork your own (skipping steps 3 and 4), please reach out and we can work out a secure process for transferring an access token.

### First-time deployment

To deployment all CDK resources the first time please run the following two commands:

* `cdk bootstrap` - this creates all collateral in your AWS account to prepare it for CDK to deploy these stacks.
* `cdk deploy --all` - this creates both CDK stacks (the Pipeline and the Service stack). If you see an error `Internal Service Error` while the Pipeline stack is starting up, please check to make sure you did pre-requisite steps 3 and 4 right (this usually means your GitHub is not connected correctly).

When the second command passes, your pipeline should be functional, listening to GitHub changes, and the service stack should be ready to go.

If you want to deploy manually (without committing), you can run the command `cdk deploy --all` again to force an update of service collateral.

## Architecture

This service is implemented as a serverless ECS Fargate service. It is fronted by an Application Load Balancer which sends traffic to the main CryptoMonkey service which runs the FrontEndService.
The CryptoMonkey service (although currently a monolith), is divided into 3 primary service components:

1. FrontEndService - The FrontEndService is meant to be the customer facing service. It controls the external service interface and calls the BackEndService as necessary to ensure that the API contract is upheld.
2. BackEndService - The BackEndService is meant to serve data for the FrontEndService by collecting information from the Runner/DataStore. It should only serve service authenticated calls. It also does any data-processing necessary to derive values as needed.
3. RunnerService - The RunnerService is meant to perform and manage aspects of the data poller. It connects to the Market API (Cryptowat.ch), loads all necessary pieces, and then pushes it to the appropriate DataStore.

![Simple Architecture Diagram](https://github.com/phroph/CryptoMonkey/blob/master/CryptoMonkey.png?raw=true)

The diagram above gives a high level view of the intended long-term architecture for this service to make it more scalable. All components can scale independently of eachother and are not pinned against physical resources but against throughput rate. The challenge regarding scaling just involves insuring that we fail gracefully and requires some analysis on what behaviors are needed when CryptoWat.ch API struggles or has slow response rate.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!
