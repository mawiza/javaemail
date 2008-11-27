#!/bin/bash

echo " Compiling java..."
javac -cp "lib/commons-beanutils-bean-collections.jar:lib/commons-beanutils-core.jar:lib/commons-beanutils.jar:lib/commons-betwixt-0.7.jar:lib/commons-collections-3.1.jar:lib/commons-digester-1.7.jar:lib/commons-lang-2.2.jar:lib/commons-logging-api.jar:lib/commons-logging.jar:lib/log4j-1.2.12.jar" dk/miles/messenger/message/*.java

if [ $? -eq 0 ] ; then
	echo "Running Parse"
	java -cp ".:lib/commons-beanutils-bean-collections.jar:lib/commons-beanutils-core.jar:lib/commons-beanutils.jar:lib/commons-betwixt-0.7.jar:lib/commons-collections-3.1.jar:lib/commons-digester-1.7.jar:lib/commons-lang-2.2.jar:lib/commons-logging-api.jar:lib/commons-logging.jar:lib/log4j-1.2.12.jar" dk.miles.messenger.message.Parse
else
	echo "Not running, compilation failed"
fi

echo "Done!"