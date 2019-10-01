# Kubernetes Demo Project

## Docker
In order to deploy an application to the Kubernetes cluster, we first need to create a Docker image.
You can do this by changing the current Dockerfile.

Make sure the Dockerfile does the following:
* runs from the Java 8 alpine JRE image
* Works from the /tmp directory
* Copies the compiled JAR file from the local machine to the Docker image
* Runs the JAR file on starting the Docker image

Build your Docker image:

`docker build -t k8s-demo .`

Run your Docker image to make sure everything is working correctly: 

`docker run -p 8080:8080 k8s-demo`

Run with environment variables:

`docker run -p 8080:8080 -e "WELCOME_NAME=JWorks" k8s-demo`

### Push to Docker registry
We need to push our Docker image to an external Docker registry so Kubernetes can pull it from somewhere. 
We are going to use Amazon ECR for this.

First, you need to create a new ECR repository. You can do so by running the `CreateECRRepository` on the JWorks Jenkins.
Click the 'Build with Parameters' option. Next, choose a name for your Docker registry (ex. k8s-demo-team1). Click 'Build' and your Docker registry should be created. You can find the repository URL in the Console Output.

Next, we need to configure our machine so we can authenticate to our Docker registry with the Docker CLI.
Simply run this command so you can authenticate to ECR:

`eval $(aws ecr get-login --region eu-west-1 --no-include-email)`

This should give a *Login Succeeded* message. You are now allowed to push / pull from to / from ECR.

Although we are now ready to push to ECR, we need to change our application a bit so it is compatible with running on Kubernetes. We need to change our base path in `src/main/resources/application-prod.properties`. Set this to `k8s-demo-<team_name>` (ex. `k8s-demo-team1`).

Build your Docker image with the following configuration (assuming you are team 1):

`docker build -t 163091829738.dkr.ecr.eu-west-1.amazonaws.com/k8s-demo-team1 .`

Push your Docker image to the registry (assuming you are team 1):

`docker push 163091829738.dkr.ecr.eu-west-1.amazonaws.com/k8s-demo-team1`

## Kubernetes
Now that our Docker image is ready for deployment, we can configure our Kubernetes settings.
We need to create a Deployment and a Service. 

### Deployment
Make sure your Deployment:
* has the correct names and metadata (check metadata, selector, containers, ...)
* Pulls the correct image and uses the right port
* Uses the correct environment variables

Our application needs to run from the production profile as we need the base path to be configured correctly. Add the following environment variable
to the configuration:
* Name: `SPRING_PROFILES_ACTIVE`
* Value: `prod`

### Service
Make sure your Service:
* has the correct names and metadata
* has the correct selector value in order to find our Deployment
* has the correct ports configured

Apply your configuration with the following command:

`kubectl apply -f k8s/demo-deployment.yaml`

You should see that the cluster created 2 objects: a Deployment and a Service. In that case, you should be able to browse to your application by going to the following URL: https://eks.ordina-jworks.io/k8s-demo-<team_name>.
If I were team1, I would use https://eks.ordina-jworks.io/k8s-demo-team1.

## Jenkins
We can automate this whole process by using the JWorks Jenkins. First, we need to create a new item (left sidebar). Fill in the name of the project (use the name of your team) and then click OK. Click on Configure and scroll down to Pipeline settings.
Choose to use a Jenkinsfile from SCM and then select Git. here, enter the SSH URL of this Git repository and select the correct credentials. In branch, you set your branch only. So if your branch is `k8s-demo-team1`, you set `/*k8s-demo-team1`.

### Jenkinsfile
Declare the stages in the Jenkinsfile. It should do 4 things:
1. Pull code from Github
2. Test & build the JAR file
3. Build and push a Docker image with BUILD_NUMBER and GIT_COMMIT tags
4. Deploy to Kubernetes

## Dashboard
[Connect to the Kubernetes dashboard](https://jworks.cfapps.io/recipes/deploying-an-application-to-kubernetes.html#access-the-kubernetes-dashboard)


