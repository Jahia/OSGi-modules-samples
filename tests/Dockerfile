FROM cypress/browsers:node-18.14.1-chrome-110.0.5481.96-1-ff-109.0-edge-110.0.1587.41-1

RUN apt-get update && apt-get install -y jq

RUN adduser --disabled-password jahians

USER jahians
WORKDIR /home/jahians

COPY --chown=jahians:jahians package.json yarn.lock /home/jahians/

RUN mkdir -p /home/jahians/run-artifacts /home/jahians/results /home/jahians/cypress/plugins

#CI=true reduces the verbosity of the installation logs
RUN CI=true yarn install

COPY --chown=jahians:jahians . /home/jahians

RUN CI=true /home/jahians/node_modules/.bin/cypress install

CMD ["/bin/bash", "-c", "/home/jahians/env.run.sh"]
