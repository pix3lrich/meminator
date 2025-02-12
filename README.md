
# SA Tech Challenge: Meminator App

***This is a demo app, don't run it in production***

This contains a sample application for use in the Honeycomb Solution Architect
Tech Challenge. The app has 4 services, replicated across 4 languages: Go, Java,
Node.js, and Python.

It generates memes by combining a randomly chosen safe for work image with a
randomly chosen phrase.

## Introduction

Hello! Welcome to the **Instrumenting Meminator with OpenTelemetry** challenge.

1. Use the Meminator app (it's fun).
2. Select the language of your choice, and instrument the app using
OpenTelemetry.
3. Send the instrumentation to Honeycomb.
4. Use Honeycomb to observe the app instrumentation.
5. Add additional instrumentation to improve the observability of the app.

## Setup your development environment

To run this app, you can use GitHub Codespaces, GitPod, or your local
environment. It is recommended to use GitHub Codespaces, but you are free to
choose the environment you are most comfortable with.

### GitHub Codespaces setup

1. From this repository in GitHub, click the `<> Code` button/dropdown down menu
2. Select the `Codespaces` tab
3. Click the `Create codespace on main` button

### GitPod setup

1. Go to
[Gitpod](https://gitpod.io/#https://github.com/honeycombio/sa-tech-challenge) to
open the repository in GitPod
2. You may need to create a GitPod account or sign in with your GitHub account

### Local development setup

Running this repository locally may require your development environment to be
setup with proper SDKs in order to edit and test the code. You can clone the
repository to your local machine by running the following command:

```bash
git clone https://github.com/honeycombio/sa-tech-challenge.git
```
## Run the application

### Choose the language of your choice

The app is writen in 4 languages: Go, Java, Node.js, and Python. Choose the
language you are most comfortable with. Go to the directory of the language you
want to work with to start and stop the app. For example to use Java, run the
following command:

```bash
cd java
```

### Run the app

To run the app, run the following command:

```bash
./run
```

This will run `docker compose` in daemon mode, to build and start all app
services. It may take several minutes for the app to build and start the first
time.

### Access the app

If running locally, the app will be available at [http://localhost:10114]()
GitHub Codespaces and GitPod will will be available at the URL provided in the
terminal by the run script. You can also go to the Ports tab inside GitHub
Codespaces or GitPod to access the app which will be running on port 10114.

### Making changes

After making changes to a service, you can rebuild just that service by running:

```bash
./run [ backend-for-frontend | image-picker | meminator | phrase-picker ]
```

### Stop the app

```bash
./stop
```
