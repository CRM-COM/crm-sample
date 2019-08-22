'use strict';

const express = require('express');
const fs = require('fs');
const request = require('request');

var stream = fs.createWriteStream('swagger.json', { defaultEncoding: 'utf16le' });

stream.once('error', function (err) {
  console.log(err);
});

stream.once('end', function () {
  console.log('response written');
});

request('https://swagger.crmcloudapi.com/commerce/v2/api-docs')
  .once('error', function (err) {
    console.log('Request Error: ' + err);
  }).pipe(stream);

const exec = require('child_process').exec;

const child = exec('spectacle swagger.json -t ./public/commerce -f commerce.html',
    (error, stdout, stderr) => {
        console.log(`stdout: ${stdout}`);
        console.log(`stderr: ${stderr}`);
        if (error !== null) {
            console.log(`exec error: ${error}`);
        }
});

const PORT = 8080;

const app = express();

app.use(express.static('public'))

app.get('/', (req, res) => {
  res.send('Hello\n');
});

app.get('/health-check',(req,res)=> {
 res.sendStatus(200);
});

app.listen(PORT);
