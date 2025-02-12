# Use an official Node.js runtime as the base image
FROM node:20-alpine

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# install curl
RUN apk add curl

# Install dependencies
RUN npm install

COPY tsconfig.json .
# Copy the rest of the application code to the working directory
COPY ./src ./src

# RG - Adding the OTel agent
COPY tracing.ts ./tracing.ts

# RG - Configuring agent env variables
ENV OTEL_SERVICE_NAME="backend-for-frontend"
ENV OTEL_EXPORTER_OTLP_PROTOCOL="http/protobuf"
ENV OTEL_EXPORTER_OTLP_TRACES_ENDPOINT="https://api.honeycomb.io/v1/traces"
ENV OTEL_EXPORTER_OTLP_TRACES_HEADERS="x-honeycomb-team=uq4F6XqUpMjvKK7ZhrWOpA"

RUN npx tsc 

# Expose the port that your app runs on
EXPOSE 10115

# Command to run your application
CMD ["npm", "start"]
# CMD [ "node", "-r", "./tracing.ts", "index.ts" ]
