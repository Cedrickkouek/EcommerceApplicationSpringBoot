#!/bin/bash
cd /home/ec2-user
aws s3 cp s3://automatiqueupdatebucket/brendaappecommerce.jar .
java -jar brendaappecommerce.jar
