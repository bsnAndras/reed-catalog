FROM ubuntu:latest
LABEL authors="folde"

ENTRYPOINT ["top", "-b"]