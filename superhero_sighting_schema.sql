-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema superheroDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema superheroDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `superheroDB` DEFAULT CHARACTER SET utf8 ;
USE `superheroDB` ;

-- -----------------------------------------------------
-- Table `superheroDB`.`organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`organization` (
  `OrganizationId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(255) NULL,
  `Address` VARCHAR(45) NULL,
  `Contact_info` VARCHAR(45) NULL,
  PRIMARY KEY (`OrganizationId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroDB`.`hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`hero` (
  `HeroId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(255) NULL,
  PRIMARY KEY (`HeroId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroDB`.`organization_has_hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`organization_has_hero` (
  `organization_OrganizationId` INT NOT NULL,
  `hero_HeroId` INT NOT NULL,
  PRIMARY KEY (`organization_OrganizationId`, `hero_HeroId`),
  INDEX `fk_organization_has_hero_hero1_idx` (`hero_HeroId` ASC) VISIBLE,
  INDEX `fk_organization_has_hero_organization_idx` (`organization_OrganizationId` ASC) VISIBLE,
  CONSTRAINT `fk_organization_has_hero_organization`
    FOREIGN KEY (`organization_OrganizationId`)
    REFERENCES `superheroDB`.`organization` (`OrganizationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organization_has_hero_hero1`
    FOREIGN KEY (`hero_HeroId`)
    REFERENCES `superheroDB`.`hero` (`HeroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroDB`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`location` (
  `locationId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  `Latitude` VARCHAR(45) NOT NULL,
  `Longitude` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NULL,
  `Description` VARCHAR(255) NULL,
  PRIMARY KEY (`locationId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroDB`.`superpower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`superpower` (
  `SuperpowerId` INT NOT NULL AUTO_INCREMENT,
  `Power` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`SuperpowerId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroDB`.`hero_has_superpower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`hero_has_superpower` (
  `hero_HeroId` INT NOT NULL,
  `superpower_SuperpowerId` INT NOT NULL,
  PRIMARY KEY (`hero_HeroId`, `superpower_SuperpowerId`),
  INDEX `fk_hero_has_superpower_superpower1_idx` (`superpower_SuperpowerId` ASC) VISIBLE,
  INDEX `fk_hero_has_superpower_hero1_idx` (`hero_HeroId` ASC) VISIBLE,
  CONSTRAINT `fk_hero_has_superpower_hero1`
    FOREIGN KEY (`hero_HeroId`)
    REFERENCES `superheroDB`.`hero` (`HeroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hero_has_superpower_superpower1`
    FOREIGN KEY (`superpower_SuperpowerId`)
    REFERENCES `superheroDB`.`superpower` (`SuperpowerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroDB`.`Sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`Sighting` (
  `SightingId` INT NOT NULL AUTO_INCREMENT,
  `Date` DATE NOT NULL,
  `location_locationId` INT NOT NULL,
  PRIMARY KEY (`SightingId`, `location_locationId`),
  INDEX `fk_Sighting_location1_idx` (`location_locationId` ASC) VISIBLE,
  CONSTRAINT `fk_Sighting_location1`
    FOREIGN KEY (`location_locationId`)
    REFERENCES `superheroDB`.`location` (`locationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroDB`.`hero_has_Sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroDB`.`hero_has_Sighting` (
  `hero_HeroId` INT NOT NULL,
  `Sighting_SightingId` INT NOT NULL,
  PRIMARY KEY (`hero_HeroId`, `Sighting_SightingId`),
  INDEX `fk_hero_has_Sighting_Sighting1_idx` (`Sighting_SightingId` ASC) VISIBLE,
  INDEX `fk_hero_has_Sighting_hero1_idx` (`hero_HeroId` ASC) VISIBLE,
  CONSTRAINT `fk_hero_has_Sighting_hero1`
    FOREIGN KEY (`hero_HeroId`)
    REFERENCES `superheroDB`.`hero` (`HeroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hero_has_Sighting_Sighting1`
    FOREIGN KEY (`Sighting_SightingId`)
    REFERENCES `superheroDB`.`Sighting` (`SightingId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
