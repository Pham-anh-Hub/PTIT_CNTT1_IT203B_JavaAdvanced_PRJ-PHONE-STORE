create database prj_phone_store_java2;
use prj_phone_store_java2;

-- users, categories, products, orders, order_details.

-- User
create table users(
	id varchar(20) primary key,
    username varchar(100), 
	role enum ('ADMIN','CUSTOMER') not null,
	password varchar(100) not null,
    
    -- thuộc tính bổ sung cho khách hàng
    fullname varchar(50),
    email varchar(50) unique,
    phone varchar(20) unique,
    address varchar(255),
    is_active boolean
);

select * from users;

-- Category
create table categories(
	cate_id varchar(50) primary key,
    cate_name varchar(200) not null unique,
    description varchar(255),
    status boolean
);

-- products
create table products(
	product_id varchar(50) primary key,
    product_name varchar(150) not null, 
    brand varchar(50) not null,
    color varchar(50) not null, 
    storage bigint not null,
    price decimal(15, 2) not null, 
    stock int not null, 
    description varchar(500),
    cate_id varchar(50),
    created_at datetime default current_timestamp,
    updated_at datetime,
    is_active boolean not null,
    foreign key (cate_id) references categories(cate_id)
);

select product_name, brand, price from products where is_active = true ;


-- orders
create table orders(
	order_id varchar(50) primary key, 
    id varchar(20),
    order_date datetime default current_timestamp,
    total_amount decimal(15,2),
    status enum ('PENDING', 'SHIPPING', 'DELIVERED', 'CANCELLED'),
    
    foreign key (id) references users(id)
);

select * from orders;

-- order-details
create table orderDetails (
	od_id varchar(20) primary key,
    order_id varchar(50),
    product_id varchar(50),
    quantity int,
    price decimal(15,2),
    foreign key (order_id) references orders(order_id),
    foreign key (product_id) references products(product_id)
);



-- Add mockdata
insert into users value
('AD001', 'admin01', 'ADMIN', '$2a$10$vCnoxohet17J.mIDHum1Ju.g.eVQ9DcgQau6zrGgT0GEfAbeFdkOW', NULL, NULL, NULL, NULL, true),
('CU002', 'user01', 'CUSTOMER', '$2a$10$vCnoxohet17J.mIDHum1Ju.g.eVQ9DcgQau6zrGgT0GEfAbeFdkOW', 'Nguyễn Văn A', 'nva@gmail.com', '0912345678', 'Hà Đông, Hà Nội', true),
('CU003', 'user02', 'CUSTOMER', '$2a$10$vCnoxohet17J.mIDHum1Ju.g.eVQ9DcgQau6zrGgT0GEfAbeFdkOW', 'Trần Thị B', 'ttb@gmail.com', '0987654321', 'Quận 7, TP.Hồ Chí Minh', true),
('CU004', 'user03', 'CUSTOMER', '$2a$10$vCnoxohet17J.mIDHum1Ju.g.eVQ9DcgQau6zrGgT0GEfAbeFdkOW', 'Lê Văn C', 'lvc@gmail.com', '0909090909', 'Ba Đình, Hà Nội', true);

select * from users where role = 'customer';

insert into categories values
('C001', 'iPhone', 'Apple smartphones', true),
('C002', 'Samsung', 'Samsung Galaxy series', true),
('C003', 'Xiaomi', 'Xiaomi smartphones', true),
('C004', 'Oppo', 'Oppo smartphones', true),
('C005', 'Vivo', 'Vivo smartphones', true);
insert into products values
('PR001', 'iPhone 15 Pro', 'Apple', 'White Titanium', 256, 30000000, 5,  'Chip A17 Pro, camera 48MP, màn hình 6.1 inch', 'C001', NOW(), NULL, true),
('PR002', 'Samsung S23 Ultra', 'Samsung', 'Phantom Black', 256, 26000000, 7,  'Snapdragon 8 Gen 2, camera 200MP, S-Pen', 'C002', NOW(), NULL, true),
('PR003', 'iPhone 15','Apple', 'Blue',128, 22000000, 8,  'Chip A16 Bionic, camera 48MP, màn hình 6.1 inch', 'C001', NOW(), NULL, true),
('PR004', 'iPhone 14', 'Apple', 'Midnight', 128, 18000000, 12, 'Chip A15 Bionic, camera 12MP, màn hình 6.1 inch', 'C001', NOW(), NULL, true),

('PR005', 'Samsung S23', 'Samsung', 'Cream', 128, 20000000, 10, 'Snapdragon 8 Gen 2, camera 50MP', 'C002', NOW(), NULL, true),
('PR006', 'iPhone 15 Pro', 'Apple', 'Black Titanium', 128, 28000000, 10, 'Chip A17 Pro, camera 48MP, màn hình 6.1 inch', 'C001', NOW(), NULL, true),
('PR007', 'Samsung A54', 'Samsung', 'Awesome White', 128, 10000000, 15, 'Exynos 1380, camera 50MP, pin 5000mAh', 'C002', NOW(), NULL, true),
('PR008', 'Oppo Reno 10 Pro', 'Oppo', 'Glossy Purple', 256, 13000000, 10, 'Dimensity 7050, camera 50MP, sạc 80W', 'C004', NOW(), NULL, true),

