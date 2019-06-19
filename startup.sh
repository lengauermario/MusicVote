echo ip-address of Server
read host

echo init of database: \(true\/false\)
read init
echo password for admin:
read password
echo folder of mp3 files:
read startFolder

echo "init=$init
password=$password
startFolder=$startFolder" > Server/JEEServer/src/main/resources/config.properties

sed "s/%%%HOST%%%/$host/g" TemplateFiles/index.html.template > Beitritt/index.html
sed "s/%%%HOST%%%/$host/g" TemplateFiles/env.template > AbspielClient/musicclient/.env
sed "s/%%%HOST%%%/$host/g" TemplateFiles/env.template > VotingClient/voting-client/.env

cd Server/JEEServer/
mvn install -DskipTests
cd ../..

#docker-compose build
#docker-compose up