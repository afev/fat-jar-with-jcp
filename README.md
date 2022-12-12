# fat-jar-with-jcp

Project demonstrates 2 variants of build of fat jar with JCP 2.0-A (java cryptographic provider with GOST support) inside:
* build.gradle
* apache maven

Project can be open in Intellij IDEA IDE as gradle or maven project.

JCP: https://cryptopro.ru/products/csp/jcp/downloads

In case of gradle (build.gradle) JCP and its dependencies must be stored in 'libs' directory.

In case of maven (pom.xml) JCP and its dependencies must be installed in 'local' repository as:
```
mvn org.apache.maven.plugins:maven-install-plugin:install-file -Dfile=libs/ASN1P.jar  -DgroupId=ru.crypto  -DartifactId=ASN1P -Dversion=2.0-A   -Dpackaging=jar -DlocalRepositoryPath=local -DcreateChecksum=true
mvn org.apache.maven.plugins:maven-install-plugin:install-file -Dfile=libs/JCP.jar    -DgroupId=ru.crypto  -DartifactId=JCP   -Dversion=2.0-A   -Dpackaging=jar -DlocalRepositoryPath=local -DcreateChecksum=true
mvn org.apache.maven.plugins:maven-install-plugin:install-file -Dfile=libs/asn1rt.jar -DgroupId=com.objsys -DartifactId=asn1rt -Dversion=5.74.0 -Dpackaging=jar -DlocalRepositoryPath=local -DcreateChecksum=true
```

JCP is usually published as a few libraries so amount of the libraries depends on what a service does. 
This test service hashes some data only therefore it requires JCP, ASN1P and asn1rt libraries.
The service uses GOST R 34.11-2012 from JCP.

Project can be build by 'assemble' with gradle or 'package' with maven.

Both variants of build finally produces a spring service that allowes to digest some data sending GET request:
```
http://localhost:8080/v1/digest/123456
```
where '123456' is data.
Result (encoded as base64) is:
```
Digest (GOST3411_2012_512) is 'mBmJ079gCMOXJjKJOT5QOjYmG1b15537xfZsek5T2wrBXPWkiKZAOIm/UoSokKtSphy+4/IlVSiZYFALonN7Hg=='
```
