#ITPM

##Issue Tracking & Project Management System

IT project management (ITPM) is the process of managing the plan,
 organization, and accountability to achieve information technology goals.
 
  Since the reach of IT spans across most of a business or enterprise,
   the scope of these projects can be large and complex.

The magnitude of IT project management often means that it’s more than just 
applying knowledge, aligning skills, and using regular tools and techniques to drive
 a project to completion.
---

###How to run.

---
Make sure you have installed the **[Docker](https://www.docker.com/products/docker-desktop)** in your computer,
 and port **8080** not using in your computer.
 ---
 Open cmd/bash, change directory to project folder(where are Dockerfile and pom.xml) run these commands.
 ```shell script
mvn package spring-boot:repackage

docker run --name mysql-standalone2 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=itpm -e MYSQL_USER=user -e MYSQL_PASSWORD=root -dp 3306:3306 mysql

docker run -dp 8080:8080 --name itpm-run --link mysql-standalone2:mysql spring-boot-itpm                                                  

```
