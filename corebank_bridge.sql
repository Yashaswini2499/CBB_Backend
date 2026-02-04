
CREATE DATABASE corebank_bridge;
USE corebank_bridge;

CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER','ADMIN') NOT NULL,
    status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    mfa_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    mfa_secret VARCHAR(255) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    account_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    account_number BIGINT UNIQUE NOT NULL,
    account_type ENUM('SAVINGS','CURRENT') NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status ENUM('ACTIVE','BLOCKED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
    txn_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    from_acc_id BIGINT NOT NULL,
    txn_type ENUM('DEPOSIT','WITHDRAW','TRANSFER') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    to_acc_id BIGINT NULL,
    status ENUM('SUCCESS','FAILED','PENDING') NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_acc_id) REFERENCES accounts(account_id),
    FOREIGN KEY (to_acc_id) REFERENCES accounts(account_id)
);


CREATE TABLE loans (
    loan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    salary DECIMAL(12,2) NOT NULL,
    loan_amount DECIMAL(15,2) NOT NULL,
    loan_type ENUM('HOME','CAR','PERSONAL') NOT NULL,
    emi DECIMAL(12,2) NOT NULL,
    status ENUM('APPROVED','PENDING','REJECTED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

CREATE TABLE audit_logs (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    action VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


-- USERS
INSERT INTO users (full_name,email,phone,password,role,status) VALUES
('Priya Darshini','priya@mail.com','9876543210','pass1','CUSTOMER','ACTIVE'),
('Rahul Kumar','rahul@mail.com','9123456780','pass2','CUSTOMER','ACTIVE'),
('Anita Sharma','anita@mail.com','9988776655','pass3','CUSTOMER','ACTIVE'),
('Admin User','admin@mail.com','9000000000','admin123','ADMIN','ACTIVE'),
('Kiran Reddy','kiran@mail.com','9345678901','pass5','CUSTOMER','ACTIVE');

-- ACCOUNTS
INSERT INTO accounts (customer_id,account_number,account_type,balance,status) VALUES
(1,100001,'SAVINGS',50000,'ACTIVE'),
(2,100002,'CURRENT',75000,'ACTIVE'),
(3,100003,'SAVINGS',30000,'ACTIVE'),
(5,100004,'SAVINGS',15000,'ACTIVE'),
(1,100005,'CURRENT',20000,'ACTIVE');

-- TRANSACTIONS
INSERT INTO transactions (from_acc_id,txn_type,amount,to_acc_id,status) VALUES
(1,'TRANSFER',5000,2,'SUCCESS'),
(2,'WITHDRAW',2000,NULL,'SUCCESS'),
(3,'DEPOSIT',7000,NULL,'SUCCESS'),
(1,'TRANSFER',10000,3,'PENDING'),
(4,'WITHDRAW',1000,NULL,'FAILED');

-- LOANS
INSERT INTO loans (customer_id,salary,loan_amount,loan_type,emi,status) VALUES
(1,50000,500000,'HOME',12000,'APPROVED'),
(2,40000,200000,'CAR',7000,'PENDING'),
(3,35000,100000,'PERSONAL',4500,'APPROVED'),
(5,60000,800000,'HOME',15000,'PENDING'),
(1,50000,150000,'PERSONAL',6000,'REJECTED');

-- AUDIT LOGS
INSERT INTO audit_logs (user_id,action) VALUES
(1,'Logged in'),
(4,'Approved a loan'),
(2,'Transferred money'),
(3,'Applied for loan'),
(1,'Updated profile');
