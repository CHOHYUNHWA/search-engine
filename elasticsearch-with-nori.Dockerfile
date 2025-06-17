FROM docker.elastic.co/elasticsearch/elasticsearch:7.17.20

RUN elasticsearch-plugin install --batch analysis-nori