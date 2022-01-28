package com.myorg;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.codebuild.BuildEnvironment;
import software.amazon.awscdk.services.codebuild.BuildSpec;
import software.amazon.awscdk.services.codebuild.LinuxBuildImage;
import software.amazon.awscdk.services.codebuild.PipelineProject;
import software.amazon.awscdk.services.codepipeline.Artifact;
import software.amazon.awscdk.services.codepipeline.Pipeline;
import software.amazon.awscdk.services.codepipeline.StageProps;
import software.amazon.awscdk.services.codepipeline.actions.CodeBuildAction;
import software.amazon.awscdk.services.codepipeline.actions.GitHubSourceAction;
import software.constructs.Construct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CryptoMonkeyPipelineStack extends Stack {
    private static String PIPELINE_STACK_PREFIX = "CryptoMonkeyPipeline";

    public CryptoMonkeyPipelineStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CryptoMonkeyPipelineStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        PipelineProject project = PipelineProject.Builder.create(this, PIPELINE_STACK_PREFIX + "Project")
                .projectName("StackBuildAndDeploy")
                .buildSpec(BuildSpec.fromSourceFilename("buildspec.yml"))
                .environment(BuildEnvironment.builder()
                        .buildImage(LinuxBuildImage.AMAZON_LINUX_2)
                        .build())
                .build();

        Artifact cdkBuildOutput = new Artifact("CDKBuildOutput");
        Artifact sourceOutput = new Artifact();

        List<StageProps> stages = new ArrayList<>();

        stages.add(StageProps.builder()
                .stageName("Source")
                .actions(Collections.singletonList(
                        GitHubSourceAction.Builder.create()
                                .actionName("GithubSource")
                                .output(sourceOutput)
                                .owner("phroph")
                                .repo("CryptoMonkeyService")
                                .oauthToken(SecretValue.secretsManager("github-access-token", SecretsManagerSecretOptions.builder()
                                                .jsonField("github-access-token")
                                        .build()))
                                .build()
                ))
                .build());

        stages.add(StageProps.builder()
                        .stageName("CDKBuildAndDeploy")
                        .actions(Collections.singletonList(
                                CodeBuildAction.Builder.create()
                                        .actionName("CDKBuildAndDeploy")
                                        .project(project)
                                        .input(sourceOutput)
                                        .outputs(Collections.singletonList(cdkBuildOutput))
                                        .build()
                        ))
                .build());

        Pipeline pipeline = Pipeline.Builder.create(this, PIPELINE_STACK_PREFIX + "Pipeline")
                .stages(stages)
                .build();
    }
}
