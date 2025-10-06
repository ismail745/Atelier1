-- Schema for mvc2jee application (classpath resource)

CREATE DATABASE IF NOT EXISTS mvc2jee DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mvc2jee;

CREATE TABLE IF NOT EXISTS clients (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100),
  email VARCHAR(150),
  telephone VARCHAR(50)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS produits (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(150),
  prix DOUBLE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS commandes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  client_id BIGINT,
  date_commande DATE,
  FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS lignes_de_commande (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  commande_id BIGINT,
  produit_id BIGINT,
  quantite INT,
  prix_unitaire DOUBLE,
  FOREIGN KEY (commande_id) REFERENCES commandes(id) ON DELETE CASCADE,
  FOREIGN KEY (produit_id) REFERENCES produits(id) ON DELETE SET NULL
) ENGINE=InnoDB;
