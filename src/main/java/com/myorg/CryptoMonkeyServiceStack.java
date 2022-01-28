package com.myorg;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class CryptoMonkeyServiceStack extends Stack {
    private static String SERVICE_STACK_PREFIX = "CryptoMonkeyService";

    public CryptoMonkeyServiceStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CryptoMonkeyServiceStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Vpc vpc = Vpc.Builder.create(this, SERVICE_STACK_PREFIX + "Vpc")
                .maxAzs(3)
                .build();

        Cluster cluster = Cluster.Builder.create(this, SERVICE_STACK_PREFIX + "EcsCluster")
                .vpc(vpc)
                .build();

        Table quoteTable = Table.Builder.create(this, SERVICE_STACK_PREFIX + "QuoteTable")
                .tableName("CryptoMonkeyQuoteTable")
                .partitionKey(Attribute.builder()
                        .name("quoteId")
                        .type(AttributeType.STRING)
                        .build())
                .billingMode(BillingMode.PROVISIONED)
                .readCapacity(1)
                .writeCapacity(10)
                .removalPolicy(RemovalPolicy.DESTROY)
                .pointInTimeRecovery(true)
                .build();

        ApplicationLoadBalancedFargateService fargateService =
                ApplicationLoadBalancedFargateService.Builder.create(this, SERVICE_STACK_PREFIX + "FargateService")
                        .cluster(cluster)
                        .cpu(512)
                        .desiredCount(1) // Our runner cant support multiple hosts atm...
                        .taskImageOptions(
                                ApplicationLoadBalancedTaskImageOptions.builder()
                                        .image(ContainerImage.fromAsset("./app"))
                                        .containerPort(8080)
                                        .build()
                        )
                        .memoryLimitMiB(1024)
                        .publicLoadBalancer(true)
                        .assignPublicIp(false)
                        .build();

        fargateService.getTargetGroup().configureHealthCheck(HealthCheck.builder()
                        .healthyHttpCodes("200")
                        .path("/actuator/health")
                        .port("8080")
                .build());
    }
}
