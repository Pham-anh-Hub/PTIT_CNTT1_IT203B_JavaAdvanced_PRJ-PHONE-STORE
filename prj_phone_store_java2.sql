create utils prj_phone_store_java2;
use prj_phone_store_java2;

-- users, categories, products, orders, order_details.

-- User
create table users(
	id varchar(20) primary key,
    username varchar(100), 
	role enum ('ADMIN','CUSTOMMER') not null,
	password varchar(100) not null,
    
    -- thuộc tính bổ sung cho khách hàng
    fullname varchar(50),
    email varchar(50) unique,
    phone varchar(20) unique,
    address varchar(255)
);

-- Category
create table categories(
	cate_id varchar(50) primary key,
    cate_name varchar(200) not null unique,
    description varchar(255)
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
    foreign key (cate_id) references categories(cate_id)
);


-- orders
create table orders(
	order_id varchar(50) primary key, 
    id varchar(20),
    order_date datetime default current_timestamp,
    total_amount decimal(15,2),
    status enum ('PENDING', 'SHIPPING', 'DELIVERED', 'CANCELLED'),
    
    foreign key (id) references users(id)
);

-- order-details
create table orderDetails (
	oit_id varchar(20) primary key,
    order_id varchar(50),
    product_id varchar(50),
    quantity int,
    price decimal(15,2),
    
    foreign key (order_id) references orders(order_id),
    foreign key (product_id) references products(product_id)
);


-- Add mockdata
INSERT INTO users VALUES
('AD001', 'admin01', 'ADMIN', '$2a$10$7QJQ8w7k9zYk7x8yQzKJ.eYg5m8uYk1zF6Q2Q1Yg8zYxk8xYzYzYy', NULL, NULL, NULL, NULL),
('CU002', 'user01', 'CUSTOMMER', '$2a$10$7QJQ8w7k9zYk7x8yQzKJ.eYg5m8uYk1zF6Q2Q1Yg8zYxk8xYzYzYy', 'Nguyễn Văn A', 'nva@gmail.com', '0912345678', 'Hà Đông, Hà Nội'),
('CU003', 'user02', 'CUSTOMMER', '$2a$10$7QJQ8w7k9zYk7x8yQzKJ.eYg5m8uYk1zF6Q2Q1Yg8zYxk8xYzYzYy', 'Trần Thị B', 'ttb@gmail.com', '0987654321', 'Quận 7, TP.Hồ Chí Minh'),
('CU004', 'user03', 'CUSTOMMER', '$2a$10$7QJQ8w7k9zYk7x8yQzKJ.eYg5m8uYk1zF6Q2Q1Yg8zYxk8xYzYzYy', 'Lê Văn C', 'lvc@gmail.com', '0909090909', 'Ba Đình, Hà Nội');


INSERT INTO categories VALUES
('C001', 'iPhone', 'Apple smartphones'),
('C002', 'Samsung', 'Samsung Galaxy series'),
('C003', 'Xiaomi', 'Xiaomi smartphones'),
('C004', 'Oppo', 'Oppo smartphones');


INSERT INTO products VALUES
-- ID, TÊN, HÃNG, MÀU, dung lượng, giá, stock, mô tả, mã danh mục, ..
('PR001', 'iPhone 15 Pro', 'Apple', 'Black', 128, 25000000, 10, 'A17 Pro chip', 'C001', NOW(), NULL),
('PR002', 'iPhone 15 Pro', 'Apple', 'Blue', 256, 28000000, 5, 'A17 Pro chip', 'C001', NOW(), NULL),
('PR003', 'Samsung S23', 'Samsung', 'White', 256, 20000000, 8, 'Snapdragon 8 Gen 2', 'C002', NOW(), NULL),
('PR004', 'Xiaomi 13', 'Xiaomi', 'Black', 128, 15000000, 15, 'Snapdragon 8 Gen 2', 'C003', NOW(), NULL),
('PR005', 'Oppo Reno 10', 'Oppo', 'Pink', 256, 12000000, 12, 'Dimensity 7050', 'C004', NOW(), NULL);


INSERT INTO orders VALUES
-- mã order, mã người dùng, created, total_amount, status
('OR001', 'CU002', NOW(), 25000000, 'PENDING'),
('OR002', 'CU004', NOW(), 20000000, 'SHIPPING'),
('OR003', 'CU002', NOW(), 40000000, 'DELIVERED'),
('OR004', 'CU003', NOW(), 15000000, 'CANCELLED');

INSERT INTO orderDetails VALUES
-- mã od detail, mã order, mã sp, số lượng , tổng giá
('OD001', 'OR001', 'PR001', 5, 125000000),
('OD002', 'OR002', 'PR003', 10, 200000000),
('OD003', 'OR003', 'PR001', 8, 200000000),
('OD004', 'OR003', 'PR002', 5, 75000000),
('OD005', 'OR004', 'PR004', 6, 90000000);

-- bảng cart bổ sung (nếu cần hoặc nếu làm được)





