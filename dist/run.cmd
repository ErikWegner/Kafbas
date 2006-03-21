@SET MINLIB=lib/log4j.jar;lib/derby.jar;lib/derbyLocale_de_DE.jar
@SET APPJAR=kafbas.jar
@SET OPTLIB=lib/bsf-2.3.0.jar;lib/bsh-1.3.0.jar;lib/itext-1.3.6.jar;lib/jcommon-1.0.1.jar;lib/jcommon-xml-1.0.1.jar;lib/jfreereport-0.8.7.jar;lib/junit.jar;lib/libfonts-0.1.4.jar;lib/pixie-0.8.4.jar;lib/poi-3.0-alpha1-20050704.jar
java -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -cp %MINLIB%;%APPJAR%;%OPTLIB% de.ewus.kafbas.Kafbas
