'use strict';

const express = require('express');
const fs = require('fs');
const request = require('request');
const exec = require('child_process').exec;

const services = process.env.SERVICES.split();

getSwaggerJsonForServices()
createSpectacleFiles()

const PORT = 8080;

const app = express();

app.use(express.static('public'))

app.get('/', (req, res) => {
  res.send('Hello\n');
});

app.get('/services', (req, res) => {
  res.send(services);
});

app.get('/health-check', (req, res) => {
  res.sendStatus(200);
});

app.listen(PORT);


function getSwaggerJsonForServices() {
  services.forEach(service => {
    var stream = fs.createWriteStream(`${service}.json`, { defaultEncoding: 'utf16le' });

    stream.once('error', function (err) {
      console.log(err);
    });

    stream.once('end', function () {
      console.log('response written');
    });

    request(`https://swagger.crmcloudapi.com/${service}/v2/api-docs`)
      .once('error', function (err) {
        console.log('Request Error: ' + err);
      }).pipe(stream);
  })
}

function createSpectacleFiles() {
  services.forEach(service => {
    exec(`spectacle ${service}.json -t ./public/${service} -f spectacle.html`,
      (error, stdout, stderr) => {
        console.log(`stdout: ${stdout}`);
        console.log(`stderr: ${stderr}`);
        if (error !== null) {
          console.log(`exec error: ${error}`);
        }
      });
  })
}