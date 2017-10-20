Nodes:
10.0.0.20                  : ok=211  changed=51   unreachable=0    failed=0
10.0.0.33                  : ok=205  changed=48   unreachable=0    failed=0
10.0.0.117                 : ok=205  changed=49   unreachable=0    failed=0

ssh ec2-user@10.0.0.20
sudo -i
yum install https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
yum install clustershell
ssh-keygen -t rsa -b 4096
scp ...
vi /etc/clustershell/groups.d/local.cfg
all: 10.0.0.[20,33,117]

```
clush -a hostname
https://github.com/jbenninghoff/cluster-validation
curl -L -o cluster-validation.tgz http://github.com/jbenninghoff/cluster-validation/tarball/master
tar xzvf cluster-validation.tgz
mv jbenninghoff-cluster-validation-* cluster-validation
clush -a --copy /root/cluster-validation
```

# Explain clush

```
clush -a date
clush -aB date
```

# Run validation

Cluster audit

```
cd /root/cluster-validation/pre-install
./cluster-audit.sh | tee cluster-audit.log
less cluster-audit.log
```
Network test

```
cd /root/cluster-validation/pre-install
./network-test.sh | tee network-test.log

```

Memory test

http://ark.intel.com/

```
cd /root/cluster-validation/pre-install
clush -Ba "$PWD/memory-test.sh | grep -e ^Func -e ^Triad" | tee memory-test.log
```

DFSIO test (achtung root geht nicht)
su - mapr
./runDFSIO.sh

RWspeedtest (local process)
./runRWSpeedTest.sh
