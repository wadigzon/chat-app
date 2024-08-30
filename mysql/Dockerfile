# Dockerfile for MySQL
FROM mysql:8.0
ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=chat_db
COPY ./schema.sql /docker-entrypoint-initdb.d/
