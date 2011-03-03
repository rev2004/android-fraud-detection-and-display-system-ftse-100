CREATE TABLE IF NOT EXISTS `group17_companies` (
  `symbol` varchar(3) character set utf8 NOT NULL,
  `name` varchar(20) character set utf8 NOT NULL,
  PRIMARY KEY  (`symbol`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `group17_finance` (
  `symbol` varchar(3) character set utf8 NOT NULL,
  `time` int(11) NOT NULL,
  `bid` double NOT NULL,
  `ask` double NOT NULL,
  `volume` bigint(20) NOT NULL,
  PRIMARY KEY  (`symbol`,`time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `group17_news` (
  `source` varchar(20) character set ucs2 NOT NULL,
  `date` int(11) NOT NULL,
  `title` varchar(20) character set utf8 NOT NULL,
  `body` text character set utf8 NOT NULL,
  PRIMARY KEY  (`source`,`date`,`title`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE USER 'group17'@'localhost';
GRANT ALL ON *.* TO 'group17'@'localhost';