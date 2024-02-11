FROM postgres


ENV POSTGRES_HOST_AUTH_METHOD=trust
COPY ./pg/master1sync/postgresql.conf /etc/postgresql/postgresql.conf

USER postgres
RUN mkdir -p /var/lib/postgresql/archive

CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]