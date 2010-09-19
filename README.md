GWTetris
========

A Tetris clone written in Java using the Google Web Toolkit.

[![Flattr this](http://api.flattr.com/button/button-compact-static-100x17.png "Flattr this")](http://flattr.com/thing/63915/GWTetris)

This will be a port of [Jetris](http://github.com/fhd/jetris) to GWT, but it
is yet work in progress.

Building
--------

Building GWTetris is easy, just get [Apache Maven](http://maven.apache.org/)
and execute:

	mvn package

You can then deploy the created WAR file on a Java application server like
[Apache Tomcat](http://tomcat.apache.org/).

To run GWTetris in the GWT development mode, execute the following:

	mvn gwt:run

License
-------

Copyright (C) 2010 Felix H. Dahlke

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; see the file COPYING. If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
Boston, MA 02110-1301, USA.