('PR009', 'Xiaomi 13','Xiaomi', 'Flora Green',   128, 14000000, 12, 'Snapdragon 8 Gen 2, camera 54MP', 'C003', NOW(), NULL, true),
('PR010', 'Redmi Note 12', 'Xiaomi', 'Ice Blue',  128, 7000000,  20, 'Snapdragon 685, camera 50MP, pin 5000mAh', 'C003', NOW(), NULL, true),
('PR011', 'Xiaomi 13 Pro', 'Xiaomi', 'Ceramic Black', 256, 18000000, 8,  'Snapdragon 8 Gen 2, camera Leica 50MP', 'C003', NOW(), NULL, true),
('PR012', 'Oppo A78', 'Oppo', 'Mist Black',    128, 7500000,  18, 'Snapdragon 680, camera 50MP, pin 5000mAh', 'C004', NOW(), NULL, true),

-- Vivo
('PR013', 'Vivo V27',    'Vivo', 'Magic Blue',  128, 9000000,  14, 'Dimensity 7200, camera 50MP, sạc 66W', 'C005', NOW(), NULL, true),
('PR014', 'Vivo Y36',    'Vivo', 'Meteor Black', 128, 6500000,  20, 'Snapdragon 680, camera 50MP, pin 5000mAh', 'C005', NOW(), NULL, true),
('PR015', 'Vivo X90 Pro','Vivo', 'Legendary Black', 256, 20000000, 6, 'Dimensity 9200, camera Zeiss 50MP', 'C005', NOW(), NULL, true);



insert into orders values
-- mã order, mã người dùng, created, total_amount, status
('OR001', 'CU002', NOW(), 25000000, 'PENDING'),
('OR002', 'CU004', NOW(), 20000000, 'SHIPPING'),
('OR003', 'CU002', NOW(), 40000000, 'DELIVERED'),
('OR004', 'CU003', NOW(), 15000000, 'CANCELLED');

insert into orderDetails values
-- mã od detail, mã order, mã sp, số lượng , tổng giá
('OD001', 'OR001', 'PR001', 5, 125000000),
('OD002', 'OR002', 'PR003', 10, 200000000),
('OD003', 'OR003', 'PR001', 8, 200000000),
('OD004', 'OR003', 'PR002', 5, 75000000),
('OD005', 'OR004', 'PR004', 6, 90000000);


select * from orderDetails where order_id = 'OR001';

-- bảng cart bổ sung (nếu cần hoặc nếu làm được)
create table cart (
    cart_id    int auto_increment primary key,
    user_id    varchar(20) not null,
    product_id varchar(50) not null,
    quantity   int not null default 1,

    foreign key (user_id) references users(id),
    foreign key (product_id) references products(product_id),
	unique (user_id, product_id)
);

-- Thêm sản phẩm vào giỏ hàng
delimiter //
create procedure pr_add_to_cart(
    in p_user_id varchar(20),
    in p_product_id varchar(50),
    in p_quantity int
)
begin
    -- kiểm tra sản phẩm đã có trong giỏ chưa
    if exists (select 1 from cart where user_id = p_user_id and product_id = p_product_id) then
        -- đã có → tăng số lượng
        update cart set quantity = quantity + p_quantity
        where user_id = p_user_id and product_id = p_product_id;
    else
        -- chưa có → thêm mới
        insert into cart (user_id, product_id, quantity)
        values (p_user_id, p_product_id, p_quantity);
    end if;
end //
delimiter ;

-- cập nhật giỏ hàng
delimiter //
create procedure pr_update_cart_quantity(
    in p_user_id varchar(20),
    in p_product_id varchar(50),
    in p_quantity int
)
begin
    update cart set quantity = p_quantity
    where user_id = p_user_id and product_id = p_product_id;
end //
delimiter ;


-- Thống kê doanh thu và khách hàng
delimiter //
create procedure pr_get_top5_bestproduct()
begin
select p.product_id, p.product_name, p.brand, sum(quantity) as total_quantity, sum(p.price) as total_revenue
from orderDetails od join products p on od.product_id = p.product_id 
join orders ord on od.order_id = ord.order_id
where month(ord.order_date) = month(now()) and year(ord.order_date) = year(now()) and ord.status = 'DELIVERED'
group by p.product_id, p.product_name, p.brand, p.color, p.cate_id order by total_revenue desc limit 5 offset 0;
end //
delimiter ;

call pr_get_top5_bestproduct;

delimiter //
create procedure pr_get_top3_potential_customer()
begin
select u.username, u.fullname, u.email, u.phone, count(ord.order_id) as total_orders, sum(ord.total_amount) as total_paid
from orders ord join users u on ord.id = u.id where ord.status = 'DELIVERED' and u.role = 'CUSTOMER' and u.is_active = true and month(ord.order_date) = month(now()) and year(ord.order_date) = year(now())
group by u.id, u.username, u.fullname, u.email, u.phone order by u.fullname asc limit 3;
end //
delimiter ;

call pr_get_top3_potential_customer;
select * from users;
select * from orders;


select sum(total_amount) as total_revenue from orders where month(order_date) = month(now()) and status = 'DELIVERED';


select * from orders where status = 'DELIVERED';
