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

## Triển khai User Storage Provider SPI

Ta tạo file `CustomUserStorageProvider` với nội dung như sau:

```java

public class CustomUserStorageProvider implements UserLookupProvider, CredentialInputValidator, UserStorageProvider {
    @Override
    public boolean supportsCredentialType(String credentialType) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }
}

```

### Các phương thức của interface CustomUserStorageProvider

- **UserLookupProvider**: Interface này xác định các phương thức tra cứu người dùng theo tên người dùng, email hoặc id của họ. Nó cũng cho phép bạn lấy số lượng người dùng trong store bên ngoài của bạn. Bạn cần triển khai Interface này nếu muốn Keycloak có thể tìm và hiển thị người dùng từ store bên ngoài của bạn trong bảng điều khiển dành cho quản trị viên hoặc bảng điều khiển quản lý tài khoản.

- **CredentialInputValidator**: Interface này xác định các phương thức xác thực thông tin xác thực của người dùng, chẳng hạn như mật khẩu hoặc OTP, đối với store bên ngoài của bạn. Bạn cần triển khai Interface này nếu bạn muốn Keycloak có thể xác thực người dùng bằng thông tin xác thực được lưu trữ trong store bên ngoài của bạn.

- **UserStorageProvider**: Interface này xác định các phương thức tạo, cập nhật, xóa và truy vấn người dùng trong store bên ngoài của bạn. Nó cũng cho phép bạn quản lý vai trò và nhóm người dùng. Bạn cần triển khai Interface này nếu muốn Keycloak có thể thực hiện các thao tác này trên người dùng từ cửa hàng bên ngoài của bạn

### Các phương thức bên trong CustomUserStorageProvider

- **supportsCredentialType**: Phương thức này xác định xem người dùng có được hỗ trợ bởi loại thông tin xác thực được cung cấp hay không. Nếu bạn không hỗ trợ loại thông tin xác thực được cung cấp, bạn có thể trả về false.

- **isValid**: Phương thức này xác định xem thông tin xác thực được cung cấp có hợp lệ hay không. Nếu thông tin xác thực được cung cấp không hợp lệ, bạn có thể trả về false.

- **getUserById**: Phương thức này trả về người dùng với id được cung cấp. Nếu người dùng không tồn tại, bạn có thể trả về null.

- **getUserByUsername**: Phương thức này trả về người dùng với tên người dùng được cung cấp. Nếu người dùng không tồn tại, bạn có thể trả về null.

- **getUserByEmail**: Phương thức này trả về người dùng với email được cung cấp. Nếu người dùng không tồn tại, bạn có thể trả về null.

- **isConfiguredFor**: Phương thức này xác định xem người dùng có được cấu hình cho loại thông tin xác thực được cung cấp hay không. Nếu người dùng không được cấu hình cho loại thông tin xác thực được cung cấp, bạn có thể trả về false.

- **close**: Phương thức này được gọi khi Keycloak không cần nữa và bạn có thể giải phóng tài nguyên của bạn.

# 2. Tạo một SPI

- Tạo một file `META-INF/services/org.keycloak.storage.UserStorageProviderFactory` với nội dung như sau:

```java

org.keycloak.examples.userstorage.CustomUserStorageProviderFactory

```

- Tạo một file `CustomUserStorageProviderFactory` với nội dung như sau:

```java

public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {

    @Override
    public CustomUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHelpText() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(Config.Scope config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

}

```
