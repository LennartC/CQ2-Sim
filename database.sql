/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS `cq2_critdb_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cq2_critdb_classes` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `race` varchar(6) NOT NULL DEFAULT 'Forest',
  `name` varchar(20) NOT NULL DEFAULT '',
  `FD` tinyint(3) unsigned NOT NULL DEFAULT '100',
  `DD` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `AD` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ED` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cq2_critdb_classes`
--

LOCK TABLES `cq2_critdb_classes` WRITE;
/*!40000 ALTER TABLE `cq2_critdb_classes` DISABLE KEYS */;
INSERT INTO `cq2_critdb_classes` VALUES (1,'Air','Air Remnant',50,50,150,50),(2,'Air','Angel',0,35,100,65),(3,'Air','Balrog',40,40,150,50),(4,'Air','Bat',0,50,100,50),(5,'Air','Dragon',0,65,100,60),(6,'Air','Gargoyle',0,65,100,30),(7,'Air','Pit Wraith',0,20,100,70),(8,'Air','Rift Dancer',0,55,110,55),(9,'Air','Spirit',0,35,100,65),(10,'Air','Storm',0,55,100,40),(11,'Death','Apocalypse',55,110,0,55),(12,'Death','Banshee',45,100,0,55),(13,'Death','Carnage',25,100,0,70),(14,'Death','Death Remnant',50,150,50,50),(15,'Death','Devil',30,100,0,65),(16,'Death','Diabolic Horde',60,100,0,30),(17,'Death','Doomguard',55,100,0,45),(18,'Death','Lich',65,100,0,25),(19,'Death','Pit Worm',40,100,0,70),(20,'Death','Reaper',40,150,40,40),(21,'Death','Undead',50,100,0,50),(22,'Earth','Earth Remnant',50,50,50,150),(23,'Earth','Fiend',65,0,25,100),(24,'Earth','Giant',35,0,60,100),(25,'Earth','Golem',60,0,45,100),(26,'Earth','Imler',55,0,40,100),(27,'Earth','Imling',20,0,65,100),(28,'Earth','Rat',50,0,50,100),(29,'Earth','Smith',20,0,70,100),(30,'Earth','Tempest',55,0,55,110),(31,'Earth','Tremor',40,40,40,150),(32,'Forest','Cleric',100,20,70,0),(33,'Forest','Ent',100,65,30,0),(34,'Forest','Forest Remnant',150,50,50,50),(35,'Forest','Hound',100,70,25,0),(36,'Forest','Hunter',100,30,65,0),(37,'Forest','Imp',100,50,50,0),(38,'Forest','Monk',150,40,40,40),(39,'Forest','Priest',100,45,50,0),(40,'Forest','Seraph',110,55,55,0),(41,'Forest','Wolf',100,55,40,0);
/*!40000 ALTER TABLE `cq2_critdb_classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cq2_critdb_crits`
--

DROP TABLE IF EXISTS `cq2_critdb_crits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cq2_critdb_crits` (
  `id` mediumint(10) unsigned NOT NULL AUTO_INCREMENT,
  `raceid` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `name` varchar(30) NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `damage` smallint(5) unsigned NOT NULL DEFAULT '1',
  `health` smallint(5) unsigned NOT NULL DEFAULT '1',
  `skill` smallint(3) unsigned NOT NULL DEFAULT '1',
  `l2m` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `l2u` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `brim` mediumint(6) unsigned NOT NULL DEFAULT '0',
  `crys` mediumint(6) unsigned NOT NULL DEFAULT '0',
  `added` varchar(20) NOT NULL DEFAULT '0',
  `tmp` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=412 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cq2_critdb_crits`
--

LOCK TABLES `cq2_critdb_crits` WRITE;
/*!40000 ALTER TABLE `cq2_critdb_crits` DISABLE KEYS */;
INSERT INTO `cq2_critdb_crits` VALUES (204,4,'Cave Bat',1,3,8,14,0,1,90,120,'mortician',0),(205,4,'Night Bat',1,12,26,50,0,1,649,708,'mortician',0),(206,4,'Pulse Bat',1,24,44,92,0,1,1932,1748,'mortician',0),(207,4,'Crypt Bat',1,34,62,130,0,4,4077,3649,'mortician',0),(208,4,'Mutated Cave Bat',1,53,106,212,0,12,13483,13483,'mortician',0),(209,4,'Mutated Pulse Bat',1,71,151,293,0,20,21227,22993,'mortician',0),(210,4,'Mutated Crypt Bat',1,95,162,352,0,26,30995,25193,'mortician',0),(211,4,'Vampire Bat',1,157,229,532,0,44,70532,46497,'mortician',0),(212,21,'Undead Gimp',1,3,8,14,0,1,90,120,'mortician',0),(213,21,'Undead Demon',1,12,29,53,0,1,649,799,'mortician',0),(214,21,'Zombie',1,21,49,91,0,1,1657,1978,'mortician',0),(215,21,'Creep Zombie',1,29,73,131,0,4,3368,4439,'mortician',0),(216,21,'Doom Zombie',1,51,108,210,0,12,12831,13813,'mortician',0),(217,28,'Mog Rat',1,3,8,14,0,1,90,120,'mortician',0),(218,28,'Black Rat',1,15,22,52,0,1,829,589,'mortician',0),(219,28,'Swamp Rat',1,26,39,91,0,1,2118,1521,'mortician',0),(220,28,'Plague Rat',1,37,59,133,0,4,4512,3438,'mortician',0),(221,37,'Fire Imp',1,3,8,14,0,1,90,120,'mortician',0),(222,37,'Imp Warrior',1,15,24,54,0,1,829,649,'mortician',0),(223,37,'Imp Warlord',1,27,38,92,0,1,2212,1477,'mortician',0),(224,37,'Imp Commander',1,37,58,132,0,4,4512,3368,'mortician',0),(225,37,'Imp Necromancer',1,59,93,211,0,12,15484,11389,'mortician',0),(226,37,'Imp Necrolord',1,93,165,351,0,26,30150,25802,'mortician',0),(227,37,'Wicked Carver',1,138,198,474,0,38,59490,38374,'mortician',0),(228,37,'Twisted Carver',1,169,217,544,0,45,77734,43308,'mortician',0),(229,10,'Broken Storm',1,43,86,171,0,8,9584,9482,'mortician',0),(230,10,'Red Storm',1,63,126,251,0,16,16851,16670,'mortician',0),(231,10,'Raving Storm',1,83,166,331,0,24,26005,25724,'mortician',0),(232,10,'Cyclone',1,99,198,394,0,30,35425,35039,'mortician',0),(233,10,'Hurricane',1,119,238,474,0,38,48924,48387,'mortician',0),(234,10,'Thunder Storm',1,141,282,550,0,46,61203,60530,'mortician',0),(235,10,'Storm Lord',1,158,316,592,0,50,71126,70345,'mortician',0),(236,35,'Blood Hound',1,46,81,172,0,8,10448,8782,'mortician',0),(237,35,'Horror Hound',1,72,148,291,0,20,21617,22158,'mortician',0),(238,35,'Shallow Hound',1,102,192,394,0,30,36838,33655,'mortician',0),(239,35,'Hollow Hound',1,128,240,494,0,40,53866,48924,'mortician',0),(240,35,'Terror Hound',1,178,241,571,0,48,83244,49193,'mortician',0),(241,18,'Rotten Lich',1,49,77,174,0,8,11328,8143,'mortician',0),(242,18,'Lich',1,95,146,334,0,24,30995,21532,'mortician',0),(243,18,'Lich Priest',1,111,175,394,0,30,41153,29478,'mortician',0),(244,18,'Lich Shadowcaster',1,135,207,474,0,38,57788,39800,'mortician',0),(245,18,'Lich Hellcaster',1,162,224,533,0,44,73512,44171,'mortician',0),(246,18,'Lich Overseer',1,168,266,574,0,48,77127,55418,'mortician',0),(247,26,'Bump Imler',1,33,105,170,0,8,6830,12242,'mortician',0),(248,26,'Crushing Imler',1,68,159,294,0,20,20069,24323,'mortician',0),(249,26,'Granite Imler',1,89,216,392,0,30,30813,39269,'mortician',0),(250,26,'Boulder Imler',1,103,287,491,0,40,40434,61951,'mortician',0),(251,26,'Silver Imler',1,142,304,562,0,47,61776,66840,'mortician',0),(252,24,'Sand Giant',2,8,10,18,0,1,120,75,'mortician',0),(253,24,'Wall Giant',2,60,71,133,0,4,4030,2126,'mortician',0),(254,24,'Rock Giant',2,99,164,252,0,16,15095,11711,'mortician',0),(255,24,'Giant Mangler',2,149,234,371,0,28,30261,21806,'mortician',0),(256,24,'Cosmic Giant',2,207,291,492,0,40,50799,31547,'mortician',0),(257,39,'Forest Priest',1,61,131,252,0,16,16164,17529,'mortician',0),(258,39,'Mountain Priest',1,89,195,371,0,28,30813,34345,'mortician',0),(259,39,'Water Priest',1,96,224,414,0,32,34025,41185,'mortician',0),(260,39,'Fire Priest',1,107,241,453,0,36,42519,49193,'mortician',0),(261,39,'War Priest',1,113,298,522,0,43,45694,65104,'mortician',0),(262,39,'Priest of Light',1,133,322,562,0,47,56661,72113,'mortician',0),(263,39,'Priest of Radiance',1,143,346,591,0,50,62351,79291,'mortician',0),(264,39,'Priest Vizier',1,153,377,612,0,52,68170,88800,'mortician',0),(265,27,'Tiny Imling',1,64,88,214,0,12,17197,10265,'mortician',0),(266,27,'Small Imling',1,93,152,334,0,24,30150,22440,'mortician',0),(267,27,'Putrid Imling',1,112,191,410,0,32,41639,32691,'mortician',0),(268,27,'Rancid Imling',1,154,211,514,0,42,68759,40363,'mortician',0),(269,27,'Diamond Imling',1,165,274,573,0,48,75315,56985,'mortician',0),(270,13,'Fallen Carnage',1,53,148,253,0,16,13483,20517,'mortician',0),(271,13,'Rotten Carnage',1,90,234,412,0,32,31267,43611,'mortician',0),(272,13,'Shadow Carnage',1,105,317,524,0,43,41473,70638,'mortician',0),(273,13,'Carnage Tormenter',1,145,345,594,0,50,63505,78989,'mortician',0),(274,29,'Brim Smith',1,88,78,253,0,16,25934,8883,'mortician',0),(275,29,'Crystal Smith',1,71,216,354,0,26,21227,35828,'mortician',0),(276,29,'Dark Brim Smith',1,153,130,434,0,34,62658,19970,'mortician',0),(277,29,'Black Crystal Smith',1,88,303,474,0,38,32849,65813,'mortician',0),(278,29,'Depravity Smith',1,184,178,532,0,44,86968,32611,'mortician',0),(279,29,'Disease Crafter',1,103,365,554,0,46,40434,84146,'mortician',0),(280,29,'Hammer Lord',1,217,201,594,0,50,108125,38285,'mortician',0),(281,29,'Death Crafter',1,138,412,613,0,52,59490,98735,'mortician',0),(282,16,'Diabolic Observer',1,61,171,290,0,20,17426,26444,'mortician',0),(283,16,'Diabolic Watchman',1,85,207,374,0,28,29011,36732,'mortician',0),(284,16,'Diabolic Spectator',1,104,249,453,0,36,40953,50791,'mortician',0),(285,16,'Diabolic Intruder',1,124,250,494,0,40,51656,51061,'mortician',0),(286,16,'Diabolic Henchman',1,141,278,544,0,45,61203,58741,'mortician',0),(287,16,'Diabolic Commander',1,147,298,564,0,47,64663,64383,'mortician',0),(288,16,'Diabolic Menace',1,159,307,583,0,49,71721,66962,'mortician',0),(289,16,'Diabolic Nemesis',1,157,374,614,0,52,70532,86895,'mortician',0),(290,5,'Dragon Scout',1,91,181,371,0,28,31723,33225,'mortician',0),(291,5,'Dragon Nymph',1,103,200,414,0,32,37312,37866,'mortician',0),(292,5,'Dragon Guardian',1,109,226,453,0,36,43572,48224,'mortician',0),(293,5,'Dragon Soldier',1,122,240,494,0,40,50559,52206,'mortician',0),(294,5,'Dragon Scream',1,124,261,520,0,43,51656,58319,'mortician',0),(295,5,'Dragon Sentinel',1,137,268,542,0,45,58921,60392,'mortician',0),(296,5,'Dragon Sentry',1,151,273,563,0,47,66996,61884,'mortician',0),(297,5,'Dragon Revenant',1,155,297,582,0,49,69349,69164,'mortician',0),(298,5,'Dragon Queen',1,168,331,613,0,52,77127,79802,'mortician',0),(299,32,'Spider Sorceress',1,84,169,334,0,24,26413,26042,'mortician',0),(300,32,'Viper Sorceress',1,108,221,433,0,34,39702,40019,'mortician',0),(301,32,'Raptor Sorceress',1,128,261,513,0,42,53866,54047,'mortician',0),(302,32,'Disciple Sorcerer',1,138,271,532,0,44,59490,56797,'mortician',0),(303,32,'Zealot Sorcerer',1,143,281,551,0,46,62351,59580,'mortician',0),(304,32,'Mirage Cleric',1,158,307,581,0,49,71126,66962,'mortician',0),(305,32,'Delusion Cleric',1,165,329,601,0,51,75315,73368,'mortician',0),(306,23,'Ferocious Fiend',1,94,187,372,0,28,33100,32154,'mortician',0),(307,23,'Berserker Fiend',1,115,225,451,0,36,46765,44431,'mortician',0),(308,23,'Thrasher Fiend',1,132,261,521,0,43,56099,54047,'mortician',0),(309,23,'Marauder Fiend',1,141,276,542,0,45,61203,58741,'mortician',0),(310,23,'Mauler Fiend',1,158,307,581,0,49,71126,66962,'mortician',0),(311,23,'Destroyer Fiend',1,167,325,601,0,51,76522,72192,'mortician',0),(312,12,'Anguish Banshee',1,93,168,354,0,26,30150,26413,'mortician',0),(313,12,'Impaler Banshee',1,102,230,434,0,34,36838,43107,'mortician',0),(314,12,'Styx Banshee',1,147,218,512,0,42,64663,43572,'mortician',0),(315,12,'Destruction Banshee',1,165,235,554,0,46,75315,48112,'mortician',0),(316,12,'Abyss Banshee',1,187,281,603,0,51,88845,60916,'mortician',0),(317,9,'Willow Spirit',1,101,231,433,0,34,36366,43352,'mortician',0),(318,9,'Frozen Spirit',1,112,289,513,0,42,45161,63216,'mortician',0),(319,9,'Succubus Spirit',1,153,288,570,0,48,68170,62927,'mortician',0),(320,9,'Ancient Spirit',1,151,354,604,0,51,66996,82628,'mortician',0),(321,19,'Spitting Pit Worm',2,64,143,191,0,10,7973,9384,'mortician',0),(322,19,'Pit Worm Gladiator',2,121,287,374,0,28,23038,29436,'mortician',0),(323,19,'Accursed Pit Worm',2,146,403,491,0,40,32042,50106,'mortician',0),(324,19,'Vicious Pit Worm',2,182,502,612,0,52,42861,66960,'mortician',0),(325,20,'Life Reaper',3,33,58,131,0,4,3933,3845,'mortician',0),(326,20,'Bone Reaper',3,67,112,259,0,17,18244,16691,'mortician',0),(327,20,'Soul Reaper',3,101,198,423,0,33,36366,40935,'mortician',0),(328,36,'Light Hunter',2,91,91,191,0,10,12510,5097,'mortician',0),(329,36,'Servant Hunter',2,207,207,433,0,34,46550,18570,'mortician',0),(330,36,'Hunter Director',2,264,264,553,0,46,70031,27742,'mortician',0),(331,33,'Ebony Ent',2,5,15,17,0,1,75,112,'mortician',0),(332,33,'Ashwood Ent',2,41,107,132,0,4,2552,3477,'mortician',0),(333,33,'Hemlock Ent',2,81,200,252,0,16,11652,15128,'mortician',0),(334,33,'Elder Ent',2,125,284,372,0,28,24041,28102,'mortician',0),(335,33,'Ancient Ent',2,154,400,493,0,40,34380,48010,'mortician',0),(336,2,'Weak Angel',2,6,13,18,0,1,90,98,'mortician',0),(337,2,'Firewound Angel',2,52,83,131,0,4,3394,2589,'mortician',0),(338,2,'Goldblight Angel',2,109,225,310,0,22,18531,19308,'mortician',0),(339,2,'Angel Soulslayer',2,189,409,551,0,46,45051,49990,'mortician',0),(340,3,'Balrog',3,34,55,129,0,4,4077,3608,'mortician',0),(341,3,'Balrog Devourer',3,55,135,261,0,17,14143,21238,'mortician',0),(342,3,'Balrog Overlord',3,65,260,420,0,33,20415,58489,'mortician',0),(343,1,'Remnant Overmind',3,140,280,0,0,0,60630,74312,'mortician',0),(344,7,'Pit Wraith Lurker',2,82,109,190,0,10,10949,6353,'mortician',0),(345,7,'Pit Wraith Dominator',2,162,209,371,0,28,33765,18599,'mortician',0),(346,7,'Pit Wraith Titan',2,194,318,490,0,40,46631,35074,'mortician',0),(347,7,'Pit Wraith Lord',2,251,382,614,0,52,65515,44679,'mortician',0),(348,30,'Urhul Tempest',4,16,29,310,0,22,3059,2809,'mortician',0),(349,30,'Ahnkol Tempest',4,17,39,372,0,28,3523,4402,'mortician',0),(350,30,'Quarnok Tempest',4,22,41,432,0,34,4939,4700,'mortician',0),(351,30,'Quamar Tempest',4,22,53,494,0,40,5270,7035,'mortician',0),(352,30,'Tempest Channeler',4,30,60,610,0,52,7936,8287,'mortician',0),(353,34,'Forest Remnant',3,50,100,217,0,13,12507,15259,'mortician',0),(354,34,'Meadow Remnant',3,80,160,347,0,26,24790,30290,'mortician',0),(355,34,'Remnant Acolyte',3,140,280,0,0,0,60630,74312,'mortician',0),(356,14,'Death Remnant',3,50,100,217,0,13,12507,15259,'mortician',0),(357,14,'Styx Remnant',3,80,160,347,0,26,24790,30290,'mortician',0),(358,14,'Cursed Remnant',3,140,280,0,0,0,60630,74312,'mortician',0),(359,25,'Magma Golem',2,58,156,191,0,10,7029,10380,'mortician',0),(360,25,'Golem Pit Warrior',2,103,241,314,0,22,17216,21340,'mortician',0),(361,25,'Granite Golem',2,22,60,73,0,1,632,897,'mortician',0),(362,25,'Golem Pit Master',2,144,329,434,0,34,28937,34826,'mortician',0),(363,25,'Haunted Golem',2,163,461,554,0,46,37056,59191,'mortician',0),(364,38,'Monk Elementalist',3,60,130,265,0,18,15823,20229,'mortician',0),(365,38,'Monk Preacher',3,35,54,130,0,4,4221,3529,'mortician',0),(366,38,'Monk Destructor',3,95,210,425,0,34,33562,44215,'mortician',0),(367,17,'Chaos Guard',2,19,63,71,0,1,538,938,'mortician',0),(368,17,'Infernal Guard',2,127,190,311,0,22,22604,15498,'mortician',0),(369,17,'Pit Guard',2,188,414,553,0,46,44736,50799,'mortician',0),(370,1,'Air Remnant',3,50,100,217,0,13,12507,15259,'mortician',0),(371,1,'Vapour Remnant',3,80,160,347,0,26,24790,30290,'mortician',0),(372,6,'Goretongue Gargoyle',2,22,57,70,0,1,632,832,'mortician',0),(373,6,'Nightwing Gargoyle',2,79,201,250,0,16,11282,15225,'mortician',0),(374,6,'Bloodclaw Gargoyle',2,151,321,434,0,34,30794,32992,'mortician',0),(375,8,'Ramun Rift Dancer',4,16,29,310,0,22,3059,2809,'mortician',0),(376,8,'Aen Rift Dancer',4,24,49,493,0,40,5911,6343,'mortician',0),(377,8,'Rift Channeler',4,33,55,614,0,52,9000,7388,'mortician',0),(378,8,'Aerum Rift Dancer',4,27,55,554,0,46,6906,7388,'mortician',0),(379,8,'Inaer Rift Dancer',4,21,43,432,0,34,4647,5002,'mortician',0),(380,8,'Nadin Rift Dancer',4,20,33,371,0,28,4359,3537,'mortician',0),(381,11,'Inox Apocalypse',4,13,35,311,0,22,2336,3587,'mortician',0),(382,11,'Agor Apocalypse',4,18,49,433,0,34,3797,5936,'mortician',0),(383,11,'Zarox Apocalypse',4,16,41,372,0,28,3254,4700,'mortician',0),(384,11,'Gorum Apocalypse',4,21,55,494,0,40,4956,7388,'mortician',0),(385,11,'Aaxer Apocalypse',4,27,55,554,0,46,6906,7388,'mortician',0),(386,11,'Apocalypse Channeler',4,29,62,610,0,52,7589,8654,'mortician',0),(387,15,'Skull Devil',2,115,129,251,0,16,18313,8592,'mortician',0),(388,15,'Stone Devil',2,59,69,130,0,4,3950,2054,'mortician',0),(389,15,'Weak Devil',2,8,9,17,0,1,120,67,'mortician',0),(390,15,'Doom Devil',2,198,224,433,0,34,43917,20593,'mortician',0),(391,31,'Crude Tremor',3,30,62,129,0,4,3508,4166,'mortician',0),(392,31,'Pyroclastic Tremor',3,46,168,280,0,19,12074,30487,'mortician',0),(393,31,'Obsidian Tremor',3,72,255,429,0,34,23342,57020,'mortician',0),(394,22,'Earth Remnant',3,50,100,217,0,13,12507,15259,'mortician',0),(395,22,'Volcanic Remnant',3,80,160,347,0,26,24790,30290,'mortician',0),(396,40,'Vanelin Seraph',4,13,35,311,0,22,2336,3587,'mortician',0),(397,40,'Neralim Seraph',4,22,64,551,0,46,5270,9024,'mortician',0),(398,40,'Andolin Seraph',4,17,51,434,0,34,3523,6255,'mortician',0),(399,40,'Seraph Channeler',4,25,70,612,0,52,6239,10157,'mortician',0),(400,40,'Undolim Seraph',4,15,43,372,0,28,2990,5002,'mortician',0),(401,40,'Siralim Seraph',4,19,58,490,0,40,4343,7924,'mortician',0),(402,41,'Mystic Wolf',2,112,226,314,0,22,19196,19209,'mortician',0),(403,41,'Dark Wolf',2,30,45,73,0,1,889,642,'mortician',0),(404,19,'Aardvark\'s Pit Maggot',2,1,2,44,1,1,21,22,'eXcalibur',0),(405,41,'Illusion Wolf',2,217,446,613,0,52,54063,55429,'mortician',0),(406,25,'Nightmare Golem',2,174,524,613,0,52,40392,70095,'mortician',0),(407,30,'Ahnok Tempest',4,27,55,554,0,46,6906,7388,'mortician',0),(408,38,'Monk Annihilator',3,160,330,689,0,60,0,0,'mortician',0),(409,3,'Balrog Shadow Lord',3,157,336,689,0,60,0,0,'mortician',0),(410,31,'Prismatic Tremor',3,151,347,689,0,60,0,0,'mortician',0),(411,20,'Incursion Reaper',3,178,299,690,0,60,0,0,'mortician',0); -- ' HL FIX :o
/*!40000 ALTER TABLE `cq2_critdb_crits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cq2_critdb_items`
--

DROP TABLE IF EXISTS `cq2_critdb_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cq2_critdb_items` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `raceid` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `name` varchar(40) NOT NULL DEFAULT '',
  `descr` varchar(255) NOT NULL DEFAULT '',
  `workshop` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `level` smallint(3) unsigned NOT NULL DEFAULT '0',
  `brim` smallint(6) NOT NULL DEFAULT '0',
  `ess` smallint(6) NOT NULL DEFAULT '0',
  `added` varchar(20) NOT NULL DEFAULT '0',
  `tmp` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cq2_critdb_items`
--

LOCK TABLES `cq2_critdb_items` WRITE;
/*!40000 ALTER TABLE `cq2_critdb_items` DISABLE KEYS */;
INSERT INTO `cq2_critdb_items` VALUES (1,32,'Spider Rune','Adds 45 damage points for every Cleric fighting on your side, removes 3 damage points for every other creature fighting on your side.',9,33,2574,5336,'mortician',0),(2,2,'Devil Slasher','Adds 60% damage during daytime and 15% damage at night.',0,12,682,1383,'mortician',0),(3,2,'Angelic Claymore','Adds 100% damage during daytime and 25% damage at night.',0,30,2326,4850,'mortician',0),(4,2,'Devil\'s Nightmare','Adds 120% damage during daytime and 30% damage at night.',0,54,5950,12754,'mortician',0),(5,4,'Flexible Wings','Increases the creature\'s damage by 10 points.',1,2,100,200,'mortician',0),(6,4,'Extended Wings',' Increases the creature\'s damage by 32 points.',2,6,300,600,'mortician',0),(7,4,'Sharp Wings',' Increases the creature\'s damage by 60 points.',3,10,566,1148,'mortician',0),(8,4,'Honed Wings',' Increases the creature\'s damage by 88 points.',4,14,798,1618,'mortician',0),(9,4,'Mutation Wings',' Increases the creature\'s damage by 141 points.',6,22,1456,2993,'mortician',0),(10,4,'Sharp Mutation Wings',' Increases the creature\'s damage by 199 points.',8,30,2326,4850,'mortician',0),(11,4,'Honed Mutation Wings',' Increases the creature\'s damage by 234 points.',10,35,2739,5711,'mortician',0),(12,4,'Vampire Wings',' Increases the creature\'s damage by 375 points.',20,53,5829,12494,'mortician',0),(13,5,'Earth Dragon Scales',' Removes 160 health points and adds 40% to earth defense.',11,37,2905,6058,'mortician',0),(14,5,'Death Dragon Scales',' Removes 160 health points and adds 40% to death defense.',12,39,3072,6405,'mortician',0),(15,5,'Air Dragon Scales',' Removes 80 health points and adds 20% to air defense.',13,41,3773,7976,'mortician',0),(16,5,'Forest Dragon Scales',' Removes 160 health points and adds 40% to forest defense.',15,45,4172,8820,'mortician',0),(17,5,'Earth Dragon Armor',' Removes 200 health points and adds 50% to earth defense.',22,55,6071,13014,'mortician',0),(18,5,'Death Dragon Armor',' Removes 200 health points and adds 50% to death defense.',23,56,6193,13274,'mortician',0),(19,5,'Forest Dragon Armor',' Removes 240 health points and adds 60% to forest defense.',28,61,7988,17361,'mortician',0),(20,9,'Willow Heart',' Creature has 80% chance of healing 170 health points every turn.',14,43,3972,8397,'mortician',0),(21,9,'Frozen Heart',' Creature has 80% chance of healing 210 health points every turn.',18,51,5587,11976,'mortician',0),(22,9,'Succubus Heart',' Creature has 80% chance of healing 280 health points every turn.',24,57,6314,13535,'mortician',0),(23,9,'Ancient Heart',' Creature has 80% chance of healing 370 health points every turn.',27,60,7841,17042,'mortician',0),(24,10,'Earth Storm Enhancer',' Removes 20% earth defense and adds 20% to all other defenses.',5,18,1031,2091,'mortician',0),(25,10,'Death Storm Enhancer',' Removes 25% death defense and adds 25% to all other defenses.',7,26,1732,3561,'mortician',0),(26,10,'Air Storm Enhancer',' Removes 25% air defense and adds 25% to all other defenses.',9,33,2574,5366,'mortician',0),(27,10,'Earth Storm Augmenter',' Removes 35% earth defense and adds 35% to all other defenses.',16,47,4373,9244,'mortician',0),(28,10,'Death Storm Augmenter',' Removes 35% death defense and adds 35% to all other defenses.',17,49,4574,9670,'mortician',0),(29,10,'Air Storm Augmenter',' Removes 40% air defense and adds 40% to all other defenses.',26,59,6558,14058,'mortician',0),(30,12,'Anguish Cry','Decreases opponent\'s defenses by 7% every turn.',10,35,2739,5711,'mortician',0),(31,12,'Impaler Cry','Decreases opponent\'s defenses by 10% every turn.',14,43,3972,8397,'mortician',0),(32,12,'Styx Cry','Decreases opponent\'s defenses by 13% every turn.',18,51,5587,11976,'mortician',0),(33,12,'Destruction Leech','Decreases opponent\'s defenses by 8% and adds 4% to creature every turn.',22,55,6071,13014,'mortician',0),(34,12,'Abyss Leech','Decreases opponent\'s defenses by 10% and adds 5% to creature every turn.',27,60,7841,17042,'mortician',0),(35,13,'Fallen Touch',' Decreases opponent\'s damage by 32 points every turn.',7,26,1732,3561,'mortician',0),(36,13,'Rotten Touch',' Decreases opponent\'s damage by 58 points every turn.',13,41,3773,7976,'mortician',0),(37,13,'Shadow Touch',' Decreases opponent\'s damage by 75 points every turn.',19,52,5708,12235,'mortician',0),(38,13,'Tormenter Touch',' Decreases opponent\'s damage by 92 points every turn.',26,59,6558,14058,'mortician',0),(39,16,'Diabolic Pendant',' Creature gains 30 damage points for every Diabolic Horde fighting on your side.',8,30,2326,4850,'mortician',0),(40,16,'Diabolic Flag',' Creature gains 35 damage points for every Diabolic Horde fighting on your side.',11,37,2905,6058,'mortician',0),(41,16,'Diabolic Banner',' Creature gains 41 damage points for every Diabolic Horde fighting on your side.',15,45,4172,8820,'mortician',0),(42,16,'Diabolic Standard',' Creature gains 46 damage points for every Diabolic Horde fighting on your side.',17,49,4574,9670,'mortician',0),(43,16,'Diabolic Carrier',' Creature gains 52 damage points for every Diabolic Horde fighting on your side.',21,54,5950,12754,'mortician',0),(44,16,'Diabolic Flame',' Creature gains 58 damage points for every Diabolic Horde fighting on your side.',23,56,6193,13274,'mortician',0),(45,16,'Diabolic Inferno',' Creature gains 64 damage points for every Diabolic Horde fighting on your side.',25,58,6436,13797,'mortician',0),(46,16,'Diabolic Abyss',' Creature gains 76 damage points for every Diabolic Horde fighting on your side.',28,61,7988,17361,'mortician',0),(47,18,'Sacrifical Knife','Reduces the opponent\'s defenses by 10% at the start of the battle.',5,18,1031,2091,'mortician',0),(48,18,'Gore Knife','Reduces the opponent\'s defenses by 14% at the start of the battle.',9,33,2574,5366,'mortician',0),(49,18,'Blood Knife','Reduces the opponent\'s defenses by 18% at the start of the battle. ',12,39,3072,6405,'mortician',0),(50,18,'Shadow Knife','Reduces the opponent\'s defenses by 22% at the start of the battle. ',16,47,4373,9244,'mortician',0),(51,18,'Hell Knife','Reduces the opponent\'s defenses by 26% at the start of the battle. ',20,53,5829,12494,'mortician',0),(52,18,'Overseer Blade','Reduces the opponent\'s defenses by 30% at the start of the battle. ',24,57,6314,13535,'mortician',0),(53,21,'Undead Club',' Increases the creature\'s damage by 10 points.',1,2,100,200,'mortician',0),(54,21,'Rot Club',' Increases the creature\'s damage by 30 points.',2,6,300,600,'mortician',0),(55,21,'Stench Club',' Increases the creature\'s damage by 65 points.',3,10,566,1148,'mortician',0),(56,21,'Zombie Club',' Increases the creature\'s damage by 86 points.',4,14,798,1618,'mortician',0),(57,21,'Doom Club',' Increases the creature\'s damage by 142 points.',6,22,1456,2993,'mortician',0),(58,23,'Ferocious Infusion',' Adds 250 damage and health, removes 60 damage and health for every other creature of the exact same type fighting on your side.',11,37,2905,6058,'mortician',0),(59,23,'Berserker Infusion',' Adds 300 damage and health, removes 75 damage and health for every other creature of the exact same type fighting on your side.',15,45,4172,8820,'mortician',0),(60,23,'Thrasher Infusion',' Adds 350 damage and health, removes 85 damage and health for every other creature of the exact same type fighting on your side.',19,52,5708,12235,'mortician',0),(61,23,'Marauder Infusion',' Adds 400 damage and health, removes 100 damage and health for every other creature of the exact same type fighting on your side.',21,54,5950,12754,'mortician',0),(62,23,'Mauler Infusion',' Adds 500 damage and health, removes 125 damage and health for every other creature of the exact same type fighting on your side.',25,58,6436,13797,'mortician',0),(63,23,'Destroyer Infusion',' Adds 600 damage and health, removes 150 damage and health for every other creature of the exact same type fighting on your side.',27,60,7841,17042,'mortician',0),(64,26,'Bump Jewel',' Creature has 14% chance of doing the damage of the opponent in addition to its own damage.',5,18,1031,2091,'mortician',0),(65,26,'Crushing Jewel',' Creature has 20% chance of doing the damage of the opponent in addition to its own damage.',8,30,2326,4850,'mortician',0),(66,26,'Granite Jewel',' Creature has 28% chance of doing the damage of the opponent in addition to its own damage.',12,39,3072,6405,'mortician',0),(67,26,'Boulder Jewel',' Creature has 34% chance of doing the damage of the opponent in addition to its own damage.',17,49,4574,9670,'mortician',0),(68,26,'Silver Jewel',' Creature has 40% chance of doing the damage of the opponent in addition to its own damage.',23,56,6193,13274,'mortician',0),(69,27,'Tiny Charm',' Drains 50 health from the opponent every turn and gives it to the creature.',6,22,1456,2993,'mortician',0),(70,27,'Small Charm',' Drains 60 health from the opponent every turn and gives it to the creature.',9,33,2574,5366,'mortician',0),(71,27,'Putrid Charm',' Drains 70 health from the opponent every turn and gives it to the creature.',13,41,3773,7976,'mortician',0),(72,27,'Rancid Charm',' Drains 90 health from the opponent every turn and gives it to the creature.',18,51,5587,11976,'mortician',0),(73,27,'Diamond Charm',' Drains 120 health from the opponent every turn and gives it to the creature.',24,57,6314,13535,'mortician',0),(74,28,'Weak Rat Poison',' Increases the creature\'s health by 20 points.',1,2,100,200,'mortician',0),(75,28,'Rat Poison',' Increases the creature\'s health by 62 points.',2,6,300,600,'mortician',0),(76,28,'Swamp Rat Poison',' Increases the creature\'s health by 132 points.',3,10,566,1148,'mortician',0),(77,28,'Plague Rat Poison',' Increases the creature\'s health by 188 points.',4,14,798,1618,'mortician',0),(78,29,'Crystal Hammer',' Increases the creature\'s health by 340 points.',7,26,1732,3561,'mortician',0),(79,29,'Brim Hammer',' Increases the creature\'s damage by 220 points.',10,35,2739,5711,'mortician',0),(80,29,'Black Crystal Hammer',' Increases the creature\'s health by 750 when fighting earth creatures and by 500 when fighting other creatures.',14,43,3972,8397,'mortician',0),(81,29,'Black Crystal Maul',' Increases the creature\'s health by 750 when fighting death creatures and by 500 when fighting other creatures.',14,43,3972,8397,'mortician',0),(82,29,'Dark Brim Hammer',' Increases the creature\'s damage by 450 when fighting air creatures and by 300 when fighting other creatures.',16,47,4373,9244,'mortician',0),(83,29,'Dark Brim Maul',' Increases the creature\'s damage by 450 when fighting forest creatures and by 300 when fighting other creatures.',16,47,4373,9244,'mortician',0),(84,29,'Depravity Hammer',' Increases the creature\'s health by 700 points.',20,53,5829,12494,'mortician',0),(85,29,'Disease Maul',' Increases the creature\'s damage by 600 when fighting death creatures and by 400 when fighting other creatures.',22,55,6071,13014,'mortician',0),(86,29,'Disease Hammer',' Increases the creature\'s damage by 600 when fighting earth creatures and by 400 when fighting other creatures.',22,55,6071,13014,'mortician',0),(87,29,'Sickness Maul',' Increases the creature\'s health by 1200 when fighting forest creatures and by 800 when fighting other creatures.',26,59,6558,14058,'mortician',0),(88,29,'Sickness Hammer',' Increases the creature\'s health by 1200 when fighting air creatures and by 800 when fighting other creatures.',26,59,6558,14058,'mortician',0),(89,29,'Death Hammer',' Increases the creature\'s damage by 600 points.',28,61,7988,17361,'mortician',0),(90,32,'Viper Rune',' Adds 60 damage points for every Cleric fighting on your side, removes 6 damage points for every other creature fighting on your side.',14,43,3972,8397,'mortician',0),(91,32,'Raptor Rune',' Adds 70 damage points for every Cleric fighting on your side, removes 9 damage points for every other creature fighting on your side.',18,51,5587,11976,'mortician',0),(92,32,'Disciple Rune',' Adds 78 damage points for every Cleric fighting on your side, removes 19 damage points for every other creature fighting on your side.',20,53,5829,12494,'mortician',0),(93,32,'Zealot Rune',' Adds 86 damage points for every Cleric fighting on your side, removes 24 damage points for every other creature fighting on your side.',22,55,6071,13014,'mortician',0),(94,32,'Mirage Rune',' Adds 97 damage points for every Cleric fighting on your side, removes 29 damage points for every other creature fighting on your side.',25,58,6436,13797,'mortician',0),(95,32,'Delusion Rune',' Adds 108 damage points for every Cleric fighting on your side, removes 34 damage points for every other creature fighting on your side.',27,60,7841,17042,'mortician',0),(96,35,'Hound Collar',' Creature has 18% chance of evading an enemy attack and receiving no damage.',5,18,1031,2091,'mortician',0),(97,35,'Enchanted Collar',' Creature has 24% chance of evading an enemy attack and receiving no damage.',8,30,2326,4850,'mortician',0),(98,35,'Collar of Agility',' Creature has 28% chance of evading an enemy attack and receiving no damage.',12,39,3072,6405,'mortician',0),(99,35,'Collar of Dexterity',' Creature has 32% chance of evading an enemy attack and receiving no damage.',17,49,4574,9670,'mortician',0),(100,35,'Collar of Excellence',' Creature has 36% chance of evading an enemy attack and receiving no damage.',24,57,6314,13535,'mortician',0),(101,37,'Imp Sword',' Increases the creature\'s health and damage by 7 points.',1,2,100,200,'mortician',0),(102,37,'Imp Axe',' Increases the creature\'s health and damage by 21 points.',2,6,300,600,'mortician',0),(103,37,'Imp Mace',' Increases the creature\'s health and damage by 40 points.',3,10,566,1148,'mortician',0),(104,37,'Imp Staff',' Increases the creature\'s health and damage by 58 points.',4,14,798,1618,'mortician',0),(105,37,'Necromancer Wand',' Increases the creature\'s health and damage by 93 points.',6,22,1456,2993,'mortician',0),(106,37,'Necrolord Wand',' Increases the creature\'s health and damage by 156 points.',10,35,2739,5711,'mortician',0),(107,37,'Distortion Wand',' Increases the creature\'s health and damage by 210 points.',16,47,4373,9244,'mortician',0),(108,37,'Corruption Wand',' Increases the creature\'s health and damage by 242 points.',21,54,5950,12754,'mortician',0),(109,39,'Security Rod',' Increases the creature\'s forest, death, air and earth defense by 12%.',7,26,1732,3561,'mortician',0),(110,39,'Shield Rod',' Increases the creature\'s forest, death, air and earth defense by 14%.',11,37,2905,6058,'mortician',0),(111,39,'Protection Wand',' Increases the creature\'s forest, death, air and earth defense by 16%.',13,41,3773,7976,'mortician',0),(112,39,'Aegis Wand',' Increases the creature\'s forest, death, air and earth defense by 24%.',23,56,6193,13274,'mortician',0),(113,39,'Staff of Warden',' Increases the creature\'s forest, death, air and earth defense by 27%.',26,59,6558,14058,'mortician',0),(114,39,'Staff of Safeguard',' Increases the creature\'s forest, death, air and earth defense by 30%.',28,61,7988,17361,'mortician',0),(115,5,'Air Dragon Armor','Removes 160 health points and adds 40% to air defense.',25,58,6436,13797,'mortician',0),(116,19,'Toothpick','Increases the creature\'s damage by 1 points.',0,6,300,600,'mortician',0),(117,19,'Pit Worm Toxin','Creature has 25% chance of doing double damage.',0,24,1593,3277,'mortician',0),(118,19,'Pit Worm Poison','Creature has 35% chance of doing double damage.',0,36,2822,5884,'mortician',0),(119,19,'Pit Worm Venom','Creature has 55% chance of doing double damage.',0,48,4473,9457,'mortician',0),(120,19,'Pit Worm Acid','Creature has 80% chance of doing double damage.',0,60,7841,17042,'mortician',0),(121,33,'Ancient Artifact','Removes 40 health points and adds 40% to every defense.',0,54,5950,12754,'mortician',0),(122,33,'Ashwood Artifact','Removes 18 health points and adds 18% to every defense.',0,12,682,1383,'mortician',0),(123,33,'Ebony Artifact','Removes 8 health points and adds 8% to every defense.',0,1,50,100,'mortician',0),(124,33,'Elder Artifact','Removes 30 health points and adds 30% to every defense.',0,36,2822,5884,'mortician',0),(125,33,'Hemlock Artifact','Removes 25 health points and adds 25% to every defense.',0,24,1593,3277,'mortician',0),(129,7,'War Tome','Increases the creature\'s damage by 50%.',0,60,7841,17042,'mortician',0),(130,7,'War Manuscript','Increases the creature\'s damage by 30%.',0,36,2822,5884,'mortician',0),(131,7,'Pit Wraith Tome','Increases the creature\'s xp by 70%.',0,48,4473,9457,'mortician',0),(132,7,'Pit Wraith Book','Increases the creature\'s xp by 40%.',0,18,1031,2091,'mortician',0),(133,25,'Pit Razor','Adds 14% damage points every turn.',0,30,2326,4850,'mortician',0),(134,25,'Granite Axe','Adds 8% damage points every turn.',0,6,300,600,'mortician',0),(135,25,'Magma Blade','Adds 10% damage points every turn.',0,18,1031,2091,'mortician',0),(136,25,'Pit Scythe','Adds 17% damage points every turn.',0,42,3873,8187,'mortician',0),(137,25,'Fear Mace','Adds 20% damage points every turn.',0,54,5950,12754,'mortician',0),(138,25,'Nightmare Cleaver','Adds 25% damage points every turn.',0,60,7841,17042,'mortician',0),(139,17,'Infernal Armor','Removes 30% from death defense and adds 30% health points.',0,30,2326,4850,'mortician',0),(140,17,'Chaos Armor','Removes 20% from death defense and adds 20% health points.',0,12,682,1383,'mortician',0),(141,17,'Pit Armor','Removes 55% from death defense and adds 55% health points.',0,54,5950,12754,'mortician',0),(142,24,'Sizzling Stone','Increases the creature\'s health by 30%.',0,24,1593,3277,'mortician',0),(143,24,'Hot Rock','Increases the creature\'s health by 20%.',0,12,682,1383,'mortician',0),(144,24,'Heavy Stone','Increases the creature\'s health by 10%.',0,1,50,100,'mortician',0),(145,24,'Searing Giant Rock','Increases the creature\'s health by 35%.',0,36,2822,5884,'mortician',0),(146,24,'Scorching Giant Rock','Increases the creature\'s health by 40%.',0,48,4473,9457,'mortician',0),(147,15,'Devil Maul','Adds 70% health at night and 18% health during daytime.',0,18,1031,2091,'mortician',0),(148,15,'Devil Axe','Increases the creature\'s damage by 50 points.',0,1,50,100,'mortician',0),(149,15,'Angel\'s Horror','Adds 110% health at night and 28% health during daytime.',0,42,3873,8187,'mortician',0),(150,15,'Angel Breaker','Adds 30% health at night and 8% health during daytime.',0,6,300,600,'mortician',0),(151,6,'Bloodclaw Teeth','Increases the amount of resources stolen by 80% for every Gargoyle fighting on your side.',0,42,3873,8187,'mortician',0),(152,6,'Goretongue Teeth','Increases the amount of resources stolen by 40% for every Gargoyle fighting on your side.',0,6,300,600,'mortician',0),(153,6,'Nightwing Teeth','Increases the amount of resources stolen by 60% for every Gargoyle fighting on your side.',0,24,1593,3277,'mortician',0),(154,41,'Dark Claws','Increases the creature\'s damage and health by 8%.',0,6,300,600,'mortician',0),(155,41,'Illusion Claws','Increases the creature\'s damage and health by 34%.',0,60,7841,17042,'mortician',0),(156,41,'Mystic Claws','Increases the creature\'s damage and health by 18%.',0,30,2326,4850,'mortician',0),(157,5,'Corrosive Dragon Breath','Increases the creature\'s damage by 360 points.',21,54,5950,12754,'mortician',0),(158,39,'Guard Rod','Increases the creature\'s forest, death, air and earth defense by 18%.',15,45,4172,8820,'mortician',0),(159,39,'Guardian Wand','Increases the creature\'s forest, death, air and earth defense by 21%.',19,52,5708,12235,'mortician',0),(160,5,'Dragon Breath','Increases the creature\'s damage by 330 points.',19,52,5708,12235,'mortician',0),(161,36,'Book of Light','Creature gains 15% damage points for every Light Hunter fighting on your side.',0,18,1031,2091,'Alienation',0),(162,1,'Overmind Essence','Negates all other items',0,9950,0,0,'likbeer',1),(163,2,'Angelic Blade','Increases damage by 50 points.',0,1,50,100,'Alienation',1),(165,36,'Servant\'s Guide','Creature gains 20% damage points for every Servant Hunter fighting on your side.',0,42,3873,8187,'mortician',0),(166,36,'Director\'s Manual','Creature gains 25% damage points for every Hunter Director fighting on your side.',0,48,4473,9457,'mortician',0),(167,38,' Annihilation Staff','Negates all other items.',0,0,0,0,'0',0);
/*!40000 ALTER TABLE `cq2_critdb_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cq2_critdb_races`
--

DROP TABLE IF EXISTS `cq2_critdb_races`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cq2_critdb_races` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `class` varchar(6) NOT NULL DEFAULT 'Forest',
  `name` varchar(20) NOT NULL DEFAULT '',
  `FD` tinyint(3) unsigned NOT NULL DEFAULT '100',
  `DD` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `AD` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ED` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cq2_critdb_races`
--

LOCK TABLES `cq2_critdb_races` WRITE;
/*!40000 ALTER TABLE `cq2_critdb_races` DISABLE KEYS */;
INSERT INTO `cq2_critdb_races` VALUES (1,'Air','Air Remnant',50,50,150,50),(2,'Air','Angel',0,35,100,65),(3,'Air','Balrog',40,40,150,50),(4,'Air','Bat',0,50,100,50),(5,'Air','Dragon',0,65,100,60),(6,'Air','Gargoyle',0,65,100,30),(7,'Air','Pit Wraith',0,20,100,70),(8,'Air','Rift Dancer',0,55,110,55),(9,'Air','Spirit',0,35,100,65),(10,'Air','Storm',0,55,100,40),(11,'Death','Apocalypse',55,110,0,55),(12,'Death','Banshee',45,100,0,55),(13,'Death','Carnage',25,100,0,70),(14,'Death','Death Remnant',50,150,50,50),(15,'Death','Devil',30,100,0,65),(16,'Death','Diabolic Horde',60,100,0,30),(17,'Death','Doomguard',55,100,0,45),(18,'Death','Lich',65,100,0,25),(19,'Death','Pit Worm',40,100,0,70),(20,'Death','Reaper',40,150,40,40),(21,'Death','Undead',50,100,0,50),(22,'Earth','Earth Remnant',50,50,50,150),(23,'Earth','Fiend',65,0,25,100),(24,'Earth','Giant',35,0,60,100),(25,'Earth','Golem',60,0,45,100),(26,'Earth','Imler',55,0,40,100),(27,'Earth','Imling',20,0,65,100),(28,'Earth','Rat',50,0,50,100),(29,'Earth','Smith',20,0,70,100),(30,'Earth','Tempest',55,0,55,110),(31,'Earth','Tremor',40,40,40,150),(32,'Forest','Cleric',100,20,70,0),(33,'Forest','Ent',100,65,30,0),(34,'Forest','Forest Remnant',150,50,50,50),(35,'Forest','Hound',100,70,25,0),(36,'Forest','Hunter',100,30,65,0),(37,'Forest','Imp',100,50,50,0),(38,'Forest','Monk',150,40,40,40),(39,'Forest','Priest',100,45,50,0),(40,'Forest','Seraph',110,55,55,0),(41,'Forest','Wolf',100,55,40,0);
/*!40000 ALTER TABLE `cq2_critdb_races` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sim_announcement`
--

DROP TABLE IF EXISTS `sim_announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_announcement` (
  `annID` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `content` longtext CHARACTER SET latin1 NOT NULL,
  `user` varchar(50) CHARACTER SET latin1 NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`annID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_battledb`
--

DROP TABLE IF EXISTS `sim_battledb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_battledb` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `attacker` int(10) unsigned DEFAULT NULL,
  `defender` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK47DEC1A69362E81C` (`defender`),
  KEY `FK47DEC1A68CE36F2E` (`attacker`)
) ENGINE=InnoDB AUTO_INCREMENT=310 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_battledbside`
--

DROP TABLE IF EXISTS `sim_battledbside`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_battledbside` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(25) CHARACTER SET latin1 NOT NULL,
  `critClass` varchar(10) CHARACTER SET latin1 NOT NULL,
  `damage` smallint(5) unsigned NOT NULL,
  `health` smallint(5) unsigned NOT NULL,
  `def` tinyint(3) unsigned NOT NULL,
  `curse` varchar(15) CHARACTER SET latin1 NOT NULL,
  `ithdamage` smallint(5) unsigned NOT NULL,
  `ithhealth` smallint(5) unsigned NOT NULL,
  `ithdef` tinyint(3) unsigned NOT NULL,
  `item` varchar(25) CHARACTER SET latin1 NOT NULL,
  `itemenchant` varchar(4) CHARACTER SET latin1 NOT NULL,
  `same` tinyint(3) unsigned NOT NULL,
  `diff` tinyint(3) unsigned NOT NULL,
  `waveenchanttype` tinyint(3) unsigned NOT NULL,
  `waveenchantlevel` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=619 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_creaturecurse`
--

DROP TABLE IF EXISTS `sim_creaturecurse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_creaturecurse` (
  `mage` varchar(255) CHARACTER SET latin1 NOT NULL,
  `skillid` int(11) NOT NULL,
  `critid` int(11) NOT NULL,
  PRIMARY KEY (`mage`,`skillid`,`critid`),
  KEY `FKB818743438E8F40` (`critid`),
  KEY `FKB8187433700271` (`skillid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_critcurses`
--

DROP TABLE IF EXISTS `sim_critcurses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_critcurses` (
  `mage` varchar(100) NOT NULL,
  `creature` mediumint(8) unsigned NOT NULL,
  `skill` tinyint(3) unsigned NOT NULL,
  `level` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`mage`,`creature`,`skill`),
  CONSTRAINT `critcurses_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `sim_cursedmages`
--

DROP TABLE IF EXISTS `sim_cursedmages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_cursedmages` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `mage` varchar(100) NOT NULL,
  `endTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `shards` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK422009FF35827E7D` (`userid`),
  KEY `cursedmages_mage_fk` (`mage`),
  CONSTRAINT `cursedmages_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cursedmages_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_gemtracker`
--

DROP TABLE IF EXISTS `sim_gemtracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_gemtracker` (
  `id` int(11) NOT NULL,
  `userid` int(10) unsigned NOT NULL,
  `mage` varchar(100) DEFAULT NULL,
  `gem` varchar(255) CHARACTER SET latin1 NOT NULL,
  `percentage` int(11) DEFAULT NULL,
  `submitDate` datetime DEFAULT NULL,
  `expectedEndDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gemtracker_mage_fk` (`mage`),
  KEY `gemtracker_user_fk` (`userid`),
  CONSTRAINT `gemtracker_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gemtracker_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_kingdom`
--

DROP TABLE IF EXISTS `sim_kingdom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_kingdom` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `modified` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_lastseentopic`
--

DROP TABLE IF EXISTS `sim_lastseentopic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_lastseentopic` (
  `userid` int(10) unsigned NOT NULL,
  `topicID` int(11) NOT NULL,
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`,`topicID`),
  CONSTRAINT `lastseentopic_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_like`
--

DROP TABLE IF EXISTS `sim_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_like` (
  `userid` int(10) unsigned NOT NULL,
  `id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL,
  PRIMARY KEY (`userid`,`id`,`type`),
  CONSTRAINT `like_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_mage`
--

DROP TABLE IF EXISTS `sim_mage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_mage` (
  `name` varchar(100) NOT NULL,
  `cq2class` varchar(7) CHARACTER SET latin1 DEFAULT NULL,
  `level` tinyint(4) DEFAULT NULL,
  `kingdom_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_mageskill`
--

DROP TABLE IF EXISTS `sim_mageskill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_mageskill` (
  `mage` varchar(100) NOT NULL,
  `skillid` int(11) NOT NULL,
  PRIMARY KEY (`mage`,`skillid`),
  KEY `FK4A05F7AF3700271` (`skillid`),
  CONSTRAINT `mageskill_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_news`
--

DROP TABLE IF EXISTS `sim_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_news` (
  `newsID` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `content` longtext CHARACTER SET latin1,
  `user` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `directlink` varchar(100) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `newsfor` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `userid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`newsID`),
  KEY `standard` (`title`,`time`),
  KEY `FK2135F54335827E7D` (`userid`),
  CONSTRAINT `news_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=763 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_note`
--

DROP TABLE IF EXISTS `sim_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_note` (
  `id` int(11) NOT NULL,
  `userid` int(10) unsigned NOT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `note` varchar(1024) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `note_user_fk` (`userid`),
  CONSTRAINT `note_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_post`
--

DROP TABLE IF EXISTS `sim_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_post` (
  `postID` bigint(20) NOT NULL AUTO_INCREMENT,
  `post` longtext CHARACTER SET latin1 NOT NULL,
  `userid` int(10) unsigned NOT NULL,
  `topicID` int(11) NOT NULL DEFAULT '0',
  `editflag` int(11) NOT NULL DEFAULT '0',
  `edit` timestamp NULL DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`postID`),
  KEY `standard` (`topicID`,`date`),
  KEY `post_user_fk` (`userid`),
  CONSTRAINT `post_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_powerspelllog`
--

DROP TABLE IF EXISTS `sim_powerspelllog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_powerspelllog` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `forestSkill` smallint(6) DEFAULT NULL,
  `deathSkill` smallint(6) DEFAULT NULL,
  `airSkill` smallint(6) DEFAULT NULL,
  `earthSkill` smallint(6) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `amount` mediumint(9) NOT NULL,
  `result` varchar(1024) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `powerspelllog_user_fk` (`userid`),
  CONSTRAINT `powerspelllog_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_raidresult`
--

DROP TABLE IF EXISTS `sim_raidresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_raidresult` (
  `id` int(11) NOT NULL,
  `userid` int(10) unsigned NOT NULL,
  `mage` varchar(100) NOT NULL,
  `text` longtext CHARACTER SET latin1,
  `totalres` int(11) DEFAULT NULL,
  `totalpwr` int(11) DEFAULT NULL,
  `totalpb` smallint(6) DEFAULT NULL,
  `totalworkers` smallint(6) DEFAULT NULL,
  `totalgem` varchar(30) CHARACTER SET latin1 DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `incoming` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `raidresult_mage_fk` (`mage`),
  KEY `raidresult_user_fk` (`userid`),
  CONSTRAINT `raidresult_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `raidresult_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_raidresultnote`
--

DROP TABLE IF EXISTS `sim_raidresultnote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_raidresultnote` (
  `raidresultid` int(11) NOT NULL,
  `noteid` int(11) NOT NULL,
  PRIMARY KEY (`raidresultid`,`noteid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_reveal`
--

DROP TABLE IF EXISTS `sim_reveal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_reveal` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `mage` varchar(100) NOT NULL,
  `kingdom` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `mageClass` varchar(7) CHARACTER SET latin1 DEFAULT NULL,
  `unparsed` text CHARACTER SET latin1 NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` int(10) unsigned NOT NULL,
  `forestSkill` smallint(6) DEFAULT NULL,
  `deathSkill` smallint(6) DEFAULT NULL,
  `airSkill` smallint(6) DEFAULT NULL,
  `earthSkill` smallint(6) DEFAULT NULL,
  `old` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `reveal_user_fk` (`userid`),
  KEY `reveal_mage_fk` (`mage`),
  CONSTRAINT `reveal_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON UPDATE CASCADE,
  CONSTRAINT `reveal_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_reveal_crit`
--

DROP TABLE IF EXISTS `sim_reveal_crit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_reveal_crit` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `reveal_ID` bigint(20) unsigned NOT NULL,
  `sortid` tinyint(3) unsigned NOT NULL,
  `name` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `creatureClass` varchar(7) CHARACTER SET latin1 DEFAULT NULL,
  `race` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `level` tinyint(3) unsigned DEFAULT '0',
  `damage` smallint(5) unsigned DEFAULT NULL,
  `health` smallint(5) unsigned DEFAULT NULL,
  `forestDef` tinyint(3) unsigned DEFAULT NULL,
  `deathDef` tinyint(3) unsigned DEFAULT NULL,
  `airDef` tinyint(3) unsigned DEFAULT NULL,
  `earthDef` tinyint(3) unsigned DEFAULT NULL,
  `item` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `enchant` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `curse` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `type` varchar(1) CHARACTER SET latin1 DEFAULT NULL,
  `ith` smallint(5) unsigned DEFAULT NULL,
  `unparsed` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `reveal_ID` (`reveal_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1157 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_revealnote`
--

DROP TABLE IF EXISTS `sim_revealnote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_revealnote` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `revealid` int(11) NOT NULL,
  `userid` int(10) unsigned NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `note` varchar(1024) CHARACTER SET latin1 DEFAULT NULL,
  `noteid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4C4BFAFD90D8B01` (`revealid`),
  KEY `FK4C4BFAF35827E7D` (`userid`),
  CONSTRAINT `revealnote_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_sharddonation`
--

DROP TABLE IF EXISTS `sim_sharddonation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_sharddonation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mage` varchar(100) DEFAULT NULL,
  `shard` varchar(255) CHARACTER SET latin1 NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `toUserId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sharddonation_user_fk` (`toUserId`),
  KEY `sharddonation_mage_fk` (`mage`),
  CONSTRAINT `sharddonation_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON UPDATE CASCADE,
  CONSTRAINT `sharddonation_user_fk` FOREIGN KEY (`toUserId`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_simlog`
--

DROP TABLE IF EXISTS `sim_simlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_simlog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` int(10) unsigned NOT NULL,
  `ip` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `action` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `simlog_user_fk` (`userid`),
  CONSTRAINT `simlog_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1067 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_skill`
--

DROP TABLE IF EXISTS `sim_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_skill` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `text` varchar(30) CHARACTER SET latin1 NOT NULL,
  `cq2class` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `dependency` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5D1ADE181E14E90` (`dependency`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_support`
--

DROP TABLE IF EXISTS `sim_support`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_support` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fromUser` int(10) unsigned NOT NULL,
  `toMage` varchar(255) CHARACTER SET latin1 NOT NULL,
  `amount` mediumint(9) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reason` varchar(1024) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE992BF5F1D366ACC` (`fromUser`),
  CONSTRAINT `support_user_fk` FOREIGN KEY (`fromUser`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_topic`
--

DROP TABLE IF EXISTS `sim_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_topic` (
  `topicID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `starter` int(10) unsigned NOT NULL,
  `sticky` int(11) NOT NULL DEFAULT '0',
  `closed` int(11) NOT NULL DEFAULT '0',
  `clearance` int(11) NOT NULL DEFAULT '0',
  `started` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastedit` timestamp NULL DEFAULT NULL,
  `location` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`topicID`),
  KEY `standard` (`location`,`lastedit`),
  KEY `topic_user_fk` (`starter`),
  CONSTRAINT `topic_user_fk` FOREIGN KEY (`starter`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_user`
--

DROP TABLE IF EXISTS `sim_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET latin1 NOT NULL,
  `password` varchar(50) CHARACTER SET latin1 NOT NULL,
  `ulvl` bigint(11) NOT NULL DEFAULT '1',
  `cq2class` varchar(7) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `level` int(11) NOT NULL DEFAULT '0',
  `lastseen` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `imagepac` varchar(150) CHARACTER SET latin1 NOT NULL DEFAULT 'http://cq2.speedxs.nl/',
  `cq2name` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `mage` varchar(100) DEFAULT NULL,
  `email` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `qauth` varchar(20) CHARACTER SET latin1 NOT NULL DEFAULT '0',
  `travelgain` int(11) DEFAULT '0',
  `raidgain` int(11) DEFAULT '0',
  `raidloss` int(11) DEFAULT '0',
  `forestSkill` smallint(6) DEFAULT '0',
  `deathSkill` smallint(6) DEFAULT '0',
  `airSkill` smallint(6) DEFAULT '0',
  `earthSkill` smallint(6) DEFAULT '0',
  `location` varchar(1024) CHARACTER SET latin1 DEFAULT NULL,
  `birthday` timestamp NULL DEFAULT NULL,
  `disabled` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `passExpired` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `ipAddress` varchar(15) CHARACTER SET latin1 DEFAULT NULL,
  `hideOnline` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_mage_fk` (`mage`),
  CONSTRAINT `user_mage_fk` FOREIGN KEY (`mage`) REFERENCES `sim_mage` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `sim_userimage`
--

DROP TABLE IF EXISTS `sim_userimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_userimage` (
  `userid` int(10) unsigned NOT NULL,
  `text` longtext CHARACTER SET latin1,
  `stats` longtext CHARACTER SET latin1,
  PRIMARY KEY (`userid`),
  KEY `FKE5D5000035827E7D` (`userid`),
  CONSTRAINT `userimage_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_userorbs`
--

DROP TABLE IF EXISTS `sim_userorbs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_userorbs` (
  `userid` int(10) unsigned NOT NULL,
  `orbs` longtext CHARACTER SET latin1 NOT NULL,
  `gems` longtext CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`userid`),
  KEY `FK9C11EE2F35827E7D` (`userid`),
  CONSTRAINT `userorbs_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `sim_xplog`
--

DROP TABLE IF EXISTS `sim_xplog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_xplog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `xp` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `xplog_user_fk` (`userid`),
  CONSTRAINT `xplog_user_fk` FOREIGN KEY (`userid`) REFERENCES `sim_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
