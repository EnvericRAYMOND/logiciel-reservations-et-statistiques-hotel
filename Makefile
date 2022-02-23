# COMMANDES #
JAVAC = javac
# note $$ to get a single shell $
JAVAC_OPTIONS = -d build -cp build:$$CLASSPATH -implicit:none
JAVA = java
JAR = jar
EXEC_JAR = ${JAVA} -jar

# CHEMINS RELATIFS
SRC = src/fr/iutfbleau/projetIHM2021FI2
BUILD = build/fr/iutfbleau/projetIHM2021FI2
DOC = doc/fr/iutfbleau/projetIHM2021FI2

# CHOIX NOMS
JAR_IHM1 = IHM1.jar
JAR_IHM2 = IHM2.jar

# BUT PAR DEFAUT #
runihm1 : ${JAR_IHM1}
	${EXEC_JAR} ${JAR_IHM1}
runihm2 : ${JAR_IHM2}
	${EXEC_JAR} ${JAR_IHM2}
clean   :
	rm -rf ${BUILD}/* *.class
clean_jar :
	rm -rf *.jar
clean_doc :
	rm -rf doc/* 
	rm -d doc
help :
	@echo "tapez (make help_exec) pour obtenir les commandes d'execution, autrement tapez (make help_clean) pour les commandes de supression de fichiers ou (make doc) pour génerer la javadoc"
help_exec :
	@echo "tapez (make runihm1) ou (make runihm2) pour lancer l'attribution de chambres ou l'outil de consultation de l'occupation"
help_clean :
	@echo "tapez (make clean) pour supprimer les .class, (make clean_jar) pour supprimer les .jar ou (make clean_doc) pour supprimer la javadoc"

# AUTRE BUTS #
doc :
	javadoc -d doc src/fr/iutfbleau/projetIHM2021FI2/API/*.java src/fr/iutfbleau/projetIHM2021FI2/APIUTIL/*.java src/fr/iutfbleau/projetIHM2021FI2/CONTROLLER/*.java src/fr/iutfbleau/projetIHM2021FI2/VUE/*.java


# REGLES DE DEPENDANCE #


## API ##
${BUILD}/API/MonPrint.class : ${SRC}/API/MonPrint.java
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/MonPrint.java

${BUILD}/API/TypeChambre.class : ${SRC}/API/TypeChambre.java 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/TypeChambre.java

${BUILD}/API/Chambre.class : ${SRC}/API/Chambre.java \
						${BUILD}/API/TypeChambre.class \
						${BUILD}/API/MonPrint.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Chambre.java

${BUILD}/API/Client.class : ${SRC}/API/Client.java \
						${BUILD}/API/MonPrint.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Client.java

${BUILD}/API/Prereservation.class : ${SRC}/API/Prereservation.java \
						${BUILD}/API/TypeChambre.class \
						${BUILD}/API/Client.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Prereservation.java

${BUILD}/API/Reservation.class : ${SRC}/API/Reservation.java \
						${BUILD}/API/Prereservation.class \
						${BUILD}/API/Chambre.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Reservation.java

${BUILD}/API/PrereservationFactory.class : ${SRC}/API/PrereservationFactory.java \
						${BUILD}/API/Prereservation.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/PrereservationFactory.java

${BUILD}/API/ReservationFactory.class : ${SRC}/API/ReservationFactory.java \
						${BUILD}/API/Prereservation.class \
						${BUILD}/API/Reservation.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/ReservationFactory.java

## MNP ##

${BUILD}/APIUTIL/PrereservationNP.class : ${SRC}/APIUTIL/PrereservationNP.java \
							  ${BUILD}/API/Client.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/APIUTIL/PrereservationNP.java

${BUILD}/APIUTIL/ChambreNP.class : ${SRC}/APIUTIL/ChambreNP.java \
							  ${BUILD}/API/Chambre.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/APIUTIL/ChambreNP.java

${BUILD}/APIUTIL/ReservationNP.class : ${SRC}/APIUTIL/ReservationNP.java \
							  ${BUILD}/API/Reservation.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/APIUTIL/ReservationNP.java

${BUILD}/APIUTIL/PrereservationFactoryUTIL.class : ${SRC}/APIUTIL/PrereservationFactoryUTIL.java \
							  ${BUILD}/API/PrereservationFactory.class \
							  ${BUILD}/APIUTIL/ClientNP.class \
							  ${BUILD}/APIUTIL/PrereservationNP.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/APIUTIL/PrereservationFactoryUTIL.java

${BUILD}/APIUTIL/ReservationFactoryUTIL.class : ${SRC}/APIUTIL/ReservationFactoryUTIL.java \
							  ${BUILD}/APIUTIL/ChambreNP.class \
							  ${BUILD}/APIUTIL/ReservationNP.class \
							  ${BUILD}/API/ReservationFactory.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/APIUTIL/ReservationFactoryUTIL.java

${BUILD}/APIUTIL/ClientNP.class : ${SRC}/APIUTIL/ClientNP.java
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/APIUTIL/ClientNP.java


# ## JARS ##

${JAR_IHM1} : ${BUILD}/VUE/Frame1.class
	${JAR} cvfe ${JAR_IHM1} fr.iutfbleau.projetIHM2021FI2.VUE.Frame1 org/ -C build fr

${JAR_IHM2} : ${BUILD}/VUE/Frame.class
	${JAR} cvfe ${JAR_IHM2} fr.iutfbleau.projetIHM2021FI2.VUE.Frame org/ -C build fr
## VUES ##

${BUILD}/VUE/Frame.class : ${SRC}/VUE/Frame.java \
							  ${BUILD}/VUE/Occupation.class \
							  ${BUILD}/CONTROLLER/Controller1.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/Frame.java

${BUILD}/VUE/Frame1.class : ${SRC}/VUE/Frame1.java \
							  ${BUILD}/VUE/RecuperationClient.class \
							  ${BUILD}/VUE/AttributionChambre.class \
							  ${BUILD}/CONTROLLER/ControllerRecupClient.class \
							  ${BUILD}/CONTROLLER/ControllerRecupClientTableau.class \
							  ${BUILD}/CONTROLLER/ControllerAttChambre.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/Frame1.java

${BUILD}/VUE/Occupation.class : ${SRC}/VUE/Occupation.java  \
							  ${BUILD}/VUE/Question.class \
							  ${BUILD}/VUE/Graph.class \
							  ${BUILD}/APIUTIL/PrereservationFactoryUTIL.class \
							  ${BUILD}/APIUTIL/ReservationFactoryUTIL.class \
							  ${BUILD}/VUE/Bd.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/Occupation.java

${BUILD}/VUE/RecuperationClient.class : ${SRC}/VUE/RecuperationClient.java \
							  ${BUILD}/APIUTIL/PrereservationFactoryUTIL.class \
							  ${BUILD}/APIUTIL/ReservationFactoryUTIL.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/RecuperationClient.java

${BUILD}/VUE/AttributionChambre.class : ${SRC}/VUE/AttributionChambre.java \
							  ${BUILD}/APIUTIL/PrereservationFactoryUTIL.class \
							  ${BUILD}/APIUTIL/ReservationFactoryUTIL.class \
							  ${BUILD}/API/ReservationFactory.class \
							  ${BUILD}/VUE/BdAttribuerReservation.class \
							  ${BUILD}/VUE/Bd.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/AttributionChambre.java

${BUILD}/VUE/Question.class : ${SRC}/VUE/Question.java 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/Question.java

${BUILD}/VUE/Graph.class : ${SRC}/VUE/Graph.java \
							  ${BUILD}/API/ReservationFactory.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/Graph.java

## CONTROLLER ##

${BUILD}/CONTROLLER/ControllerRecupClient.class : ${SRC}/CONTROLLER/ControllerRecupClient.java
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/CONTROLLER/ControllerRecupClient.java

${BUILD}/CONTROLLER/ControllerRecupClientTableau.class : ${SRC}/CONTROLLER/ControllerRecupClientTableau.java
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/CONTROLLER/ControllerRecupClientTableau.java

${BUILD}/CONTROLLER/ControllerAttChambre.class : ${SRC}/CONTROLLER/ControllerAttChambre.java
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/CONTROLLER/ControllerAttChambre.java

${BUILD}/CONTROLLER/Controller1.class : ${SRC}/CONTROLLER/Controller1.java
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/CONTROLLER/Controller1.java

## BD ##

${BUILD}/VUE/Bd.class : ${SRC}/VUE/Bd.java \
							  ${BUILD}/APIUTIL/ClientNP.class \
							  ${BUILD}/APIUTIL/ReservationNP.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/Bd.java

${BUILD}/VUE/BdAttribuerReservation.class : ${SRC}/VUE/BdAttribuerReservation.java
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/VUE/BdAttribuerReservation.java
