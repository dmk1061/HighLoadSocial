FROM postgres



# Copy the modified postgresql.conf file into the image
COPY ./pg/slave1/postgresql.conf /etc/postgresql/postgresql.conf
# Copy and set permissions for entrypoint script
RUN chown -R postgres:postgres /etc/postgresql/postgresql.conf
RUN chmod 700 /etc/postgresql/postgresql.conf
USER postgres

CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]