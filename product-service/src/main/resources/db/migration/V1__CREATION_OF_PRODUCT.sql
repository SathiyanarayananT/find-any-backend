CREATE TABLE PRODUCTS
(
    ID INT GENERATED ALWAYS AS IDENTITY,
    USER_ID VARCHAR(100) NOT NULL,
    PRODUCT_NAME VARCHAR(100) NOT NULL,
    QUANTITY INT NOT NULL,
    UOM VARCHAR(5) NOT NULL,
    PRICE INT NOT NULL,
    IMAGES_URL JSONB,
    CATEGORY VARCHAR(100) NOT NULL,
    CITY VARCHAR(20) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_MODIFIED_AT TIMESTAMP NOT NULL,
    PRODUCT_DESCRIPTION JSONB,
    PRIMARY KEY(ID)
);

