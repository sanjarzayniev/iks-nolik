# **Iks-nolik**

<p align="center">
    <img src="./assets/images/game_intro.png">
</p>

> [!NOTE]
> The multiplayer edition **Tic-Tac-Toe** game, made using Java SWING.
> * Sanjar Zayniev (`U2210264`)
> * Abduaziz Ziyodov (`U2210265`)



## **Server**

Dependencies:
* PostgreSQL (16)
* Openjdk 17.0.8
* OpenJDK Runtime Environment
* OpenJDK 64-Bit Server VM
* PostgreSQL Driver For Java (`*.jar` file)
* Java SWING

## **How to Run?**

Using terminal:
```shell
$ java src/Server.java 
```
If postgres driver does not exist on your java class path, you should specify it:

```shell
$ java -cp lib/postgresql-42.7.0.jar src/Server.java
```

TCP server is running on host `localhost` and port `2121`:

![](./assets/images/server_running.png)

> [!TIP]
> Instead of running `Client.java`, you can use tools like `netcat`. But you will not have beautiful `GUI`.

If client connects:

![](./assets/images/server_client_connected.png)

> [!IMPORTANT]
> If game ends, you need to restart both `Server.java` and `Client.java`.