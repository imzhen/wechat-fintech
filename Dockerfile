# Copied From http://tanin.nanakorn.com/blogs/345her

FROM gcr.io/google_appengine/openjdk
COPY ./target/universal/wechatfintech-1.0-SNAPSHOT.tgz /wechatfintech-1.0-SNAPSHOT.tgz
#COPY ./docker-entrypoint.bash /docker-entrypoint.bash
RUN tar -touch -xvf wechatfintech-1.0-SNAPSHOT.tgz
WORKDIR /wechatfintech-1.0-SNAPSHOT
RUN chmod 755 /docker-entrypoint.bash
ENTRYPOINT ["/docker-entrypoint.bash"]
CMD ["bin/wechatfintech", "-Dconfig.file=conf/prod.conf", "-Dhttp.port=8080"]
