CREATE TABLE IF NOT EXISTS ACCOUNT (
    ID INT PRIMARY KEY,
    ACCOUNT_ID INT,
    CUSTOMER_NUMBER varchar(100),
    ACCOUNT_BALANCE DECIMAL,
    MINIMUM_BALANCE DECIMAL,
    MAXIMUM_WITHDRAW_AMOUNT DECIMAL,
    ACCOUNT_NUMBER varchar(100),
    INTEREST_RATE DOUBLE
    BANK_ACCOUNT_TYPE varchar(100),
    OVERDRAFT_LIMIT DECIMAL
);