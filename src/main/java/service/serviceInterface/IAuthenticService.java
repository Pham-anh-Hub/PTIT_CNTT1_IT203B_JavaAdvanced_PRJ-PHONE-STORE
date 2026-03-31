package service.serviceInterface;

public interface IAuthenticService {
    // đăng nhập
    public void login();

    // đăng ký - với customer
    public void register();

    // Đăng xuất
    public void logout();

    // cập nhật thông tin hồ sơ cho khách hàng
    public void updateProfile();

}
