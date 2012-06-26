CQ2 Sim
=======
Simulator/Database for the for the free web-browser game Castle Quest 2.

Created by:
* Lennart Coopmans
* Robbin Pellegrims

LICENSE
=======
"THE WHISKY-WARE LICENSE" (Revision 1):
Lennart Coopmans and Robbin Pellegrims wrote these files. As long as you
retain this notice you can do whatever you want with this stuff. If we meet
some day, and you think this stuff is worth it, you can buy me a whisky in
return.

INSTALLATION
=======
Installation guide for JBoss AS 7.2 and MySQL 5.


Initialize database
--------
* Create a MySQL database and import the file "database.sql".
* Add a first admin user (replace username and md5 password):
	INSERT INTO sim_user  VALUES (1, 'username', 'md5 password', 7420738134810, 'Forest', 0, '2012-06-23 18:18:49', 'http://cq2.speedxs.nl/', NULL, NULL, NULL, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, NULL, 0);

Enable MySQL connector in JBoss.
--------
JBoss doesn't come with the JDBC driver for MySQL.
To add it you can follow the guide on https://community.jboss.org/wiki/DataSourceConfigurationInAS7#Installing_a_JDBC_driver_as_a_module

Add Datasource in JBoss.
--------
Described in the previous link: https://community.jboss.org/wiki/DataSourceConfigurationInAS7#Defining_the_DataSource_itself

Make sure the DataSource used in src/main/resource/be/lacerta/cq2/sim/hbn/hibernate.cfg.xml matches your configured datasource in JBoss.

Compilation
--------
CQ2 Sim uses Maven, so compiling is (or should be) as easy as executing the following command in the base directory of CQ2 Sim:

	mvn package
	
Deploy
--------
Copy the created war file to the JBoss deployment directory, and enjoy the bugs!
