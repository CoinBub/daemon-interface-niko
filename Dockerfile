# Dockerfile for NIKO coin daemon

#
# Daemon executable builder
#
FROM ubuntu:16.04 as builder

RUN apt-get -y update; \
    apt-get -y install \
        build-essential \
        git \
        libboost-filesystem-dev \
        libboost-program-options-dev \
        libboost-system-dev \
        libboost-thread-dev \
        libssl-dev \
        libdb++-dev \
        libminiupnpc-dev \
        libqrencode-dev

RUN git clone https://github.com/unifiedvalue/NIKO /tmp/niko \
 && cd /tmp/niko/src \
 && chmod +x leveldb/build_detect_platform \
 && make -f makefile.unix


#
# Final Docker image
#
FROM ubuntu:16.04
EXPOSE 3333 13754 13755

COPY --from=builder /tmp/niko/src/NIKOd /usr/local/bin/nikod
COPY .docker /

RUN apt-get -y update \
 && apt-get -y install \
        gettext \
        libboost-filesystem-dev \
        libboost-program-options-dev \
        libboost-system-dev \
        libboost-thread-dev \
        libssl-dev \
        libdb++-dev \
        libminiupnpc-dev \
        libqrencode-dev \
        netcat \
        pwgen \
 && groupadd --gid 1000 niko \
 && useradd --uid 1000 --gid niko --shell /bin/false --create-home niko \
 && chmod +x /usr/local/bin/notifier \
 && chmod +x /usr/local/bin/entrypoint

USER niko
WORKDIR /home/niko

RUN mkdir -p /home/niko/.NIKO

ENTRYPOINT [ "/usr/local/bin/entrypoint" ]
CMD [ "nikod", "-printtoconsole" ]
