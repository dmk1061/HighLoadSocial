FROM postgres


ENV POSTGRES_HOST_AUTH_METHOD=trust
# Copy the modified postgresql.conf file into the image
COPY ./pg/master1/postgresql.conf /etc/postgresql/postgresql.conf
#COPY ./pg/master1/pg_hba.conf /etc/postgresql/pg_hba.conf
# Copy and set permissions for entrypoint script



USER postgres
RUN mkdir -p /var/lib/postgresql/archive

CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]