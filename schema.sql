
CREATE DATABASE `librarydb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;


CREATE TABLE `book_issue` (
  `id` int NOT NULL,
  `book_id` varchar(45) DEFAULT NULL,
  `issued_to` varchar(45) DEFAULT NULL,
  `issued_on` date DEFAULT NULL,
  `issued_for_days` int DEFAULT NULL,
  `returned_flag` varchar(45) DEFAULT NULL,
  `returned_on` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
