-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema superherodb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema superherodb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `superherodb` DEFAULT CHARACTER SET utf8 ;
USE `superherodb` ;

-- -----------------------------------------------------
-- Table `superherodb`.`superpower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superherodb`.`superpower` (
  `SuperpowerId` INT NOT NULL AUTO_INCREMENT,
  `Power` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`SuperpowerId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO `superpower`(`Power`) VALUE("None");

-- -----------------------------------------------------
-- Table `superherodb`.`hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superherodb`.`hero` (
  `HeroId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(255) NULL DEFAULT NULL,
  `superpower_SuperpowerId` INT NOT NULL,
  PRIMARY KEY (`HeroId`, `superpower_SuperpowerId`),
  INDEX `fk_hero_superpower1_idx` (`superpower_SuperpowerId` ASC) VISIBLE,
  CONSTRAINT `fk_hero_superpower1`
    FOREIGN KEY (`superpower_SuperpowerId`)
    REFERENCES `superherodb`.`superpower` (`SuperpowerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superherodb`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superherodb`.`location` (
  `LocationId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Latitude` VARCHAR(45) NULL,
  `Longitude` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  `Description` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`LocationId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superherodb`.`organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superherodb`.`organization` (
  `OrganizationId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(255) NULL DEFAULT NULL,
  `Address` VARCHAR(45) NULL DEFAULT NULL,
  `Contact_info` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`OrganizationId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superherodb`.`organization_has_hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superherodb`.`organization_has_hero` (
  `organization_OrganizationId` INT NOT NULL,
  `hero_HeroId` INT NOT NULL,
  PRIMARY KEY (`organization_OrganizationId`, `hero_HeroId`),
  INDEX `fk_organization_has_hero_hero1_idx` (`hero_HeroId` ASC) VISIBLE,
  INDEX `fk_organization_has_hero_organization_idx` (`organization_OrganizationId` ASC) VISIBLE,
  CONSTRAINT `fk_organization_has_hero_hero1`
    FOREIGN KEY (`hero_HeroId`)
    REFERENCES `superherodb`.`hero` (`HeroId`),
  CONSTRAINT `fk_organization_has_hero_organization`
    FOREIGN KEY (`organization_OrganizationId`)
    REFERENCES `superherodb`.`organization` (`OrganizationId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superherodb`.`sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superherodb`.`sighting` (
  `SightingId` INT NOT NULL AUTO_INCREMENT,
  `location_LocationId` INT NOT NULL,
  `hero_HeroId` INT NOT NULL,
  `Date` DATETIME NOT NULL,
  PRIMARY KEY (`SightingId`, `location_LocationId`, `hero_HeroId`),
  INDEX `fk_location_has_hero_hero1_idx` (`hero_HeroId` ASC) VISIBLE,
  INDEX `fk_location_has_hero_location1_idx` (`location_LocationId` ASC) VISIBLE,
  CONSTRAINT `fk_location_has_hero_location1`
    FOREIGN KEY (`location_LocationId`)
    REFERENCES `superherodb`.`location` (`LocationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_has_hero_hero1`
    FOREIGN KEY (`hero_HeroId`)
    REFERENCES `superherodb`.`hero` (`HeroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
