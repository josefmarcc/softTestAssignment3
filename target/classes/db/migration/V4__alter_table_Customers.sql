ALTER TABLE `DemoApplicationTest`.`Customers`
    ADD COLUMN `phoneNumber` VARCHAR(15) NULL DEFAULT NULL AFTER `birthdate`,
ADD UNIQUE INDEX `phonenumber_UNIQUE` (`phoneNumber` ASC) VISIBLE;
;