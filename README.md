## Instructions

To run the application on your own computer, you will need to change the database connections in `/server/src/main/resources/application.yml` to your own db server configuration. You will also need to create database and tables in your own db server. Create table expressions are shown below:

```mysql
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userID` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `friend` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userID1` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `userID2` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `offline` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fromID` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `toID` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `message` varchar(200) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `is_sent` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
```



Then you should follow the instructions below:

1. Run server application, the default port is 8888.
2. Run client application, the default port is 8080.
3. Since this is an IM system, you might want to run clients on different port as different users. Thus, you would need to change the port configurations on both the settings of IDE and the source code. (Unfortunately the web-socket port is hard coded in `./client/src/main/resources/static/bundle.js`, the default value is 8080)
4. Now you can test all features of our IM system.