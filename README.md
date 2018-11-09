# dep_app2
Hands on exercise

## WAR-application

### Create

~~git clone https://github.com/efsavage/hello-world-war~~

~~pushd  ./hello-world-war/src/main/webapp~~

~~sed -i 's/Hello World!/Welcome to Electric Cloud/g' index.jsp~~

~~jar -cvf ../../../../hello-ec.war *~~

~~popd~~

```
git clone https://github.com/valpere/dep_app2.git
cd dep_app2/app
jar -cvf ../hello-ec.war *
```
### Deploy

```
../bin/dep_app2.groovy \
    --path /hello-ec \
    --application ./hello-ec.war \
    --user admin \
    --password secret \
    --proxy http://localhost:8080
```

### Result

[Manager page](img/dep_app2-01.png)

[Application page](img/dep_app2-02.png)
