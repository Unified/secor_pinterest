# Unified Pinterest Secor

## Setup Guide

##### Get Secor code
```sh
git clone [git-repo-url] secor
cd secor
```

##### Install FakeS3
To run and test secor locally you need to install [fake-s3](https://github.com/jubos/fake-s3).

`gem install fakes3`

##### Install s3Cmd
Install s3Cmd to be able to use a query the local fake s3 store
[s3Cmd](https://github.com/s3tools/s3cmd)

```sh
s3cmd -c test.s3cfg ls s3://test-bucket/
s3cmd -c test.s3cfg get s3://test-bucket/file.json
```
The `test.s3cfg` file can be found after running the [Local secor development](local-secor-deployment) in `secor_deploy` 

##### Copy over the Fake S3 overrides
<aside class="notice">DO NOT COMMIT THIS FILE</aside>

This configuration is to point secor to the fake s3 host/port.
```sh
cp src/test/config/jets3t.properties src/main/config
```

##### Local secor deployment
```sh
./unified_local_test_deploy.sh

# starts fake s3
./secor_deploy/scripts/start_local.sh

# stops fake s3
./secor_deploy/scripts/start_local.sh
```

#### Running S3 in Intellij

VM Options
 - Create a Run configuration with the following VM Configurations
 - Change the `-Dconfig` to the configuration you want to run
 ```
-server
-ea
-Dlog4j.configuration=log4j.dev.properties
-Dconfig=unified/secor.unified.job_instance_id.properties
```
![Image of IntelliJ Java Configuration](https://github.com/Unified/secor_pinterest/blob/master/secor_pinterest_idea.png?raw=true)
