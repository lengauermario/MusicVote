echo ip-address of Server
read host

echo init of database: \(true\/false\)
read init
echo password for admin:
read password
echo folder of mp3 files:
read startFolder
echo WLAN SSID:
read ssid
echo WLAN password:
read wlan_pwd

echo "init=$init
password=$password
startFolder=$startFolder" > Server/JEEServer/src/main/resources/config.properties

sed "s/%%%HOST%%%/$host/g; s/%%%SSID%%%/$ssid/g; s/%%%WLAN_PWD%%%/$wlan_pwd/g"  TemplateFiles/index.html.template > Beitritt/index.html
sed "s/%%%HOST%%%/$host/g" TemplateFiles/env.template > AbspielClient/musicclient/.env
sed "s/%%%HOST%%%/$host/g" TemplateFiles/env.template > VotingClient/voting-client/.env

cd Server/JEEServer/
mvn install -DskipTests
cd ../..

docker-compose build --parallel
docker-compose up