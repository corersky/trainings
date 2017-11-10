http://10.0.0.121/#/login

docker build -t devproof/salary-app .



docker run -it -e MAPR_CLUSTER=demo.mapr.com -e MAPR_CLDB_HOSTS=10.0.0.94 -e MAPR_CONTAINER_USER=mapr -e MAPR_CONTAINER_UID=5000 -e MAPR_CONTAINER_GID=5000 -e MAPR_CONTAINER_GROUP=mapr -p 8080 devproof/salary-app

docker run -it -e MAPR_CLUSTER=demo.mapr.com -e MAPR_CLDB_HOSTS=10.0.0.94 -e MAPR_CONTAINER_USER=mapr -e MAPR_CONTAINER_UID=5000 -e MAPR_CONTAINER_GID=5000 -e MAPR_CONTAINER_GROUP=mapr -p 11111:11111 devproof/salary-app

docker push devproof/salary-app

URL: http://10.0.0.205:11111/salaries/#/


http://10.0.0.121/#/?_k=okwcyb

Setup DCOS

bootstrap_url: file:///opt/dcos_install_tmp
cluster_name: mapr
exhibitor_storage_backend: static
ip_detect_path: genconf/ip-detect
master_discovery: static
master_list:
- 10.0.0.69
process_timeout: 10000
public_agent_list:
- 10.0.0.136
- 10.0.0.141
resolvers:
- 8.8.8.8
- 8.8.4.4
ssh_key_path: /Users/chufe/.ssh/id_rsa
ssh_port: 22
ssh_user: centos


===========

{
  "id": "/salaries",
  "instances": 1,
  "portDefinitions": [],
  "container": {
    "type": "DOCKER",
    "docker": {
      "portMappings": [
        {
          "containerPort": 11111,
          "hostPort": 11111,
          "protocol": "tcp",
          "name": "customers"
        }
      ],
      "network": "USER",
      "image": "devproof/salary-app",
      "forcePullImage": true
    }
  },
  "cpus": 2,
  "mem": 1024,
  "requirePorts": false,
  "ipAddress": {
    "groups": [],
    "networkName": "dcos"
  },
  "env": {
    "MAPR_CLUSTER": "demo.mapr.com",
    "MAPR_CLDB_HOSTS": "10.0.0.94",
    "MAPR_CONTAINER_USER": "mapr",
    "MAPR_CONTAINER_UID": "5000",
    "MAPR_CONTAINER_GID": "5000",
    "MAPR_CONTAINER_GROUP": "mapr"
  }
}


URL: http://10.0.0.205:11111/salaries/#/
URL: http://10.0.0.200:11111/salaries/api/gyro/delete

select * from dfs.`/demo/esp32_gyro` order by _id desc limit 20
