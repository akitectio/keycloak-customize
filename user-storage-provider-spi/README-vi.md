## Customed Keycloak User Storage SPI

Trong hướng dẫn này, mình sẽ chia sẻ với bạn cách triển khai tra cứu và xác thực người dùng bằng SPI lưu trữ người dùng của Keycloak. Để bắt đầu, SPI là [Service Provider Interface](https://access.redhat.com/documentation/en-us/red_hat_single_sign-on/7.6/html/server_developer_guide/user-storage-spi#provider_interfaces) và đó là một cách để viết các phần mở rộng cho Keycloak để kết nối với cơ sở dữ liệu người dùng bên ngoài và sử dụng các xác thưc của keycloak.

Trong hướng hướng dẫn này, phiên bản Keycloak mới nhất là `22.0.4` và Spring Boot 3.

### 1. Tạo một ứng dụng Spring Boot

Tạo một ứng dụng Spring Boot và thêm vào `pom.xml` các phụ thuộc sau:

```xml

<!-- https://mvnrepository.com/artifact/org.keycloak/keycloak-server-spi -->
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-server-spi</artifactId>
    <version>22.0.4</version> <!-- 11.0.3 -->
    <scope>provided</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/org.keycloak/keycloak-core -->
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-core</artifactId>
    <version>22.0.4</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.keycloak/keycloak-services -->
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-services</artifactId>
    <version>22.0.4</version>
    <scope>provided</scope>
</dependency>
 <!-- https://mvnrepository.com/artifact/org.keycloak/keycloak-model-legacy -->
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-model-legacy</artifactId>
    <version>22.0.4</version>
</dependency>
```
